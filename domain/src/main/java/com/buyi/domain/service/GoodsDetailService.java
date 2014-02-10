package com.buyi.domain.service;

import java.util.List;

import com.buyi.dal.entity.dataobject.GoodsDetailDO;

public interface GoodsDetailService {

	/**
	 * 根据id获取商品详情
	 * 
	 * @param id
	 * @return
	 */
	GoodsDetailDO queryGoodsDetailById(Long id);

	/**
	 * 根据IDS获取商品信息
	 * 
	 * @param ids
	 * @return
	 */
	List<GoodsDetailDO> queryGoodsDetailByIds(List<Long> ids);

	/**
	 * 保存商品信息
	 * 
	 * @param goodsDetailDO
	 * @return
	 */
	Long saveGoodsDetail(GoodsDetailDO goodsDetailDO);

	/**
	 * 更新商品信息
	 * 
	 * @param goodsDetailDO
	 * @return
	 */
	boolean updateGoodsDetail(GoodsDetailDO goodsDetailDO);

	/**
	 * 搜索detail
	 * 
	 * @param title
	 * @param startRow
	 * @param pageSize
	 * @return
	 */
	List<GoodsDetailDO> querySmallGoodsDetail(String title, int startRow, int pageSize);
	
	int countGoodsDetails(String title);
	
	/**
	 * 根据标题查询商品，防止标题重复
	 * @param title
	 * @return
	 */
	boolean querySmallGoodsDetailByTitle(String title, Long categoryId);

}
