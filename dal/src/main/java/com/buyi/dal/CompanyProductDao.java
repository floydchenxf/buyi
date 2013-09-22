package com.buyi.dal;

import java.util.List;

import com.buyi.dal.entity.dataobject.CompanyProductDO;

public interface CompanyProductDao {

	/**
	 * 分页查询商品
	 * 
	 * @param productTypeId
	 * @param start
	 * @param pageSize
	 * @return
	 */
	List<CompanyProductDO> queryProductsByCondition(Long productTypeId,
			int startRow, int pageSize);

	/**
	 * 插入公司产品
	 * 
	 * @param productDO
	 * @return
	 */
	Long insertCompanyProduct(CompanyProductDO productDO);

}
