package com.buyi.domain.service;

import java.util.List;

import com.buyi.dal.entity.dataobject.GoodsTypeDO;

public interface GoodsTypeService {

	/**
	 * 根据goodsId获取该商品的类型
	 * 
	 * @param goodsId
	 * @return
	 */
	List<GoodsTypeDO> fetchGoodsTypesByGoodsId(Long goodsId);

	/**
	 * 更新goodsType
	 * 
	 * @param goodsTypeDO
	 * @return
	 */
	boolean updateGoodsType(GoodsTypeDO goodsTypeDO);

	/**
	 * 添加goodsType
	 * 
	 * @param goodsTypeDO
	 * @return
	 */
	Long insertGoodsType(GoodsTypeDO goodsTypeDO);

	/**
	 * 删除goodsType
	 * 
	 * @param id
	 * @return
	 */
	boolean deleteGoodsType(Long id);

	/**
	 * 装载数据根据ID
	 * 
	 * @param id
	 * @return
	 */
	GoodsTypeDO loadGoodsTypeById(Long id);

	/**
	 * 根据商品id删除商品类型
	 * @param id
	 * @return
	 */
	boolean deleteGoodsTypeByGoodsId(Long id);

}
