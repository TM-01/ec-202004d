package jp.co.example.ecommerce_d.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jp.co.example.ecommerce_d.domain.Item;
import jp.co.example.ecommerce_d.domain.Order;
import jp.co.example.ecommerce_d.domain.OrderItem;
import jp.co.example.ecommerce_d.domain.OrderTopping;
import jp.co.example.ecommerce_d.repository.ItemRepository;
import jp.co.example.ecommerce_d.repository.OrderItemRepository;
import jp.co.example.ecommerce_d.repository.OrderRepository;
import jp.co.example.ecommerce_d.repository.OrderToppingRepository;

/**
 * 注文履歴一覧を表示するためのサービスクラス.
 * 
 * @author hikaru.tsuda
 *
 */
@Service
@Transactional
public class ViewOrderHistoryService {

	@Autowired
	private OrderRepository orderRepository;
	
	@Autowired
	private OrderItemRepository orderItemRepository;
	
	@Autowired
	private ItemRepository itemRepository;
	
	@Autowired
	private OrderToppingRepository orderToppingRepository;

	/**
	 * ユーザIDから注文状態1以上の注文情報を検索. (新規注文が上)
	 * 
	 * @param userId ユーザID
	 * @return 注文情報リスト
	 */
	public List<Order> findByUserId(int userId) {
		List<Order> orderList = null;
		if (orderRepository.findByUserId(userId) == null) {
			orderList = new ArrayList<>();
		} else {
			orderList = orderRepository.findByUserId(userId);
		}
		return orderList;
	}
	
	/**
	 * 注文IDから商品画像のパスを返すメソッド.
	 * @param orderId　注文ID
	 * @return 商品画像パス
	 */
	public String findItemPathByOrderId(Integer orderId){
		String imagePath = itemRepository.findImagePath(orderId);
		return imagePath;
	}
	
	
	/**
	 * 注文IDから注文商品情報一覧を返すメソッド.
	 * 
	 * @param orderId　注文ID
	 * @return 注文商品情報一覧
	 */
	public List<OrderItem> getOrderItemListByOrderId(int orderId) {
		int status = orderRepository.findByOrderId(orderId).getStatus();
		List<OrderItem> orderItemList = orderItemRepository.findByOrderIdAndStatus(orderId, status);
		for(OrderItem orderItem: orderItemList) {
			Item item = itemRepository.load(orderItem.getItemId());
			orderItem.setItem(item);
			List<OrderTopping> orderToppingList = orderToppingRepository.findByOrderItemIdAndStatus(orderItem.getId(),status);
			orderItem.setOrderToppingList(orderToppingList);
		}

		return orderItemList;
	}
	
	/**
	 * 注文IDから注文情報を返すメソッド.
	 * 
	 * @param orderId　注文ID
	 * @return 注文情報
	 */
	public Order getOrderbyOrderId(int orderId) {
		return orderRepository.findByOrderId(orderId);
	}
	
	/**
	 * 注文IDからステータスをキャンセルにする.
	 * 
	 * @param orderId 注文ID
	 */
	public void canselByOrderId(int orderId) {
		orderRepository.updateStatus(orderId, 9);
	}
}