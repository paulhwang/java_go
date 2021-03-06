/*
 ******************************************************************************
 *                                       
 *  Copyright (c) 2021 phwang. All rights reserved.
 *
 ******************************************************************************
 */


package phwang.test;

import phwang.utils.*;
import phwang.front.FrontDExportInterface;

public class HttpTest implements ThreadInterface {
    private String objectName() {return "HttpTest";}
    private String httpTestThreadName() { return "HttpTestThread"; }

    private FrontDExportInterface frontExportInterface_;
    private ThreadMgr threadMgr_;
    private LockedInteger threadCount_;
    
    public FrontDExportInterface frontExportInterface() { return this.frontExportInterface_; }
    public ThreadMgr threadMgr() { return this.threadMgr_; }

    public HttpTest(FrontDExportInterface front_export_interface_val) {
        this.debug(false, "HttpTest", "init start");
        
        this.frontExportInterface_ = front_export_interface_val;
        this.threadMgr_ = new ThreadMgr();
        this.threadCount_ = new LockedInteger(0);
    }
    
    public void startTest() {
    	this.threadMgr().createThreadObject(this.httpTestThreadName(), this);
     }
    
	public void threadCallbackFunction() {
		this.incrementThreadCount();
		this.httpTestThreadFunc();
		this.decrementThreadCount();
	}
    
    private void httpTestThreadFunc() {
        this.debug(true, "httpTestThreadFunc", "*******start " + this.httpTestThreadName());
        UtilsClass.sleep(100);  
        
        for (int j = 0; j < 1; j++) {
        	for (int i = 0; i < 2; i++) {
        		new HttpTestCase(this, i).startTestTest();
        		
        		//UtilsClass.sleep(1);
        	}
        }
    }
    
    public void incrementThreadCount() {
  		this.threadCount_.increment();
    	this.debug(false, "incrementThreadCount", "*************************" + this.threadCount_.get());
    }
    
    public void decrementThreadCount() {
  		this.threadCount_.decrement();
    	this.debug(true, "decrementThreadCount", "*************************" + this.threadCount_.get());
    	if (this.threadCount_.get() < 0) {
    		this.abend("decrementThreadCount", "smaller than 0");
    	}
    }
    
    private void debug(Boolean on_off, String s0, String s1) { if (on_off) this.log(s0, s1); }
    private void log(String s0, String s1) { Abend.log(this.objectName() + "." + s0 + "()", s1); }
    public void abend(String s0, String s1) { Abend.abend(this.objectName() + "." + s0 + "()", s1); }
}
