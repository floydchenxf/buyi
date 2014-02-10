package com.buyi.domain.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.buyi.dal.CompanyDao;
import com.buyi.dal.CompanyProductDao;
import com.buyi.dal.ProductTypeDAO;
import com.buyi.dal.entity.dataobject.CompanyDO;
import com.buyi.dal.entity.dataobject.CompanyProductDO;
import com.buyi.dal.entity.dataobject.ProductTypeDO;
import com.buyi.dal.entity.viewobject.CompanyVO;
import com.buyi.dal.entity.viewobject.ProductTypeVO;
import com.buyi.dal.entity.viewobject.TopProductVO;
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
	public List<ProductTypeDO> queryProductTypes(Long companyId, int startRow, int pageSize) {
		return productTypeDAO.queryProductTypesByCompanyId(companyId, startRow, pageSize);
	}

	@Override
	public List<CompanyProductDO> queryProductsByCondition(Long productTypeId, int startRow, int pageSize) {
		return companyProductDao.queryProductsByCondition(productTypeId, startRow, pageSize);
	}

	@Override
	public CompanyVO loadCompanyVO(Long companyId) {
		return companyDao.loadCompanyVOById(companyId);
	}

	@Override
	public Long insertProduct(CompanyProductDO productDO) {
		return companyProductDao.insertCompanyProduct(productDO);
	}

	@Override
	public List<ProductTypeVO> queryTopProductVOs(Long companyId, int startRow, int pageSize) {
		List<ProductTypeVO> result = new ArrayList<ProductTypeVO>();
		
		List<ProductTypeDO> productTypes = this.queryProductTypes(companyId, startRow, pageSize);
		if (productTypes == null || productTypes.isEmpty()) {
			return result;
		}
		
		List<Long> productTypeIds = new ArrayList<Long>();
		for (ProductTypeDO type : productTypes) {
			ProductTypeVO productTypeVO = new ProductTypeVO();
			productTypeVO.setId(type.getId());
			productTypeVO.setTypeName(type.getTypeName());
			productTypeVO.setCompanyId(type.getCompanyId());
			result.add(productTypeVO);
			productTypeIds.add(type.getId());
		}

		List<TopProductVO> topInfos = companyProductDao.queryTopProductVO(companyId, productTypeIds);

		if (topInfos == null || topInfos.size() <= 0) {
			return result;
		}
		
		List<Long> productIds = new ArrayList<Long>();
		Map<Long, List<Long>> topMap = new HashMap<Long, List<Long>>();
		for (TopProductVO topProductVO : topInfos) {
			List<Long> t_proudct_ids = topProductVO.fetchProductIds();
			topMap.put(topProductVO.getId(), t_proudct_ids);
			productIds.addAll(t_proudct_ids);
		}
		
		if (productIds.isEmpty()) {
			return result;
		}
		
		Map<Long, CompanyProductDO> comMap = companyProductDao.queryProductMap(productIds);
		
		//init list
		for (ProductTypeVO productTypeVO : result) {
			Long id = productTypeVO.getId();
			List<Long> t_proudct_ids = topMap.get(id);
			if (t_proudct_ids == null || t_proudct_ids.isEmpty()) {
				continue;
			}
			
			List<CompanyProductDO> ccc = new ArrayList<CompanyProductDO>();
			for (Long ii : t_proudct_ids) {
				CompanyProductDO pp = comMap.get(ii);
				if (pp != null) {
					ccc.add(pp);
				}
			}
			
		    productTypeVO.setTopProductDOs(ccc);
		}
		
		return result;
	}

}
