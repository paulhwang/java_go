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
	private static PhwangRoot phwangRoot_;

	public static void main(String[] args) {
		phwangRoot_ = new PhwangRoot();
		
		Boolean httpTestOn = true;
		Boolean androidTestOn = true;
		new TestRoot(httpTestOn, androidTestOn);
	}
}
