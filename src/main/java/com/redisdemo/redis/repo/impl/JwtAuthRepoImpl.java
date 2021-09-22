package com.redisdemo.redis.repo.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Repository;
import com.redisdemo.redis.entity.JwtAuth;
import com.redisdemo.redis.repo.JwtAuthRepo;

@Repository
public class JwtAuthRepoImpl implements JwtAuthRepo{

	@Autowired
	private StringRedisTemplate redisTemplate;
	
	@Override
	public JwtAuth save(JwtAuth jwtAuth) {
		redisTemplate.opsForList().leftPushAll(jwtAuth.getUserId(), jwtAuth.getToken());
		return jwtAuth;
	}

	@Override
	public JwtAuth getById(String userId) {
		List<String> tokens = null;
		if(Boolean.TRUE.equals(redisTemplate.hasKey(userId))) {
			tokens = redisTemplate.opsForList().range(userId, 0, -1);
		}
		return new JwtAuth(userId, tokens);	
	}

	@Override
	public List<JwtAuth> getAll() {
		// TODO Auto-generated method stub
		return null;
	}

}
