/*
 ******************************************************************************
 *                                       
 *  Copyright (c) 2018 phwang. All rights reserved.
 *
 ******************************************************************************
 */

package Phwang.Main;

import Phwang.Utils.Binder.BinderTestClass;

public class MainClass {
	static public GlobalVariableClass globalVariableObject;

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		globalVariableObject = new GlobalVariableClass();
		globalVariableObject.getGoRoot();
		
		globalVariableObject.doTest(true);
	}
}
