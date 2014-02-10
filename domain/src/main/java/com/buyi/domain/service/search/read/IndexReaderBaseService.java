/**
 * hisuda copy right 1.0 
 */
package com.buyi.domain.service.search.read;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.SimpleAnalyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.search.Filter;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.Sort;
import org.apache.lucene.search.TopDocs;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageableEx;

import com.buyi.domain.service.search.SearchPojoProcessor;

/**
 * 
 * 
 * IndexReaderBaseService.java
 * 
 * @author cxf128
 */
public class IndexReaderBaseService<T> {

    private static final Logger logger = LoggerFactory.getLogger(IndexReaderBaseService.class);
    
    private static final int MAX_SEARCH_NUM = 2000;

    @Autowired
    protected IndexReaderSwtichService indexReaderSwtichService;

    @Autowired
    protected SearchPojoProcessor searchPojoProcessor;

    protected Class<T> clazz;

    @Autowired
    protected Analyzer defaultChinaAnalyzer;

    @Autowired
    protected SimpleAnalyzer simpleAnalyzer;

    @Autowired
    protected StandardAnalyzer standardAnalyzer;

    public IndexReaderBaseService(Class<T> clazz) {
        this.clazz = clazz;
    }

    public Document searchOne(Query query) {
        IndexReader reader = getReader();
        Document doc = null;
        IndexSearcher is = null;
        try {
            is = new IndexSearcher(reader);// TODO INDEX SEARCH 不一定需要每次都实例化
            TopDocs topDocs = is.search(query, 1);
            ScoreDoc[] totalResult = topDocs.scoreDocs;
            if (topDocs != null && totalResult.length > 0) {
                doc = is.doc(topDocs.scoreDocs[0].doc);
            }
        } catch (Exception e) {
            logger.error("search cause error:", e);
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    // do nothing
                }
            }
        }

        return doc;
    }

    /**
     * @return
     */
    protected IndexReader getReader() {
        IndexReader reader = indexReaderSwtichService.chooseReader(this.clazz.getSimpleName());
        return reader;
    }

    public List<T> searchPojo(Query query, Filter filter, Sort sort, PageableEx pageable) {
        List<T> result = new ArrayList<T>();
        List<Document> docs = this.search(query, filter, sort, pageable);
        if (docs != null) {
            result = searchPojoProcessor.convert2List(docs, this.clazz);
        }

        return result;
    }

    public int searchCountNum(Query query, Filter filter) {
        IndexReader reader = getReader();
        IndexSearcher is = null;
        try {
            is = new IndexSearcher(reader);// TODO INDEX SEARCH 不一定需要每次都实例化
            TopDocs topDocs = null;

            topDocs = is.search(query, filter, MAX_SEARCH_NUM);

            return topDocs.scoreDocs.length;

        } catch (Exception e) {
            logger.error("search cause error:", e);
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    // do nothing
                }
            }
        }

        return 0;
    }

    public List<Document> search(Query query, Filter filter, Sort sort, PageableEx pageable) {
        IndexReader reader = getReader();
        List<Document> docs = new ArrayList<Document>();
        IndexSearcher is = null;
        try {
            is = new IndexSearcher(reader);// TODO INDEX SEARCH 不一定需要每次都实例化
            int num = pageable.getPageSize();
            int start = pageable.getOffset();
            TopDocs topDocs = null;
            if (null == sort) {
                topDocs = is.search(query, filter, MAX_SEARCH_NUM);
            } else {
                topDocs = is.search(query, filter, MAX_SEARCH_NUM, sort);
            }
            ScoreDoc[] totalResult = topDocs.scoreDocs;
            pageable.setTotalSize(totalResult.length);
            if (topDocs != null && totalResult.length > 0) {
                for (int i = start; i < totalResult.length && i < start + num; i++) {
                    ScoreDoc scoreDoc = topDocs.scoreDocs[i];
                    Document doc = is.doc(scoreDoc.doc);
                    if (doc != null) {
                        docs.add(doc);
                    }
                }
            }
        } catch (Exception e) {
            logger.error("search cause error:", e);
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    // do nothing
                }
            }
        }

        return docs;
    }

    public List<Document> randomSearch(Query query, Filter filter, Sort sort, int length, boolean isRandom) {
        IndexReader reader = getReader();
        List<Document> docs = new ArrayList<Document>();
        IndexSearcher is = null;
        try {
            is = new IndexSearcher(reader);// TODO INDEX SEARCH 不一定需要每次都实例化
            TopDocs topDocs = null;
            if (null == sort) {
                topDocs = is.search(query, filter, MAX_SEARCH_NUM);
            } else {
                topDocs = is.search(query, filter, MAX_SEARCH_NUM, sort);
            }
            ScoreDoc[] totalResult = topDocs.scoreDocs;

            Random r = new Random();
            int randomStart = 0;
            if (isRandom && totalResult.length > length + 1) {
                randomStart = r.nextInt(totalResult.length - length - 1);
            }

            if (topDocs != null && totalResult.length > 0) {
                for (int i = randomStart; i < totalResult.length && i < randomStart + length; i++) {
                    ScoreDoc scoreDoc = topDocs.scoreDocs[i];
                    Document doc = is.doc(scoreDoc.doc);
                    if (doc != null) {
                        docs.add(doc);
                    }
                }
            }
        } catch (Exception e) {
            logger.error("search cause error:", e);
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    // do nothing
                }
            }
        }

        return docs;
    }
}
