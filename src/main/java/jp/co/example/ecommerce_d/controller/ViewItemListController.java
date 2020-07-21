package jp.co.example.ecommerce_d.controller;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import jp.co.example.ecommerce_d.domain.Item;
import jp.co.example.ecommerce_d.service.ViewItemListService;
import net.arnx.jsonic.JSON;

/**
 * 商品一覧を表示するコントローラ.
 * 
 * @author taira.matsuta
 *
 */
@Controller
@RequestMapping("/")
public class ViewItemListController {

	@Autowired
	private ViewItemListService viewItemListService;
	
	/**
	 * 商品一覧画面を表示する.
	 * 
	 * @param fuzzyName 名前（曖昧可）
	 * @param sort　並び順
	 * @param model　リクエストスコープ
	 * @return　商品一覧
	 */
	@RequestMapping("")
	private String showItemList(String fuzzyName, String sort, Model model) {
		Map<String, String> sortMap = new LinkedHashMap<>();
		sortMap.put("cheap", "安い順");
		sortMap.put("expensive", "高い順");
		sortMap.put("nameAsc", "名前の昇順");
		sortMap.put("nameDesc", "名前の降順");
		sortMap.put("popularity", "人気度");
		model.addAttribute("sortMap", sortMap);
		
		if(fuzzyName == null) {
			fuzzyName = "";
		}
		if(sort == null) {
			sort = "cheap";
		}
		List<List<Item>> itemLists = viewItemListService.showItemList(fuzzyName, sort);
		if(itemLists.size() == 0) {
			itemLists = viewItemListService.showItemList("", sort);
			model.addAttribute("notFoundMessage", "該当する商品はありませんでした。");
		}
		model.addAttribute("sort", sort);
		model.addAttribute("itemLists", itemLists);
		return "item_list";
	}
	
	@ResponseBody
	@RequestMapping(value = "/autocomplete", method = RequestMethod.GET)
	public String getAutoComplete() {
		List<String> nameList = viewItemListService.showAllName();
		return JSON.encode(nameList);
	}
	
}
