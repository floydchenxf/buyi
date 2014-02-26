package com.buyi.domain.service.search.read.impl;

import java.io.IOException;
import java.io.StringReader;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.SimpleAnalyzer;
import org.apache.lucene.analysis.StopAnalyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.WhitespaceAnalyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.util.Version;

public class IndexMain {

	public static void displayToken(String str, Analyzer analyzer) {
		try {
			// 将一个字符串创建成Token流
			TokenStream stream = analyzer.tokenStream("", new StringReader(str));
			// 保存相应词汇
			CharTermAttribute cta = stream.addAttribute(CharTermAttribute.class);
			while (stream.incrementToken()) {
				System.out.print("[" + cta + "]");
			}
			System.out.println();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		Analyzer aly1 = new StandardAnalyzer(Version.LUCENE_35);
		Analyzer aly2 = new StopAnalyzer(Version.LUCENE_35);
		Analyzer aly3 = new SimpleAnalyzer(Version.LUCENE_35);
		Analyzer aly4 = new WhitespaceAnalyzer(Version.LUCENE_35);

		String str = "3302 3001 3 ";

		IndexMain.displayToken(str, aly1);
		IndexMain.displayToken(str, aly2);
		IndexMain.displayToken(str, aly3);
		IndexMain.displayToken(str, aly4);
	}

}
