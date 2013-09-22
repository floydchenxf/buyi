package com.buyi.dal.impl.ibatis;

import org.springframework.stereotype.Repository;

import com.buyi.dal.CompanyDao;
import com.buyi.dal.entity.dataobject.CompanyDO;
import com.buyi.dal.entity.viewobject.CompanyVO;

@Repository("companyDao")
public class CompanyDaoIBatis extends BaseDaoIBatis<Long> implements CompanyDao {

	private static final String LOAD_COMPANY_VO_BY_ID = "LOAD-COMPANY-VO-BY-ID";
	private static final String LOAD_COMPANY_BY_ID = "LOAD-COMPANY-BY-ID";

	@Override
	public CompanyDO loadCompanyById(Long id) {
		return (CompanyDO) this.getSqlMapClientTemplate().queryForObject(
				LOAD_COMPANY_BY_ID, id);
	}

	@Override
	public CompanyVO loadCompanyVOById(Long id) {
		return (CompanyVO) this.getSqlMapClientTemplate().queryForObject(
				LOAD_COMPANY_VO_BY_ID, id);
	}

}
