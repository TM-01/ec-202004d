package jp.co.example.ecommerce_d.controller.login_controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import jp.co.example.ecommerce_d.domain.LoginUser;
import jp.co.example.ecommerce_d.domain.User;
import jp.co.example.ecommerce_d.service.UserService;

/**
 * ログイン・ログアウト.
 * 
 * @author tatsuro.miyazaki
 *
 */

@Controller
@RequestMapping("/loginUser")
public class LoginController {
	
	@Autowired
	 private UserService userService;
	
	@Autowired
	private HttpSession session;
	
	/**
	 * ログインページへ遷移、遷移する前の情報をセッションに格納.
	 * @param pageinfo 遷移前のページ情報
	 * @return
	 */
	@RequestMapping("")
	public String login(String pageinfo) {
		System.out.println(pageinfo);
		if(!(pageinfo==null)) {
			session.setAttribute("backPage", pageinfo);
		}
		return "login";
	}
	
	/**
	 * ログイン成功時の処理.
	 * @param redirectAttributes
	 * @param model
	 * @param loginUSer ログインしたユーザの情報.
	 * @return ログイン前のページへ遷移、ログアウトしている場合は商品一覧へ遷移
	 */
	@RequestMapping("/succes")
	public String item(RedirectAttributes redirectAttributes, Model model, @AuthenticationPrincipal LoginUser loginUSer) {
		User user = userService.findByEmail(loginUSer.getUsername());
		redirectAttributes.addFlashAttribute("loginId", user.getId());// ユーザのIDをスコープに格納
		session.setAttribute("loginId", user.getId());
		session.setAttribute("userName", user.getName());
		if(session.getAttribute("backPage")==null) {
			session.setAttribute("backPage", "");
		}
		return "redirect:/merge";
	}
	
	@RequestMapping("/faile")
	public String faile(Model model) {
		model.addAttribute("errormessage","メールアドレスかパスワードが不正です");
		
		return "login";
	}
	
	@RequestMapping("/exit")
	public String logout(Model model) {
		Integer loginId = (Integer) session.getAttribute("loginId");
		Integer userId = (Integer) session.getAttribute("userId");
		if(userId != null) {
			if(loginId != null && loginId == userId) {
				session.removeAttribute("userId");
			}
		}
		session.removeAttribute("loginId");
		session.removeAttribute("userName");
		
		return "redirect:/loginUser";
	}
}
