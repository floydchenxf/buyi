/**
 * hisuda copy right 1.0 
 */
package com.buyi.domain.service.search.read.impl;

import java.io.File;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.lucene.index.IndexReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.buyi.domain.service.search.read.IndexReaderManager;
import com.buyi.domain.service.search.read.IndexReaderSwtichService;

/**
 * 
 * 
 * IndexReaderSwtichServiceImpl.java
 * 
 * @author cxf128
 */

@Service("indexReaderSwtichService")
public class IndexReaderSwtichServiceImpl implements IndexReaderSwtichService, InitializingBean {

    private static final Logger logger = LoggerFactory.getLogger(IndexReaderSwtichServiceImpl.class);

    private Map<String, String> keyMap = new HashMap<String, String>();

    private Map<String, Date> readTimeMap = new HashMap<String, Date>();

    @Autowired
    private IndexReaderManager indexReaderManager;

    private String basePath;

    @Override
    public IndexReader chooseReader(String key) {
        String readFile = keyMap.get(key);
        if (readFile == null) {
            readFile = fetchFile(key);
            if (readFile == null) {
                logger.error("read file:" + key + " cause error pls check");
                return null;
            }
            readTimeMap.put(key, new Date());
            keyMap.put(key, readFile);
        }

        Date lastReadTime = readTimeMap.get(key);
        Date date = new Date();
        // 每一小时重新读取文件
        if (date.getTime() - lastReadTime.getTime() > 3600000) {
            logger.info("read file every one hour start---------");
            String newReadFile = fetchFile(key);
            if (newReadFile == null) {
                logger.error("read file:" + key + " cause error pls check");
                return null;
            }
            logger.info("read file----" + newReadFile + "---old file:" + readFile);

            readTimeMap.put(key, new Date());
            if (!newReadFile.equals(readFile)) {
                // 关闭老的read
                keyMap.put(key, newReadFile);
                indexReaderManager.colseReader(readFile);
                readFile = newReadFile;
            }
            logger.info("read file every one hour end---------");
        }

        IndexReader indexReader = indexReaderManager.getReader(readFile);
        return indexReader;
    }

    public String fetchFile(String key) {
        File file = new File(basePath + File.separator + key);
        String result = null;
        if (file.exists()) {
            File[] subFiles = file.listFiles();
            if (subFiles != null && subFiles.length > 0) {
                for (File subFile : subFiles) {
                    if (subFile.isFile()) {
                        result = key + File.separator + subFile.getName().replaceAll(".txt", "");
                        break;
                    }
                }
            }
        }
        return result;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        basePath = indexReaderManager.getBasePath();
    }

}
