package com.buyi.webapp.common;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.buyi.domain.service.CategoryService;
import com.buyi.domain.vo.CategoryVO;
import com.buyi.util.UrlUtil;

@Controller
public class MarketController {
	
	@Autowired
	private CategoryService categoryService;
	
	@RequestMapping(value={UrlUtil.MARKET_INDEX})
	public void showMarket(Model model) {
		List<CategoryVO> categorys = categoryService.getRootCategorys();
		model.addAttribute("categorys", categorys);
	}

}
