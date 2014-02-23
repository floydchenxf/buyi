package com.buyi.webapp.common;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageableEx;
import org.springframework.data.web.PageableDefaults;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

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
		List<CategoryVO> categorys = categoryService.getRootCategorys();
		model.addAttribute("categorys", categorys);
	}

	/**
	 * 用户搜索
	 * 
	 * @param model
	 * @param keyword
	 * @param pageable
	 */
	@RequestMapping(value = { UrlUtil.MARKET_GOODS_SEARCH }, method = RequestMethod.GET)
	public void goodsSearch(Model model, @RequestParam(value = "q", required = true) String keyword, @PageableDefaults(16) PageableEx pageable) {
		List<GoodsDetailDO> goodsDetailDOs = goodsDetailIndexSearchService.searchGoodsDetails(keyword, pageable);
		model.addAttribute("goodsDetailList", goodsDetailDOs);
		model.addAttribute("pageable", pageable);
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
