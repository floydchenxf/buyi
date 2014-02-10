/**
 * hisuda copy right 1.0 
 */
package com.buyi.domain.service.search.read;

import org.apache.lucene.index.IndexReader;

/**
 * 
 * 
 * IndexReaderSwtichService.java
 * 
 * @author cxf128
 */
public interface IndexReaderSwtichService {

    /**
     * 选择IndexReader
     * @param key
     * @param isback 
     * @return
     */
    public IndexReader chooseReader(String key);

}
