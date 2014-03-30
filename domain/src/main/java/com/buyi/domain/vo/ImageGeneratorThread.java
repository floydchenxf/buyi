package com.buyi.domain.vo;

import java.io.IOException;
import java.io.InputStream;

import net.coobird.thumbnailator.Thumbnails;

import org.apache.log4j.Logger;

public class ImageGeneratorThread extends Thread {
	private static final Logger logger = Logger.getLogger(ImageGeneratorThread.class);

	private int width;
	private int height;
	private InputStream is;
	private String toFilePath;

	public ImageGeneratorThread(InputStream is, int width, int height, String toFilePath) {
		this.width = width;
		this.height = height;
		this.is = is;
		this.toFilePath = toFilePath;
		this.start();
	}

	@Override
	public void run() {
		try {
			Thumbnails.of(is).size(width, height).toFile(toFilePath);
		} catch (IOException e) {
			logger.error(e.toString());
		}
	}

}
