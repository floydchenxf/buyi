package com.buyi.webapp.buyi.vo;

/**
 * 产品发布类
 * 
 * @author cxf128
 * 
 */
public class ProductPublishedVO {

	/**
	 * 产品发布之后的信息，如果有错误就显示错误信息
	 */
	private String message;

	/**
	 * 是否发布成功
	 */
	private boolean success;

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

}
