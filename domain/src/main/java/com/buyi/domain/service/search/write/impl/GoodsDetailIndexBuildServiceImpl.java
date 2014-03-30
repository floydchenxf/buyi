package com.buyi.domain.service.search.write.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.lucene.index.Term;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.buyi.dal.entity.dataobject.GoodsDetailDO;
import com.buyi.domain.service.CategoryService;
import com.buyi.domain.service.search.vo.GoodsDetailIndexVO;
import com.buyi.domain.service.search.write.GoodsDetailIndexBuildService;
import com.buyi.domain.service.search.write.IndexWriterBaseService;
import com.buyi.domain.vo.CategoryVO;

@Service("goodsDetailIndexBuildService")
public class GoodsDetailIndexBuildServiceImpl extends IndexWriterBaseService<GoodsDetailIndexVO> implements GoodsDetailIndexBuildService {

	@Autowired
	private CategoryService categoryService;

	public GoodsDetailIndexBuildServiceImpl() {
		super(GoodsDetailIndexVO.class);
	}

	@Override
	public void buildGoodsIndex(GoodsDetailDO goodsDetail, boolean isback) {
		GoodsDetailIndexVO goodsDetailIndexVO = convertD2V(goodsDetail);
		super.createIndex(goodsDetailIndexVO, isback);
	}

	@Override
	public void saveSearchGoodsBatch(List<GoodsDetailDO> goodsList, boolean isback) {
		List<GoodsDetailIndexVO> docList = new ArrayList<GoodsDetailIndexVO>(goodsList.size());
		for (GoodsDetailDO goods : goodsList) {
			docList.add(this.convertD2V(goods));
		}

		super.createIndexes(docList, isback);
	}

	@Override
	public void swtichOn() {
		super.switchOn();
	}

	private GoodsDetailIndexVO convertD2V(GoodsDetailDO goodsDetail) {
		GoodsDetailIndexVO vo = new GoodsDetailIndexVO();
		vo.setId(goodsDetail.getId()+"");
		vo.setGoodsDesc(goodsDetail.getGoodsDesc());
		vo.setGmtCreate(goodsDetail.getGmtCreate().getTime());
		vo.setOriginPrice(goodsDetail.getOriginPrice().getAmount());
		vo.setGoodsPic1(goodsDetail.getGoodsPic1());
		vo.setPostfee(goodsDetail.getPostfee());
		vo.setGoodsTitle(goodsDetail.getTitle());
		Long cateId = goodsDetail.getCategoryId();
		List<CategoryVO> categoryVOs = categoryService.queryCategoryTree(cateId);
		StringBuilder categoryBulder = new StringBuilder();
		if (categoryVOs != null && !categoryVOs.isEmpty()) {
			for (CategoryVO categoryVO : categoryVOs) {
				categoryBulder.append(categoryVO.getId()).append(" ");
			}
		}
		vo.setCategoryIds(categoryBulder.toString());
		return vo;
	}

	@Override
	public void deleteGoodsIndex(Long goodsId, boolean isback) {
		Term term = new Term(GoodsDetailIndexVO.ID, goodsId + "");
		super.deleteIndex(term, isback);
	}
}
