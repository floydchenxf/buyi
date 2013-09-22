package com.buyi.util;

import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class StringTool {
	private static Logger log = LoggerFactory.getLogger(StringTool.class);
	private static Pattern htmlLinkPattern = null;

	static {
		try {
			htmlLinkPattern = Pattern
					.compile("((http|https)://)+(([a-zA-Z0-9\\._-]+\\.[a-zA-Z]{2,6})|([0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}))(/[a-zA-Z0-9\\&amp;%_\\./-~-]*)?");
		} catch (Exception e) {
			log.error("Init menuPattern error cased by : ", e);
		}
	}

	public static String txt2Html(Object obj) {
		if (obj == null) {
			return null;
		} else {
			String res1 = obj.toString();
			res1 = String.valueOf(obj).replaceAll("\r\n", "<br>")
					.replaceAll("\n", "<br />\n").replaceAll(" ", "&nbsp;")
					.replaceAll("&amp;", "&");
			Matcher matcher = htmlLinkPattern.matcher(res1);
			Set<String> m = new HashSet<String>();
			int i = 0;
			while (matcher.find()) {
				int start = matcher.start() + i;
				String url = matcher.group(0);
				int nbspNum = url.indexOf("&nbsp;");
				if (nbspNum > 0) {
					url = url.substring(0, nbspNum);
				}
				int end = start + url.length();

				String s_start = res1.substring(0, start);
				String s_end = res1.substring(end);
				String newUrl = "<a target='_blank' href='" + url + "'>" + url
						+ "</a>";
				i = i + newUrl.length() - url.length();
				res1 = s_start + newUrl + s_end;
			}
			for (String s : m) {
				res1 = res1.replaceAll(s, "<a target='_blank' href='" + s
						+ "'>" + s + "</a>");
			}
			return res1;
		}

	}

}
