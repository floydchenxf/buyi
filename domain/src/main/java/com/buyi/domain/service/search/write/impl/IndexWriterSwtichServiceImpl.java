package com.buyi.domain.service.search.write.impl;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import org.apache.lucene.index.IndexWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.buyi.domain.service.search.write.IndexWriterManager;
import com.buyi.domain.service.search.write.IndexWriterSwtichService;

/**
 * 
 * 
 * IndexWriterSwtichServiceImpl.java
 * 
 * @author cxf128
 */
@Service("indexWriterSwtichService")
public class IndexWriterSwtichServiceImpl implements IndexWriterSwtichService, InitializingBean {

    /**
     * FILE_POSTFIX
     */
    private static final String FILE_SUFFIX = ".txt";

    private static final Logger logger = LoggerFactory.getLogger(IndexWriterSwtichServiceImpl.class);

    /**
     * DATE_FORMAT
     */
    private static final String DATE_FORMAT = "yyyyMMddHHmmss";

    @Autowired
    private IndexWriterManager indexWriterManager;

    private Map<String, LinkedList<String>> keyMap = new HashMap<String, LinkedList<String>>();

    private String basePath;

    @Override
    public IndexWriter chooseWriter(String key, boolean isback) {
        LinkedList<String> keyList = keyMap.get(key);
        if (keyList == null) {
            keyList = new LinkedList<String>();
            String writeFile = fetchFile(key);
            keyList.add(writeFile);
        }

        String currentKey = keyList.get(0);

        if (currentKey == null) {
            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.HOUR, -1);
            String num = new SimpleDateFormat(DATE_FORMAT).format(calendar.getTime());
            currentKey = key + File.separator + num;
            generateFile(key, num);
            keyList.set(0, currentKey);
        }

        IndexWriter indexWriter = null;
        if (!isback) {
            indexWriter = indexWriterManager.getWriter(currentKey);
            keyMap.put(key, keyList);
            return indexWriter;
        }

        String nextKey = null;
        if (keyList.size() > 1) {
            nextKey = keyList.get(1);
        }

        if (nextKey == null) {
            nextKey = key + File.separator + new SimpleDateFormat(DATE_FORMAT).format(new Date());
            if (keyList.size() > 1) {
                keyList.set(1, nextKey);
            } else {
                keyList.add(nextKey);
            }
        }

        keyMap.put(key, keyList);
        return indexWriterManager.getWriter(nextKey);
    }

    /**
     * @param currentKey
     */
    private void generateFile(String key, String num) {
        String basePath = indexWriterManager.getBasePath();
        String parentPath = basePath + File.separator + key;
        File parentFile = new File(parentPath);
        if (!parentFile.exists()) {
            parentFile.mkdir();
        }
        File file = new File(parentFile + File.separator + num + FILE_SUFFIX);
        try {
            file.createNewFile();
        } catch (IOException e) {
            logger.error("create file " + parentFile + File.separator + num + FILE_SUFFIX + " cause error:", e);
        }

    }

    @Override
    public void switchOn(String key) {
        logger.error("----------index swtich start " + key);
        LinkedList<String> keyList = keyMap.get(key);
        if (keyList != null && keyList.size() > 1) {
            String oldKey = keyList.poll();
            String usedKey = keyList.peek();
            if (oldKey != null) {
                renameFile(oldKey, usedKey);
                indexWriterManager.colseWriter(oldKey);
            }
            logger.error("------------use index:" + usedKey);
        }
        logger.error("----------index swtich end success " + key);

    }

    /**
     * @param oldKey
     * @param usedKey
     */
    private void renameFile(String oldKey, String usedKey) {
        File oldFile = new File(basePath + File.separator + oldKey + FILE_SUFFIX);
        File usedFile = new File(basePath + File.separator + usedKey + FILE_SUFFIX);
        oldFile.renameTo(usedFile);
    }

    public String fetchFile(String key) {
        File file = new File(basePath + File.separator + key);
        String result = null;
        if (file.exists()) {
            File[] subFiles = file.listFiles();
            if (subFiles != null && subFiles.length > 0) {
                for (File subFile : subFiles) {
                    if (subFile.isFile()) {
                        result = key + File.separator + subFile.getName().replaceAll(FILE_SUFFIX, "");
                        break;
                    }
                }
            }
        }
        return result;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        basePath = indexWriterManager.getBasePath();
    }

	@Override
	public void closeWriter(String key) {
		LinkedList<String> keyList = keyMap.get(key);
        if (keyList == null) {
            keyList = new LinkedList<String>();
            String writeFile = fetchFile(key);
            keyList.add(writeFile);
        }

        String currentKey = keyList.get(0);
        if (currentKey != null) {
        	indexWriterManager.colseWriter(currentKey);
        }
	}
}
