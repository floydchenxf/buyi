package com.buyi.dal;

import com.buyi.dal.entity.dataobject.CompanyDO;
import com.buyi.dal.entity.viewobject.CompanyVO;

public interface CompanyDao {

	/**
	 * load 公司
	 * 
	 * @param id
	 * @return
	 */
	CompanyDO loadCompanyById(Long id);

	/**
	 * load 公司信息
	 * 
	 * @param id
	 * @return
	 */
	CompanyVO loadCompanyVOById(Long id);

}
