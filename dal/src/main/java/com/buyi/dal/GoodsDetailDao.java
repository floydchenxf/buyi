package com.buyi.dal;

import java.util.List;

import com.buyi.dal.entity.dataobject.GoodsDetailDO;

public interface GoodsDetailDao {

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
	
	List<GoodsDetailDO> searchGoodsDetails(String title, int startRow, int pageSize);
	
	int countGoodsDetails(String title);
	
	boolean querySmallGoodsDetailByTitle(String title, Long goodsId);

	/**
	 * 删除商品
	 * @param goodsId
	 * @return
	 */
	boolean deleteGoodsDetailById(Long goodsId);

	/**
	 * 下架商品
	 * @param goodsId
	 * @return
	 */
	boolean offlineGoods(Long goodsId);

}
