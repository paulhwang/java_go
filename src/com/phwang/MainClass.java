/*
 ******************************************************************************
 *                                       
 *  Copyright (c) 2021 phwang. All rights reserved.
 *
 ******************************************************************************
 */

package com.phwang;

import com.phwang.core.android.AndroidRoot;
import com.phwang.core.android.AndroidTest;
import com.phwang.core.root.CoreRoot;
import com.phwang.front.FrontRoot;

public class MainClass {

	public static void main(String[] args) {
		new CoreRoot();
		new FrontRoot().startTest(false);
		new AndroidRoot();
		new AndroidTest().startTest(true);
	}
}
