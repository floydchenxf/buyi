package com.buyi.domain.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.buyi.dal.GoodsDetailDao;
import com.buyi.dal.entity.dataobject.GoodsDetailDO;
import com.buyi.domain.service.GoodsDetailService;

@Service("goodsDetailService")
public class GoodsDetailServiceImpl implements GoodsDetailService {

	@Autowired
	private GoodsDetailDao goodsDetailDao;

	@Override
	public GoodsDetailDO queryGoodsDetailById(Long id) {
		return goodsDetailDao.queryGoodsDetailById(id);
	}

	@Override
	public List<GoodsDetailDO> queryGoodsDetailByIds(List<Long> ids) {
		return goodsDetailDao.queryGoodsDetailByIds(ids);
	}

	@Override
	public Long saveGoodsDetail(GoodsDetailDO goodsDetailDO) {
		return goodsDetailDao.saveGoodsDetail(goodsDetailDO);
	}

	@Override
	public boolean updateGoodsDetail(GoodsDetailDO goodsDetailDO) {
		return goodsDetailDao.updateGoodsDetail(goodsDetailDO);
	}

	@Override
	public List<GoodsDetailDO> querySmallGoodsDetail(String title, int startRow, int pageSize) {
		return goodsDetailDao.searchGoodsDetails(title, startRow, pageSize);
	}

	@Override
	public int countGoodsDetails(String title) {
		return goodsDetailDao.countGoodsDetails(title);
	}

	@Override
	public boolean querySmallGoodsDetailByTitle(String title, Long categoryId) {
		return goodsDetailDao.querySmallGoodsDetailByTitle(title, categoryId);
	}

}
