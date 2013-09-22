package com.buyi.dal.entity.dataobject;

import com.buyi.dal.entity.BaseDO;

public class CompanyDO extends BaseDO<Long> {

	private static final long serialVersionUID = -1215533535484731223L;

	/**
	 * 公司图片
	 */
	private String companyPic;

	/**
	 * 公司介绍
	 */
	private String companyInfo;

	/**
	 * 公司地址
	 */
	private String companyAddress;

	/**
	 * 公司电话
	 */
	private String companyPhone;

	/**
	 * 公司联系人
	 */
	private Long contractId;

	public String getCompanyPic() {
		return companyPic;
	}

	public void setCompanyPic(String companyPic) {
		this.companyPic = companyPic;
	}

	public String getCompanyInfo() {
		return companyInfo;
	}

	public void setCompanyInfo(String companyInfo) {
		this.companyInfo = companyInfo;
	}

	public String getCompanyAddress() {
		return companyAddress;
	}

	public void setCompanyAddress(String companyAddress) {
		this.companyAddress = companyAddress;
	}

	public String getCompanyPhone() {
		return companyPhone;
	}

	public void setCompanyPhone(String companyPhone) {
		this.companyPhone = companyPhone;
	}

	public Long getContractId() {
		return contractId;
	}

	public void setContractId(Long contractId) {
		this.contractId = contractId;
	}
}
