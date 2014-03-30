package com.buyi.webapp.common;

import java.io.File;
import java.io.IOException;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.LogDocMergePolicy;
import org.apache.lucene.index.Term;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;
import org.wltea.analyzer.lucene.IKAnalyzer;

public class Main {

	public static void main(String[] args) {
		long id = 80;

		String luceneIndex = "C:\\lucene\\index\\GoodsDetailIndexVO\\20140330121136";
		// 分词器
		Analyzer analyzer = new IKAnalyzer();
		IndexWriter indexWriter = null;
		try {
			LogDocMergePolicy mergePolicy = new LogDocMergePolicy();
			mergePolicy.setMaxMergeDocs(1000);
			mergePolicy.setMergeFactor(10);
			mergePolicy.setMinMergeDocs(10);
			IndexWriterConfig indexWriterConfig = new IndexWriterConfig(Version.LUCENE_35, analyzer);
			indexWriterConfig.setMergePolicy(mergePolicy);
			Directory directory = FSDirectory.open(new File(luceneIndex));
			indexWriter = new IndexWriter(directory, indexWriterConfig);
			indexWriter.deleteDocuments(new Term("id", ""+id));
			indexWriter.commit();
			indexWriter.setUseCompoundFile(true);
			indexWriter.optimize();
		} catch (CorruptIndexException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			// 4、关闭IndexWriter
			if (null != indexWriter) {
				try {
					indexWriter.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		} // end finally

	} // end getIndexInfor
}
