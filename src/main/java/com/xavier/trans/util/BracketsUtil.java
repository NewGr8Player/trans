package com.xavier.trans.util;

import java.util.Stack;

/**
 * 括号工具类
 *
 * @author NewGr8Player
 */
public class BracketsUtil {

	/**
	 * 根据传入内容判断括号是否匹配
	 *
	 * @param content
	 * @return
	 */
	public static boolean bracketsCheck(String content) {
		Stack<Character> stack = new Stack<>();
		int length = content.length();
		for (int i = 0; i < length; ++i) {
			switch (content.charAt(i)) {
				case '(':
				case '[':
				case '{':
					stack.add(content.charAt(i));
					break;
				case ')':
				case ']':
				case '}':
					if (stack.isEmpty() || !bracketsMatch(stack.pop(), content.charAt(i))) {
						return false;
					}
					break;
			}
		}
		return true;
	}

	/**
	 * 括号是否匹配
	 *
	 * @param left  左括号
	 * @param right 右括号
	 * @return
	 */
	private static boolean bracketsMatch(Character left, Character right) {
		if (
				(left.charValue() == '(' && right.charValue() == ')')
						|| (left.charValue() == '[' && right.charValue() == ']')
						|| (left.charValue() == '{' && right.charValue() == '}')
		) {
			return true;
		} else {
			return false;
		}
	}
}
