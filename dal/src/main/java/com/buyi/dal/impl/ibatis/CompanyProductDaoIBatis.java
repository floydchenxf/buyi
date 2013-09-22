package com.buyi.dal.impl.ibatis;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.buyi.dal.CompanyProductDao;
import com.buyi.dal.entity.dataobject.CompanyProductDO;

@Repository("companyProductDao")
public class CompanyProductDaoIBatis extends BaseDaoIBatis<Long> implements
		CompanyProductDao {

	private static final String INSERT_COMPANY_PRODUCT = "INSERT-COMPANY-PRODUCT";
	private static final String QUERY_COMPANY_PRODUCT_BY_TYPE_ID = "QUERY-COMPANY-PRODUCT-BY-TYPE-ID";

	@SuppressWarnings("unchecked")
	public List<CompanyProductDO> queryProductsByCondition(Long productTypeId,
			int startRow, int pageSize) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("productTypeId", productTypeId);
		params.put("startRow", startRow);
		params.put("pageSize", pageSize);
		return this.getSqlMapClientTemplate().queryForList(
				QUERY_COMPANY_PRODUCT_BY_TYPE_ID, params);
	}

	@Override
	public Long insertCompanyProduct(CompanyProductDO productDO) {
		return (Long) this.getSqlMapClientTemplate().insert(INSERT_COMPANY_PRODUCT, productDO);
	}

}
