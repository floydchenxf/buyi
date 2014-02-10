package com.buyi.dal.impl.ibatis;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.buyi.dal.ProductTypeDAO;
import com.buyi.dal.entity.dataobject.ProductTypeDO;

@Repository("productTypeDAO")
public class ProductTypeDaoIBatis extends BaseDaoIBatis<Long> implements ProductTypeDAO {

	private static final String QUERY_PRODUCT_TYPE_BY_COMPANY_ID = "QUERY-PRODUCT-TYPE-BY-COMPANY-ID";

	@SuppressWarnings("unchecked")
	public List<ProductTypeDO> queryProductTypesByCompanyId(Long companyId, int startRow, int pageSize) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("companyId", companyId);
		params.put("startRow", startRow);
		params.put("pageSize", pageSize);
		return this.sqlMapClientTemplate.queryForList(QUERY_PRODUCT_TYPE_BY_COMPANY_ID, params);
	}

}
