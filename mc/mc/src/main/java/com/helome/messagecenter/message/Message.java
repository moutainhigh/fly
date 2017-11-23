package com.helome.messagecenter.message;

import io.netty.buffer.ByteBuf;

import java.util.Map;

import com.fasterxml.jackson.databind.JsonNode;
import com.helome.messagecenter.core.Endpoint;

public interface Message {

	short getCode();

	int getLength();

	void setLength(int length);

	void setEndpoint(Endpoint endpoint);

	void onReceived();

	ByteBuf toBinary();

	void fromBinary(ByteBuf buffer);

	String toJson();

	void fromMap(Map<String, JsonNode> map);

}
