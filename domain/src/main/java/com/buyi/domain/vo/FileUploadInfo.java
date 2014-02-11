package com.buyi.domain.vo;

import java.io.File;

import com.buyi.util.UrlUtil;

public class FileUploadInfo {

	public static final String SMALL_IMAGE_PATH = "small_image";

	/**
	 * 设置文件路径
	 */
	private String filePath;

	public File getFile() {
		File newFile = new File(filePath);
		if (!newFile.exists() && newFile.isDirectory()) {
			newFile.mkdir();
		}
		return newFile;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public String getFilePath() {
		return filePath;
	}

	public String getImagePath(String filename) {
		return filePath + File.separator + UrlUtil.IMAGE_PATH + File.separator + filename;
	}
	
	public String getSmallImagePath(String filename) {
		return filePath + File.separator + UrlUtil.SMALL_IMAGE_PATH + File.separator + filename;
	}
	
	public String getTinyImagePath(String filename) {
		return filePath + File.separator + UrlUtil.TINY_IMAGE_PATH + File.separator + filename;
	}
	
	public String getSearchImagePath(String filename) {
		return filePath + File.separator + UrlUtil.SEARCH_IMAGE_PATH + File.separator + filename;
	}

}
