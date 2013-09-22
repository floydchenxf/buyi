/**
 * hisuda copy right 1.0 
 */
package com.buyi.util;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;

/**
 * 
 * 
 * URL.java
 * 
 * @author cxf128
 */
public class UrlPojo {

    private String url;

    private Map<String, Object> params = new LinkedHashMap<String, Object>(1);

    public UrlPojo(String url) {
        this.url = url;
    }

    public static UrlPojo getNewInstance(String url) {
        return new UrlPojo(url);
    }

    public UrlPojo pm(String key, Object value) {
        return this.addParameter(key, value);
    }

    public UrlPojo addParameter(String key, Object value) {
        params.put(key, url(value));
        return this;
    }

    public UrlPojo addPath(String path) {
        url = url + path;
        return this;
    }

    public String render() {
        if (params.isEmpty()) {
            return url;
        }

        if (StringUtils.isNotBlank(url)) {
            Pattern pattern = Pattern.compile("\\{\\w+\\}");
            Matcher matcher = pattern.matcher(url);
            List<String> keyList = new ArrayList<String>();
            while (matcher.find()) {
                String key = matcher.group().split("[\\{\\}]")[1];
                if (key != null) {
                    keyList.add(key);
                }
            }

            if (keyList != null && !keyList.isEmpty()) {
                for (String item : keyList) {
                    Object o = params.get(item);
                    if (o == null) {
                        url = url.replaceAll("\\{" + item + "\\}", "");
                    } else {
                        params.remove(item);
                        url = url.replaceAll("\\{" + item + "\\}", o.toString());
                    }
                }
            }
        }

        StringBuilder sb = new StringBuilder(url);
        sb.append("?");
        if (params != null && params.size() > 0) {
            for (Map.Entry<String, Object> ent : params.entrySet()) {
                if (ent.getValue() != null) {
                    sb.append(ent.getKey());
                    sb.append("=");
                    sb.append(ent.getValue());
                    sb.append("&");;
                }
            }
        }
        String url = sb.substring(0, sb.length() - 1);
        params.clear();
        return url;
    }

    @Override
    public String toString() {
        return render();
    }

    private String url(Object string) {
        if (string == null) {
            return null;
        }
        try {
            return URLEncoder.encode(String.valueOf(string), "UTF-8");
        } catch (UnsupportedEncodingException uee) {
            return null;
        }
    }

    public static void main(String[] args) {
        String s = "g-{id}-{gid}-{itemId}--";
        UrlPojo urlPojo = new UrlPojo(s);
        urlPojo.addParameter("id", "1234").addParameter("gid", "sdfsdf").addParameter("sdfdf", "dsfd222");
        System.out.println(urlPojo);
    }
}
