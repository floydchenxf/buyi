package com.buyi.domain.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.buyi.dal.CompanyDao;
import com.buyi.dal.CompanyProductDao;
import com.buyi.dal.ProductTypeDAO;
import com.buyi.dal.entity.dataobject.CompanyDO;
import com.buyi.dal.entity.dataobject.CompanyProductDO;
import com.buyi.dal.entity.dataobject.ProductTypeDO;
import com.buyi.dal.entity.viewobject.CompanyVO;
import com.buyi.domain.service.CompanyService;

@Service("companyService")
public class CompanyServiceImpl implements CompanyService {

	@Autowired
	private CompanyDao companyDao;

	@Autowired
	private ProductTypeDAO productTypeDAO;

	@Autowired
	private CompanyProductDao companyProductDao;

	@Override
	public CompanyDO loadCompany(Long companyId) {
		return companyDao.loadCompanyById(companyId);
	}

	@Override
	public List<ProductTypeDO> queryProductTypes(Long companyId) {
		return productTypeDAO.queryProductTypesByCompanyId(companyId);
	}

	@Override
	public List<CompanyProductDO> queryProductsByCondition(Long productTypeId,
			int startRow, int pageSize) {
		return companyProductDao.queryProductsByCondition(productTypeId,
				startRow, pageSize);
	}

	@Override
	public CompanyVO loadCompanyVO(Long companyId) {
		return companyDao.loadCompanyVOById(companyId);
	}

	@Override
	public Long insertProduct(CompanyProductDO productDO) {
		return companyProductDao.insertCompanyProduct(productDO);
	}

}
