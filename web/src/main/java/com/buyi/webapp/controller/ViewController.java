package com.buyi.webapp.controller;

import java.util.Map;

import org.apache.velocity.context.Context;

/**
 * 豆腐块逻辑接口
 * 
 * @author cxf128
 * 
 */
public interface ViewController {

	/**
	 * 获取和合并内容
	 * 
	 * @param params
	 * @return
	 */
	Context fillData(Map<String, Object> params);

	/**
	 * 获取模板
	 * 
	 * @return
	 */
	String getTemplate();

}
