package com.buyi.webapp.buyi;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.buyi.util.UrlUtil;

@Controller
public class BuyiController {

	@RequestMapping(value = { UrlUtil.BUYI_SHOW })
	public void show(Model model) {

	}

	@RequestMapping(value = { UrlUtil.BUYI_LOGIN }, method = RequestMethod.GET)
	public void login(Model model) {

	}

	@RequestMapping(value = { UrlUtil.BUYI_MANAGERMENT })
	public void managerShow(Model model) {

	}
}
