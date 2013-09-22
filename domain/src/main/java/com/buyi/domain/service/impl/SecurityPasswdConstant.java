package com.buyi.domain.service.impl;

public class SecurityPasswdConstant {

	private static final String PUYI = "@@puyi@@";

	public static String encodingPasswd(String uname, String pwd) {
		StringBuilder sb = new StringBuilder(uname);
		sb.append(PUYI);
		sb.append(pwd);
		return sb.toString();
	}

}
