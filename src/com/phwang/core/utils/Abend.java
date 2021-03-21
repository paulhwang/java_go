/*
 ******************************************************************************
 *                                       
 *  Copyright (c) 2021 phwang. All rights reserved.
 *
 ******************************************************************************
 */

package com.phwang.core.utils;

import java.util.concurrent.locks.*;

public class Abend {
	static private boolean logStopped = false;
    static private Lock abendLock = new ReentrantLock();
    static private AbendInterface abendInterface;
	
    static public void initAbend(AbendInterface abend_interface_val) {
    	abendInterface = abend_interface_val;
    }
    
    static public void log(String s0, String s1) {
    	if (logStopped) {
    		return;
    	}
    	abendInterface.log(s0 + " " + s1);
    }

    static public void abend(String s0, String s1) {
    	if (logStopped) {
    		return;
    	}
    	logStopped = true;
   	
    	abendLock.lock();
    	System.out.println("***ABEND*** " + s0 + " " + s1);
    	forceCrash();
    	abendLock.unlock();
    }
    
	static Boolean crashHere;
    static private void forceCrash() {
    	if (crashHere) return;
    }
}
