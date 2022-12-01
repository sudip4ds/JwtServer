package com.example.demo;

public class Response {
	
	private String token;
	private boolean flag;
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public boolean isFlag() {
		return flag;
	}
	public void setFlag(boolean flag) {
		this.flag = flag;
	}
	public Response(String token, boolean flag) {
		
		this.token = token;
		this.flag = flag;
	}

}
