package com.buyi.dal;

import java.util.List;
import java.util.Map;

import com.buyi.dal.entity.dataobject.CompanyProductDO;
import com.buyi.dal.entity.viewobject.TopProductVO;

public interface CompanyProductDao {

	/**
	 * 分页查询商品
	 * 
	 * @param productTypeId
	 * @param start
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
	Long insertCompanyProduct(CompanyProductDO productDO);
	
	/**
	 * 查询同一类型的top 3的产品
	 * 
	 * @return
	 */
	List<TopProductVO> queryTopProductVO(Long companyId, List<Long> productTypeIds);
	
	List<CompanyProductDO> queryProductByIds(List<Long> ids);
	
	Map<Long, CompanyProductDO> queryProductMap(List<Long> ids);
	
}
