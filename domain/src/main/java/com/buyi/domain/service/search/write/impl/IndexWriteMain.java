/**
 * sudaw copy right 1.0 
 */
package com.buyi.domain.service.search.write.impl;

import java.io.File;
import org.apache.lucene.analysis.SimpleAnalyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;

/**
 * 
 * 
 * IndexWriteMain.java
 * 
 * @author cxf128
 */
public class IndexWriteMain {

    public static void main(String[] args) throws Exception {
        Directory directory = FSDirectory.open(new File("c:\\lucene"));
        IndexWriterConfig config = new IndexWriterConfig(Version.LUCENE_34, new StandardAnalyzer(Version.LUCENE_34));
        config.setMaxBufferedDocs(100);
        config.setMaxBufferedDocs(Integer.MAX_VALUE);
        IndexWriter indexWriter = new IndexWriter(directory, config);
        indexWriter.deleteDocuments(new Term("id", "2"));
        indexWriter.commit();
        indexWriter.optimize();
        indexWriter.close();

         Document doc1 = new Document();
        
//         Field idField = new Field("id", "1", Field.Store.YES,
//         Index.NOT_ANALYZED_NO_NORMS);
//         Field contentField = new Field("title", "无限科技阿斯顿飞", Field.Store.NO,
//         Index.NOT_ANALYZED);
//         doc1.add(idField);
//         doc1.add(contentField);
//         indexWriter.addDocument(doc1);
//        
//         Document doc2 = new Document();
//         Field idField2 = new Field("id", "2", Field.Store.YES,
//         Index.NOT_ANALYZED_NO_NORMS);
//         Field contentField2 = new Field("title", "asdfsadfaaa",
//         Field.Store.NO, Index.NOT_ANALYZED);
//         doc2.add(idField2);
//         doc2.add(contentField2);
//        
//         indexWriter.addDocument(doc2);
//         // 这两句一定要执行
//        
//         indexWriter.optimize();
//         indexWriter.close();
        Query q = new QueryParser(Version.LUCENE_34, "title", new SimpleAnalyzer()).parse("asdfsadfaaa");
        IndexReader reader = IndexReader.open(directory);
        IndexSearcher is = new IndexSearcher(reader);
        TopDocs tops = is.search(q, 100);
        for (int i = 0; i < tops.totalHits; i++) {
            ScoreDoc scoreDoc = tops.scoreDocs[i];
            Document doc = is.doc(scoreDoc.doc);
            System.out.println(doc.getFieldable("id"));
        }
    }

}
