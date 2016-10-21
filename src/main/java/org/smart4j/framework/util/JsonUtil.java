package org.smart4j.framework.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public final class JsonUtil {
	
	private static final Logger logger = LoggerFactory.getLogger(JsonUtil.class);
	private static final  ObjectMapper OBJECT_MAPPER=new ObjectMapper();

	public static<T> String toJson(T obj){
		String json="";
		try {
			json=OBJECT_MAPPER.writeValueAsString(obj);
		} catch (JsonProcessingException e) {
			logger.error("change to json failure",e);
			throw new RuntimeException(e);
		}
		return json;
		
	}
	public static<T> T fromJson(String json,Class<T>type){
		T t=null;
		try {
			t=OBJECT_MAPPER.readValue(json, type);
		} catch (Exception e) {
			logger.error("change to Object failure",e);
			throw new RuntimeException(e);
		}
		return t;
		
	}

}
