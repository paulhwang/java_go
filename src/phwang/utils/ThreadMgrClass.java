/*
 ******************************************************************************
 *                                       
 *  Copyright (c) 2018 phwang. All rights reserved.
 *
 ******************************************************************************
 */

package phwang.utils;

public class ThreadMgrClass {
    private String objectName() {return "ThreadMgrClass";}

    public ThreadMgrClass() {
        this.debug(false, "ThreadMgrClass", "init start");
    	
    }

    public ThreadClass createThreadObject(String thread_name_val, ThreadInterface calling_object_val) {
    	ThreadClass thread_object = new ThreadClass(thread_name_val, calling_object_val);
    	
    	this.insertToThreadList(thread_object);
    	return thread_object;
    }
    
    private void insertToThreadList(ThreadClass thread_object_val) {
    	
    }
    
    private void debug(Boolean on_off, String s0, String s1) { if (on_off) this.log(s0, s1); }
    private void log(String s0, String s1) { AbendClass.log(this.objectName() + "." + s0 + "()", s1); }
    public void abend(String s0, String s1) { AbendClass.abend(this.objectName() + "." + s0 + "()", s1); }
}
