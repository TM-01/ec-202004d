package jp.co.example.ecommerce_d.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import jp.co.example.ecommerce_d.domain.Topping;

/**
 * toppingsを操作するリポジトリ.
 * 
 * @author yu.konishi
 *
 */
@Repository
public class ToppingRepository {

	/**
	 * トッピングのオブジェクトを生成
	 */
	private static final RowMapper<Topping> TOPPING_ROW_MAPPER = (rs, i) -> {
		Topping topping = new Topping();
		topping.setId(rs.getInt("id"));
		topping.setName(rs.getString("name"));
		topping.setPriceM(rs.getInt("price_m"));
		topping.setPriceL(rs.getInt("price_l"));
		topping.setDeleted(rs.getBoolean("deleted"));
		return topping;
	};

	@Autowired
	private NamedParameterJdbcTemplate template;

	/**
	 * 削除されていない全トッピングを取得する.
	 * 
	 * @return トッピング一覧
	 */
	public List<Topping> findAll() {
		String sql = "SELECT id,name,price_m,price_l,deleted FROM toppings WHERE deleted = false ORDER BY id;";
		List<Topping> toppingList = template.query(sql, TOPPING_ROW_MAPPER);
		return toppingList;
	}

	/**
	 * 削除されているかされていないかを指定して全件検索を行います.
	 * 
	 * @param deleted true:削除済み,false:未削除
	 * @return トッピング一覧
	 */
	public List<Topping> findAll(boolean deleted) {
		String sql = "SELECT id,name,price_m,price_l,deleted FROM toppings　WHERE deleted = :deleted ORDER BY id;";
		SqlParameterSource param = new MapSqlParameterSource().addValue("deleted", deleted);
		List<Topping> toppingList = template.query(sql, param, TOPPING_ROW_MAPPER);
		return toppingList;
	}

	/**
	 * トッピングを編集します.
	 * 
	 * @param topping トッピング情報
	 */
	public void updateTopping(Topping topping) {
		SqlParameterSource param = new BeanPropertySqlParameterSource(topping);
		String sql = "UPDATE toppings SET name = :name,price_m = :priceM,price_l = :priceL WHERE id = :id";
		template.update(sql, param);
	}
	
	/**
	 * トッピングを削除また削除取り消しをします.
	 * 
	 * @param id トッピングID
	 * @param deleted true:削除,false:未削除
	 */
	public void updateToppingDeleted(int id, boolean deleted) {
		String sql = "UPDATE toppings SET deleted = :deleted WHERE id = :id;";
		SqlParameterSource param = new MapSqlParameterSource().addValue("deleted", deleted);
		template.update(sql, param);
	}

	/**
	 * 登録されているトッピングの最大IDを返します.
	 * 
	 * @return 一番 値の大きなID
	 */
	public int findMaxId() {
		String sql = "SELECT MAX(id) FROM toppings;";
		return template.queryForObject(sql, new MapSqlParameterSource(), Integer.class);
	}

}
