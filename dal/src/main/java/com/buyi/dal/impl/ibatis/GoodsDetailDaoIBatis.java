package com.buyi.dal.impl.ibatis;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.buyi.dal.GoodsDetailDao;
import com.buyi.dal.entity.dataobject.GoodsDetailDO;

@Repository("goodsDetailDao")
public class GoodsDetailDaoIBatis extends BaseDaoIBatis<GoodsDetailDO>
		implements GoodsDetailDao {

	private static final String UPDATE_GOODS_DETAIL_INFO = "UPDATE_GOODS_DETAIL_INFO";
	private static final String GOODS_DETAIL_INSERT = "GOODS_DETAIL_INSERT";
	private static final String QUERY_GOODS_DETAIL_BY_IDS = "QUERY_GOODS_DETAIL_BY_IDS";
	private static final String COUNT_GOODS_DETAIL_BY_TITLE = "COUNT_GOODS_DETAIL_BY_TITLE";
	private static final String QUERY_SMALL_GOODS_DETAIL_BY_TITLE = "QUERY_SMALL_GOODS_DETAIL_BY_TITLE";
	private static final String QUERY_SMALL_GOODS_DETAIL_BY_TITLE_UNLIKE = "QUERY_SMALL_GOODS_DETAIL_BY_TITLE_UNLIKE";

	@Override
	public GoodsDetailDO queryGoodsDetailById(Long id) {
		List<Long> ids = new ArrayList<Long>();
		ids.add(id);
		List<GoodsDetailDO> resultList = this.queryGoodsDetailByIds(ids);
		if (resultList == null || resultList.isEmpty()) {
			return null;
		}

		return resultList.get(0);
	}

	@SuppressWarnings("unchecked")
	public List<GoodsDetailDO> queryGoodsDetailByIds(List<Long> ids) {
		Map<String, Object> idMap = new HashMap<String, Object>();
		idMap.put("ids", ids);
		List<GoodsDetailDO> goodsDetails = (List<GoodsDetailDO>) super
				.getSqlMapClientTemplate().queryForList(QUERY_GOODS_DETAIL_BY_IDS, ids);
		return goodsDetails;
	}

	@Override
	public Long saveGoodsDetail(GoodsDetailDO goodsDetailDO) {
		return (Long) super.getSqlMapClientTemplate().insert(GOODS_DETAIL_INSERT, goodsDetailDO);
	}

	@Override
	public boolean updateGoodsDetail(GoodsDetailDO goodsDetailDO) {
		int result = super.getSqlMapClientTemplate().update(
				UPDATE_GOODS_DETAIL_INFO, goodsDetailDO);
		return result > 0;
	}

	@SuppressWarnings("unchecked")
	public List<GoodsDetailDO> searchGoodsDetails(String title, int startRow, int pageSize) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("title", title);
		params.put("startRow", startRow);
		params.put("pageSize", pageSize);
		return super.getSqlMapClientTemplate().queryForList(QUERY_SMALL_GOODS_DETAIL_BY_TITLE, params);
	}
	
	public int countGoodsDetails(String title) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("title", title);
		return (Integer)super.getSqlMapClientTemplate().queryForObject(COUNT_GOODS_DETAIL_BY_TITLE, params);
	}

	@Override
	public boolean querySmallGoodsDetailByTitle(String title, Long categoryId) {
		Map<String, Object> parmas = new HashMap<String, Object>();
		parmas.put("title", title);
		parmas.put("categoryId", categoryId);
		GoodsDetailDO detail = (GoodsDetailDO) super.getSqlMapClientTemplate().queryForObject(QUERY_SMALL_GOODS_DETAIL_BY_TITLE_UNLIKE, parmas);
		return detail != null && detail.getTitle() != null;
	}

}
