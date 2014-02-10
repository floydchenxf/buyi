package com.buyi.dal.entity.dataobject;

import com.buyi.dal.entity.BaseDO;

public class GoodsTypeDO extends BaseDO<Long> {

	private static final long serialVersionUID = 1L;

	/**
	 * 商品id
	 */
	private Long goodsId;

	/**
	 * 商品类型名
	 */
	private String typeName;

	/**
	 * 图片名称
	 */
	private String picName;

	/**
	 * 商品数量
	 */
	private Integer goodsNum;

	public Long getGoodsId() {
		return goodsId;
	}

	public void setGoodsId(Long goodsId) {
		this.goodsId = goodsId;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public String getPicName() {
		return picName;
	}

	public void setPicName(String picName) {
		this.picName = picName;
	}

	public Integer getGoodsNum() {
		return goodsNum;
	}

	public void setGoodsNum(Integer goodsNum) {
		this.goodsNum = goodsNum;
	}
}
