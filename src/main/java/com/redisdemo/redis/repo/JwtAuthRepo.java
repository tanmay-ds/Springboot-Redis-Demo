package com.redisdemo.redis.repo;

import java.util.List;

import com.redisdemo.redis.entity.JwtAuth;


public interface JwtAuthRepo {

	JwtAuth save(JwtAuth jwtAuth);
	
	JwtAuth getById(String userId);
	
	List<JwtAuth> getAll();
}
