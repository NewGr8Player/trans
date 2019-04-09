package com.xavier.trans.util;

import com.xavier.trans.TransApplicationTests;
import org.testng.annotations.Test;

public class BracketsUtilTest  {

	@Test
	public void bracketsCheckTest() {
		System.out.println(BracketsUtil.bracketsCheck("(([(fdafsdfsaf)])))"));
	}
}