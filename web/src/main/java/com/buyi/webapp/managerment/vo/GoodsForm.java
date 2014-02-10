package com.buyi.webapp.managerment.vo;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;

public class GoodsForm {
	
	private Long id;

	/**
	 * 商品名称
	 */
	private String goodsTitle;

	/**
	 * 商品价格
	 */
	private String goodsPrice;

	private Integer postfeeType;

	/**
	 * 平邮
	 */
	private Integer pinyou;

	/**
	 * 快递
	 */
	private Integer kuaidi;

	/**
	 * EMS
	 */
	private Integer ems;

	private Long categoryId;

	private String goodsContent;
	
	private String picNames;
	
	public String getPicNames() {
		return picNames;
	}

	public void setPicNames(String picNames) {
		this.picNames = picNames;
	}

	@Size(min = 6, max = 200, message = "{goods.title.range}")
	public String getGoodsTitle() {
		return goodsTitle;
	}

	public void setGoodsTitle(String goodsTitle) {
		this.goodsTitle = goodsTitle;
	}

	@NotBlank(message = "{goods.price.input}")
	@Pattern(regexp = "[0-9]+\\.[0-9]{2}", message = "{goods.price.format}")
	public String getGoodsPrice() {
		return goodsPrice;
	}

	public void setGoodsPrice(String goodsPrice) {
		this.goodsPrice = goodsPrice;
	}

	public Integer getPinyou() {
		return pinyou;
	}

	public void setPinyou(Integer pinyou) {
		this.pinyou = pinyou;
	}

	public Integer getKuaidi() {
		return kuaidi;
	}

	public void setKuaidi(Integer kuaidi) {
		this.kuaidi = kuaidi;
	}

	public Integer getEms() {
		return ems;
	}

	public void setEms(Integer ems) {
		this.ems = ems;
	}

	public Long getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
	}

	@Size(min = 6, max = 1000, message = "{goods.content.range}")
	public String getGoodsContent() {
		return goodsContent;
	}

	public void setGoodsContent(String goodsContent) {
		this.goodsContent = goodsContent;
	}

	public Integer getPostfeeType() {
		return postfeeType;
	}

	public void setPostfeeType(Integer postfeeType) {
		this.postfeeType = postfeeType;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
}
