/*
 ******************************************************************************
 *                                       
 *  Copyright (c) 2021 phwang. All rights reserved.
 *
 ******************************************************************************
 */


package com.phwang.core.android;

import com.phwang.core.utils.Abend;
import com.phwang.core.utils.ThreadEntityInt;
import com.phwang.core.utils.ThreadMgr;
import com.phwang.core.utils.LockedInteger;
import com.phwang.core.utils.Utils;

public class AndroidTest implements ThreadEntityInt {
    private String objectName() {return "AndroidTest";}
    private String androidTestThreadName() { return "AndroidTestThread"; }

    private int i_ = 1;
    private int j_ = 1;

    private ThreadMgr threadMgr_;
    private LockedInteger threadCount_;
    
    protected ThreadMgr threadMgr() { return this.threadMgr_; }

    public AndroidTest() {
        this.debug(false, "AndroidTest", "init start");
        
        this.threadMgr_ = new ThreadMgr();
        this.threadCount_ = new LockedInteger(0);
    }
    
    public void startTest(Boolean test_on_val) {
    	if (!test_on_val) {
    		return;
    	}
    	
    	this.threadMgr().createThreadObject(this.androidTestThreadName(), this);
    }
    
	public void threadCallbackFunction() {
		this.incrementThreadCount();
		this.httpTestThreadFunc();
		this.decrementThreadCount();
	}
    
    private void httpTestThreadFunc() {
        this.debug(true, "httpTestThreadFunc", "*******start " + this.androidTestThreadName());
        Utils.sleep(100);  
        
        for (int j = 0; j < this.i_; j++) {
        	for (int i = 0; i < this.j_; i++) {
        		new AndroidTestCase(this, i).startTestTest();
        		
        		//UtilsClass.sleep(1);
        	}
        }
    }
    
    protected void incrementThreadCount() {
  		this.threadCount_.increment();
    	this.debug(false, "incrementThreadCount", "*************************" + this.threadCount_.get());
    }
    
    protected void decrementThreadCount() {
  		this.threadCount_.decrement();
    	this.debug(true, "decrementThreadCount", "*************************" + this.threadCount_.get());
    	if (this.threadCount_.get() < 0) {
    		this.abend("decrementThreadCount", "smaller than 0");
    	}
    }
    
    private void debug(Boolean on_off, String s0, String s1) { if (on_off) this.log(s0, s1); }
    private void log(String s0, String s1) { Abend.log(this.objectName() + "." + s0 + "()", s1); }
    protected void abend(String s0, String s1) { Abend.abend(this.objectName() + "." + s0 + "()", s1); }
}
