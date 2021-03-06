package com.buyi.domain.service.search.vo;

import com.buyi.domain.service.search.annotations.Index;
import com.buyi.domain.service.search.annotations.SearchableProperty;
import com.buyi.domain.service.search.annotations.Store;

public class GoodsDetailIndexVO {

	public static final String ID = "id";
	public static final String GOODS_TITLE = "goodsTitle";
	private static final String GOODS_DESC = "goodsDesc";
	private static final String GOODS_PIC = "goodsPic";
	private static final String GOODS_POSTFEE = "goodsPostfee";
	private static final String GMT_CREATE = "gmtCreate";
	private static final String CATEGORY_NAMES = "categoryNames";
	private static final String ORIGIN_PRICE = "originPrice";

	@SearchableProperty(name = ID, index = Index.NO, store = Store.YES)
	private Long id;

	// 标题，需要分析需要存储
	@SearchableProperty(name = GOODS_TITLE, index = Index.ANALYZED, store = Store.YES)
	private String goodsTitle;

	// 商品描述,需要分析，不需要存储
	@SearchableProperty(name = GOODS_DESC, index = Index.ANALYZED, store = Store.NO)
	private String goodsDesc;

	// 商品图片1
	@SearchableProperty(name = GOODS_PIC, index = Index.NOT_ANALYZED, store = Store.YES)
	private String goodsPic1;

	// 运费
	@SearchableProperty(name = GOODS_POSTFEE, index = Index.NOT_ANALYZED, store = Store.YES)
	private String postfee;

	// 类目名称
	@SearchableProperty(name = CATEGORY_NAMES, index = Index.ANALYZED, store = Store.NO)
	private String categoryNames;

	@SearchableProperty(name = GMT_CREATE, index = Index.NOT_ANALYZED, store = Store.YES)
	private Long gmtCreate;

	// 价格
	@SearchableProperty(name = ORIGIN_PRICE, index = Index.NOT_ANALYZED, store = Store.YES)
	private Integer originPrice;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getGoodsTitle() {
		return goodsTitle;
	}

	public void setGoodsTitle(String goodsTitle) {
		this.goodsTitle = goodsTitle;
	}

	public String getGoodsDesc() {
		return goodsDesc;
	}

	public void setGoodsDesc(String goodsDesc) {
		this.goodsDesc = goodsDesc;
	}

	public String getGoodsPic1() {
		return goodsPic1;
	}

	public void setGoodsPic1(String goodsPic1) {
		this.goodsPic1 = goodsPic1;
	}

	public String getPostfee() {
		return postfee;
	}

	public void setPostfee(String postfee) {
		this.postfee = postfee;
	}

	public String getCategoryNames() {
		return categoryNames;
	}

	public void setCategoryNames(String categoryNames) {
		this.categoryNames = categoryNames;
	}

	public Long getGmtCreate() {
		return gmtCreate;
	}

	public void setGmtCreate(Long gmtCreate) {
		this.gmtCreate = gmtCreate;
	}

	public Integer getOriginPrice() {
		return originPrice;
	}

	public void setOriginPrice(Integer originPrice) {
		this.originPrice = originPrice;
	}

}
