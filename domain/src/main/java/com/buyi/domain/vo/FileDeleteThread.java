package com.buyi.domain.vo;

import java.io.File;

public class FileDeleteThread extends Thread {
	private File file;

	public FileDeleteThread(File file) {
		this.file = file;
		this.start();
	}

	@Override
	public void run() {
		if (file.exists()) {
			file.delete();
		}
	}
}
