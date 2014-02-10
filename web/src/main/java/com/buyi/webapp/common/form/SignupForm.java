package com.buyi.webapp.common.form;

import org.hibernate.validator.constraints.NotBlank;

public class SignupForm {

	/**
	 * 用户名
	 */
	private String uname;

	/**
	 * 用户密码
	 */
	private String pwd;

	/**
	 * 确认密码
	 */
	private String confirmPwd;

	@NotBlank(message = "请输入用户名")
	public String getUname() {
		return uname;
	}

	public void setUname(String uname) {
		this.uname = uname;
	}

	@NotBlank(message = "请输入密码")
	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	@NotBlank(message = "请确认密码")
	public String getConfirmPwd() {
		return confirmPwd;
	}

	public void setConfirmPwd(String confirmPwd) {
		this.confirmPwd = confirmPwd;
	}
}
