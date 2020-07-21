package jp.co.example.ecommerce_d.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jp.co.example.ecommerce_d.domain.User;
import jp.co.example.ecommerce_d.form.RegisterUserForm;
import jp.co.example.ecommerce_d.repository.UserRepository;

/**
 * ユーザ情報のサービスクラス.
 * 
 * @author hikaru.tsuda
 *
 */
@Service
@Transactional
public class UserService {

	@Autowired
	private UserRepository repository;

	/**
	 * フォームからユーザ情報を挿入するメソッド.
	 * 
	 * @param form
	 */
	synchronized public void insert(RegisterUserForm form) {
		User user = new User();
		user.setName(form.getName());
		user.setEmail(form.getEmail());
		user.setPassword(BCrypt.hashpw(form.getPassword(), BCrypt.gensalt()));
		user.setZipcode(form.getZipcode().replaceAll("-", ""));
		user.setAddress(form.getAddress());
		user.setTelephone(form.getTelephone());
		repository.insert(user);
	}
	
	/**
	 * メールアドレスからユーザ情報を検索するメソッド.
	 * 
	 * @param email　メールアドレス
	 * @return　ない場合はnull
	 */
	public User findByEmail(String email) {
		return repository.findByEmail(email);
	}

}
