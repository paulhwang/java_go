/*
 ******************************************************************************
 *                                       
 *  Copyright (c) 2021 phwang. All rights reserved.
 *
 ******************************************************************************
 */


package com.phwang.test;

import com.phwang.core.utils.*;
import com.phwang.front.FrontDExportInt;

public class FrontTest implements ThreadEntityInt {
    private String objectName() {return "FrontTest";}
    private String httpTestThreadName() { return "HttpTestThread"; }

    private int i_ = 1;
    private int j_ = 1;
    
    private FrontDExportInt frontExportInt_;
    private ThreadMgr threadMgr_;
    private LockedInteger threadCount_;
    
    protected FrontDExportInt frontExportInt() { return this.frontExportInt_; }
    protected ThreadMgr threadMgr() { return this.threadMgr_; }

    public FrontTest(FrontDExportInt front_export_int_val) {
        this.debug(false, "FrontTest", "init start");
        
        this.frontExportInt_ = front_export_int_val;
        this.threadMgr_ = new ThreadMgr();
        this.threadCount_ = new LockedInteger(0);
    }
    
    public void startTest(Boolean test_on_val) {
    	if (!test_on_val) {
    		return;
    	}

    	this.threadMgr().createThreadObject(this.httpTestThreadName(), this);
     }
    
	public void threadCallbackFunction() {
		this.incrementThreadCount();
		this.httpTestThreadFunc();
		this.decrementThreadCount();
	}
    
    private void httpTestThreadFunc() {
        this.debug(true, "httpTestThreadFunc", "*******start " + this.httpTestThreadName());
        //Utils.sleep(100);  
        
        for (int j = 0; j < this.i_; j++) {
        	for (int i = 0; i < this.j_; i++) {
        		new FrontTestCase(this, i).startTestTest();
        		
        		//UtilsClass.sleep(1);
        	}
        }
    }
    
    protected void incrementThreadCount() {
  		this.threadCount_.increment();
    	this.debug(false, "incrementThreadCount", "***" + this.threadCount_.get());
    }
    
    protected void decrementThreadCount() {
  		this.threadCount_.decrement();
    	if (this.threadCount_.get() < 0) {
    		this.abend("decrementThreadCount", "smaller than 0");
    	}
    	
    	if (this.threadCount_.get() < 3) {
    		this.debug(true, "decrementThreadCount", "***" + this.threadCount_.get());
    	}
    }
    
    private void debug(Boolean on_off, String s0, String s1) { if (on_off) this.log(s0, s1); }
    private void log(String s0, String s1) { Abend.log(this.objectName() + "." + s0 + "()", s1); }
    protected void abend(String s0, String s1) { Abend.abend(this.objectName() + "." + s0 + "()", s1); }
}
