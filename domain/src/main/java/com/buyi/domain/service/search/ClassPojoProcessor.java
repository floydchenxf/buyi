/**
 * hisuda copy right 1.0 
 */
package com.buyi.domain.service.search;

import java.lang.reflect.Field;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Fieldable;
import org.apache.lucene.document.NumericField;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.buyi.domain.service.search.annotations.Index;
import com.buyi.domain.service.search.annotations.SearableMeta;
import com.buyi.domain.service.search.annotations.SearchableProperty;
import com.buyi.domain.service.search.annotations.Store;

/**
 * 
 * 
 * SearchPojoProcessorImpl.java
 * 
 * @author cxf128
 */

@Service("searchPojoProcessor")
public class ClassPojoProcessor implements SearchPojoProcessor {

    private static final Logger logger = LoggerFactory.getLogger(ClassPojoProcessor.class);

    private Map<String, List<SearableMeta>> searableMetas = new HashMap<String, List<SearableMeta>>();

    private Map<String, Analyzer> analyzers = new HashMap<String, Analyzer>();

    @Override
    public Document convert2Doc(Object t) {
        Class<?> clazz = t.getClass();
        List<SearableMeta> metaList = searableMetas.get(clazz.getName());
        if (metaList == null) {
            metaList = generateMetaInfos(clazz);
            searableMetas.put(clazz.getName(), metaList);
        }

        Document doc = new Document();
        for (SearableMeta searableMeta : metaList) {
            Field field = searableMeta.getField();
            Object value = null;
            try {
                field.setAccessible(true);
                value = field.get(t);
            } catch (Exception e) {
                // do nothing;
                continue;
            }

            if (value == null) {
                continue;
            }

            String methodName = searableMeta.getName();
            org.apache.lucene.document.Field.Index index = searableMeta.getIndex();
            org.apache.lucene.document.Field.Store store = searableMeta.getStore();

            String dateFormat = searableMeta.getFormat();
            Class<?> prefix = searableMeta.getPrefix();
            Fieldable lField = null;
            if (Long.class.isAssignableFrom(prefix) || long.class.isAssignableFrom(prefix)) {
                lField = new NumericField(methodName, store, index.isIndexed()).setLongValue(Long.parseLong(value
                        .toString()));
            } else if (Integer.class.isAssignableFrom(prefix) || int.class.isAssignableFrom(prefix)) {
                lField = new NumericField(methodName, store, index.isIndexed()).setIntValue(Integer.parseInt(value
                        .toString()));
            } else if (Float.class.isAssignableFrom(prefix) || float.class.isAssignableFrom(prefix)) {
                lField = new NumericField(methodName, store, index.isIndexed()).setFloatValue(Float.parseFloat(value
                        .toString()));
            } else if (Date.class.isAssignableFrom(prefix)) {
                if (!StringUtils.isBlank(dateFormat)) {
                    String dateValue = new SimpleDateFormat(dateFormat).format(value);
                    lField = new org.apache.lucene.document.Field(methodName, dateValue, store, index);
                } else {
                    long dateValue = ((Date) value).getTime();
                    lField = new NumericField(methodName, store, index.isIndexed()).setLongValue(dateValue);
                }
            } else {
                lField = new org.apache.lucene.document.Field(methodName, value.toString(), store, index);
            }

            doc.add(lField);
        }

        if (doc.getFields().isEmpty()) {
            return null;
        }

        return doc;
    }

    @Override
    public Collection<Document> convert2Docs(Collection<?> o) {
        if (o == null || o.isEmpty()) {
            return null;
        }

        List<Document> documents = new ArrayList<Document>();
        for (Object t : o) {
            Document doc = this.convert2Doc(t);
            if (doc != null) {
                documents.add(doc);
            }
        }
        return documents;
    }

