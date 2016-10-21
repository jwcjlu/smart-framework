package org.smart4j.framework.bean;

import java.util.HashMap;
import java.util.Map;
/**
 * 处理视图
 * @author jinwei
 *
 */
public class View {
	/**
	 * 视图路径
	 */
	private String path;
	/**
	 * 模型数据
	 */
	private Map<String,Object> model;
	public View(String path){
		this.path=path;
		model=new HashMap<String,Object>();
	}
	public void addModel(String key,Object value){
		model.put(key, value);
	}
	public String getPath() {
		return path;
	}
	public Map<String, Object> getModel() {
		return model;
	}
	

}
