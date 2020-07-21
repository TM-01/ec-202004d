package jp.co.example.ecommerce_d.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import jp.co.example.ecommerce_d.service.DeleteCartService;

/**
 * ショッピングカートの削除のコントローラ.
 * 
 * @author yu.konishi
 *
 */
@Controller
@RequestMapping("/deleteCart")
public class DeleteCartController {

	@Autowired
	private HttpSession session;

	/**
	 * ショッピングカートの削除のサービス
	 */
	@Autowired
	private DeleteCartService deleteCartService;

	/**
	 * ショッピングカート内の注文商品を削除する.
	 * 
	 * @param orderItemId 注文商品ID
	 * @return ショッピングカート画面
	 */
	@RequestMapping("")
	public String deleteCart(Integer orderItemId) {
		Integer userId = (Integer) session.getAttribute("userId");
		deleteCartService.deleteCartItem(orderItemId, userId);
		return "redirect:/showCartList";
	}

}
