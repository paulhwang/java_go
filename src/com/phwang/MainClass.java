/*
 ******************************************************************************
 *                                       
 *  Copyright (c) 2021 phwang. All rights reserved.
 *
 ******************************************************************************
 */

package com.phwang;

import com.phwang.client.ClientRoot;
import com.phwang.client.ClientTest;
import com.phwang.core.root.CoreRoot;
import com.phwang.front.FrontRoot;

public class MainClass {

	public static void main(String[] args) {
		new CoreRoot();
		new FrontRoot().startTest(false);
		new ClientRoot();
		new ClientTest().startTest(true);
	}
}
