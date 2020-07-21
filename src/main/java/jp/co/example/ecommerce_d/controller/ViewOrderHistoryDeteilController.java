package jp.co.example.ecommerce_d.controller;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import jp.co.example.ecommerce_d.domain.Order;
import jp.co.example.ecommerce_d.domain.OrderItem;
import jp.co.example.ecommerce_d.service.ViewOrderHistoryService;

/**
 * 注文情報詳細を表示するためのコントローラクラス.
 * 
 * @author hikaru.tsuda
 *
 */
@Controller
@RequestMapping("")
public class ViewOrderHistoryDeteilController {

	@Autowired
	private ViewOrderHistoryService servise;

	/**
	 * ユーザIDから注文IDを取得して注文画面詳細を表示するメソッド.
	 * 
	 * @param loginuser ユーザ情報
	 * @param model
	 * @param orderId   注文ID
	 * @return 注文詳細画面
	 */
	@RequestMapping("/showOrderHistoryDetail")
	public String showOrderHistoryDetail(Model model, Integer orderId) {

		List<OrderItem> orderItemList = servise.getOrderItemListByOrderId(orderId);
		Order order = servise.getOrderbyOrderId(orderId);

		model.addAttribute("order", order);
		model.addAttribute("orderItemList", orderItemList);

		System.out.println(orderItemList.toString());
		return "order_history_detail";
	}

	/**
	 * 注文をキャンセルして注文履歴画面に遷移するメソッド.
	 *
	 * @param orderId 注文ID
	 * @return
	 */

	@RequestMapping("/orderCansel")
	public String orderCansel(Integer orderId) {
		servise.canselByOrderId(orderId);	
		return "redirect:/showOrderHistory";
	}

}
