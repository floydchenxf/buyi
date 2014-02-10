package com.buyi.dal.entity.viewobject;

import java.util.ArrayList;
import java.util.List;

import com.buyi.dal.entity.dataobject.CompanyProductDO;

public class TopProductVO {

	private Long companyId;

	private Long id;

	private String productIds;

	private Integer productNum;

	private List<CompanyProductDO> topProductDOs = new ArrayList<CompanyProductDO>();

	public Long getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getProductIds() {
		return productIds;
	}

	public void setProductIds(String productIds) {
		this.productIds = productIds;
	}

	public Integer getProductNum() {
		return productNum;
	}

	public void setProductNum(Integer productNum) {
		this.productNum = productNum;
	}

	public List<CompanyProductDO> getTopProductDOs() {
		return topProductDOs;
	}

	public void setTopProductDOs(List<CompanyProductDO> topProductDOs) {
		this.topProductDOs = topProductDOs;
	}

	public List<Long> fetchProductIds() {
		List<Long> productIds = new ArrayList<Long>();
		if (this.productIds == null || this.productIds.equals("")) {
			return productIds;
		}

		String[] productIdStr = this.productIds.split(",");
		if (productIdStr.length <= 0) {
			return productIds;
		}

		for (String productId : productIdStr) {
			Long id = Long.valueOf(productId);
			productIds.add(id);
		}

		return productIds;
	}
}
