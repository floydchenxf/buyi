package com.buyi.domain.service.search.read;

import java.util.List;

import org.springframework.data.domain.PageableEx;

import com.buyi.dal.entity.dataobject.GoodsDetailDO;

public interface GoodsDetailIndexSearchService {

	/**
	 * 查询商品详情
	 * 
	 * @param keyword
	 * @param pageable
	 * @return
	 */
	List<GoodsDetailDO> searchGoodsDetails(Long categoryId, String keyword, PageableEx pageable);

}
