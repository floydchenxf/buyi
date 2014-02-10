package com.buyi.domain.service.search;

import org.apache.lucene.document.Document;
import org.apache.lucene.document.NumericField;

public class LuceneSearchUtil {

    public static Long getLongFieldValue(Document doc, String fieldName) {
        Long result = 0L;
        try {
            NumericField numberField = (NumericField) doc.getFieldable(fieldName);
            result = (Long) numberField.getNumericValue();
        } catch (Exception e) {
            // do noting
        }
        return result;
    }

    public static Integer getIntFieldValue(Document doc, String fieldName) {
        Integer result = 0;
        try {
            NumericField numberField = (NumericField) doc.getFieldable(fieldName);
            result = (Integer) numberField.getNumericValue();
        } catch (Exception e) {
            // do noting
        }
        return result;
    }
}
