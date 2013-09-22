package com.buyi.domain.vo;

import java.io.File;

public class FileUploadInfo {

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

}
