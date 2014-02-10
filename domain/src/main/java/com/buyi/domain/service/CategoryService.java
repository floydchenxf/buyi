package com.buyi.domain.service;

import java.util.List;

import com.buyi.domain.vo.CategoryVO;

public interface CategoryService {

	/**
	 * 获取根类目
	 * 
	 * @return
	 */
	List<CategoryVO> getRootCategorys();

	/**
	 * 根据类目id获取子类目信息
	 * 
	 * @param id
	 * @return
	 */
	List<CategoryVO> queryCategoryByParentId(Long id);

	/**
	 * 根据id获取当前类目树
	 * 
	 * @param id
	 * @return
	 */
	List<CategoryVO> queryCategoryTree(Long id);

}
