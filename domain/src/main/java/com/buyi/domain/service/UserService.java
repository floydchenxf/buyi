package com.buyi.domain.service;

import java.util.List;
import java.util.Map;

import com.buyi.dal.entity.dataobject.UserDO;

public interface UserService {

	/**
	 * 获取登录用户信息
	 * 
	 * @return
	 */
	UserDetail getAuthenticatedUser();

	/**
	 * 获取登录用户id
	 * 
	 * @return
	 */
	Long getAuthenticatedUserId();

	/**
	 * 插入用户
	 * 
	 * @param jkUserDO
	 * @return
	 */
	Long insertUser(UserDO jkUserDO);

	/**
	 * 更新用户
	 * 
	 * @param accessToken
	 * @return
	 */
	int updateUser(UserDO jkUserDO);

	/**
	 * load user by name
	 * 
	 * @param username
	 * @return
	 */
	UserDO loadUserByName(String username);

	/**
	 * 批量获取用户信息
	 * 
	 * @param memberIds
	 * @return
	 */
	Map<Long, UserDO> fetchUserInfoByIds(List<Long> memberIds);

	/**
	 * 根据id获取用户信息
	 * 
	 * @param mId
	 * @return
	 */
	UserDO load(Long mId);

}
