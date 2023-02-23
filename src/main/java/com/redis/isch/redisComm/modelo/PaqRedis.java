package com.redis.isch.redisComm.modelo;

public class PaqRedis {
	
	private String key;
	private String message;
	
	
	
	public String getKey() {
		return key;
	}



	public void setKey(String key) {
		this.key = key;
	}



	public String getMessage() {
		return message;
	}



	public void setMessage(String message) {
		this.message = message;
	}



	public PaqRedis() {
		
		key ="";
		message="";
	}
	
	public PaqRedis(String key, Float valor) {
		
		this.key =key;
		message= valor.toString();
	}
	
	public PaqRedis(String key, Integer valor) {
		
		this.key =key;
		message= valor.toString();
	}
	
}
