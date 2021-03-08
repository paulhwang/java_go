/*
 ******************************************************************************
 *                                       
 *  Copyright (c) 2021 phwang. All rights reserved.
 *
 ******************************************************************************
 */

package phwang.main;

import phwang.test.HttpTest;
import phwang.test.AndroidTest;

public class MainClass {

	public static void main(String[] args) {
		PhwangRoot root_ = new PhwangRoot();
		
		Boolean http_test_on = true;
		if (http_test_on) {
			new HttpTest(root_.frontRoot().frontDExport()).startTest();
		}
		
		Boolean android_test_on = true;
		if (android_test_on) {
			new AndroidTest(root_.androidRoot().androidDExport()).startTest();
			
		}
	}
}
