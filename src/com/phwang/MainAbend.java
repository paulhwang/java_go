/*
 ******************************************************************************
 *                                       
 *  Copyright (c) 2021 phwang. All rights reserved.
 *
 ******************************************************************************
 */

package com.phwang;

import com.phwang.core.utils.AbendInterface;

public class MainAbend implements AbendInterface {
	public void log(String str_val) {
        System.out.println(str_val);
	}
}
