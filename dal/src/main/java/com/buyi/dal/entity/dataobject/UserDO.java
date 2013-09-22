package com.buyi.dal.entity.dataobject;

import java.util.Date;

import com.buyi.dal.entity.BaseDO;
import com.buyi.dal.entity.viewobject.RoleType;

public class UserDO extends BaseDO<Long> {

	private static final long serialVersionUID = 1L;

	private Date lastOnlineTime;

	private String pwd;

	private String nickName;

	private String realName;

	/**
	 * 个人图片信息
	 */
	private String profileImage;

	private RoleType roleType;

	/**
	 * @return the profileImage
	 */
	public String getProfileImage() {
		return profileImage;
	}

	/**
	 * @param profileImage
	 *            the profileImage to set
	 */
	public void setProfileImage(String profileImage) {
		this.profileImage = profileImage;
	}

	/**
	 * @return the roleType
	 */
	public RoleType getRoleType() {
		return roleType;
	}

	/**
	 * @param roleType
	 *            the roleType to set
	 */
	public void setRoleType(RoleType roleType) {
		this.roleType = roleType;
	}

	/**
	 * @return the realName
	 */
	public String getRealName() {
		return realName;
	}

	/**
	 * @param realName
	 *            the realName to set
	 */
	public void setRealName(String realName) {
		this.realName = realName;
	}

	/**
	 * @return the lastOnlineTime
	 */
	public Date getLastOnlineTime() {
		return lastOnlineTime;
	}

	/**
	 * @param lastOnlineTime
	 *            the lastOnlineTime to set
	 */
	public void setLastOnlineTime(Date lastOnlineTime) {
		this.lastOnlineTime = lastOnlineTime;
	}

	/**
	 * @return the pwd
	 */
	public String getPwd() {
		return pwd;
	}

	/**
	 * @param pwd
	 *            the pwd to set
	 */
	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	/**
	 * @return the nickName
	 */
	public String getNickName() {
		return nickName;
	}

	/**
	 * @param nickName
	 *            the nickName to set
	 */
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

}
