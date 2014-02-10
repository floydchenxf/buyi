package com.buyi.dal;

import java.util.List;

import com.buyi.dal.entity.dataobject.GoodsTypeDO;

public interface GoodsTypeDao {

	/**
	 * 查询商品对应的类型品种
	 * 
	 * @param goodsId
	 * @return
	 */
	List<GoodsTypeDO> queryGoodsTypeByGoodsId(Long goodsId);

	/**
	 * 保存商品品种
	 * 
	 * @param goodsType
	 * @return
	 */
	Long saveGoodsType(GoodsTypeDO goodsType);

	/**
	 * 更新商品品种
	 * 
	 * @param goodsType
	 * @return
	 */
	int updateGoodsType(GoodsTypeDO goodsType);

	/**
	 * 根据ID获取数据
	 * @param id
	 * @return
	 */
	GoodsTypeDO loadGoodsTypeById(Long id);

	boolean deleteGoodsType(Long id);

}
