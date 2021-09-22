package com.redisdemo.web.rest;

import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.redisdemo.redis.entity.JwtAuth;
import com.redisdemo.redis.repo.JwtAuthRepo;

@RestController
@EnableCaching
public class JwtAuthRest {
	
	@Autowired
	private JwtAuthRepo jwtAuthRepo;
	
	@PostMapping("save")
	public JwtAuth save(@RequestBody JwtAuth jwtAuth) {
		return jwtAuthRepo.save(jwtAuth);
	}
	
	@GetMapping("getById")
	public JwtAuth getById(@PathParam("userId") String userId) {
		return jwtAuthRepo.getById(userId);
	}
}
