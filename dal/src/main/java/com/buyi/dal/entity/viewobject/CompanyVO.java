package com.buyi.dal.entity.viewobject;

public class CompanyVO {

	public Long id;

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
	private String companyContract;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

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

	public String getCompanyContract() {
		return companyContract;
	}

	public void setCompanyContract(String companyContract) {
		this.companyContract = companyContract;
	}
}