    @SuppressWarnings("unchecked")
	public <T> T convert2Obj(Document doc, Class<T> clazz) {
        List<SearableMeta> metaList = searableMetas.get(clazz.getName());
        if (metaList == null) {
            metaList = generateMetaInfos(clazz);
            searableMetas.put(clazz.getName(), metaList);
        }

        Object o = null;
        try {
            o = clazz.newInstance();
        } catch (Exception e) {}

        if (o == null) {
            return null;
        }

        for (SearableMeta searableMeta : metaList) {
            String methodName = searableMeta.getName();
            Fieldable l_field = doc.getFieldable(methodName);
            if (l_field == null) {
                continue;
            }

            Field c_field = searableMeta.getField();
            Class<?> prefix = searableMeta.getPrefix();
            String dateFormat = searableMeta.getFormat();
            Object value = null;
            if (Long.class.isAssignableFrom(prefix)) {
                value = (Long) ((NumericField) l_field).getNumericValue();
            } else if (long.class.isAssignableFrom(prefix)) {
                value = ((NumericField) l_field).getNumericValue().longValue();
            } else if (Integer.class.isAssignableFrom(prefix)) {
                value = (Integer) ((NumericField) l_field).getNumericValue();
            } else if (int.class.isAssignableFrom(prefix)) {
                value = ((NumericField) l_field).getNumericValue().intValue();
            } else if (Float.class.isAssignableFrom(prefix)) {
                value = (Float) ((NumericField) l_field).getNumericValue();
            } else if (float.class.isAssignableFrom(prefix)) {
                value = ((NumericField) l_field).getNumericValue().floatValue();
            } else if (Date.class.isAssignableFrom(prefix)) {
                if (!StringUtils.isBlank(dateFormat)) {
                    String dateStr = doc.get(methodName);
                    try {
                        value = new SimpleDateFormat(dateFormat).parse(dateStr);
                    } catch (ParseException e) {
                        continue;
                    }
                } else {
                    Long dateTime = (Long) ((NumericField) l_field).getNumericValue();
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTimeInMillis(dateTime);
                    value = calendar.getTime();
                }
            } else {
                value = doc.get(methodName);
            }
            try {
                c_field.set(o, value);
            } catch (Exception e) {
                logger.error("set value " + value + "for field " + c_field + "cause error", e);
                continue;
            }
        }

        return (T) o;
    }

    @Override
    public <T> List<T> convert2List(List<Document> docs, Class<T> clazz) {
        List<T> result = new ArrayList<T>();
        for (Document doc : docs) {
            T t = convert2Obj(doc, clazz);
            result.add(t);
        }
        return result;
    }

    /**
     * @param t
     * @return
     */
    private List<SearableMeta> generateMetaInfos(Class<?> clazz) {
        List<SearableMeta> metaList = new ArrayList<SearableMeta>();
        Field[] fields = clazz.getDeclaredFields();
        if (fields != null) {
            for (Field field : fields) {
                SearableMeta meta = new SearableMeta();
                field.setAccessible(true);
                SearchableProperty property = field.getAnnotation(SearchableProperty.class);
                if (property == null) {
                    continue;
                }

                String methodName = property.name();
                if (methodName == null) {
                    methodName = field.getName();
                }

                meta.setName(methodName);

                Store tStore = property.store();
                org.apache.lucene.document.Field.Store store = org.apache.lucene.document.Field.Store.NO;
                if (tStore == Store.YES) {
                    store = org.apache.lucene.document.Field.Store.YES;
                }
                meta.setStore(store);
                org.apache.lucene.document.Field.Index index = org.apache.lucene.document.Field.Index.NO;
                Index tIndex = property.index();
                if (tIndex == Index.ANALYZED) {
                    index = org.apache.lucene.document.Field.Index.ANALYZED;
                } else if (tIndex == Index.NOT_ANALYZED) {
                    index = org.apache.lucene.document.Field.Index.NOT_ANALYZED;
                } else if (tIndex == Index.ANALYZED_NO_NORMS) {
                    index = org.apache.lucene.document.Field.Index.ANALYZED_NO_NORMS;
                } else if (tIndex == Index.NOT_ANALYZED_NO_NORMS) {
                    index = org.apache.lucene.document.Field.Index.NOT_ANALYZED_NO_NORMS;
                }

                meta.setIndex(index);
                meta.setBoost(property.boost());

                String anString = property.analyzer();
                Analyzer analyzer = analyzers.get(anString);
                meta.setAnalyzer(analyzer);

                meta.setNullValue(property.nullValue());
                meta.setFormat(property.format());
                Class<?> type = field.getType();
                meta.setPrefix(type);
                meta.setField(field);
                metaList.add(meta);
            }
        }
        return metaList;
    }

}
