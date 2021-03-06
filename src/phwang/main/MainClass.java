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
		PhwangRoot root = new PhwangRoot();
		
		Boolean http_test_on = true;
		if (http_test_on) {
			HttpTest http_test_root = new HttpTest(root.frontRoot().frontDExport());
			http_test_root.startTest();
		}
		
		Boolean android_test_on = true;
		if (android_test_on) {
			new AndroidTest();
			
		}
	}
}
