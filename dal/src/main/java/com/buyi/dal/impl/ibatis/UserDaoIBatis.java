package com.buyi.dal.impl.ibatis;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.buyi.dal.UserDao;
import com.buyi.dal.entity.dataobject.UserDO;

@Repository("userDao")
public class UserDaoIBatis extends BaseDaoIBatis<Long> implements UserDao {

	private static final String QUERY_USER_BY_MEMBERIDS = "QUERY_USER_BY_MEMBERIDS";
	private static final String UPDATE_USER = "UPDATE-USER";
	private static final String INSERT_USER = "INSERT-USER";
	private static final String LOAD_USER_BY_ID = "LOAD-USER-BY-ID";
	private static final String QUERY_USER_BY_NAME = "QUERY-USER-BY-NAME";

	@Override
	public UserDO loadUserById(Long id) {
		return (UserDO) getSqlMapClientTemplate().queryForObject(
				LOAD_USER_BY_ID, id);
	}

	@Override
	public UserDO loadUserByName(String username) {
		return (UserDO) getSqlMapClientTemplate().queryForObject(
				QUERY_USER_BY_NAME, username);
	}

	@Override
	public Long insertUser(UserDO jkUserDO) {
		return (Long) getSqlMapClientTemplate().insert(INSERT_USER, jkUserDO);
	}

	@Override
	public int updateUser(UserDO jkUserDO) {
		return getSqlMapClientTemplate().update(UPDATE_USER, jkUserDO);
	}

	@Override
	public Map<Long, UserDO> fetchUserInfoByIds(List<Long> memberIds) {
		List<UserDO> userInfos = this.queryUsersByIds(memberIds);
		Map<Long, UserDO> userInfoMap = new HashMap<Long, UserDO>();
		if (null != userInfos && !userInfos.isEmpty()) {
			for (UserDO sdUserDO : userInfos) {
				userInfoMap.put(sdUserDO.getId(), sdUserDO);
			}
		}
		return userInfoMap;
	}

	@SuppressWarnings("unchecked")
	public List<UserDO> queryUsersByIds(List<Long> ids) {
		if (ids == null || ids.size() == 0) {
			return Collections.emptyList();
		}
		return (List<UserDO>) getSqlMapClientTemplate().queryForList(
				QUERY_USER_BY_MEMBERIDS, ids);
	}

}
