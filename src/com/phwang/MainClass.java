/*
 ******************************************************************************
 *                                       
 *  Copyright (c) 2021 phwang. All rights reserved.
 *
 ******************************************************************************
 */

package com.phwang;

import com.phwang.core.utils.Abend;
import com.phwang.client.ClientRoot;
import com.phwang.test.ClientTest;
import com.phwang.test.FrontTest;
import com.phwang.core.root.CoreRoot;
import com.phwang.front.FrontRoot;

public class MainClass {
	public static void main(String[] args) {
		Abend.initAbend(new MainAbend());
		new CoreRoot();
		
		FrontRoot front_root = new FrontRoot();
		new FrontTest(front_root.frontDExport(), 1, 1).startTest(true);
		
		//new ClientRoot();
		new ClientTest(1, 1).startTest(false);
	}
}
