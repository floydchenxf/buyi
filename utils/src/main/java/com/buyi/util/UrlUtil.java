/**
 * hisuda copy right 1.0 
 */
package com.buyi.util;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 页面使用 $urlUtil.MEMBER_INDEX 页面使用
 * $urlUtil.MEMBER_INDEX.addParameter('id','1234') 内部使用 UrlUtil.MEMBER_INDEX;
 * 内部使用UrlUtil.getBackendUrl(UrlUtil.MEMBER_INDEX).addParameter('id','1234);
 * UrlUtil.java
 * 
 * <br/>
 * <br/>
 * <br/>
 * REST风格的url规范： <li>/.../entity/edit 添加</li> <li>/.../entity/edit?id=22 编辑</li>
 * <li>/.../entity?id=22 query</li> <li>/.../entity/list 列表</li>
 * 
 * @author cxf128
 * @author ju
 */
public class UrlUtil {
	private static final Logger logger = LoggerFactory.getLogger(UrlUtil.class);

	public static final String IMAGE_PATH = "image";
	public static final String SMALL_IMAGE_PATH = "small_image";
	public static final String TINY_IMAGE_PATH = "tiny_image";

	private static final String BUYI_CONTEXTPATH = "buyi.contextpath";
	private static final String BUYI_STATIC_ADDRESS = "buyi.static.address";
	private static final String BUYI_IMG_ADDRESS = "buyi.img.address";

	private static final Map<String, String> urlFiledMap = new HashMap<String, String>();

	private static final Properties buyiProperties = new Properties();

	public static final String RESULT_URL = "/result_inline";

	public static final String BUYI_SHOW = "/buyi/buyi_show";
	public static final String BUYER_TEST = "/buyer/test";
	public static final String BUYI_MANAGERMENT = "/managerment/list";
	public static final String BUYI_WB_LOGIN = "/wb/login";
	public static final String BUYI_WB_COMPANY = "/wb/company";
	public static final String BUYI_WB_PRODUCT_TYPE = "/wb/product_type";
	public static final String BUYI_WB_PRODUCT = "/wb/product";
	public static final String BUYI_WB_PRODUCT_PUBLISHED = "/wb/product_published";
	public static final String BUYI_WB_TOP_PRODUCT_TYPE = "/wb/top_product_type";

	// 登陆相关---------------------------------------------
	public static final String BUYI_LOGIN = "/sso/login";
	public static final String BUYI_SIGNUP = "/sso/signup";

	public static final String MARKET_INDEX = "/market/index";

	// 管理相关-------------------------------------------------
	public static final String MANAGER_INDEX = "/managerment/index";
	public static final String SELLED_GOODS = "/managerment/selled_goods";
	public static final String PUBLISH_GOODS = "/managerment/publish_goods";
	public static final String DIVIVE_GOODS = "/managerment/divive_goods";
	public static final String QUERY_SUB_CATEGORY = "/managerment/query_sub_category";
	public static final String GOODS_SETTING = "/managerment/goods_setting";
	public static final String UPLOAD_FILES = "/managerment/upload_files";
	public static final String ADD_GOODS = "/managerment/add_goods";
	public static final String SEARCH_GOODS = "/managerment/search_goods";
	public static final String SHOW_GOODS = "/managerment/show_goods";
	public static final String EDIT_GOODS = "/managerment/edit_goods";
	public static final String GOODS_TYPE_ADD = "/managerment/goods_type_add";
	public static final String GOODS_TYPE_EDIT = "/managerment/goods_type_edit";
	public static final String GOODS_TYPE_DELETE = "/managerment/goods_type_delete";

	/**
	 * 该方法用于前端使用，获取url
	 * 
	 * @param key
	 * @param withoutContextPath
	 * @return
	 */
	public static UrlPojo get(String key, boolean withoutContextPath) {
		String url = urlFiledMap.get(key);
		if (url == null) {
			return null;
		}

		if (!withoutContextPath) {
			String contextPath = buyiProperties.getProperty(BUYI_CONTEXTPATH);
			if (StringUtils.isNotBlank(contextPath)) {
				url = contextPath + url;
			}
		}
		return new UrlPojo(url);
	}

	/**
	 * 该方法用于前端使用，获取url
	 * 
	 * @param key
	 * @param withoutContextPath
	 * @return
	 */
	public static UrlPojo get(String key) {
		return get(key, false);
	}

	/**
	 * 
	 * @param url
	 * @param withoutContextPath
	 *            是否带有context path
	 * @return
	 */
	public static UrlPojo getBackendUrl(String url, boolean withoutContextPath) {
		if (!withoutContextPath) {
			String contextPath = buyiProperties.getProperty(BUYI_CONTEXTPATH);
			if (StringUtils.isNotBlank(contextPath)) {
				url = contextPath + url;
			}
		}
		return new UrlPojo(url);
	}

	/**
	 * 用户后台获取url
	 * 
	 * @param url
	 * @return
	 */
	public static UrlPojo getBackendUrl(String url) {
		return getBackendUrl(url, false);
	}

	static {
		Field[] fields = UrlUtil.class.getDeclaredFields();
		for (Field field : fields) {
			field.setAccessible(true);
			if (String.class.isAssignableFrom(field.getType())) {
				try {
					urlFiledMap.put(field.getName(), field.get(UrlUtil.class).toString());
				} catch (Exception e) {
					logger.error("reflect key cause error:", e);
					continue;
				}
			}
		}

		try {
			buyiProperties.load(UrlUtil.class.getResourceAsStream("/buyi.properties"));
		} catch (IOException e) {
			logger.error("hisuda properties load cause error:", e);
		}
	}

	public static String getStaticServer() {
		return buyiProperties.getProperty(BUYI_STATIC_ADDRESS);
	}

	public static String getImageServer() {
		return buyiProperties.getProperty(BUYI_IMG_ADDRESS);
	}

	public static String getBigImagePath(String filename) {
		return getImageServer() + File.separator + IMAGE_PATH + File.separator + filename;
	}
	
	public static String getSmallImagePath(String filename) {
		return getImageServer() + File.separator + SMALL_IMAGE_PATH  + File.separator + filename;
	}
	
	public static String getTinyImagePath(String filename) {
		return getImageServer() + File.separator + TINY_IMAGE_PATH  + File.separator + filename;
	}

	public static void main(String[] args) {
		System.out.println(UrlUtil.get("VISITOR_RECORD_REMOVE"));
	}
}
