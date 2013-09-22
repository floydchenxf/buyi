package com.buyi.dal.impl.ibatis;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.buyi.dal.ProductTypeDAO;
import com.buyi.dal.entity.dataobject.ProductTypeDO;

@Repository("productTypeDAO")
public class ProductTypeDaoIBatis extends BaseDaoIBatis<Long> implements
		ProductTypeDAO {

	private static final String QUERY_PRODUCT_TYPE_BY_COMPANY_ID = "QUERY-PRODUCT-TYPE-BY-COMPANY-ID";

	@SuppressWarnings("unchecked")
	public List<ProductTypeDO> queryProductTypesByCompanyId(Long companyId) {
		return this.sqlMapClientTemplate.queryForList(
				QUERY_PRODUCT_TYPE_BY_COMPANY_ID, companyId);
	}

}
