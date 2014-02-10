package com.buyi.webapp.managerment.vo;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

public class GoodsTypeForm {

	private Long id;

	/**
	 * 类型名称
	 */
	private String typeName;

	/**
	 * 该类型的商品数量
	 */
	private int goodsNum;
	/**
	 * 商品id
	 */
	private Long goodsId;

	/**
	 * 图片名称
	 */
	private String picName;

	@NotBlank(message="{goods.type.name}")
	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Min(value = 1, message = "{goods.num.input}")
	public int getGoodsNum() {
		return goodsNum;
	}

	public void setGoodsNum(int goodsNum) {
		this.goodsNum = goodsNum;
	}

	@NotNull
	public Long getGoodsId() {
		return goodsId;
	}

	public void setGoodsId(Long goodsId) {
		this.goodsId = goodsId;
	}

	@NotBlank(message="{goods.picname.input}")
	public String getPicName() {
		return picName;
	}

	public void setPicName(String picName) {
		this.picName = picName;
	}
}
