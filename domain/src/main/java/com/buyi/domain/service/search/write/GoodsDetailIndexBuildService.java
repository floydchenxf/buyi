package com.buyi.domain.service.search.write;

import java.util.List;

import com.buyi.dal.entity.dataobject.GoodsDetailDO;

/**
 * 构建商品索引接口
 * 
 * @author cxf128
 * 
 */
public interface GoodsDetailIndexBuildService {
	
	 /**
     * 将商品已经商品关系，保存到搜索引擎
     * 
     * @param goods
     */
    void buildGoodsIndex(GoodsDetailDO goodsDetail, boolean isback);

    /**
     * 将商品已经商品关系，保存到搜索引擎
     * 
     * @param goodsList
     */
    void saveSearchGoodsBatch(List<GoodsDetailDO> goodsList, boolean isback);
    
    /**
     * 将商品从索引删除
     * @param goodsDetail
     * @param isback
     */
    void deleteGoodsIndex(Long goodsId, boolean isback);

    void swtichOn();


}
