package jp.co.example.ecommerce_d.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import jp.co.example.ecommerce_d.domain.User;

/**
 * usersテーブルを操作するリポジトリ.
 * 
 * @author hikaru.tsuda
 *
 */
@Repository
public class UserRepository {

	@Autowired
	private NamedParameterJdbcTemplate template;

	private static final RowMapper<User> USER_ROW_MAPPER = (rs, i) -> {
		User user = new User();
		user.setId(rs.getInt("id"));
		user.setName(rs.getString("name"));
		user.setEmail(rs.getString("email"));
		user.setPassword(rs.getString("password"));
		user.setZipcode(rs.getString("zipcode"));
		user.setAddress(rs.getString("address"));
		user.setTelephone(rs.getString("telephone"));
		return user;
	};

	/**
	 * メールアドレスからユーザ情報を検索.
	 * 
	 * @param email メールアドレス
	 * @return ユーザ情報 ない場合はnullを返します
	 */
	public User findByEmail(String email) {
		String sql = "SELECT id,name,email,password,zipcode,address,telephone FROM users WHERE email=:email ;";
		SqlParameterSource param = new MapSqlParameterSource().addValue("email", email);
		List<User> UserList = template.query(sql, param, USER_ROW_MAPPER);
		if (UserList.size() == 0) {
			return null;
		}
		return UserList.get(0);

	}

	/**
	 * ユーザIDからユーザ情報を検索します.
	 * 
	 * @param id ユーザID
	 * @return ユーザ情報(0件ならnull)
	 */
	public User load(int id) {
		String sql = "SELECT id,name,email,password,zipcode,address,telephone FROM users WHERE id = :id;";
		SqlParameterSource param = new MapSqlParameterSource().addValue("id", id);
		List<User> userList = template.query(sql, param, USER_ROW_MAPPER);
		if (userList.size() == 0) {
			return null;
		}
		return userList.get(0);
	}

	/**
	 * ユーザ情報を挿入します.
	 * 
	 * @param user
	 */
	public void insert(User user) {
		SqlParameterSource param = new BeanPropertySqlParameterSource(user);
		String insertSql = "INSERT INTO users(name, email, password, zipcode, address, telephone) VALUES(:name, :email, :password, :zipcode, :address, :telephone);";
		template.update(insertSql, param);

	}
	
	/**
	 * ユーザ情報を削除します.
	 * 
	 * @param userId ユーザID
	 */
	public void delete(int userId) {
		String sql = "DELETE FROM users WHERE id=:userId;";
		SqlParameterSource param = new MapSqlParameterSource().addValue("userId",userId);
		template.update(sql, param);
	}

}
