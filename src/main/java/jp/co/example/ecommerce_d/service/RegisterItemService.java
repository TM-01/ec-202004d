package jp.co.example.ecommerce_d.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jp.co.example.ecommerce_d.domain.Item;
import jp.co.example.ecommerce_d.repository.ItemRepository;

/**
 * 商品を登録するサービスです.
 * 
 * @author kazuteru.takahashi
 *
 */
@Service
@Transactional
public class RegisterItemService {
	@Autowired
	private ItemRepository itemRepository;

	/**
	 * 名前と一致する情報があるか検索をします. 名前があるなら商品名,ないならnullを返します
	 * 
	 * @param name 名前
	 * @return 商品名(0件ならnullを返す)
	 */
	public String findByName(String name) {
		List<String> itemList = itemRepository.findByName(name);
		if (itemList.size() == 0) {
			return null;
		}
		return itemList.get(0);
	}

	/**
	 * 商品を追加します.
	 * 
	 * @param item 追加する商品
	 */
	synchronized public void addItem(Item item) {
		itemRepository.insert(item);
	}

	synchronized public int findMaxId() {
		return itemRepository.findMaxId();
	}

	/**
	 * 商品情報を更新します.
	 * 
	 * @param item 商品情報
	 */
	synchronized public void updateItem(Item item, boolean updateFlag) {
		itemRepository.updateItem(item, updateFlag);
	}
	
	/**
	 * 商品名から商品一覧を検索する.
	 * 
	 * @param fuzzyName 商品名（曖昧可）
	 * @param　sort　並び順
	 * @return 商品一覧
	 */
	public List<List<Item>> showItemListAll(String fuzzyName, String sort) {
		List<Item> itemList = itemRepository.findByNameForAll(fuzzyName, sort);
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
	 * 商品の削除されているかどうかのステータスの更新を行います.
	 * 
	 * @param id ID
	 * @param deletedFlag 削除されているかどうか(true:削除 false:未削除)
	 */
	public void updateItemStatus(int id, boolean deletedFlag) {
		itemRepository.updateItemDeleted(id, deletedFlag);
	}
}
