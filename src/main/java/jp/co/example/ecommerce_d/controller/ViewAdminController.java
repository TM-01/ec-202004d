package jp.co.example.ecommerce_d.controller;

import java.io.IOException;
import java.util.Base64;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import jp.co.example.ecommerce_d.domain.Item;
import jp.co.example.ecommerce_d.form.RegisterItemForm;
import jp.co.example.ecommerce_d.service.RegisterItemService;
import jp.co.example.ecommerce_d.service.ViewItemDetailService;
import jp.co.example.ecommerce_d.service.ViewItemListService;

/**
 * 管理者が操作できる情報を表示するコントローラーです.
 * 
 * @author kazuteru.takahashi
 *
 */
@Controller
@RequestMapping("/admin")
public class ViewAdminController {

	@Autowired
	private RegisterItemService registerItemService;

	@Autowired
	private ViewItemDetailService viewItemDetailService;

	@ModelAttribute
	public RegisterItemForm setUpEditItemForm() {
		return new RegisterItemForm();
	}

	/**
	 * 管理者用画面を表示します.
	 * 
	 * @return 管理者画面
	 */
	@RequestMapping("")
	public String admin() {
		return "admin/management";
	}

	/**
	 * 商品登録画面を表示します.
	 * 
	 * @return 商品登録画面
	 */
	@RequestMapping("/registerItem")
	public String registerItem() {
		return "admin/register_item";
	}

	@RequestMapping("/editItemList")
	public String editItem(String fuzzyName, String sort, Model model) {
		Map<String, String> sortMap = new LinkedHashMap<>();
		sortMap.put("cheap", "安い順");
		sortMap.put("expensive", "高い順");
		sortMap.put("nameAsc", "名前の昇順");
		sortMap.put("nameDesc", "名前の降順");
		sortMap.put("popularity", "人気度");
		model.addAttribute("sortMap", sortMap);

		if (fuzzyName == null) {
			fuzzyName = "";
		}
		if (sort == null) {
			sort = "cheap";
		}
		List<List<Item>> itemLists = registerItemService.showItemListAll(fuzzyName, sort);
		if (itemLists.size() == 0) {
			itemLists = registerItemService.showItemListAll("", sort);
			model.addAttribute("notFoundMessage", "該当する商品はありませんでした。");
		}
		model.addAttribute("sort", sort);
		model.addAttribute("itemLists", itemLists);
		return "admin/item_list";
	}

	/**
	 * 商品詳細を表示する.
	 * 
	 * @param itemId 商品ID
	 * @param model  リクエストスコープ
	 * @return 商品詳細画面
	 */
	@RequestMapping("/editItemDetail")
	public String editItemDetail(String itemId, Model model) {
		Item item = viewItemDetailService.showDetail(itemId);
		model.addAttribute("itemDetail", item);
		return "admin/item_detail";
	}

	/**
	 * 商品を追加します.
	 * 
	 * @param form   商品情報
	 * @param result バリデーションエラー
	 * @return 管理者画面
	 */
	@RequestMapping("/addItem")
	public String addItem(@Validated RegisterItemForm form, BindingResult result) {
		String name = registerItemService.findByName(form.getName());
		if (name != null) {
			FieldError fieldError = new FieldError(result.getObjectName(), "name", "その名前の商品は既に登録されています");
			result.addError(fieldError);
		}
		if (form.getImagePath().isEmpty()) {
			FieldError fieldError = new FieldError(result.getObjectName(), "imagePath", "画像ファイルを選択してください");
			result.addError(fieldError);
		}
		try {
			String[] fileType = form.getImagePath().getContentType().split("/");
			if (!"image".equals(fileType[0])) {
				FieldError fieldError = new FieldError(result.getObjectName(), "imagePath", "画像ファイルを選択してください");
				result.addError(fieldError);
			}
		} catch (Exception e) {
			FieldError fieldError = new FieldError(result.getObjectName(), "imagePath", "不正なファイルを選択してください");
			result.addError(fieldError);
		}
		if (result.hasErrors()) {
			return registerItem();
		}
		Item item = new Item();
		BeanUtils.copyProperties(form, item);
		item.setPriceM(Integer.parseInt(form.getPriceM()));
		item.setPriceL(Integer.parseInt(form.getPriceL()));
		byte[] imageByte;
		try {
			imageByte = Base64.getEncoder().encode(form.getImagePath().getBytes());
			item.setImagePath("data:" + form.getImagePath().getContentType() + ";base64," + new String(imageByte));
		} catch (IOException e) {
			e.printStackTrace();
		}
		int MaxId;
		try {
			MaxId = registerItemService.findMaxId() + 1;
		} catch (NullPointerException e) {
			MaxId = 0;
		}
		item.setId(MaxId);
		registerItemService.addItem(item);
		return "redirect:/admin";
	}

	/**
	 * 商品を更新します.
	 * 
	 * @param id       ID
	 * @param itemName 元商品名
	 * @param form     商品情報
	 * @param result   ヴァリエーションエラー
	 * @param model    リクエストスコープ
	 * @return 管理者画面
	 */
	@RequestMapping("/editItem")
	public String editItem(String id, String itemName, @Validated RegisterItemForm form, BindingResult result,
			Model model) {
		boolean isImageUpdate = true;
		String name = registerItemService.findByName(form.getName());
		if (name != null) {
			if (!name.equals(itemName)) {
				FieldError fieldError = new FieldError(result.getObjectName(), "name", "その名前の商品は既に登録されています");
				result.addError(fieldError);
			}
		}
		if (form.getImagePath().isEmpty()) {
			isImageUpdate = false;
		} else {
			String[] fileType = form.getImagePath().getContentType().split("/");
			if (!"image".equals(fileType[0])) {
				FieldError fieldError = new FieldError(result.getObjectName(), "imagePath", "画像を選択してください");
				result.addError(fieldError);
			}
		}
		if (result.hasErrors()) {
			return editItemDetail(id, model);
		}
		Item item = new Item();
		BeanUtils.copyProperties(form, item);
		item.setPriceM(Integer.parseInt(form.getPriceM()));
		item.setPriceL(Integer.parseInt(form.getPriceL()));
		byte[] imageByte;
		try {
			imageByte = Base64.getEncoder().encode(form.getImagePath().getBytes());
			item.setImagePath("data:" + form.getImagePath().getContentType() + ";base64," + new String(imageByte));
		} catch (IOException e) {
			return "redirect:/admin";
		}

		try {
			item.setId(Integer.parseInt(id));
			registerItemService.updateItem(item, isImageUpdate);
		} catch (NumberFormatException e) {
			return "redirect:/admin";
		}
		return "redirect:/admin";
	}

	@RequestMapping("/switchItem")
	public String switchItem(String id, String status) {
		boolean isShowItem;
		if ("true".equals(status)) {
			isShowItem = true;
		} else if ("false".equals(status)) {
			isShowItem = false;
		} else {
			return "redirect:/admin";
		}
		try {
			registerItemService.updateItemStatus(Integer.parseInt(id), isShowItem);
		}catch (Exception e) {
			return "redirect:/admin";
		}
		return "redirect:/admin";
	}
}
