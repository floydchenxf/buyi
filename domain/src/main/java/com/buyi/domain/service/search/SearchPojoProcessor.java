/**
 * hisuda copy right 1.0 
 */
package com.buyi.domain.service.search;

import java.util.Collection;
import java.util.List;
import org.apache.lucene.document.Document;

/**
 * 
 * 
 * DocumentProcessor.java
 * 
 * @author cxf128
 */
public interface SearchPojoProcessor {

    /**
     * 转化为单个搜索文档
     * @param t
     * @return
     */
    Document convert2Doc(Object t);

    /**
     * 转化为多个文档
     * @param o
     * @return
     */
    Collection<Document> convert2Docs(Collection<?> o);

    /**
     * 搜索文档生成对象
     * @param doc
     * @return
     */
    <T> T convert2Obj(Document doc, Class<T> clazz);

    /**
     * 搜索文档转换为list
     * @param docs
     * @return
     */
    <T> List<T> convert2List(List<Document> docs, Class<T> clazz);

}
