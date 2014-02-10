package com.buyi.dal.entity.dataobject;

import java.util.ArrayList;
import java.util.List;

import com.buyi.dal.entity.BaseDO;

/**
 * 类目信息
 * 
 * @author cxf128
 * 
 */
public class CategoryDO extends BaseDO<Long> {

	private static final long serialVersionUID = -685305098120267128L;

	/**
	 * 类目名
	 */
	private String categoryName;

	/**
	 * 顺序
	 */
	private Integer order;

	private Long parentId;

	private Boolean leaf;

	private List<CategoryDO> subCategorys = new ArrayList<CategoryDO>();

	public Boolean getLeaf() {
		return leaf;
	}

	public void setLeaf(Boolean leaf) {
		this.leaf = leaf;
	}

	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	public Integer getOrder() {
		return order;
	}

	public void setOrder(Integer order) {
		this.order = order;
	}

	public List<CategoryDO> getSubCategorys() {
		return subCategorys;
	}

	public void setSubCategorys(List<CategoryDO> subCategorys) {
		this.subCategorys = subCategorys;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public void addSubCategory(CategoryDO categoryDO) {
		if (!subCategorys.contains(categoryDO)) {
			subCategorys.add(categoryDO);
		}
	}

}
