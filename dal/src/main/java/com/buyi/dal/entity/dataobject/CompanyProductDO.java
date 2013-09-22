package com.buyi.dal.entity.dataobject;

import com.buyi.dal.entity.BaseDO;

public class CompanyProductDO extends BaseDO<Long> {

	private static final long serialVersionUID = 2997565577689942339L;

	/**
	 * 公司id
	 */
	private Long companyId;

	/**
	 * 产品类型id
	 */
	private Long productTypeId;

	/**
	 * 产品名称
	 */
	private String productName;

	/**
	 * 产品图片
	 */
	private String productPic;

	/**
	 * 产品介绍
	 */
	private String productInfo;

	private String productPrice;

	private String productNum;

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getProductPrice() {
		return productPrice;
	}

	public void setProductPrice(String productPrice) {
		this.productPrice = productPrice;
	}

	public String getProductNum() {
		return productNum;
	}

	public void setProductNum(String productNum) {
		this.productNum = productNum;
	}

	public Long getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}

	public Long getProductTypeId() {
		return productTypeId;
	}

	public void setProductTypeId(Long productTypeId) {
		this.productTypeId = productTypeId;
	}

	public String getProductPic() {
		return productPic;
	}

	public void setProductPic(String productPic) {
		this.productPic = productPic;
	}

	public String getProductInfo() {
		return productInfo;
	}

	public void setProductInfo(String productInfo) {
		this.productInfo = productInfo;
	}

}
