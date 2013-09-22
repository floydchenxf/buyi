package com.buyi.domain.service;

import java.util.List;

import net.sf.ehcache.util.ProductInfo;

import com.buyi.dal.entity.dataobject.CompanyDO;
import com.buyi.dal.entity.dataobject.CompanyProductDO;
import com.buyi.dal.entity.dataobject.ProductTypeDO;
import com.buyi.dal.entity.viewobject.CompanyVO;

public interface CompanyService {

	/**
	 * load company info
	 * 
	 * @param companyId
	 * @return
	 */
	CompanyDO loadCompany(Long companyId);

	CompanyVO loadCompanyVO(Long companyId);

	/**
	 * 公司的产品的类型
	 * 
	 * @param companyId
	 * @return
	 */
	List<ProductTypeDO> queryProductTypes(Long companyId);

	/**
	 * 获取类型公司商品
	 * 
	 * @param productTypeId
	 * @param startRow
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
	Long insertProduct(CompanyProductDO productDO);

}
