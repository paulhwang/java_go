/*
 ******************************************************************************
 *                                       
 *  Copyright (c) 2018 phwang. All rights reserved.
 *
 ******************************************************************************
 */

package Phwang.Utils;

public class UtilsClass {
	public static void sleep (int val) {
        try {
        	Thread.sleep(val);
        }
        catch(InterruptedException ex) {
        }
	}
}
