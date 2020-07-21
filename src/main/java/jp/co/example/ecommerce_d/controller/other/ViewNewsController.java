package jp.co.example.ecommerce_d.controller.other;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
/**
 * お知らせを表示するためのコントローラーです.
 * 
 * @author kazuteru.takahashi
 *
 */
@Controller
@RequestMapping("/news")
public class ViewNewsController {
	
	/**
	 * お知らせ一覧を表示します.
	 * 
	 * @return　お知らせ一覧ページ
	 */
	@RequestMapping("")
	public String news() {
		return "news/news";
	}
	
	/**
	 * 配送に関するお知らせページを表示します.
	 * 
	 * @return　配送に関するお知らせページ
	 */
	@RequestMapping("/delivery")
	public String delivery() {
		return "news/delivery";
	}
	
	/**
	 * 新型コロナウイルス感染症に関するお知らせを表示します.
	 * 
	 * @return 新型コロナウイルス感染症についてのお知らせページ
	 */
	@RequestMapping("/covid-19")
	public String covid19() {
		return "news/covid19";
	}
	

	/**
	 * サービス終了に関するお知らせを表示します.
	 * 
	 * @return サービス終了に関するお知らせページ
	 */
	@RequestMapping("/service")
	public String holiday() {
		return "news/service";
	}
	
	/**
	 * ラクラクアロハを装った迷惑メールに関するお知らせを表示します.
	 * 
	 * @return　迷惑メールについてのお知らせページ
	 */
	@RequestMapping("/spamMail")
	public String spamMail() {
		return "news/spamMail";
	}
}
