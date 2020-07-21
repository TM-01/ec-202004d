package jp.co.example.ecommerce_d.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import jp.co.example.ecommerce_d.domain.Order;
import jp.co.example.ecommerce_d.service.ViewCartService;
/**
 * ショッピングカートの表示のコントローラ.
 * 
 * @author yu.konishi
 *
 */
@Controller
@RequestMapping("/showCartList")
public class ViewCartController {

	/**
	 * セッションスコープ
	 */
	@Autowired
	private HttpSession session;
	/**
	 * ショッピングカートの表示のサービス
	 */
	@Autowired
	private ViewCartService viewCartService;
	
	/**
	 * ショッピングカートの中身を表示する.
	 * @param model リクエストスコープ
	 * @return ショッピングカート画面
	 */
	@RequestMapping("")
	public String showCartList(Model model) {
		Integer userId = (Integer)session.getAttribute("userId");
		if(userId == null) {
			userId = viewCartService.makeRandomUserId();
			session.setAttribute("userId", userId);
		}
		Order order = viewCartService.viewCart(userId);
		model.addAttribute("order",order);
		if(order.getOrderItemList().isEmpty()) {
			model.addAttribute("error","カートに商品がありません");
		}
		return "cart_list";
	}
	
}
