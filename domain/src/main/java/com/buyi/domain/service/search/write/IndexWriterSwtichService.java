/**
 * hisuda copy right 1.0 
 */
package com.buyi.domain.service.search.write;

import org.apache.lucene.index.IndexWriter;

/**
 * 
 * 
 * IndexWriterSwtichService.java
 * 
 * @author cxf128
 */
public interface IndexWriterSwtichService {

    /**
     * 选择IndexWriter
     * @param key
     * @param isback true时产生第二个index writer, fasle为当前使用的 index writer
     * @return
     */
    public IndexWriter chooseWriter(String key, boolean isback);
    
    /**
     * 关闭indexWrite
     * @param key
     */
    public void closeWriter(String key);

    /**
     * 是否切换
     * @param key
     */
    public void switchOn(String key);

}
