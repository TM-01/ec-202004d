package jp.co.example.ecommerce_d.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import jp.co.example.ecommerce_d.common.Status;
import jp.co.example.ecommerce_d.domain.Order;
import jp.co.example.ecommerce_d.domain.User;

/**
 * ordersを操作するリポジトリ.
 * 
 * @author yu.konishi
 *
 */
@Repository
public class OrderRepository {

	/**
	 * 注文のオブジェクトを生成
	 */
	private static final RowMapper<Order> ORDER_ROW_MAPPER = (rs, i) -> {
		Order order = new Order();
		order.setId(rs.getInt("id"));
		order.setUserId(rs.getInt("user_id"));
		order.setStatus(rs.getInt("status"));
		order.setTotalPrice(rs.getInt("total_price"));
		order.setOrderDate(rs.getDate("order_date"));
		order.setDestinationName(rs.getString("destination_name"));
		order.setDestinationEmail(rs.getString("destination_email"));
		order.setDestinationZipcode(rs.getString("destination_zipcode"));
		order.setDestinationAddress(rs.getString("destination_address"));
		order.setDestinationTel(rs.getString("destination_tel"));
		order.setDeliveryTime(rs.getTimestamp("delivery_time"));
		order.setPaymentMethod(rs.getInt("payment_method"));
		return order;
	};

	@Autowired
	private NamedParameterJdbcTemplate template;

	/**
	 * ユーザIDで注文情報を取得する.
	 * 
	 * @param userId ユーザID
	 * @return 注文情報(ない場合はnullを返す)
	 */
	public List<Order> findByUserIdAndStatus(int userId, int status) {
		String sql = "SELECT id,user_id,status,total_price,order_date,destination_name,destination_email,"
				+ "destination_zipcode,destination_address,destination_tel,delivery_time,payment_method FROM orders WHERE "
				+ "user_id=:userId";
		if (status == Status.INVALID.getStatusId() || status == Status.BEFOREORDER.getStatusId()) {
			sql += " AND status = :status;";
		} else {
			sql += " AND status >= :status;";
		}
		SqlParameterSource param = new MapSqlParameterSource().addValue("userId", userId).addValue("status", status);
		List<Order> orderList = template.query(sql, param, ORDER_ROW_MAPPER);
		if (orderList.size() == 0) {
			return null;
		}
		return orderList;
	}

	/**
	 * ユーザIDを指定しオーダを作成します.
	 * 
	 * @param userId ユーザID
	 */
	public void insert(int userId) {
		String sql = "INSERT INTO orders(user_id,status,total_price) VALUES(:userId,0,0);";
		SqlParameterSource param = new MapSqlParameterSource().addValue("userId", userId);
		template.update(sql, param);
	}

	/**
	 * ユーザ情報からオーダを作成します.
	 * 
	 * @param userId ユーザID
	 * @param user   ユーザ情報
	 */
	public void insert(User user) {
		String sql = "INSERT INTO orders(user_id,status,total_price,destination_name,destination_email,destination_zipcode,destination_address,destination_tel) "
				+ "VALUES(:userId,0,0,:name,:email,:zipcode,:address,:telephone);";
		SqlParameterSource param = new MapSqlParameterSource().addValue("userId", user.getId())
				.addValue("email", user.getEmail()).addValue("name", user.getName())
				.addValue("zipcode", user.getZipcode()).addValue("address", user.getAddress())
				.addValue("telephone", user.getTelephone());
		template.update(sql, param);
	}

	/**
	 * 注文IDからステータス情報をアップデートする.
	 * 
	 * @param orderId 注文ID
	 * @param status  ステータス
	 */
	public void updateStatus(int orderId, int status) {
		String sql = "UPDATE orders SET status = :status WHERE id = :orderId;";
		SqlParameterSource param = new MapSqlParameterSource().addValue("status", status).addValue("orderId", orderId);
		template.update(sql, param);
	}

	/**
	 * オーダの宛先情報と支払方法を更新する.
	 * 
	 * @param order  オーダ
	 * @param status 状態
	 */
	public void updateDestinationAndPaymentMethod(Order order, int status) {
		String sql = "UPDATE orders SET status=:status,order_date=:date,"
				+ "destination_name=:destinationName,destination_email=:destinationEmail,"
				+ "destination_zipcode=:destinationZipcode,destination_address=:destinationAddress,"
				+ "destination_tel=:destinationTel,"
				+ "delivery_time=:deliveryTime,payment_method=:paymentMethod WHERE id=:id";
		SqlParameterSource param = new MapSqlParameterSource().addValue("id", order.getId()).addValue("status", status)
				.addValue("date", order.getOrderDate()).addValue("destinationName", order.getDestinationName())
				.addValue("destinationEmail", order.getDestinationEmail())
				.addValue("destinationZipcode", order.getDestinationZipcode())
				.addValue("destinationAddress", order.getDestinationAddress())
				.addValue("destinationTel", order.getDestinationTel()).addValue("deliveryTime", order.getDeliveryTime())
				.addValue("paymentMethod", order.getPaymentMethod());
		template.update(sql, param);
	}

	/**
	 * オーダの合計金額を更新する.
	 * 
	 * @param orderId    オーダID
	 * @param totalPrice 合計金額
	 */
	public void updateTotalPrice(int orderId, int totalPrice) {
		String sql = "UPDATE orders SET total_price=:totalPrice WHERE id=:orderId;";
		SqlParameterSource param = new MapSqlParameterSource().addValue("orderId", orderId).addValue("totalPrice",
				totalPrice);
		template.update(sql, param);
	}
	
	
	/**
	 * 注文IDから注文情報を返すメソッド.
	 * 
	 * @param orderId 注文ID
	 * @return　注文情報
	 */
	public Order findByOrderId(int orderId) {
		String sql = "SELECT id,user_id,status,total_price,order_date,destination_name,destination_email,"
				+ "destination_zipcode,destination_address,destination_tel,delivery_time,payment_method FROM orders WHERE "
				+ "id=:orderId";
		SqlParameterSource param = new MapSqlParameterSource().addValue("orderId", orderId);
		return template.queryForObject(sql, param, ORDER_ROW_MAPPER);
	}
	
	/**
	 * ユーザIDで注文情報を取得する(注文状況が1以上、id昇順).
	 * 
	 * @param userId ユーザID
	 * @return 注文情報(ない場合はnullを返す)
	 */
	public List<Order> findByUserId(int userId) {
		String sql = "SELECT id,user_id,status,total_price,order_date,destination_name,destination_email,"
				+ "destination_zipcode,destination_address,destination_tel,delivery_time,payment_method FROM orders WHERE "
				+ "user_id=:userId AND status >= 1 ORDER BY id DESC;";
		SqlParameterSource param = new MapSqlParameterSource().addValue("userId", userId);
		List<Order> orderList = template.query(sql, param, ORDER_ROW_MAPPER);
		if (orderList.size() == 0) {
			return null;
		}
		return orderList;
	}


}
