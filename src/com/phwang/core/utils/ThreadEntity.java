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

    private String threadName_;
    private Thread thread_;
	private ThreadEntityInt callingObject_;
	
    public Thread thread() { return this.thread_; }
    public String threadName( ) { return this.threadName_; }
    
    public ThreadEntity(String thread_name_val, ThreadEntityInt calling_object_val) {
        this.debug(false, "ThreadEntity", "init start");
    	
        this.threadName_ = thread_name_val;
    	this.StartThread(calling_object_val);
    }
    
    private void StartThread(ThreadEntityInt calling_object_val) {
        this.debug(false, "StartThread", "Create thread (" + this.threadName() + ")");
        this.callingObject_ = calling_object_val;
        this.thread_ = new Thread(this);
        this.thread_.start();
    }
    
	public void run() {
		this.callingObject_.threadCallbackFunction();
        this.debug(false, "run", this.threadName() + "exit");
	}
    
    private void debug(Boolean on_off, String s0, String s1) { if (on_off) this.log(s0, s1); }
    private void log(String s0, String s1) { Abend.log(this.objectName() + "." + s0 + "()", s1); }
    protected void abend(String s0, String s1) { Abend.abend(this.objectName() + "." + s0 + "()", s1); }
}
