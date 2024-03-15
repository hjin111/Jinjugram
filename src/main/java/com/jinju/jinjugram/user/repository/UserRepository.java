package com.jinju.jinjugram.user.repository;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UserRepository {

	public int insertUser(
			@Param("loginId") String loginId
			, @Param("password") String password
			, @Param("name") String name
			, @Param("email") String email);

	// 전달받은 loginId 와 일치하는 행의 갯수를 조회 하는 기능
	// 일치하는 갯수를 조회할거니깐 갯수만 리턴해주면 됨 그래서 int 타입으로 지정
	public int selectCountByLoginId(@Param("loginId") String loginId);

}
