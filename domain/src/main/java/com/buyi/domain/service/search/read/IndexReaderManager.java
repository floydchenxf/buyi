/**
 * hisuda copy right 1.0 
 */
package com.buyi.domain.service.search.read;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.apache.lucene.index.IndexReader;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.NIOFSDirectory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * 
 * IndexReaderManager.java
 * 
 * @author cxf128
 */
public class IndexReaderManager {

	private static final Logger logger = LoggerFactory.getLogger(IndexReaderManager.class);

	private Map<String, Directory> directories = new HashMap<String, Directory>();

	private Map<String, IndexReader> readerMap = new HashMap<String, IndexReader>();

	// private Map<String, Date> createTimeMap = new HashMap<String, Date>();

	private ScheduledExecutorService scheduledExecutorService = new ScheduledThreadPoolExecutor(2);

	private String basePath;

	public void setBasePath(String basePath) {
		this.basePath = basePath;
	}

	public String getBasePath() {
		return basePath;
	}

	public IndexReader getReader(final String key) {
		Directory directory = directories.get(key);
		if (directory == null) {
			try {
				directory = new NIOFSDirectory(new File(basePath + File.separator + key));
				directories.put(key, directory);
			} catch (IOException e) {
				logger.error("read directory" + key + " cause error", e);
			}
		}

		IndexReader reader = readerMap.get(key);
		if (reader == null) {
			IndexReader newReader = null;
			try {
				newReader = IndexReader.open(directory);
				// createTimeMap.put(key, new Date());
			} catch (Exception e) {
				logger.error("create reader " + key + " cause error:", e);
				return null;
			}

			readerMap.put(key, newReader);
			return newReader;
		} else {
			// 超过10分钟重新刷新
			// Date createDate = createTimeMap.get(key);
			// Date now = new Date();
			// if (now.getTime() - createDate.getTime() > 600000) {
			logger.info("start reflash index--------------");
			IndexReader newReader = null;
			try {
				newReader = IndexReader.openIfChanged(reader);
			} catch (Exception e) {
				logger.error("reopen reader " + key + " cause error:", e);
			}

			// 有更新
			if (newReader != null) {
				logger.info("convert to new reader for key------" + key);
				readerMap.put(key, newReader);
				// createTimeMap.put(key, new Date());
				scheduledExecutorService.schedule(new CloseReaderRunnable(reader, key), 2, TimeUnit.MINUTES);
				return newReader;
			}

			// createTimeMap.put(key, new Date());
			logger.info("end reflash index--------------");
			// }

			return reader;
		}
	}

	public void colseReader(String key) {
		IndexReader reader = readerMap.get(key);
		logger.info("close reader " + reader + "start for:" + key);
		if (reader != null) {
			readerMap.remove(key);
			try {
				reader.close();
				reader = null;
			} catch (Exception e) {
				logger.error("close reader " + key + " cause error:", e);
				return;
			}
		}
		logger.info("close reader end for:" + key);
	}

	public void colseAll() {
		for (String key : directories.keySet()) {
			colseReader(key);
		}
	}

	static class CloseReaderRunnable implements Runnable {

		private IndexReader indexReader;

		private String key;

		public CloseReaderRunnable(IndexReader indexReader, String key) {
			this.indexReader = indexReader;
			this.key = key;
		}

		@Override
		public void run() {
			try {
				logger.info("close reader " + key + "start!" + indexReader);
				indexReader.close();
				indexReader = null;
			} catch (IOException e) {
				logger.error("close reader " + indexReader + " cause error:", e);
			}
		}

	}
}