/**
 * hisuda copy right 1.0 
 */
package org.springframework.velocity.tools;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.velocity.tools.generic.SafeConfig;
import org.springframework.beans.factory.BeanFactory;

import com.buyi.dal.entity.dataobject.UserDO;
import com.buyi.domain.service.UserService;

/**
 * 使用方法 #set($b = [1,2]) $userInfoTool.init($b) #set($user =
 * $userInfoTool.get('1'))
 * 
 * UserRelationTool.java
 * 
 * @author cxf128
 */
public class UserInfoTool extends SafeConfig {

	private UserService userService;

	private Map<Long, UserDO> userInfos = new HashMap<Long, UserDO>();

	private BeanFactory beanFactory;

	public Map<Long, UserDO> getUserInfos() {
		return userInfos;
	}

	public void setBeanFactory(BeanFactory beanFactory) {
		if (beanFactory == null) {
			NullPointerException e = new NullPointerException("beanFactory should not be null");
			throw e;
		}
		this.beanFactory = beanFactory;
		userService = (UserService) this.beanFactory.getBean("userService");
		if (userService == null) {
			NullPointerException e = new NullPointerException("userService or userRelationService should not be null");
			throw e;
		}
	}

	public void init(Collection mIds) {
		if (null == mIds || mIds.isEmpty()) {
			return;
		}

		List<Long> memberIds = new ArrayList<Long>();
		for (Object mId : mIds) {
			Long id = null;
			if (mId instanceof Integer || mId instanceof String) {
				id = Long.parseLong(mId.toString());
			} else {
				id = (Long) mId;
			}

			if (userInfos.get(id) == null) {
				memberIds.add(id);
			}
		}

		if (!memberIds.isEmpty()) {
			Map<Long, UserDO> tUserInfos = userService.fetchUserInfoByIds(memberIds);
			if (null != tUserInfos && !tUserInfos.isEmpty()) {
				userInfos.putAll(tUserInfos);
			}
		}
	}

	public UserDO get(Long mId) {
		UserDO sdUserDO = userInfos.get(mId);
		if (sdUserDO == null) {
			// 从cache里获取
			// 从数据库获取
			sdUserDO = userService.load(mId);
			if (sdUserDO != null) {
				userInfos.put(mId, sdUserDO);
				return sdUserDO;
			}
		}
		return sdUserDO;
	}
}
