package jp.co.example.ecommerce_d.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jp.co.example.ecommerce_d.repository.UserRepository;

/**
 * ユーザ退会のサービス.
 * 
 * @author yu.konishi
 *
 */
@Service
@Transactional
public class WithdrawService {

	/**
	 * usersを操作するリポジトリ
	 */
	@Autowired
	private UserRepository userRepository;
	
	/**
	 * ユーザ情報を削除する.
	 * @param userId ユーザID
	 */
	public void withdrawExecute(Integer userId) {
		userRepository.delete(userId);
	}

}
