package jp.co.example.ecommerce_d.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jp.co.example.ecommerce_d.domain.Item;
import jp.co.example.ecommerce_d.domain.Order;
import jp.co.example.ecommerce_d.domain.OrderItem;
import jp.co.example.ecommerce_d.repository.ItemRepository;
import jp.co.example.ecommerce_d.repository.OrderItemRepository;
import jp.co.example.ecommerce_d.repository.OrderRepository;

/**
 * メール送信のサービスクラス.
 * @author tatsuro.miyazaki
 *
 */
@Service
@Transactional
public class MailService {
	@Autowired
	private OrderRepository orderRepository;
	
	@Autowired
	private OrderItemRepository orderItemRepository;
	
	@Autowired
	private ItemRepository itemRepository;
	
	/**
	 * ユーザIDで注文情報を取得する.
	 * 
	 * @param userId ユーザID
	 * @return 注文情報(ない場合はnullを返す)
	 */
	public List<Order> findByUserIdAndStatus(String id){
		return orderRepository.findByUserIdAndStatus(Integer.parseInt(id), 0);
	}
	
	/**
	 * 注文IDで注文商品一覧を取得する.
	 * 
	 * @param orderId 注文ID
	 * @return 注文商品一覧、なかった場合には空のリストを返す
	 */
	public List<OrderItem> findByOrderIdAndStatus(String orderid){
		return orderItemRepository.findByOrderIdAndStatus(Integer.parseInt(orderid), 0);
	}
	
	/**
	 * 商品IDから商品情報を取得する.
	 * 
	 * @param id 商品ID
	 * @return 商品情報
	 */
	public Item findItemName(Integer itemId) {
		return itemRepository.load(itemId);
	}

}
