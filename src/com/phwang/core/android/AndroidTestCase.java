/*  Copyright (c) 2021 phwang. All rights reserved.
 *
 ******************************************************************************
 */

package com.phwang.core.android;

import com.phwang.core.utils.Abend;
import com.phwang.core.utils.ThreadMgr;
import com.phwang.core.utils.ThreadEntityInt;
import com.phwang.core.utils.EncodeNumber;

class AndroidTestCase implements ThreadEntityInt {
    private String objectName() {return "AndroidTestCase";}
    private String androidTestThreadName() { return "AndroidTestThread"; }
    
    private AndroidTest androidTest_;
    private AndroidRoot androidRoot_;
    private String indexString_;
    private String myNameString_;
    private String password_ = "Tennis";
        
    private AndroidTest androidTest() { return this.androidTest_; }
    private ThreadMgr threadMgr() { return this.androidTest().threadMgr();}

    protected AndroidTestCase(AndroidTest android_test_val, int index_val) {
        this.debug(false, "AndroidTestCase", "init start");
        
        this.androidTest_ = android_test_val;
        this.indexString_ = EncodeNumber.encode(index_val, 6);
        this.myNameString_ = "Android_" + this.indexString_;
        
		this.androidRoot_ = new AndroidRoot();
        
    }
    
    protected void startTestTest() {
    	this.threadMgr().createThreadObject(this.androidTestThreadName(), this);
     }

	public void threadCallbackFunction() {
		this.androidTest().incrementThreadCount();
		this.androidTestCaseThreadFunc();
		this.androidTest().decrementThreadCount();
	}
    
    private void androidTestCaseThreadFunc() {
        try {
        	Thread.sleep(100);
        }
        catch (Exception ignore) {}
    	
    	this.doSetupLink();
    	
    	//UtilsClass.sleep(100);
    	this.doGetLinkData();
    	
    	//UtilsClass.sleep(100);
    	this.doGetNameList();
    	
    	//UtilsClass.sleep(100);
    	this.doSetupSession();
    	
    	//Utils.sleep(100);
    	this.doSetupSession3();
    	
    	//Utils.sleep(100);
    	this.doPutSessionData();
    	
    	//Utils.sleep(100);
    	this.doGetSessionData();
    	
    	//Utils.sleep(100);
    	this.doSetupSession2();
    }
    
    private void doSetupLink() {
    	this.debug(false, "doSetupLink", "doSetupLink");
    	this.androidRoot_.androidDExport().setupLink(this.myNameString_, this.password_);
    }
    
    private void doGetLinkData() {
    }
    
    private void doGetNameList() {
    }
    
    private void doSetupSession() {
    }
    
    private void doSetupSession2() {
    }
    
    private void doSetupSession3() {
    }

    private void doPutSessionData() {
    }

    private void doGetSessionData() {
    }
     
    private void debug(Boolean on_off, String s0, String s1) { if (on_off) this.log(s0, s1); }
    private void log(String s0, String s1) { Abend.log(this.objectName() + "." + s0 + "()", s1); }
    protected void abend(String s0, String s1) { Abend.abend(this.objectName() + "." + s0 + "()", s1); }
}

