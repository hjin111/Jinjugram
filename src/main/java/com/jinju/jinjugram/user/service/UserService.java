package com.jinju.jinjugram.user.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jinju.jinjugram.common.EncryptUtils;
import com.jinju.jinjugram.user.repository.UserRepository;

@Service
public class UserService {
	
	@Autowired
	private UserRepository userRepository;

	public int addUser(String loginId, String password, String name, String email) {
		
		// 암호화 
		String encryptPassword = EncryptUtils.md5(password);
		return userRepository.insertUser(loginId, encryptPassword, name, email);
	}
	
	// 전달받은 loginId 값이 중복되었는지 알려주는 기능
	public boolean isDuplicateId(String loginId) { // id 를 전달 받아서 중복되었는지를 알려준다. 즉, 중복 결과를 리턴해주는 메소드를 만드는거고 중복 결과는 중복이 됐다, 안됐다 기다, 아니다의 결과이다 그래서 이런 경우에 true/false 값을 리턴할 수 있는 boolean 타입으로 지정 
		
		// 해당 하는 로그인 id 를 조건으로 걸어서 몇 개의 행이 조회되는지를 확인해서 조회되는게 없으면 중복이 안된거고 조회되는게 뭐라도 하나 있으면 중복이 된것이다.
		// select 쿼리와 count 함수를 통해서 조회 결과의 갯수를 확인하고 그 갯수에 따라서 중복 됐다, 안됐다를 판단해주면 된다.
		int count = userRepository.selectCountByLoginId(loginId);
		
		if(count >= 1) {
			// 중복
			return true;
		}else {
			// 중복 아님
			return false;
		}
		
	}
}
