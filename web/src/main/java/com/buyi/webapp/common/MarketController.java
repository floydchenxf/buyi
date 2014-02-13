package com.buyi.webapp.common;

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
import com.buyi.domain.service.CategoryService;
import com.buyi.domain.service.search.read.GoodsDetailIndexSearchService;
import com.buyi.domain.vo.CategoryVO;
import com.buyi.util.UrlUtil;

@Controller
public class MarketController {

	@Autowired
	private CategoryService categoryService;

	@Autowired
	private GoodsDetailIndexSearchService goodsDetailIndexSearchService;

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
	public void goodsSearch(Model model, @RequestParam(value = "q", required = true) String keyword, @PageableDefaults(10) PageableEx pageable) {
		List<GoodsDetailDO> goodsDetailDOs = goodsDetailIndexSearchService.searchGoodsDetails(keyword, pageable);
		model.addAttribute("goodsDetailList", goodsDetailDOs);
	}

}
