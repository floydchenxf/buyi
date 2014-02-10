package com.buyi.dal.impl.ibatis;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Repository;

import com.buyi.dal.CategoryDao;
import com.buyi.dal.entity.dataobject.CategoryDO;

@Repository("categoryDao")
public class CategoryDaoIBatis extends BaseDaoIBatis<Long> implements
		CategoryDao, InitializingBean {

	private static final String QUERY_ALL_CATEGORY = "QUERY_ALL_CATEGORY";
	private static final String QUERY_CATEGORY_BY_PARENT_ID = "QUERY_CATEGORY_BY_PARENT_ID";
	
	private static final Map<Long, CategoryDO> categoryCache = new HashMap<Long, CategoryDO>();
	
	public void init() {
		if (categoryCache != null && !categoryCache.isEmpty()) {
			return;
		}
		List<CategoryDO> categorys = fetchAllCategorys();
		if (categorys == null || categorys.isEmpty()) {
			return;
		}

		for (CategoryDO categoryDO : categorys) {
			categoryCache.put(categoryDO.getId(), categoryDO);
		}
	}
	
	public Map<Long, CategoryDO> fetchCategoryMap() {
		return categoryCache;
	}


	@SuppressWarnings("unchecked")
	private List<CategoryDO> fetchAllCategorys() {
		return this.getSqlMapClientTemplate().queryForList(QUERY_ALL_CATEGORY);
	}

	@SuppressWarnings("unchecked")
	public List<CategoryDO> queryCategoryByParentId(Long id) {
		return this.getSqlMapClientTemplate().queryForList(QUERY_CATEGORY_BY_PARENT_ID, id);
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		super.afterPropertiesSet();
		init();
	}
	
	

}
