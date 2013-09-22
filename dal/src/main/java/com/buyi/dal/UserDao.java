package com.buyi.dal;

import java.util.List;
import java.util.Map;

import com.buyi.dal.entity.dataobject.UserDO;

public interface UserDao {

	/**
	 * 根据id查找用户
	 * 
	 * @param id
	 * @return
	 */
	UserDO loadUserById(Long id);

	/**
	 * 根据名字查找用户
	 * 
	 * @param name
	 * @return
	 */
	UserDO loadUserByName(String name);

	/**
	 * 创建用户
	 * 
	 * @param jkUserDO
	 * @return
	 */
	Long insertUser(UserDO jkUserDO);

	/**
	 * 更新用户信息
	 * 
	 * @param jkUserDO
	 * @return
	 */
	int updateUser(UserDO jkUserDO);

	/**
	 *批量 获取用户的信息
	 * @param memberIds
	 * @return
	 */
	Map<Long, UserDO> fetchUserInfoByIds(List<Long> memberIds);
	
	List<UserDO> queryUsersByIds(List<Long> memberIds);

}
