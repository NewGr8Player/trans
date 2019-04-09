package com.xavier.trans.util;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * EsSql工具类
 * TODO 这个工具类应该改成语法分析器
 *
 * @author NewGr8Player
 */
public class EsSqlUtil {

	/**
	 * 关键字SELECT
	 */
	public static final String KEY_SELECT = "SELECT";

	/**
	 * 关键字FROM
	 */
	public static final String KEY_FROM = "FROM";

	/**
	 * 关键字FROM
	 */
	public static final String KEY_WHERE = "WHERE";

	/**
	 * 关键字ORDER_BY
	 */
	public static final String KEY_ORDER_BY = "ORDER BY";

	public static void main(String[] args) {
		List<String> fieldList = new ArrayList<>();
		String sql = "select * from petition_case";
		String regex = "(?i)(?<=" + KEY_SELECT + ").*?(?=" + KEY_FROM + ")";/* 忽略大小写，获取查询的字段 */
		Pattern pattern = Pattern.compile(regex);
		Matcher m = pattern.matcher(sql);
		for (int i = 0; m.find(); ++i) {
			fieldList.add(m.group(i).trim());
		}
		if (fieldList.size() > 1) {
			System.out.println("GG");
		} else {
			System.out.println(fieldList);
		}
	}

}
