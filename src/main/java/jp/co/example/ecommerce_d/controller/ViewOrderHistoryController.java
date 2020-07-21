package jp.co.example.ecommerce_d.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import jp.co.example.ecommerce_d.domain.Order;
import jp.co.example.ecommerce_d.service.ViewOrderHistoryService;

/**
 * 注文履歴を表示するコントローラークラス.
 * 
 * @author hikaru.tsuda
 *
 */
@Controller
@RequestMapping("")
public class ViewOrderHistoryController {

	@Autowired
	private ViewOrderHistoryService service;

	@Autowired
	private HttpSession session;

	/**
	 * ユーザIDから注文情報リストを格納して注文履歴画面にフォワード.
	 * 
	 * @param userID ユーザID
	 * @param model  注文履歴リスト
	 * @return 注文履歴画面
	 */
	@RequestMapping("/showOrderHistory")
	public String showOrderHistory(Model model) {
		Integer userId = (Integer) session.getAttribute("loginId");
		if (userId == null) {
			session.setAttribute("backPage", "showOrderHistory");
			return "redirect:/loginUser";
		}
		List<Order> orderList = service.findByUserId(userId);
		System.out.println(orderList);
		if (orderList.size() == 0) {
			model.addAttribute("notFoundMessage", "注文履歴はありません");
		}
		// サムネ画像を1枚もってきてパスをOrderに追加
		for (Order order : orderList) {
			order.setThumbPath(service.findItemPathByOrderId(order.getId()));
		}

		model.addAttribute("orderList", orderList);

		return "order_history";
	}

}
