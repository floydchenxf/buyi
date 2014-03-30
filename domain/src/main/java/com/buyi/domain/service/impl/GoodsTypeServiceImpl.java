package com.buyi.domain.service.impl;

import java.io.File;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.buyi.dal.GoodsTypeDao;
import com.buyi.dal.entity.dataobject.GoodsTypeDO;
import com.buyi.domain.service.GoodsTypeService;
import com.buyi.domain.vo.FileDeleteThread;
import com.buyi.domain.vo.FileUploadInfo;

@Service("goodsTypeService")
public class GoodsTypeServiceImpl implements GoodsTypeService {

	@Autowired
	private GoodsTypeDao goodsTypeDao;

	@Autowired
	private FileUploadInfo fileUploadInfo;

	@Override
	public List<GoodsTypeDO> fetchGoodsTypesByGoodsId(Long goodsId) {
		return goodsTypeDao.queryGoodsTypeByGoodsId(goodsId);
	}

	@Override
	public boolean updateGoodsType(GoodsTypeDO goodsTypeDO) {
		return goodsTypeDao.updateGoodsType(goodsTypeDO) > 0;
	}

	@Override
	public Long insertGoodsType(GoodsTypeDO goodsTypeDO) {
		return goodsTypeDao.saveGoodsType(goodsTypeDO);
	}

	@Override
	public boolean deleteGoodsType(Long id) {
		GoodsTypeDO typeDO = goodsTypeDao.loadGoodsTypeById(id);
		deleteGoodsTypePic(typeDO);
		return goodsTypeDao.deleteGoodsType(id);
	}

	/**
	 * 线程删除商品类型图片
	 * 
	 * @param goodsTypeDO
	 */
	private void deleteGoodsTypePic(GoodsTypeDO goodsTypeDO) {
		String filename = goodsTypeDO.getPicName();
		if (filename != null) {
			String imagePath = fileUploadInfo.getImagePath(filename);
			File bigImageFile = new File(imagePath);
			new FileDeleteThread(bigImageFile);

			String smallImagePath = fileUploadInfo.getSmallImagePath(filename);
			File smallImageFile = new File(smallImagePath);
			new FileDeleteThread(smallImageFile);

			String tinyImagePath = fileUploadInfo.getTinyImagePath(filename);
			File tinyImageFile = new File(tinyImagePath);
			new FileDeleteThread(tinyImageFile);
		}
	}

	@Override
	public GoodsTypeDO loadGoodsTypeById(Long id) {
		return goodsTypeDao.loadGoodsTypeById(id);
	}


	@Override
	public boolean deleteGoodsTypeByGoodsId(Long id) {
		// 删除图片
		List<GoodsTypeDO> goodsTypeDOs = fetchGoodsTypesByGoodsId(id);
		if (goodsTypeDOs != null && !goodsTypeDOs.isEmpty()) {
			for (GoodsTypeDO typeDo : goodsTypeDOs) {
				deleteGoodsTypePic(typeDo);
			}
		}
		// 删除数据库信息
		boolean result = goodsTypeDao.deleteGoodsTypeByGoodsId(id);
		return result;
	}

}
