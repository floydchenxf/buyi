package org.springframework.security.web.authentication;

import java.io.IOException;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.StandardPasswordEncoder;

import com.buyi.dal.entity.dataobject.UserDO;
import com.buyi.domain.service.UserDetail;

/**
 * 
 * @author cxf128
 * 
 */
public class SavedRequestAwareAuthenticationSuccessHandlerEx extends
		SavedRequestAwareAuthenticationSuccessHandler {
	public static final String SECURITY_EMAIL_PROMPT = "SECURITY_EMAIL_PROMPT";

	public void onAuthenticationSuccess(HttpServletRequest request,
			HttpServletResponse response, Authentication authentication)
			throws ServletException, IOException {
		Object principal = authentication.getPrincipal();
		if (!(principal instanceof UserDetail)) {
			super.onAuthenticationSuccess(request, response, authentication);
			return;
		}

		UserDetail user = (UserDetail) principal;
		UserDO jkUserDO = user.getJkUserDO();
		jkUserDO.setLastOnlineTime(new Date());

		super.onAuthenticationSuccess(request, response, authentication);
	}

	public static void main(String[] args) {
		StandardPasswordEncoder s = new StandardPasswordEncoder("keleba.org");
		String b = s.encode("cxf128");
		System.out.println(b);
	}

}
