package com.helome.messagecenter.client.socket;


public class MessageDateInfo {
	
	private String type;
	private Object value;
	
	public MessageDateInfo(String type, Object value) {
		super();
		this.type = type;
		this.value = value;
	}

	public String getType() {
		return type;
	}

	public Object getValue() {
		return value;
	}

}
