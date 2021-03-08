/*
 ******************************************************************************
 *                                       
 *  Copyright (c) 2021 phwang. All rights reserved.
 *
 ******************************************************************************
 */


package phwang.test;

import phwang.android.AndroidDExportInt;
import phwang.utils.*;

public class AndroidTest implements ThreadEntityInt {
    private String objectName() {return "AndroidTest";}
    private String androidTestThreadName() { return "AndroidTestThread"; }

    private int i_ = 1;
    private int j_ = 1;

    private AndroidDExportInt androidExportInt_;
    private ThreadMgr threadMgr_;
    private LockedInteger threadCount_;
    
    private AndroidDExportInt androidExportInt() { return this.androidExportInt_; }
    public ThreadMgr threadMgr() { return this.threadMgr_; }

    public AndroidTest(AndroidDExportInt android_export_int_val) {
        this.debug(false, "AndroidTest", "init start");
        
        this.androidExportInt_ = android_export_int_val;
        this.threadMgr_ = new ThreadMgr();
        this.threadCount_ = new LockedInteger(0);
    }
    
    public void startTest() {
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
