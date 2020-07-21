package jp.co.example.ecommerce_d.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jp.co.example.ecommerce_d.domain.Item;
import jp.co.example.ecommerce_d.form.AddOrderForm;
import jp.co.example.ecommerce_d.service.ViewItemDetailService;

/**
 * 商品詳細のコントローラ.
 * 
 * @author yu.konishi
 *
 */
@Controller
@RequestMapping("")
public class ViewItemDetailController {

	/**
	 * 商品詳細のサービス
	 */
	@Autowired
	private ViewItemDetailService viewItemDetailService;
	
	@ModelAttribute
	public AddOrderForm setUpAddOrderForm() {
		AddOrderForm form = new AddOrderForm();
		form.setSize('M');
		return form;
	}

	/**
	 * 商品詳細を表示する.
	 * 
	 * @param itemId 商品ID
	 * @param model  リクエストスコープ
	 * @return 商品詳細画面
	 */
	@RequestMapping("/showItemDetail")
	public String showDetail(String itemId, Model model) {
		Item item = viewItemDetailService.showDetail(itemId);
		model.addAttribute("itemDetail", item);
		return "item_detail";
	}

	@RequestMapping("/addCart")
	public String cartAdd(AddOrderForm form,RedirectAttributes flash) {
		flash.addFlashAttribute("addOrder",form);
		return "redirect:/addOrder";
	}

}
