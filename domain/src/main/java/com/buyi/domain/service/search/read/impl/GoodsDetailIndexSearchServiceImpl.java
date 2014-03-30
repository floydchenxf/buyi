package com.buyi.domain.service.search.read.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.BooleanClause.Occur;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.highlight.Highlighter;
import org.apache.lucene.search.highlight.QueryScorer;
import org.apache.lucene.search.highlight.SimpleFragmenter;
import org.apache.lucene.search.highlight.SimpleHTMLFormatter;
import org.apache.lucene.util.Version;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageableEx;
import org.springframework.stereotype.Service;

import com.buyi.dal.entity.dataobject.GoodsDetailDO;
import com.buyi.dal.entity.viewobject.Money;
import com.buyi.domain.service.search.read.GoodsDetailIndexSearchService;
import com.buyi.domain.service.search.read.IndexReaderBaseService;
import com.buyi.domain.service.search.vo.GoodsDetailIndexVO;

@Service("goodsDetailIndexSearchService")
public class GoodsDetailIndexSearchServiceImpl extends IndexReaderBaseService<GoodsDetailIndexVO> implements GoodsDetailIndexSearchService {

	private static Logger logger = LoggerFactory.getLogger(GoodsDetailIndexSearchServiceImpl.class);

	@Autowired
	private Analyzer perFieldAnalyzerWrapper;

	public GoodsDetailIndexSearchServiceImpl() {
		super(GoodsDetailIndexVO.class);
	}

	@Override
	public List<GoodsDetailDO> searchGoodsDetails(Long categoryId, String keyword, PageableEx pageable) {
		List<GoodsDetailDO> result = new ArrayList<GoodsDetailDO>();
		if (StringUtils.isBlank(keyword) && categoryId == null) {
			return result;
		}


		BooleanQuery query = new BooleanQuery();
		BooleanQuery keywordQuery = new BooleanQuery();
		Query titleQuery = null;
		Query categoryQuery = null;
		try {
			if (StringUtils.isNotBlank(keyword)) {
				titleQuery = new QueryParser(Version.LUCENE_35, GoodsDetailIndexVO.GOODS_TITLE, perFieldAnalyzerWrapper).parse(keyword);
				keywordQuery.add(titleQuery, Occur.SHOULD);
			}

			if (categoryId != null) {
				categoryQuery = new TermQuery(new Term(GoodsDetailIndexVO.CATEGORY_IDS, categoryId.toString()));
				keywordQuery.add(categoryQuery, Occur.SHOULD);
			}
		} catch (ParseException e) {
			logger.error("search parase for :" + keyword + "cause error:", e);
			return result;
		}

		query.add(keywordQuery, Occur.MUST);
		List<GoodsDetailIndexVO> goodsDetailSearchVOs = super.searchPojo(query, null, null, pageable);
		
		Highlighter highlighter = null;
		if (titleQuery != null) {
			SimpleHTMLFormatter simpleHTMLFormatter = new SimpleHTMLFormatter("<font color='red'>", "</font>");
			highlighter = new Highlighter(simpleHTMLFormatter, new QueryScorer(titleQuery));
		}
		for (GoodsDetailIndexVO goodsDetailIndexVO : goodsDetailSearchVOs) {
			GoodsDetailDO goodsDetailDO = convertV2O(goodsDetailIndexVO);
			String title = goodsDetailIndexVO.getGoodsTitle();
			if (title != null && highlighter != null) {
				highlighter.setTextFragmenter(new SimpleFragmenter(title.length() + 25));
				String highLightText = null;
				try {
					highLightText = highlighter.getBestFragment(perFieldAnalyzerWrapper, GoodsDetailIndexVO.GOODS_TITLE, title);
				} catch (Exception e) {
					logger.error("hightLight cause error:" + e.getMessage());
				}

				if (highlighter != null) {
					goodsDetailDO.setTitle(highLightText);
				}
			}

			result.add(goodsDetailDO);
		}

		return result;
	}

	private GoodsDetailDO convertV2O(GoodsDetailIndexVO goodsDetailIndexVO) {
		GoodsDetailDO goodsDetailDO = new GoodsDetailDO();
		goodsDetailDO.setId(Long.parseLong(goodsDetailIndexVO.getId()));
		goodsDetailDO.setTitle(goodsDetailIndexVO.getGoodsTitle());
		goodsDetailDO.setGoodsPic1(goodsDetailIndexVO.getGoodsPic1());
		goodsDetailDO.setOriginPrice(new Money(goodsDetailIndexVO.getOriginPrice()));
		goodsDetailDO.setPostfee(goodsDetailIndexVO.getPostfee());
		goodsDetailDO.setCategoryId(goodsDetailIndexVO.getCategoryId());
		return goodsDetailDO;
	}

}
