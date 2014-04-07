package com.buyi.webapp.common;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequestEx;
import org.springframework.data.domain.PageableEx;
import org.springframework.data.web.PageableDefaults;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.buyi.dal.entity.dataobject.CategoryDO;
import com.buyi.dal.entity.dataobject.GoodsDetailDO;
import com.buyi.dal.entity.dataobject.GoodsTypeDO;
import com.buyi.domain.service.CategoryService;
import com.buyi.domain.service.GoodsDetailService;
import com.buyi.domain.service.GoodsTypeService;
import com.buyi.domain.service.search.read.GoodsDetailIndexSearchService;
import com.buyi.domain.vo.CategoryVO;
import com.buyi.util.UrlUtil;

@Controller
public class MarketController {

	@Autowired
	private CategoryService categoryService;

	@Autowired
	private GoodsDetailIndexSearchService goodsDetailIndexSearchService;

	@Autowired
	private GoodsTypeService goodsTypeService;

	@Autowired
	private GoodsDetailService goodsDetailService;

	@RequestMapping(value = { UrlUtil.MARKET_INDEX })
	public void showMarket(Model model) {
		List<CategoryDO> categorys = categoryService.getCategorys();
		model.addAttribute("categorys", categorys);
		//绒布
		PageableEx pageableEx = new PageRequestEx(0, 11, 0);
		List<GoodsDetailDO> rongbu = goodsDetailIndexSearchService.searchGoodsDetails(1l, null, pageableEx);
		//仿麻
		List<GoodsDetailDO> fangbu = goodsDetailIndexSearchService.searchGoodsDetails(2l, null, pageableEx);
		//雪尼尔
		List<GoodsDetailDO> xnrbu = goodsDetailIndexSearchService.searchGoodsDetails(3l, null, pageableEx);
		//仿皮
		List<GoodsDetailDO> fangpi = goodsDetailIndexSearchService.searchGoodsDetails(4l, null, pageableEx);
		model.addAttribute("rongbu", rongbu);
		model.addAttribute("fangbu", fangbu);
		model.addAttribute("xnrbu", xnrbu);
		model.addAttribute("fangpi", fangpi);
	}

	/**
	 * 用户搜索
	 * 
	 * @param model
	 * @param keyword
	 * @param pageable
	 */
	@RequestMapping(value = { UrlUtil.MARKET_GOODS_SEARCH }, method = RequestMethod.GET)
	public void goodsSearch(Model model, @RequestParam(value = "c", required = false) Long categoryId, @RequestParam(value = "q", required = false) String keyword, @PageableDefaults(12) PageableEx pageable) {
		List<GoodsDetailDO> goodsDetailDOs = goodsDetailIndexSearchService.searchGoodsDetails(categoryId, keyword, pageable);
		List<CategoryDO> categoryTree = categoryService.getCategorys();
		model.addAttribute("goodsDetailList", goodsDetailDOs);
		model.addAttribute("pageable", pageable);
		model.addAttribute("categoryTree", categoryTree);
		if (categoryId != null) {
			List<CategoryVO> categoryViews = categoryService.queryCategoryTree(categoryId);
			List<CategoryVO> revertView = new ArrayList<CategoryVO>();
			for(int i=0, size=categoryViews.size(); i< size; i++) {
				revertView.add(categoryViews.get(size - (i + 1)));
			}
			model.addAttribute("cateoryViewList", revertView);
		}
	}

	@RequestMapping(value = { UrlUtil.MARKET_GOODS_SHOW }, method = RequestMethod.GET)
	public void goodsShow(Model model, @RequestParam(value = "id", required = true) Long goodsId) {
		GoodsDetailDO goodsDetail = goodsDetailService.queryGoodsDetailById(goodsId);
		Long categoryId = goodsDetail.getCategoryId();
		model.addAttribute("goodsDetail", goodsDetail);
		List<CategoryVO> categoryTree = categoryService.queryCategoryTree(categoryId);
		if (categoryTree.size() > 0) {
			List<CategoryVO> categoryList = new ArrayList<CategoryVO>();
			for (int i = categoryTree.size() - 1; i >= 0; i--) {
				categoryList.add(categoryTree.get(i));
			}
			model.addAttribute("categoryList", categoryList);
		}

		List<GoodsTypeDO> goodsTypes = goodsTypeService.fetchGoodsTypesByGoodsId(goodsId);
		model.addAttribute("goodsTypes", goodsTypes);
	}

}
