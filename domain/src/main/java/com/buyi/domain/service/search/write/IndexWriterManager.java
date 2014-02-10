package com.buyi.domain.service.search.write;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.NIOFSDirectory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * IndexManager.java
 * 
 * @author cxf128
 */
public abstract class IndexWriterManager {

    private static final Logger logger = LoggerFactory.getLogger(IndexWriterManager.class);

    private Map<String, Directory> directories = new HashMap<String, Directory>();

    private Map<String, IndexWriter> writerMap = new HashMap<String, IndexWriter>();

    private String basePath;

    public void setBasePath(String basePath) {
        this.basePath = basePath;
    }

    public String getBasePath() {
        return this.basePath;
    }

    public IndexWriter getWriter(String key) {
        IndexWriter writer = writerMap.get(key);
        if (writer == null) {
            writer = createWriter(key);
            writerMap.put(key, writer);
        }
        return writer;
    }

    public IndexWriter createWriter(String key) {
        IndexWriter writer = null;
        try {
            Directory directory = new NIOFSDirectory(new File(basePath + File.separator + key));
            IndexWriterConfig conf = generateIndexWriterConfig();
            writer = new IndexWriter(directory, conf);
            writer.setUseCompoundFile(true);
            // FIXME 第一次自动提交一下,防止读取无seg文件
            writer.commit();
            directories.put(key, directory);
        } catch (Exception e) {
            logger.error("create writer " + key + " cause error:", e);
            return null;
        }
        return writer;
    }

    public void colseWriter(String key) {
        IndexWriter writer = writerMap.get(key);
        if (writer != null) {
            try {
                writer.close();
                writer = null;
            } catch (Exception e) {
                logger.error("close index writer:" + key + " cause error:", e);
            }
            writerMap.remove(key);
        }
    }

    public void colseAll() {
        for (String key : directories.keySet()) {
            colseWriter(key);
        }
    }

    public abstract IndexWriterConfig generateIndexWriterConfig();

}
