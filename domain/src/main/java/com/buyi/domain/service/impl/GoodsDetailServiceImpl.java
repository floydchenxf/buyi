package com.buyi.domain.service.impl;

import java.io.File;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.buyi.dal.GoodsDetailDao;
import com.buyi.dal.entity.dataobject.GoodsDetailDO;
import com.buyi.domain.service.GoodsDetailService;
import com.buyi.domain.vo.FileDeleteThread;
import com.buyi.domain.vo.FileUploadInfo;

@Service("goodsDetailService")
public class GoodsDetailServiceImpl implements GoodsDetailService {

	@Autowired
	private GoodsDetailDao goodsDetailDao;
	
	@Autowired
	private FileUploadInfo fileUploadInfo;

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

	@Override
	public boolean deleteGoodsDetail(Long goodsId) {
		// 删除商品图片
		GoodsDetailDO goodsDetailDO = queryGoodsDetailById(goodsId);
		if (goodsDetailDO != null) {
			String pic1 = goodsDetailDO.getGoodsPic1();
			if (!StringUtils.isEmpty(pic1)) {
				String pic1Path = fileUploadInfo.getImagePath(pic1);
				String smallPic1Path = fileUploadInfo.getSmallImagePath(pic1);
				String searchPic1Path = fileUploadInfo.getSearchImagePath(pic1);
				String tinyPic1Image = fileUploadInfo.getTinyImagePath(pic1);
				new FileDeleteThread(new File(pic1Path));
				new FileDeleteThread(new File(smallPic1Path));
				new FileDeleteThread(new File(searchPic1Path));
				new FileDeleteThread(new File(tinyPic1Image));
			}
			
			String pic2 = goodsDetailDO.getGoodsPic2();
			if (!StringUtils.isEmpty(pic2)) {
				String pic2Path = fileUploadInfo.getImagePath(pic2);
				String smallPic2Path = fileUploadInfo.getSmallImagePath(pic2);
				String tinyPic2Image = fileUploadInfo.getTinyImagePath(pic2);
				new FileDeleteThread(new File(pic2Path));
				new FileDeleteThread(new File(smallPic2Path));
				new FileDeleteThread(new File(tinyPic2Image));
			}
			
			String pic3 = goodsDetailDO.getGoodsPic3();
			if (!StringUtils.isEmpty(pic3)) {
				String pic3Path = fileUploadInfo.getImagePath(pic3);
				String smallPic3Path = fileUploadInfo.getSmallImagePath(pic3);
				String tinyPic3Image = fileUploadInfo.getTinyImagePath(pic3);
				new FileDeleteThread(new File(pic3Path));
				new FileDeleteThread(new File(smallPic3Path));
				new FileDeleteThread(new File(tinyPic3Image));
			}
		}
		// 删除商品信息
		return goodsDetailDao.deleteGoodsDetailById(goodsId);
	}

	@Override
	public boolean offlineGoods(Long goodsId) {
		return goodsDetailDao.offlineGoods(goodsId);
	}

}
