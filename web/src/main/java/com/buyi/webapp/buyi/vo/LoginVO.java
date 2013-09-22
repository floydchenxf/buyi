package com.buyi.webapp.buyi.vo;

import com.buyi.dal.entity.dataobject.UserDO;

public class LoginVO {

	/**
	 * 执行成功，返回token;执行失败，返回错误信息
	 */
	private String message;

	/**
	 * 是否执行成功
	 */
	private boolean success;

	private UserDO userDO;

	public UserDO getUserDO() {
		return userDO;
	}

	public void setUserDO(UserDO userDO) {
		this.userDO = userDO;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
