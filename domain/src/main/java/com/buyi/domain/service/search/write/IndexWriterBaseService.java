/**
 * hisuda copy right 1.0 
 */
package com.buyi.domain.service.search.write;

import java.util.Collection;

import org.apache.lucene.document.Document;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.Term;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.buyi.domain.service.search.SearchPojoProcessor;

/**
 * 
 * 
 * IndexWriterBaseService.java
 * 
 * @author cxf128
 */
public class IndexWriterBaseService<T> {

    private static final Logger logger = LoggerFactory.getLogger(IndexWriterBaseService.class);

    @Autowired
    protected IndexWriterSwtichService indexWriterSwtichService;

    @Autowired
    protected SearchPojoProcessor searchPojoProcessor;

    protected Class<T> clazz;

    public IndexWriterBaseService(Class<T> clazz) {
        this.clazz = clazz;
    }

    public void createIndex(T t) {
        IndexWriter indexWriter = indexWriterSwtichService.chooseWriter(t.getClass().getSimpleName(), false);
        Document doc = searchPojoProcessor.convert2Doc(t);
        try {
            indexWriter.addDocument(doc);
        } catch (Exception e) {
            logger.error("add docment cause error:", e);
        } finally {
            try {
                indexWriter.commit();
            } catch (Exception e) {
                logger.error("index writer commit cause error:--------obj:" + t, e);
            }
        }
    }

    /**
     * 批量创建索引
     * @param t
     */
    public void createIndexes(Collection<T> t) {
        this.createIndexes(t, false);
    }

    public void createIndexes(Collection<T> t, boolean isback) {
        IndexWriter indexWriter = indexWriterSwtichService.chooseWriter(this.clazz.getSimpleName(), isback);
        Collection<Document> docs = searchPojoProcessor.convert2Docs(t);
        try {
            indexWriter.addDocuments(docs);
        } catch (Exception e) {
            logger.error("add docments cause error:", e);
        } finally {
            try {
                indexWriter.commit();
            } catch (Exception e) {
                logger.error("index writer commit cause error:--------obj:" + t, e);
            }
        }
    }

    public void deleteIndexes(Term... terms) {
        IndexWriter indexWriter = indexWriterSwtichService.chooseWriter(this.clazz.getSimpleName(), false);
        try {
            indexWriter.deleteDocuments(terms);
        } catch (Exception e) {
            logger.error("index writer delete index cause error: terms:" + terms.toString() + "---------", e);
        } finally {
            try {
                indexWriter.commit();
            } catch (Exception e) {
                logger.error("index writer delete commit cause error:--------obj:" + terms, e);
            }
        }
    }

    public void switchOn() {
        this.indexWriterSwtichService.switchOn(this.clazz.getSimpleName());
    }

}
