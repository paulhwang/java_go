/*
 ******************************************************************************
 *                                       
 *  Copyright (c) 2018 phwang. All rights reserved.
 *
 ******************************************************************************
 */

package phwang.main;

import phwang.utils.*;

public class MainClass {
	static public GlobalVariableClass globalVariableObject;

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		globalVariableObject = new GlobalVariableClass();
		globalVariableObject.getGoRoot();
		
		globalVariableObject.doTest(false);
	}
}
