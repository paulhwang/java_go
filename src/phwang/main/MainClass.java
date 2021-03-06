/*
 ******************************************************************************
 *                                       
 *  Copyright (c) 2021 phwang. All rights reserved.
 *
 ******************************************************************************
 */

package phwang.main;

import phwang.test.TestRoot;

public class MainClass {
	static public GlobalVariableClass globalVariableObject;

	public static void main(String[] args) {
		globalVariableObject = new GlobalVariableClass();
		
		Boolean httpTestOn = true;
		Boolean androidTestOn = true;
		new TestRoot(httpTestOn, androidTestOn);
	}
}
