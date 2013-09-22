package com.buyi.domain.service.impl;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.buyi.domain.service.UserDetail;
import com.buyi.domain.service.UserService;
import com.buyi.dal.UserDao;
import com.buyi.dal.entity.dataobject.UserDO;

@Service("userService")
public class UserServiceImpl implements UserService, UserDetailsService {
	private static final Logger logger = LoggerFactory
			.getLogger(UserServiceImpl.class);

	@Autowired
	private UserDao userDao;

	@Override
	public UserDetails loadUserByUsername(String username)
			throws UsernameNotFoundException {

		UserDO userDO = userDao.loadUserByName(username);
		if (userDO == null) {
			throw new UsernameNotFoundException(username);
		}

		UserDetail user = new UserDetail(userDO);
		user.setUsername(username);
		return user;
	}

	@Override
	public UserDetail getAuthenticatedUser() {
		Authentication auth = SecurityContextHolder.getContext()
				.getAuthentication();
		if (auth == null) {
			return null;
		}

		Object principal = auth.getPrincipal();

		if (principal instanceof UserDetail) {

			return (UserDetail) principal;
		}

		if (auth.getClass().getSimpleName().indexOf("Anonymous") < 0)
			logger.error("Unknown authentication encountered, ignore it. "
					+ auth);

		return null;
	}

	@Override
	public Long insertUser(UserDO jkUserDO) {
		return userDao.insertUser(jkUserDO);
	}

	@Override
	public int updateUser(UserDO jkUserDO) {
		return userDao.updateUser(jkUserDO);
	}

	@Override
	public UserDO loadUserByName(String username) {
		return userDao.loadUserByName(username);
	}

	@Override
	public Long getAuthenticatedUserId() {
		UserDetail userDetail = this.getAuthenticatedUser();
		if (userDetail == null) {
			return null;
		} 
		
		return userDetail.getId();
	}

	@Override
	public Map<Long, UserDO> fetchUserInfoByIds(List<Long> memberIds) {
		return userDao.fetchUserInfoByIds(memberIds);
	}

	@Override
	public UserDO load(Long mId) {
		return userDao.loadUserById(mId);
	}

}
