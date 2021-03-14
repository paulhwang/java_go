/*
 ******************************************************************************
 *                                       
 *  Copyright (c) 2021 phwang. All rights reserved.
 *
 ******************************************************************************
 */

package com.phwang.core.utils;

public class ThreadMgr {
    private String objectName() {return "ThreadMgr";}

    public ThreadMgr() {
        this.debug(false, "ThreadMgr", "init start");
    	
    }

    public ThreadEntity createThreadObject(String thread_name_val, ThreadEntityInt calling_object_val) {
    	ThreadEntity thread_object = new ThreadEntity(thread_name_val, calling_object_val);
    	
    	this.insertToThreadList(thread_object);
    	return thread_object;
    }
    
    private void insertToThreadList(ThreadEntity thread_object_val) {
    	
    }
    
    private void debug(Boolean on_off, String s0, String s1) { if (on_off) this.log(s0, s1); }
    private void log(String s0, String s1) { Abend.log(this.objectName() + "." + s0 + "()", s1); }
    public void abend(String s0, String s1) { Abend.abend(this.objectName() + "." + s0 + "()", s1); }
}
