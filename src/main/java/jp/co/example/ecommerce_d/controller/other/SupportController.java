package jp.co.example.ecommerce_d.controller.other;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import jp.co.example.ecommerce_d.form.SupportForm;

/**
 * お問い合わせフォームを表示するコントローラ.
 * 
 * @author taira.matsuta
 *
 */
@Controller
@RequestMapping("/support")
public class SupportController {
	
	@ModelAttribute
	private SupportForm setUpSupportForm() {
		return new SupportForm();
	}

	/**
	 * お問い合わせフォームを表示する.
	 * 
	 * @return　お問い合わせフォーム
	 */
	@RequestMapping("")
	public String index() {
		return "support/support_form";
	}
	
	/**
	 * フォームに入力された内容を操作する.
	 * 
	 * @return　完了画面にリダイレクト
	 */
	@RequestMapping("/submit")
	public String submit(
			@Validated SupportForm form
			, BindingResult result) {
		if(result.hasErrors()) {
			return index();
		}
		return "redirect:/support/finished";
	}
	
	/**
	 * 完了画面を表示する.
	 * 
	 * @return 完了画面
	 */
	@RequestMapping("/finished")
	public String finished() {
		return "support/finished";
	}
}
