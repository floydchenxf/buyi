package com.buyi.dal.impl.ibatis;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.buyi.dal.GoodsTypeDao;
import com.buyi.dal.entity.dataobject.GoodsTypeDO;

@Repository("goodsTypeDao")
public class GoodsTypeDaoIBatis extends BaseDaoIBatis<GoodsTypeDO> implements GoodsTypeDao {

	private static final String DELETE_GOODS_TYPE_BY_GOODS_ID = "DELETE_GOODS_TYPE_BY_GOODS_ID";
	private static final String UPDATE_GOODS_TYPE = "UPDATE_GOODS_TYPE";
	private static final String QUERY_GOODS_TYPE_BY_GOODS_ID = "QUERY_GOODS_TYPE_BY_GOODS_ID";
	private static final String GOODS_TYPE_DO_INSERT = "GOODS_TYPE_DO_INSERT";
	private static final String QUERY_GOODS_TYPE_BY_ID = "QUERY_GOODS_TYPE_BY_ID";
	private static final String DELETE_GOODS_TYPE_BY_ID = "DELETE_GOODS_TYPE_BY_ID";

	@SuppressWarnings("unchecked")
	public List<GoodsTypeDO> queryGoodsTypeByGoodsId(Long goodsId) {
		List<GoodsTypeDO> goodsTypes = super.getSqlMapClientTemplate().queryForList(QUERY_GOODS_TYPE_BY_GOODS_ID, goodsId);
		return goodsTypes;
	}

	@Override
	public Long saveGoodsType(GoodsTypeDO goodsType) {
		return (Long) super.getSqlMapClientTemplate().insert(GOODS_TYPE_DO_INSERT, goodsType);
	}

	@Override
	public int updateGoodsType(GoodsTypeDO goodsType) {
		return super.getSqlMapClientTemplate().update(UPDATE_GOODS_TYPE, goodsType);
	}

	@Override
	public GoodsTypeDO loadGoodsTypeById(Long id) {
		return (GoodsTypeDO) super.getSqlMapClientTemplate().queryForObject(QUERY_GOODS_TYPE_BY_ID, id);
	}

	@Override
	public boolean deleteGoodsType(Long id) {
		return super.getSqlMapClientTemplate().delete(DELETE_GOODS_TYPE_BY_ID, id) > 0;
	}

	@Override
	public boolean deleteGoodsTypeByGoodsId(Long goodsId) {
		return super.getSqlMapClientTemplate().delete(DELETE_GOODS_TYPE_BY_GOODS_ID,goodsId) > 0;
	}

}
