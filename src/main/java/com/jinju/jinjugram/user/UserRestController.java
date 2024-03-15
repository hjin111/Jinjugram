package com.jinju.jinjugram.user;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jinju.jinjugram.user.service.UserService;

@RestController
public class UserRestController {
	
	@Autowired
	private UserService userService;
	
	@PostMapping("/user/join")
	public Map<String, String> join(
		@RequestParam("loginId") String loginId
		, @RequestParam("password") String password
		, @RequestParam("name") String name
		, @RequestParam("email") String email){
		
		int count = userService.addUser(loginId, password, name, email);
		
		Map<String, String> resultMap = new HashMap<>();
		
		if(count == 1) {
			resultMap.put("result", "success");
		}else {
			resultMap.put("result", "fail");
		}
		
		return resultMap;
	}
	
	// Controller 는 request, response 처리 이외에는 직접적으로 뭔가를 수행하면 안된다.
	// password 를 암호화 하는 과정도 마찬가지이다.
	// 그러면 password 가 어차피 Service 한테도 전달이 되니깐 Service 에서 암호화 처리 하기
	// 암호화 과정을 Service 에서 직접 구현해서 처리할 수도 있지만 이것도 마찬가지로 이런 복잡한 코드가 하나의 메소드 안에 너무 많이 들어가는건 좋지도 않고
	// 또 명확한 의미를 가진 기능이라면 따로 분리해서 처리하는게 더 명확하다 심지어 이 암호화는 여러군데에서 사용될 수도 있다
	// 그러면 메소드로 따로 암호화 하는 기능을 만들건데 더불어서 이걸 새로운 클래스에다가 넣어서 어디서든 사용할 수 있도록 구조를 만들면 좋겠다.
	// 어디서든 사용게 할 클래스를 만들 패키지를 common 이라고 하는 이름으로 만들어서 여기다가 암호화와 관련된 기능을 모아놓는다는 의미로 EncryptUtils 라는 이름에 클래스를 만들어서 여기다가 특정한 문자열을 전달 받아서 그 문자열을 암호화 하고 암호화 된 결과를 메소드로 만들어 보겠습니다.
	
	// id 중복 확인 API
	@GetMapping("/user/duplicate-id")
	public Map<String, Boolean> idDuplicateId(@RequestParam("loginId") String loginId) {
		
		boolean isDuplicate = userService.isDuplicateId(loginId);
		
		Map<String, Boolean> resultMap = new HashMap<>();
		
		resultMap.put("isDuplicate", isDuplicate);
		
//		if(isDuplicate) { // true 일때
//			resultMap.put("isDuplicate", true);
//		} else { // false 일때
//			resultMap.put("isDuplicate", false);
//		}
		
		return resultMap;
		
	}
	
}
