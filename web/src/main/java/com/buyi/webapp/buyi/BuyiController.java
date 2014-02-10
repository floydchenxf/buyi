package com.buyi.webapp.buyi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.buyi.dal.GoodsDetailDao;
import com.buyi.dal.GoodsTypeDao;
import com.buyi.dal.entity.dataobject.GoodsDetailDO;
import com.buyi.dal.entity.dataobject.GoodsTypeDO;
import com.buyi.dal.entity.viewobject.GoodsStatus;
import com.buyi.dal.entity.viewobject.Money;
import com.buyi.util.UrlUtil;

@Controller
public class BuyiController {

	@Autowired
	private GoodsDetailDao goodsDetailDao;

	@Autowired
	private GoodsTypeDao goodsTypeDao;

	@RequestMapping(value = { UrlUtil.BUYI_SHOW })
	public void show(Model model) {
		// model.addAttribute("user", "nihao");
		// GoodsDetailDO goodsDetailDO = new GoodsDetailDO();
		// goodsDetailDO.setGoodsNum(123);
		// goodsDetailDO.setPostfee(new Money(500));
		// Money price =new Money(2340);
		// goodsDetailDO.setOriginPrice(price);
		// goodsDetailDO.setNowPrice(price);
		// goodsDetailDO.setTitle("cxf测试数据");
		// goodsDetailDO.setGoodsPic1("http://www.sina.com");
		// goodsDetailDO.setGoodsDesc("<html><body>wuliao a</body></html>");
		// goodsDetailDO.setStatus(GoodsStatus.ONLINE);
		// goodsDetailDO.setCategoryId(2l);
		// Long goodsId = goodsDetailDao.saveGoodsDetail(goodsDetailDO);
		//
		// GoodsDetailDO databaseRecord =
		// goodsDetailDao.queryGoodsDetailById(goodsId);
		// model.addAttribute("goodsDetailDO", databaseRecord);
		//
		// GoodsTypeDO goodsTypeDO = new GoodsTypeDO();
		// goodsTypeDO.setBigPic("http://www.sina.com");
		// goodsTypeDO.setSmallPic("http://sss.com");
		// goodsTypeDO.setTypeName("单元测试");
		// goodsTypeDO.setGoodsId(goodsId);
		// goodsTypeDao.saveGoodsType(goodsTypeDO);
	}

	@RequestMapping(value = { UrlUtil.BUYI_MANAGERMENT })
	public void managerShow(Model model) {

	}
}
