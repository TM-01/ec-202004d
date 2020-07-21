package jp.co.example.ecommerce_d.controller.other;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * サイト情報を表示するコントローラ.
 * 
 * @author taira.matsuta
 *
 */
@Controller
@RequestMapping("")
public class ViewAboutController {

	/**
	 * サイト情報を表示します.
	 * 
	 * @return サイト情報
	 */
	@RequestMapping("/about")
	public String about() {
		return "about/about";
	}
}
