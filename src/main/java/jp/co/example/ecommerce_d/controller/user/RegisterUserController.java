package jp.co.example.ecommerce_d.controller.user;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import jp.co.example.ecommerce_d.domain.User;
import jp.co.example.ecommerce_d.form.RegisterUserForm;
import jp.co.example.ecommerce_d.service.UserService;

/**
 * 新規ユーザ登録するコントローラ.
 * 
 * @author hikaru.tsuda
 *
 */
@Controller
@RequestMapping("/showUserRegister")
public class RegisterUserController {

	@Autowired
	private UserService service;

	@ModelAttribute
	public RegisterUserForm setUpRegisterUserForm() {
		return new RegisterUserForm();
	}

	/**
	 * 登録画面にフォワード
	 * 
	 * @return
	 */
	@RequestMapping("")
	public String index(Model model) {
		return "register_user";
	}

	/**
	 * ユーザを新規登録してログイン画面にフォワードするメソッド.
	 * 
	 * @param form   入力されたユーザ情報
	 * @param result
	 * @return ログイン画面
	 */
	@RequestMapping("/register")
	public String register(@Validated RegisterUserForm form, BindingResult result, Model model) {
		/*
		 * // パスワード不一致の場合 System.out.println(form); try { if
		 * (!form.getPassword().equals(form.getConPassword())) { FieldError fieldError =
		 * new FieldError(result.getObjectName(), "conPassword",
		 * "パスワードと確認用パスワードが不一致です"); result.addError(fieldError); } } catch
		 * (NullPointerException e) { FieldError fieldError = new
		 * FieldError(result.getObjectName(), "conPassword", "パスワードと確認用パスワードが不一致です");
		 * result.addError(fieldError);
		 * 
		 * }
		 */
		// メールアドレス重複の場合
		User user = service.findByEmail(form.getEmail());
		if (user != null) {
			FieldError fieldError2 = new FieldError(result.getObjectName(), "email", "そのメールアドレスは既に登録されています");
			result.addError(fieldError2);
		}

		// エラーが一つでもある場合
		if (result.hasErrors()) {
			return index(model);
		}

		// エラーがなければログイン画面にリダイレクト
		service.insert(form);
		return "redirect:/loginUser";
	}

	/**
	 * パスワードと確認用パスワードの一致チェックメソッド.
	 * 
	 * @param password パスワード
	 * @param conPassword　確認用パスワード
	 * @return 確認分
	 */
	@ResponseBody
	@RequestMapping(value = "/check", method = RequestMethod.GET)
	public Map<String, String> check(String password, String conPassword) {
		Map<String, String> map = new HashMap<>();

		// 文字数チェック
		String robustnessMessage = null;
		if (password.length() < 8) {
			robustnessMessage = "パスワードは8文字以上で入力してください";
		} else if (password.length() > 16) {
			robustnessMessage = "パスワードは16文字以下で入力してください";
		}
		map.put("robustnessMessage", robustnessMessage);

		// パスワード一致チェック
		String disagreementMessage = null;
		if (password.equals(conPassword)) {
			disagreementMessage = null;
		} else {
			disagreementMessage = "パスワードが一致していません";
		}
		map.put("disagreementMessage", disagreementMessage);

		System.out.println(password + ":" + conPassword);

		return map;
	}

}
