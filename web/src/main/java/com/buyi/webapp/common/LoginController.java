package com.buyi.webapp.common;

import java.util.Arrays;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.DataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.buyi.dal.entity.dataobject.UserDO;
import com.buyi.dal.entity.viewobject.RoleType;
import com.buyi.domain.service.UserService;
import com.buyi.util.UrlUtil;
import com.buyi.webapp.common.form.LoginForm;
import com.buyi.webapp.common.form.SignupForm;
import com.buyi.webapp.common.validation.LoginValidation;

@Controller
public class LoginController {

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private AuthenticationSuccessHandler authenticationSuccessHandler;

	@Autowired
	private UserService userService;

	@Autowired
	private PasswordEncoder pwdEncoder;

	@InitBinder("loginForm")
	public void initBinder(DataBinder binder) {
		binder.setValidator(new LoginValidation());
	}

	@RequestMapping(value = { UrlUtil.BUYI_LOGIN }, method = RequestMethod.GET)
	public void login(Model model) {
		model.addAttribute("loginForm", new LoginForm());
	}

	/**
	 * 用户登陆接口
	 * 
	 * @param loginForm
	 * @param result
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = { UrlUtil.BUYI_LOGIN }, method = RequestMethod.POST)
	public String login(@Valid LoginForm loginForm, BindingResult result, HttpServletRequest request, HttpServletResponse response) {

		if (result.hasErrors()) {
			return UrlUtil.BUYI_LOGIN;
		}

		String uname = loginForm.getUname();
		String pwd = loginForm.getPwd();

		UserDO jkUserDO = userService.loadUserByName(uname);
		boolean isExists = jkUserDO != null;
		if (!isExists) {
			result.reject("info", "用户不存在");
			return UrlUtil.BUYI_LOGIN;
		}

		boolean isMatch = pwdEncoder.matches(pwd, jkUserDO.getPwd());
		if (!isMatch) {
			result.reject("info", "密码不正确");
			return UrlUtil.BUYI_LOGIN;
		}

		UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(uname, pwd, Arrays.asList(new SimpleGrantedAuthority(jkUserDO.getRoleType().name())));
		Authentication auth = authenticationManager.authenticate(token);
		SecurityContextHolder.getContext().setAuthentication(auth);
		try {
			authenticationSuccessHandler.onAuthenticationSuccess(request, response, auth);
		} catch (Exception e) {
			e.printStackTrace();
		}

		// 调用onAuthenticationSuccess会重新定向。所以return null;
		return null;
	}

	/**
	 * 显示注册用户
	 */
	@RequestMapping(value = { UrlUtil.BUYI_SIGNUP }, method = RequestMethod.GET)
	public void showRegForm(Model model) {
		model.addAttribute("signupForm", new SignupForm());
	}

	@RequestMapping(value = { UrlUtil.BUYI_SIGNUP }, method = RequestMethod.POST)
	public String signupUser(@Valid SignupForm signupForm, BindingResult bindingResult, HttpServletRequest request, HttpServletResponse response) {

		if (bindingResult.hasErrors()) {
			return UrlUtil.BUYI_SIGNUP;
		}

		String uname = signupForm.getUname();
		UserDO userDO = userService.loadUserByName(uname);
		if (userDO != null) {
			bindingResult.rejectValue("uname", "user.not_exist", "用户存在");
			return UrlUtil.BUYI_SIGNUP;
		}

		String pwd = signupForm.getPwd();
		String confirmPwd = signupForm.getConfirmPwd();
		if (!pwd.equals(confirmPwd)) {
			bindingResult.rejectValue("confirmPwd", "user.not_same", "确认密码不一致");
			return UrlUtil.BUYI_SIGNUP;
		}

		String encodePwd = pwdEncoder.encode(pwd);
		userDO = new UserDO();
		userDO.setRealName(uname);
		userDO.setPwd(encodePwd);
		userDO.setLastOnlineTime(new Date());
		userDO.setRoleType(RoleType.ROLE_NORMAL);
		userService.insertUser(userDO);

		// 赋权登录
		UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(uname, pwd, Arrays.asList(new SimpleGrantedAuthority(userDO.getRoleType().name())));
		Authentication auth = authenticationManager.authenticate(token);
		SecurityContextHolder.getContext().setAuthentication(auth);
		try {
			authenticationSuccessHandler.onAuthenticationSuccess(request, response, auth);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

}
