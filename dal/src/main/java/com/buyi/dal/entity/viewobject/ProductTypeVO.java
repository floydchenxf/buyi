package com.buyi.dal.entity.viewobject;

import java.util.ArrayList;
import java.util.List;

import com.buyi.dal.entity.dataobject.CompanyProductDO;

public class ProductTypeVO {

	private Long id;

	/**
	 * 公司id
	 */
	private Long companyId;

	/**
	 * 类型名称
	 */
	private String typeName;

	private List<CompanyProductDO> topProductDOs = new ArrayList<CompanyProductDO>();

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public List<CompanyProductDO> getTopProductDOs() {
		return topProductDOs;
	}

	public void setTopProductDOs(List<CompanyProductDO> topProductDOs) {
		this.topProductDOs = topProductDOs;
	}

}
