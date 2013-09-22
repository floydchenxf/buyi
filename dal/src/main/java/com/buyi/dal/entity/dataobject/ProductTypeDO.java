package com.buyi.dal.entity.dataobject;

import com.buyi.dal.entity.BaseDO;

public class ProductTypeDO extends BaseDO<Long> {

	private static final long serialVersionUID = -8387681533004658636L;

	/**
	 * 公司id
	 */
	private Long companyId;

	/**
	 * 类型图片
	 */
	private String typePic;

	/**
	 * 类型名称
	 */
	private String typeName;

	public Long getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}

	public String getTypePic() {
		return typePic;
	}

	public void setTypePic(String typePic) {
		this.typePic = typePic;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

}
