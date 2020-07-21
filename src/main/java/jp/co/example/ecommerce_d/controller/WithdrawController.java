package jp.co.example.ecommerce_d.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import jp.co.example.ecommerce_d.service.WithdrawService;

/**
 * ユーザ退会のコントローラ.
 * 
 * @author yu.konishi
 *
 */
@Controller
@RequestMapping("")
public class WithdrawController {

	/**
	 * ユーザ退会のサービス
	 */
	@Autowired
	private WithdrawService withdrawService;
	/**
	 * セッションスコープ
	 */
	@Autowired
	private HttpSession session;

	/**
	 * 退会画面を表示する.
	 * 
	 * @return 退会画面
	 */
	@RequestMapping("/withdraw")
	public String withdraw() {
		return "withdraw_user";
	}

	/**
	 * 退会処理を行う.
	 * 
	 * @param choice 選択
	 * @return 商品一覧画面
	 */
	@RequestMapping("/execute")
	public String execute(Integer choice) {
		Integer loginId = (Integer) session.getAttribute("loginId");
		if (choice != 1) {
			return "redirect:/";
		}
		withdrawService.withdrawExecute(loginId);
		Integer userId = (Integer) session.getAttribute("userId");
		if (userId != null) {
			if (loginId != null && loginId == userId) {
				session.removeAttribute("userId");
			}
		}
		session.removeAttribute("loginId");
		session.removeAttribute("userName");
		return "redirect:/";
	}

}
