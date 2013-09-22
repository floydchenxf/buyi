package com.buyi.dal;

import java.util.List;

import com.buyi.dal.entity.dataobject.ProductTypeDO;

public interface ProductTypeDAO {

	/**
	 * load 产品类型
	 * 
	 * @param companyId
	 * @return
	 */
	List<ProductTypeDO> queryProductTypesByCompanyId(Long companyId);

}
