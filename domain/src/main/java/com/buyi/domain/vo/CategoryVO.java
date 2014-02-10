package com.buyi.domain.vo;

public class CategoryVO {

	private Long id;

	/**
	 * 类目名
	 */
	private String categoryName;

	/**
	 * 顺序
	 */
	private Integer order;

	/**
	 * 是否是叶子节点
	 */
	private boolean leaf;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public Integer getOrder() {
		return order;
	}

	public void setOrder(Integer order) {
		this.order = order;
	}

	public boolean isLeaf() {
		return leaf;
	}

	public void setLeaf(boolean leaf) {
		this.leaf = leaf;
	}

}
