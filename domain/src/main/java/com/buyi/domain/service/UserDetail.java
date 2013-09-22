package com.buyi.domain.service;

import java.util.Arrays;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.buyi.dal.entity.dataobject.UserDO;

public class UserDetail implements UserDetails {

	private static final long serialVersionUID = 1L;

	private String username;

	private UserDO jkUserDO;

	public UserDetail(UserDO userDO) {
		this.jkUserDO = userDO;
	}

	public UserDetail(UserDO userDO, String username) {
		this.jkUserDO = userDO;
		this.username = username;
	}

	/**
	 * @return the jkUserDO
	 */
	public UserDO getJkUserDO() {
		return jkUserDO;
	}

	/**
	 * @param jkUserDO
	 *            the jkUserDO to set
	 */
	public void setJkUserDO(UserDO jkUserDO) {
		this.jkUserDO = jkUserDO;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return Arrays.asList(new SimpleGrantedAuthority(jkUserDO.getRoleType()
				.name()));
	}

	@Override
	public String getPassword() {
		String ret = jkUserDO.getPwd();
		return ret == null ? "" : ret;
	}

	@Override
	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

	public Long getId() {
		return this.jkUserDO == null ? null : this.jkUserDO.getId();
	}

	public String getProfileImage() {
		return this.jkUserDO == null ? null : this.jkUserDO.getProfileImage();
	}
}
