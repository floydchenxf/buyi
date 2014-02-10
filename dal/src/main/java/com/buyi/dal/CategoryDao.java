package com.buyi.dal;

import java.util.List;
import java.util.Map;

import com.buyi.dal.entity.dataobject.CategoryDO;

/**
 * 
 * @author cxf128
 * 
 */
public interface CategoryDao {
	
	Map<Long, CategoryDO> fetchCategoryMap();

	/**
	 * 根据父类目Id获取子类目信息
	 * 
	 * @param id
	 * @return
	 */
	List<CategoryDO> queryCategoryByParentId(Long id);

}
