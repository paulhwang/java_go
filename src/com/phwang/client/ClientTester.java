/*  Copyright (c) 2021 phwang. All rights reserved.
 *
 ******************************************************************************
 */

package com.phwang.client;

import com.phwang.core.utils.Abend;
import com.phwang.core.utils.ThreadMgr;
import com.phwang.core.utils.ThreadEntityInt;
import com.phwang.core.utils.EncodeNumber;

class ClientTester implements ThreadEntityInt {
    private String objectName() {return "ClientTester";}
    private String clientTestThreadName() { return "ClientTestThread"; }
    
    private ClientTest clientTest_;
    private ClientRoot clientRoot_;
    private String indexString_;
    private String myNameString_;
    private String password_ = "Tennis";
        
    private ClientTest clientTest() { return this.clientTest_; }
    private ThreadMgr threadMgr() { return this.clientTest().threadMgr();}

    private ClientDExport clientDExport() { return this.clientRoot_.clientDExport(); }
    
    protected ClientTester(ClientTest android_test_val, int index_val) {
        this.debug(false, "ClientTester", "init start");
        
        this.clientTest_ = android_test_val;
        this.indexString_ = EncodeNumber.encode(index_val, 6);
        this.myNameString_ = "Android_" + this.indexString_;
        
		this.clientRoot_ = new ClientRoot();
        
    }
    
    protected void startTest() {
    	this.threadMgr().createThreadObject(this.clientTestThreadName(), this);
     }

	public void threadCallbackFunction() {
		this.clientTest().incrementThreadCount();
		this.clientTestCaseThreadFunc();
		this.clientTest().decrementThreadCount();
	}
    
    private void clientTestCaseThreadFunc() {
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
    	this.clientDExport().setupLink(this.myNameString_, this.password_);
    }
    
    public void parserSetupLinkResponse() {
    	this.debug(true, "*****parserSetupLinkResponse", "linkIdStr=" + this.clientRoot_.clientFabricInfo().linkIdStr());
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

