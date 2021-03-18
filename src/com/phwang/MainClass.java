/*
 ******************************************************************************
 *                                       
 *  Copyright (c) 2021 phwang. All rights reserved.
 *
 ******************************************************************************
 */

package com.phwang;

import com.phwang.client.ClientRoot;
import com.phwang.test.ClientTest;
import com.phwang.core.root.CoreRoot;
import com.phwang.front.FrontRoot;

public class MainClass {
	private static FrontRoot frontRoot_;
	
	public static void main(String[] args) {
		new CoreRoot();
		
		frontRoot_ = new FrontRoot();
		frontRoot_.startTest(true);
		
		new ClientRoot();
		new ClientTest().startTest(false);
	}
}
