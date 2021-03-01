/*
 ******************************************************************************
 *                                       
 *  Copyright (c) 2018 phwang. All rights reserved.
 *
 ******************************************************************************
 */

package phwang.utils;

import phwang.utils.*;

public class ThreadClass implements Runnable {
    private String objectName() {return "ThreadClass";}

    private String threadName;
    private Thread theThread;
	private ThreadInterface callingObject;
	
    public String ThreadName( ) { return this.threadName; }
    
    public ThreadClass(String thread_name_val, ThreadInterface calling_object_val) {
        this.debug(false, "ThreadClass", "init start");
    	
        this.threadName = thread_name_val;
    	this.StartThread(calling_object_val);
    }
    
    public void StartThread(ThreadInterface calling_object_val) {
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
    private void log(String s0, String s1) { AbendClass.log(this.objectName() + "." + s0 + "()", s1); }
    public void abend(String s0, String s1) { AbendClass.abend(this.objectName() + "." + s0 + "()", s1); }
}
