package jp.co.example.ecommerce_d.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
/**
 * 注文完了のコントローラ.
 * 
 * @author yu.konishi
 *
 */
@Controller
@RequestMapping("/toFinish")
public class OrderFinishController {

	/**
	 * 注文完了画面を表示する.
	 * @return 注文完了画面
	 */
	@RequestMapping("")
	public String toFinish() {
		return "order_finished";
	}

}
