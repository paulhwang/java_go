/*
 ******************************************************************************
 *                                       
 *  Copyright (c) 2021 phwang. All rights reserved.
 *
 ******************************************************************************
 */

package com.phwang.core.utils;

import com.phwang.core.utils.*;

public class ThreadEntity implements Runnable {
    private String objectName() {return "ThreadEntity";}

    private String threadName;
    private Thread theThread;
	private ThreadEntityInt callingObject;
	
    public String ThreadName( ) { return this.threadName; }
    
    public ThreadEntity(String thread_name_val, ThreadEntityInt calling_object_val) {
        this.debug(false, "ThreadEntity", "init start");
    	
        this.threadName = thread_name_val;
    	this.StartThread(calling_object_val);
    }
    
    public void StartThread(ThreadEntityInt calling_object_val) {
        this.debug(false, "StartThread", "Create thread (" + this.ThreadName() + ")");
        this.callingObject = calling_object_val;
        this.theThread = new Thread(this);
        this.theThread.start();
    }
    
	public void run() {
		this.callingObject.threadCallbackFunction();
        this.debug(false, "run", this.ThreadName() + "exit");
	}
    
    private void debug(Boolean on_off, String s0, String s1) { if (on_off) this.log(s0, s1); }
    private void log(String s0, String s1) { Abend.log(this.objectName() + "." + s0 + "()", s1); }
    public void abend(String s0, String s1) { Abend.abend(this.objectName() + "." + s0 + "()", s1); }
}
