package jp.co.example.ecommerce_d.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import jp.co.example.ecommerce_d.domain.Item;

/**
 * itemsテーブルを操作するリポジトリ.
 * 
 * @author taira.matsuta
 *
 */
@Repository
public class ItemRepository {

	/**
	 * Itemオブジェクトを生成するローマッパー.
	 */
	private static final RowMapper<Item> ITEM_ROW_MAPPER = (rs, i) -> {
		Item item = new Item();
		item.setId(rs.getInt("id"));
		item.setName(rs.getString("name"));
		item.setDescription(rs.getString("description"));
		item.setPriceM(rs.getInt("price_m"));
		item.setPriceL(rs.getInt("price_l"));
		item.setImagePath(rs.getString("image_path"));
		item.setDeleted(rs.getBoolean("deleted"));
		return item;
	};

	/**
	 * Stringオブジェクトを生成するローマッパー.
	 */
	private static final RowMapper<String> ITEM_NAME_ROW_MAPPER = (rs, i) -> {
		return rs.getString("name");
	};

	@Autowired
	private NamedParameterJdbcTemplate template;

	/**
	 * 商品名から商品一覧を検索する.
	 * 
	 * @param fuzzyName 商品名（曖昧可）
	 * @param sort      並び順
	 * @return 商品一覧（該当するものがなければ全件検索される）
	 */
	public List<Item> findByName(String fuzzyName, String sort) {
		String sql = "SELECT id,name,description,price_m,price_l,image_path,deleted FROM items WHERE UPPER(name) LIKE UPPER(:name) AND deleted = false ORDER BY ";
		if ("expensive".equals(sort)) {
			sql = sql + "price_m DESC;";
		} else if ("nameAsc".equals(sort)) {
			sql = sql + "name;";
		} else if ("nameDesc".equals(sort)) {
			sql = sql + "name DESC;";
		} else {
			sql = sql + "price_m;";
		}
		SqlParameterSource param = new MapSqlParameterSource("name", "%" + fuzzyName + "%");
		List<Item> itemList = template.query(sql, param, ITEM_ROW_MAPPER);
		return itemList;
	}
	
	public List<Item> findByNameForAll(String fuzzyName, String sort) {
		String sql = "SELECT id,name,description,price_m,price_l,image_path,deleted FROM items WHERE UPPER(name) LIKE UPPER(:name) ORDER BY ";
		if ("expensive".equals(sort)) {
			sql = sql + "price_m DESC;";
		} else if ("nameAsc".equals(sort)) {
			sql = sql + "name;";
		} else if ("nameDesc".equals(sort)) {
			sql = sql + "name DESC;";
		} else {
			sql = sql + "price_m;";
		}
		SqlParameterSource param = new MapSqlParameterSource("name", "%" + fuzzyName + "%");
		List<Item> itemList = template.query(sql, param, ITEM_ROW_MAPPER);
		return itemList;
	}

	/**
	 * 商品IDから商品情報を取得する.
	 * 
	 * @param id 商品ID
	 * @return 商品情報
	 */
	public Item load(int id) {
		String sql = "SELECT id,name,description,price_m,price_l,image_path,deleted FROM items WHERE id=:id;";
		SqlParameterSource param = new MapSqlParameterSource().addValue("id", id);
		Item item = template.queryForObject(sql, param, ITEM_ROW_MAPPER);
		return item;
	}

	/**
	 * 商品名を全件検索する.
	 * 
	 * @return 商品名一覧
	 */
	public List<String> findAllName() {
		String sql = "SELECT name FROM items WHERE deleted = false ORDER BY name;";
		List<String> nameList = template.query(sql, ITEM_NAME_ROW_MAPPER);
		return nameList;
	}

	/**
	 * 指定された商品名を検索します（曖昧不可）
	 * 
	 * @return 商品名一覧
	 */
	public List<String> findByName(String name) {
		String sql = "SELECT name FROM items WHERE name = :name AND deleted = false;";
		SqlParameterSource param = new MapSqlParameterSource().addValue("name", name);
		List<String> nameList = template.query(sql, param, ITEM_NAME_ROW_MAPPER);
		return nameList;
	}

	/**
	 * 商品を登録します.
	 * 
	 * @param item 登録する商品
	 */
	public void insert(Item item) {
		String sql = "INSERT INTO items(id,name,description,price_m,price_l,image_path) VALUES(:id,:name,:description,:priceM,:priceL,:imagePath);";
		SqlParameterSource param = new BeanPropertySqlParameterSource(item);
		template.update(sql, param);
	}

	/**
	 * 商品情報を更新します.
	 * 
	 * @param item 商品情報
	 */
	public void updateItem(Item item, boolean updateFlag) {
		SqlParameterSource param = new BeanPropertySqlParameterSource(item);
		String sql;
		if (updateFlag) {
			sql = "UPDATE items SET name = :name,description = :description,price_m = :priceM,price_l = :priceL,image_path = :imagePath WHERE id = :id;";
		} else {
			sql = "UPDATE items SET name = :name,description = :description,price_m = :priceM,price_l = :priceL WHERE id = :id;";
		}
		template.update(sql, param);
	}

	/**
	 * 商品を削除また削除取り消しをします.
	 * 
	 * @param id      商品ID
	 * @param deleted true:削除,false:未削除
	 */
	public void updateItemDeleted(int id, boolean deleted) {
		String sql = "UPDATE items SET deleted = :deleted WHERE id = :id;";
		SqlParameterSource param = new MapSqlParameterSource().addValue("deleted", deleted).addValue("id", id);
		template.update(sql, param);
	}

	/**
	 * 現在登録されているIDの最大値を検索します.
	 * 
	 * @return IDの最大値
	 */
	public int findMaxId() {
		String sql = "SELECT MAX(id) FROM items;";
		SqlParameterSource param = new MapSqlParameterSource();
		return template.queryForObject(sql, param, Integer.class);
	}

	/**
	 * 注文IDから商品の画像パスを返すメソッド.
	 * 
	 * @param orderId 注文ID
	 * @return 画像パス
	 */
	public String findImagePath(Integer orderId) {
		String sql = "SELECT i.image_path FROM items i WHERE i.id=(SELECT MIN(oi.item_id) FROM order_items oi WHERE oi.order_id= :orderId);";
		SqlParameterSource param = new MapSqlParameterSource().addValue("orderId", orderId);
		return template.queryForObject(sql, param, String.class);
	}
}
