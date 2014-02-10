package com.buyi.webapp.common.validation;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.buyi.webapp.common.form.LoginForm;

public class LoginValidation implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		return clazz.isAssignableFrom(LoginForm.class);
	}

	@Override
	public void validate(Object obj, Errors errors) {
		LoginForm loginForm = (LoginForm) obj;
		String uname = loginForm.getUname();
		if (null == uname || uname.trim().equals("")) {
			errors.reject("info", "uname is empty.");
			return;
		}

		String pwd = loginForm.getPwd();
		if (null == pwd || "".equals(pwd)) {
			errors.reject("info", "Pwd is empty.");
		}
	}

}
