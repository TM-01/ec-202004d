package jp.co.example.ecommerce_d.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jp.co.example.ecommerce_d.domain.Order;
import jp.co.example.ecommerce_d.domain.OrderItem;
import jp.co.example.ecommerce_d.domain.OrderTopping;
import jp.co.example.ecommerce_d.domain.User;
import jp.co.example.ecommerce_d.repository.OrderItemRepository;
import jp.co.example.ecommerce_d.repository.OrderRepository;
import jp.co.example.ecommerce_d.repository.OrderToppingRepository;
import jp.co.example.ecommerce_d.repository.UserRepository;

/**
 * 注文情報を操作するサービス.
 * 
 * @author taira.matsuta
 *
 */
@Service
@Transactional
public class MergeOrderService {

	@Autowired
	private OrderRepository orderRepository;
	
	@Autowired
	private OrderItemRepository orderItemRepository;
	
	@Autowired
	private OrderToppingRepository orderToppingRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	/**
	 * ユーザIDとステータスから注文情報を検索する.
	 * 
	 * @param userId ユーザID
	 * @param status　ステータス（0）
	 * @return 注文情報
	 */
	public List<Order> showOrderList(int userId, int status) {
		return orderRepository.findByUserIdAndStatus(userId, status);
	}
	
	/**
	 * 注文商品のリストを検索して取得する.
	 * 
	 * @param orderId　注文ID
	 * @param status　ステータス
	 * @return　注文商品リスト
	 */
	public List<OrderItem> showOrderItemList(int orderId, int status) {
		return orderItemRepository.findByOrderIdAndStatus(orderId, status);
	}
	
	/**
	 * 注文IDのステータスを更新する.
	 * 
	 * @param orderId　注文ID
	 * @param status ステータス
	 */
	public void updateStatus(int orderId, int status) {
		orderRepository.updateStatus(orderId, status);
	}
	
	/**
	 * 注文表に商品を追加する.
	 * 
	 * @param orderItem 注文商品
	 * @return　注文アイテムID
	 */
	public int insertOrderItem(OrderItem orderItem) {
		return orderItemRepository.insertItem(orderItem);
	}
	
	/**
	 * 注文トッピングを検索する.
	 * 
	 * @param orderItemId 
	 * @return 注文トッピングリスト
	 */
	public List<OrderTopping> showOrderTopping(int orderItemId) {
		return orderToppingRepository.findByOrderItemId(orderItemId);
	}
	
	/**
	 * トッピングを追加する.
	 * 
	 * @param orderToppingList 注文トッピングリスト
	 */
	public void insertOrderTopping(List<OrderTopping> orderToppingList) {
		orderToppingRepository.insertTopping(orderToppingList);
	}

	/**
	 * 注文表を作成する.
	 * 
	 * @param userId ユーザID
	 */
	public void createOrder(int userId) {
		User user = userRepository.load(userId);
		orderRepository.insert(user);
	}
	
	public void updateOrderItemOfOrderId(OrderItem orderItem) {
		orderItemRepository.updateOrderId(orderItem);
	}
}
