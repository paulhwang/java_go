/*
 ******************************************************************************
 *                                       
 *  Copyright (c) 2021 phwang. All rights reserved.
 *
 ******************************************************************************
 */

package com.phwang.core.main;

import com.phwang.core.test.HttpTest;
import com.phwang.core.test.AndroidTest;

public class MainClass {

	public static void main(String[] args) {
		PhwangRoot root_ = new PhwangRoot();
		
		Boolean http_test_on = false;
		if (http_test_on) {
			new HttpTest(root_.frontRoot().frontDExport()).startTest();
		}
		
		Boolean android_test_on = true;
		if (android_test_on) {
			new AndroidTest(root_.androidRoot().androidDExport()).startTest();
			
		}
	}
}
