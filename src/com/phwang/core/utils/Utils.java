/*
 ******************************************************************************
 *                                       
 *  Copyright (c) 2021 phwang. All rights reserved.
 *
 ******************************************************************************
 */

package com.phwang.core.utils;

public class Utils {
	public static void sleep (int val) {
        try {
        	Thread.sleep(val);
        }
        catch(InterruptedException ex) {
        }
	}
}
