package com.buyi.domain.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.buyi.dal.CategoryDao;
import com.buyi.dal.entity.dataobject.CategoryDO;
import com.buyi.domain.service.CategoryService;
import com.buyi.domain.vo.CategoryVO;

@Service("categoryService")
public class CategoryServiceImpl implements CategoryService, InitializingBean {

	@Autowired
	private CategoryDao categoryDao;

	private static final List<CategoryDO> result = new ArrayList<CategoryDO>();

	private void init() {
		Map<Long, CategoryDO> categoryMap = categoryDao.fetchCategoryMap();

		if (categoryMap == null || categoryMap.isEmpty()) {
			return;
		}

		for (Map.Entry<Long, CategoryDO> ent : categoryMap.entrySet()) {
			CategoryDO categoryDO = ent.getValue();
			Long parentId = categoryDO.getParentId();
			if (parentId == null) {
				result.add(categoryDO);
			} else {
				CategoryDO parentCategory = categoryMap.get(parentId);
				if (parentCategory != null) {
					parentCategory.addSubCategory(categoryDO);
				}
			}
		}
	}

	public List<CategoryVO> queryCategoryByParentId(Long id) {
		List<CategoryVO> cResult = new ArrayList<CategoryVO>();
		if (id == null) {
			return cResult;
		}

		Map<Long, CategoryDO> categoryMap = categoryDao.fetchCategoryMap();
		if (categoryMap.isEmpty()) {
			return cResult;
		}

		CategoryDO category = categoryMap.get(id);
		if (category != null) {
			List<CategoryDO> categoryDOs = category.getSubCategorys();
			Collections.sort(categoryDOs);
			if (!categoryDOs.isEmpty()) {
				for (CategoryDO c : categoryDOs) {
					CategoryVO vo = this.covertObj(c);
					cResult.add(vo);
				}
			}
		}
		return cResult;
	}
	
	/**
	 * 获取类目信息。
	 * @param id
	 * @return 0 --> 1 --> 2
	 *        孙  --> 子   --> 父
	 */
	public List<CategoryVO> queryCategoryTree(Long id) {
		List<CategoryVO> cResult = new ArrayList<CategoryVO>();
		if (id == null) {
			return cResult;
		}

		Map<Long, CategoryDO> categoryMap = categoryDao.fetchCategoryMap();
		if (categoryMap.isEmpty()) {
			return cResult;
		}
		
		CategoryDO current = categoryMap.get(id);
		if (current == null) {
			return cResult;
		}
		
		CategoryVO currentVO = this.covertObj(current);
		cResult.add(currentVO);
		
		Long parentId = current.getParentId();
		while(parentId != null) {
			CategoryDO tmp = categoryMap.get(parentId);
			if (tmp == null) {
				continue;
			}
			CategoryVO tmpVO = this.covertObj(tmp);
			cResult.add(tmpVO);
			parentId = tmp.getParentId();
		}
		
		return cResult;
	}

	@Override
	public List<CategoryVO> getRootCategorys() {
		List<CategoryVO> cResult = new ArrayList<CategoryVO>();
		if (result.isEmpty()) {
			return cResult;
		}

		for (CategoryDO categoryDO : result) {
			CategoryVO vo = this.covertObj(categoryDO);
			cResult.add(vo);
		}

		return cResult;
	}

	private CategoryVO covertObj(CategoryDO categoryDO) {
		CategoryVO vo = new CategoryVO();
		vo.setCategoryName(categoryDO.getCategoryName());
		vo.setId(categoryDO.getId());
		vo.setOrder(categoryDO.getOrder());
		vo.setLeaf(categoryDO.getLeaf());
		return vo;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		if (categoryDao == null) {
			throw new NullPointerException("categoryDao is not init");
		}
		init();
	}
}
