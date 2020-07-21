package jp.co.example.ecommerce_d.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import jp.co.example.ecommerce_d.domain.OrderItem;

/**
 * order_itemsを操作するリポジトリ.
 * 
 * @author yu.konishi
 *
 */
@Repository
public class OrderItemRepository {

	/**
	 * 注文商品のオブジェクトを作成
	 */
	private static final RowMapper<OrderItem> ORDER_ITEM_ROW_MAPPER = (rs, i) -> {
		OrderItem orderItem = new OrderItem();
		orderItem.setId(rs.getInt("id"));
		orderItem.setItemId(rs.getInt("item_id"));
		orderItem.setOrderId(rs.getInt("order_id"));
		orderItem.setQuantity(rs.getInt("quantity"));
		orderItem.setSize(rs.getString("size").charAt(0));
		return orderItem;
	};

	@Autowired
	private NamedParameterJdbcTemplate template;

	/**
	 * 注文IDで注文商品一覧を取得する.
	 * 
	 * @param orderId 注文ID
	 * @param status 状態
	 * @return 注文商品一覧、なかった場合には空のリストを返す
	 */
	public List<OrderItem> findByOrderIdAndStatus(Integer orderId,Integer status) {
		String sql = "SELECT oi.id,oi.item_id,oi.order_id,oi.quantity,oi.size FROM order_items oi "
				+ "inner join orders o on o.id = oi.order_id WHERE order_id=:orderId AND o.status = :status;";
		SqlParameterSource param = new MapSqlParameterSource().addValue("orderId", orderId).addValue("status",status);
		List<OrderItem> orderItemList = template.query(sql, param, ORDER_ITEM_ROW_MAPPER);
		return orderItemList;
	}

	/**
	 * 注文商品を削除する.
	 * 
	 * @param orderItemId 注文商品ID
	 * @param orderId     注文ID
	 */
	public void deleteOrderItem(Integer orderItemId, Integer orderId) {
		String sql = "DELETE FROM order_items WHERE id=:itemId AND order_id=:orderId;";
		SqlParameterSource param = new MapSqlParameterSource().addValue("itemId", orderItemId).addValue("orderId",
				orderId);
		template.update(sql, param);
	}

	/**
	 * オーダ表にカートに商品を追加します.
	 * 
	 * @param orderItem 追加する注文
	 * @return 自動採番されるID
	 */
	public int insertItem(OrderItem orderItem) {
		String sql = "INSERT INTO order_items(item_id,order_id,quantity,size) VALUES(:itemId,:orderID,:quantity,:size);";
		SqlParameterSource param = new MapSqlParameterSource().addValue("itemId", orderItem.getItemId())
				.addValue("orderID", orderItem.getOrderId()).addValue("quantity", orderItem.getQuantity())
				.addValue("size", orderItem.getSize());
		KeyHolder keyHolder = new GeneratedKeyHolder();
		String[] key = { "id" };
		template.update(sql, param, keyHolder, key);
		return keyHolder.getKey().intValue();
	}
	
	/**
	 * オーダIDを変更します.
	 * 
	 * @param orderItem 変更するアイテム
	 */
	public void updateOrderId(OrderItem orderItem) {
		String sql = "UPDATE order_items SET order_id = :orderId WHERE id = :id";
		SqlParameterSource param = new MapSqlParameterSource().addValue("orderId", orderItem.getOrderId()).addValue("id", orderItem.getId());
		template.update(sql, param);
	}

}
