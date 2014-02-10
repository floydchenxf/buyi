/**
 * hisuda copy right 1.0 
 */
package com.buyi.domain.service.search.annotations;

import java.lang.reflect.Field;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Field.Index;
import org.apache.lucene.document.Field.Store;

/**
 * 
 * 
 * SearableMeta.java
 * 
 * @author cxf128
 */
public class SearableMeta {

    /**
     * 字段名
     */
    private String name;

    /**
     * 权重
     */
    private float boost;

    /**
     * index
     */
    private Index index;

    /**
     * 是否存储
     */
    private Store store;

    /**
     * 使用分析器
     */
    private Analyzer analyzer;

    /**
     * 返回值类型
     */
    private Class<?> prefix;

    private String format;

    private String nullValue;

    private Field field;

    public Field getField() {
        return field;
    }

    public void setField(Field field) {
        this.field = field;
    }

    public Class<?> getPrefix() {
        return prefix;
    }

    public void setPrefix(Class<?> prefix) {
        this.prefix = prefix;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public String getNullValue() {
        return nullValue;
    }

    public void setNullValue(String nullValue) {
        this.nullValue = nullValue;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getBoost() {
        return boost;
    }

    public void setBoost(float boost) {
        this.boost = boost;
    }

    public Index getIndex() {
        return index;
    }

    public void setIndex(Index index) {
        this.index = index;
    }

    public Store getStore() {
        return store;
    }

    public void setStore(Store store) {
        this.store = store;
    }

    public Analyzer getAnalyzer() {
        return analyzer;
    }

    public void setAnalyzer(Analyzer analyzer) {
        this.analyzer = analyzer;
    }
}
