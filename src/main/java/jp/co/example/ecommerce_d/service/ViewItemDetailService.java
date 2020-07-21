package jp.co.example.ecommerce_d.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jp.co.example.ecommerce_d.domain.Item;
import jp.co.example.ecommerce_d.repository.ItemRepository;
import jp.co.example.ecommerce_d.repository.ToppingRepository;

/**
 * 商品詳細のサービス.
 * 
 * @author yu.konishi
 *
 */
@Service
@Transactional
public class ViewItemDetailService {

	/**
	 * 商品のリポジトリ
	 */
	@Autowired
	private ItemRepository itemRepository;
	/**
	 * トッピングのリポジトリ
	 */
	@Autowired
	private ToppingRepository toppingRepository;

	/**
	 * 商品の詳細を取得する.
	 * 
	 * @param itemId 商品ID
	 * @return 商品情報
	 */
	public Item showDetail(String itemId) {
		int id = Integer.parseInt(itemId);
		Item item = itemRepository.load(id);
		item.setToppingList(toppingRepository.findAll());
		return item;
	}
	
}
