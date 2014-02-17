package com.buyi.domain.service.impl;

import java.io.File;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.buyi.dal.GoodsTypeDao;
import com.buyi.dal.entity.dataobject.GoodsTypeDO;
import com.buyi.domain.service.GoodsTypeService;
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
		String filename = typeDO.getPicName();
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
		return goodsTypeDao.deleteGoodsType(id);
	}

	@Override
	public GoodsTypeDO loadGoodsTypeById(Long id) {
		return goodsTypeDao.loadGoodsTypeById(id);
	}
	
	public static class FileDeleteThread extends Thread {
		private File file;
		
		public FileDeleteThread(File file) {
			this.file = file;
			this.start();
		}

		@Override
		public void run() {
			if (file.exists()) {
				file.delete();
			}
		}
		
		
			
	}

}
