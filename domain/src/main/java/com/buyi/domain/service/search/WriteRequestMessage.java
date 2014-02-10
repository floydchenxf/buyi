/**
 * hisuda copy right 1.0 
 */
package com.buyi.domain.service.search;

/**
 * 
 * 
 * WriteRequestVO.java
 * 
 * @author cxf128
 */
public class WriteRequestMessage {

    private String className;

    private Object value;

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

}
