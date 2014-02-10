package com.buyi.dal.entity.dataobject;

import net.sf.json.JSONObject;

import com.buyi.dal.entity.BaseDO;
import com.buyi.dal.entity.viewobject.GoodsStatus;
import com.buyi.dal.entity.viewobject.Money;
import com.buyi.dal.entity.viewobject.PostfeeVO;

public class GoodsDetailDO extends BaseDO<Long> {

	private static final long serialVersionUID = 1L;

	/**
	 * 商品标题
	 */
	private String title;

	/**
	 * 最初价格
	 */
	private Money originPrice;

	/**
	 * 运费
	 */
	private String postfee;

	/**
	 * 商品数量
	 */
	private Integer goodsNum;

	/**
	 * 商品状态
	 */
	private GoodsStatus status;

	/**
	 * 商品图片1
	 */
	private String goodsPic1;

	/**
	 * 商品图片2
	 */
	private String goodsPic2;

	/**
	 * 商品图片3
	 */
	private String goodsPic3;

	/**
	 * 商品详情
	 */
	private String goodsDesc;

	/**
	 * 类目id
	 */
	private Long categoryId;

	public Long getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Money getOriginPrice() {
		return originPrice;
	}

	public void setOriginPrice(Money originPrice) {
		this.originPrice = originPrice;
	}

	public String getPostfee() {
		return postfee;
	}

	public void setPostfee(String postfee) {
		this.postfee = postfee;
	}

	public Integer getGoodsNum() {
		return goodsNum;
	}

	public void setGoodsNum(Integer goodsNum) {
		this.goodsNum = goodsNum;
	}

	public GoodsStatus getStatus() {
		return status;
	}

	public void setStatus(GoodsStatus status) {
		this.status = status;
	}

	public String getGoodsPic1() {
		return goodsPic1;
	}

	public void setGoodsPic1(String goodsPic1) {
		this.goodsPic1 = goodsPic1;
	}

	public String getGoodsPic2() {
		return goodsPic2;
	}

	public void setGoodsPic2(String goodsPic2) {
		this.goodsPic2 = goodsPic2;
	}

	public String getGoodsPic3() {
		return goodsPic3;
	}

	public void setGoodsPic3(String goodsPic3) {
		this.goodsPic3 = goodsPic3;
	}

	public String getGoodsDesc() {
		return goodsDesc;
	}

	public void setGoodsDesc(String goodsDesc) {
		this.goodsDesc = goodsDesc;
	}

	public PostfeeVO getPostfeeVO() {
		PostfeeVO result = null;
		if (postfee != null) {
			JSONObject json = JSONObject.fromObject(postfee);
			result = (PostfeeVO) JSONObject.toBean(json, PostfeeVO.class); 
		}
		return result;
	}
	
	public void setPostfeeVO(PostfeeVO vo) {
		JSONObject json = JSONObject.fromObject(vo);
		this.postfee = json.toString();
	}

}
