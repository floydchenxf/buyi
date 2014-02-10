package org.springframework.velocity.tools;

import java.util.Collection;

import org.apache.velocity.runtime.log.Log;
import org.apache.velocity.tools.Scope;
import org.apache.velocity.tools.config.DefaultKey;
import org.apache.velocity.tools.config.ValidScope;
import org.apache.velocity.tools.generic.SafeConfig;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import com.buyi.domain.service.UserService;

/**
 * 
 */
@DefaultKey("security")
@ValidScope(Scope.REQUEST)
public class SecurityTool extends SafeConfig {
	private BeanFactory beanFactory;
	Log log;

	public Authentication getAuthentication() {
		return SecurityContextHolder.getContext().getAuthentication();
	}

	public Object getMe() {
		return beanFactory.getBean(UserService.class).getAuthenticatedUser();
	}

	public boolean isAuthed() {
		return getMe() != null;
	}

	/**
	 * @return 若傳入role == null, 始終返回false
	 */
	public boolean hasRole(String role) {
		if (role == null)
			return false;
		Object me = getMe();
		if (me instanceof UserDetails) {
			Collection<? extends GrantedAuthority> authorities = ((UserDetails) me)
					.getAuthorities();
			if (authorities != null) {
				for (GrantedAuthority authority : authorities) {
					if (authority != null)
						return role.equals(authority.getAuthority());
				}
			}
		}
		return false;
	}

	public void setBeanFactory(BeanFactory beanFactory) {
		if (beanFactory == null) {
			NullPointerException e = new NullPointerException(
					"beanFactory should not be null");
			log.error("", e);
			throw e;
		}
		this.beanFactory = beanFactory;
	}

	public void setLog(Log log) {
		this.log = log;
	}
}
