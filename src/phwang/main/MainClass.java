/*
 ******************************************************************************
 *                                       
 *  Copyright (c) 2021 phwang. All rights reserved.
 *
 ******************************************************************************
 */

package phwang.main;

import phwang.test.FrontTestClass;

public class MainClass {

	public static void main(String[] args) {
		PhwangRoot root = new PhwangRoot();
		
		Boolean http_test_on = true;
		if (http_test_on) {
			FrontTestClass http_test_root = new FrontTestClass(root.frontRoot().frontDExport());
			http_test_root.startTest();
		}
		
		Boolean android_test_on = true;
		if (android_test_on) {
			
		}
	}
}
