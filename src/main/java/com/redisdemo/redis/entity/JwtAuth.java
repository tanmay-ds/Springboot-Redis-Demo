package com.redisdemo.redis.entity;

import java.util.List;

public class JwtAuth {
	
	private String userId;
	private List<String> token;
	
	public JwtAuth() {
	}
	public JwtAuth(String userId, List<String> token) {
		super();
		this.userId = userId;
		this.token = token;
	}
	
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public List<String> getToken() {
		return token;
	}
	public void setToken(List<String> token) {
		this.token = token;
	}
	
	@Override
	public String toString() {
		return "JwtAuth [userId=" + userId + ", token=" + token + "]";
	}
}
