package jp.co.example.ecommerce_d.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jp.co.example.ecommerce_d.domain.Item;
import jp.co.example.ecommerce_d.repository.ItemRepository;

/**
 * 商品情報を操作するサービス.
 * 
 * @author taira.matsuta
 *
 */
@Service
@Transactional
public class ViewItemListService {

	@Autowired
	private ItemRepository itemRepository;

	/**
	 * 商品名から商品一覧を検索する.
	 * 
	 * @param fuzzyName 商品名（曖昧可）
	 * @param　sort　並び順
	 * @return 商品一覧
	 */
	public List<List<Item>> showItemList(String fuzzyName, String sort) {
		List<Item> itemList = itemRepository.findByName(fuzzyName, sort);
		List<Item> items = new ArrayList<>();
		List<List<Item>> itemLists = new ArrayList<List<Item>>();
		for (int i = 1; i <= itemList.size(); i++) {
			items.add(itemList.get(i - 1));
			if (i % 3 == 0) {
				itemLists.add(items);
				items = new ArrayList<>();
			}
		}
		if (0 < items.size() && items.size() < 3) {
			itemLists.add(items);
		}
		return itemLists;
	}
	
	/**
	 * 商品名を全件検索する.
	 * 
	 * @return 商品名
	 */
	public List<String> showAllName() {
		List<String> nameList = itemRepository.findAllName();
		return nameList;
	}
}
