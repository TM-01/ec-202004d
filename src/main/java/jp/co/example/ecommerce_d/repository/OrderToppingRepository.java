package jp.co.example.ecommerce_d.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import jp.co.example.ecommerce_d.domain.OrderTopping;
import jp.co.example.ecommerce_d.domain.Topping;

/**
 * order_toppingsを操作するリポジトリ.
 * 
 * @author yu.konishi
 *
 */
@Repository
public class OrderToppingRepository {

	/**
	 * 注文トッピングのオブジェクトを作成
	 */
	private static final RowMapper<OrderTopping> ORDER_TOPPING_ROW_MAPPER = (rs, i) -> {
		OrderTopping orderTopping = new OrderTopping();
		orderTopping.setId(rs.getInt("id"));
		orderTopping.setOrderItemId(rs.getInt("order_item_id"));
		orderTopping.setToppingId(rs.getInt("topping_id"));
		Topping topping = new Topping();
		topping.setId(rs.getInt("tId"));
		topping.setName(rs.getString("name"));
		topping.setPriceM(rs.getInt("price_m"));
		topping.setPriceL(rs.getInt("price_l"));
		orderTopping.setTopping(topping);
		return orderTopping;
	};

	@Autowired
	private NamedParameterJdbcTemplate template;

	/**
	 * 注文トッピングを削除する.
	 * 
	 * @param orderItemId 注文商品ID
	 */
	public void deleteOrderTopping(int orderItemId) {
		String sql = "DELETE FROM order_toppings WHERE order_item_id=:orderItemId;";
		SqlParameterSource param = new MapSqlParameterSource().addValue("orderItemId", orderItemId);
		template.update(sql, param);
	}

	/**
	 * トッピングオーダのリストを受け取ってその情報を登録します.
	 * 
	 * @param orderToppingList トッピングオーダ
	 */
	public void insertTopping(List<OrderTopping> orderToppingList) {
		String sql = "INSERT INTO order_toppings(topping_id,order_item_id) VALUES ";
		MapSqlParameterSource param = new MapSqlParameterSource();
		for (int i = 0; i < orderToppingList.size(); i++) {
			sql += "(:toppingId" + i + ",:orderItemId" + i + ")";
			param = param.addValue("toppingId" + i, orderToppingList.get(i).getToppingId()).addValue("orderItemId" + i,
					orderToppingList.get(i).getOrderItemId());
			if (i != orderToppingList.size() - 1) {
				sql += ",";
			}
		}
		sql += ";";
		template.update(sql, param);
	}

	/**
	 * 注文商品IDで注文トッピングの一覧を取得する.
	 * 
	 * @param orderItemId 注文商品ID
	 * @return 該当する注文トッピング一覧
	 */
	public List<OrderTopping> findByOrderItemId(int orderItemId) {
		String sql = "SELECT ot.id,ot.topping_id,ot.order_item_id,t.id tId,t.name,t.price_m,t.price_l FROM "
				+ "order_toppings ot inner join toppings t ON ot.topping_id=t.id INNER JOIN order_items oi"
				+ " ON ot.order_item_id=oi.id INNER JOIN orders o ON oi.order_id=o.id"
				+ " WHERE ot.order_item_id=:orderItemId AND o.status=0;";
		SqlParameterSource param = new MapSqlParameterSource().addValue("orderItemId", orderItemId);
		List<OrderTopping> orderToppingList = template.query(sql, param, ORDER_TOPPING_ROW_MAPPER);
		return orderToppingList;
	}
	
	/**
	 * 注文商品IDで注文トッピングの一覧を取得する.
	 * 
	 * @param orderItemId 注文商品ID
	 * @param status 注文状態
	 * @return 該当する注文トッピング一覧
	 */
	public List<OrderTopping> findByOrderItemIdAndStatus(int orderItemId, int status) {
		String sql = "SELECT ot.id,ot.topping_id,ot.order_item_id,t.id tId,t.name,t.price_m,t.price_l FROM "
				+ "order_toppings ot inner join toppings t ON ot.topping_id=t.id INNER JOIN order_items oi"
				+ " ON ot.order_item_id=oi.id INNER JOIN orders o ON oi.order_id=o.id"
				+ " WHERE ot.order_item_id=:orderItemId AND o.status=:status;";
		SqlParameterSource param = new MapSqlParameterSource().addValue("orderItemId", orderItemId).addValue("status", status);
		List<OrderTopping> orderToppingList = template.query(sql, param, ORDER_TOPPING_ROW_MAPPER);
		return orderToppingList;
	}

}
