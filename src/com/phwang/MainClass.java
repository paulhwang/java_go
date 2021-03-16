/*
 ******************************************************************************
 *                                       
 *  Copyright (c) 2021 phwang. All rights reserved.
 *
 ******************************************************************************
 */

package com.phwang;

import com.phwang.core.root.CoreRoot;
import com.phwang.front.FrontRoot;

public class MainClass {

	public static void main(String[] args) {
		new CoreRoot().startTest(false);
		new FrontRoot().startTest(true);
	}
}
