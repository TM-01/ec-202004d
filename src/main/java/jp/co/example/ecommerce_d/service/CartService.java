package jp.co.example.ecommerce_d.service;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jp.co.example.ecommerce_d.common.Status;
import jp.co.example.ecommerce_d.domain.Order;
import jp.co.example.ecommerce_d.domain.OrderItem;
import jp.co.example.ecommerce_d.domain.OrderTopping;
import jp.co.example.ecommerce_d.domain.User;
import jp.co.example.ecommerce_d.repository.OrderItemRepository;
import jp.co.example.ecommerce_d.repository.OrderRepository;
import jp.co.example.ecommerce_d.repository.OrderToppingRepository;
import jp.co.example.ecommerce_d.repository.UserRepository;

@Service
@Transactional
public class CartService {

	@Autowired
	private HttpSession session;

	@Autowired
	private OrderRepository orderRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private OrderItemRepository orderItemRepository;
	
	@Autowired
	private OrderToppingRepository orderToppingRepository;

	/**
	 * ユーザIDとステータスからオーダ情報を検索します.
	 * 
	 * @param userId ユーザ情報
	 * @param status ステータス
	 * @return オーダ情報
	 */
	public List<Order> findByUserIdAndStatus(int userId, int status) {
		return orderRepository.findByUserIdAndStatus(userId, status);
	}

	/**
	 * ユーザIDからユーザ情報を検索します.
	 * 
	 * @param id ユーザID
	 * @return ユーザ情報
	 */
	public User loadUser(int id) {
		return userRepository.load(id);
	}

	/**
	 * ユーザIDからオーダを作成します.
	 * 
	 * @param userId ユーザID
	 */
	synchronized public void createCart(int userId) {
		orderRepository.insert(userId);
	}

	/**
	 * ユーザ情報からオーダを作成します.
	 * 
	 * @param user ユーザ情報
	 */
	synchronized public void createCart(User user) {
		orderRepository.insert(user);
	}

	/**
	 * セッションに入っているIDがあるか確認し、あればそれを、なければ乱数を生成し返します.
	 * 
	 * @return ユーザID
	 */
	synchronized public Integer checkUserId() {
		Integer userId = (Integer) session.getAttribute("userId");
		if (userId != null) {
			return userId;
		}
		List<Order> orderList = new ArrayList<>();
		int newUserId = 0;
		while (orderList != null) {
			newUserId = (int) ((Integer.MAX_VALUE - 1_000_000_000) * Math.random() + 1_000_000_000);
			orderList = orderRepository.findByUserIdAndStatus(newUserId, Status.BEFOREORDER.getStatusId());
		}
		session.setAttribute("userId", newUserId);
		return newUserId;
	}

	/**
	 * オーダ表に商品を追加します.
	 * 
	 * @param orderItem オーダされた商品
	 * @return 自動採番ID
	 */
	synchronized public int addOrderItem(OrderItem orderItem) {
		return orderItemRepository.insertItem(orderItem);
	}
	
	/**
	 * トッピングオーダのリストを受け取りその情報を登録します.
	 * 
	 * @param orderToppingList トッピングオーダ
	 */
	synchronized public void addOrderTopping(List<OrderTopping> orderToppingList) {
		orderToppingRepository.insertTopping(orderToppingList);
	}
}
