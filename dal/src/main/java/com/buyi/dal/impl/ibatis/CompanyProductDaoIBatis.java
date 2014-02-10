package com.buyi.dal.impl.ibatis;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.buyi.dal.CompanyProductDao;
import com.buyi.dal.entity.dataobject.CompanyProductDO;
import com.buyi.dal.entity.viewobject.TopProductVO;

@Repository("companyProductDao")
public class CompanyProductDaoIBatis extends BaseDaoIBatis<Long> implements
		CompanyProductDao {

	private static final String INSERT_COMPANY_PRODUCT = "INSERT-COMPANY-PRODUCT";
	private static final String QUERY_COMPANY_PRODUCT_BY_CONDITION = "QUERY-COMPANY-PRODUCT-BY-CONDITION";

	@SuppressWarnings("unchecked")
	public List<CompanyProductDO> queryProductsByCondition(Long productTypeId,
			int startRow, int pageSize) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("productTypeId", productTypeId);
		params.put("startRow", startRow);
		params.put("pageSize", pageSize);
		return this.getSqlMapClientTemplate().queryForList(QUERY_COMPANY_PRODUCT_BY_CONDITION, params);
	}
	
	@SuppressWarnings("unchecked")
	public List<CompanyProductDO> queryProductByIds(List<Long> ids) {
		Map<String,Object> params = new HashMap<String, Object>();
		params.put("productIds", ids);
		return this.getSqlMapClientTemplate().queryForList(QUERY_COMPANY_PRODUCT_BY_CONDITION, params);
	}

	@Override
	public Long insertCompanyProduct(CompanyProductDO productDO) {
		return (Long) this.getSqlMapClientTemplate().insert(INSERT_COMPANY_PRODUCT, productDO);
	}

	@SuppressWarnings("unchecked")
	public List<TopProductVO> queryTopProductVO(Long companyId, List<Long> productTypeIds) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("companyId", companyId);
		params.put("productTypeIds", productTypeIds);
		return this.getSqlMapClientTemplate().queryForList("QUERY-COMPANY-PRODUCT-TOP-3-BY-TYPE", params);
	}

	@Override
	public Map<Long, CompanyProductDO> queryProductMap(List<Long> ids) {
		List<CompanyProductDO> productDOs = this.queryProductByIds(ids);
		Map<Long, CompanyProductDO> resultMap = new HashMap<Long, CompanyProductDO>();
		for (CompanyProductDO companyProductDO : productDOs) {
			resultMap.put(companyProductDO.getId(), companyProductDO);
		}
		return resultMap;
	}

}
