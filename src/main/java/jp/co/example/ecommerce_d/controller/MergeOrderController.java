
package jp.co.example.ecommerce_d.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import jp.co.example.ecommerce_d.common.Status;
import jp.co.example.ecommerce_d.domain.Order;
import jp.co.example.ecommerce_d.domain.OrderItem;
import jp.co.example.ecommerce_d.service.MergeOrderService;

/**
 * カートの中身をマージするコントローラ.
 *
 * @author taira.matsuta
 */
@Controller
@RequestMapping("")
public class MergeOrderController {

	@Autowired
	private MergeOrderService mergeOrderService;

	@Autowired
	private HttpSession session;

	/**
	 * カートの中身をマージする.
	 *
	 * @return セッションスコープにあるurlにリダイレクト
	 */
	@RequestMapping("/merge")
	public String merge() {
		// ゲストORユーザID取得
		Integer userId = (Integer) session.getAttribute("userId");
		// ログインユーザID取得
		Integer loginId = (Integer) session.getAttribute("loginId");// 必ず見つかる

		if (userId == null) {
			userId = -1;
		}
		List<Order> userOrderList = mergeOrderService.showOrderList(userId, 0);
		List<Order> loginOrderList = mergeOrderService.showOrderList(loginId, 0);
		if (loginOrderList == null) {
			mergeOrderService.createOrder(loginId);
		}
		
		loginOrderList = mergeOrderService.showOrderList(loginId, 0);
		int loginOrderId = loginOrderList.get(0).getId();
		if (userOrderList != null) {
			int userOrderId = userOrderList.get(0).getId();
			List<OrderItem> userOrderItemList = mergeOrderService.showOrderItemList(userOrderId, 0);
			for (OrderItem userOrderItem : userOrderItemList) {
				userOrderItem.setOrderId(loginOrderId);
				mergeOrderService.updateOrderItemOfOrderId(userOrderItem);
			}
			mergeOrderService.updateStatus(userOrderId, Status.INVALID.getStatusId());
		}
		session.setAttribute("userId", loginId);
		String url = (String) session.getAttribute("backPage");
		session.removeAttribute("backPage");
		return "redirect:/" + url;
	}
}
