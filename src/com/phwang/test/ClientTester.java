/*  Copyright (c) 2021 phwang. All rights reserved.
 *
 ******************************************************************************
 */

package com.phwang.test;

import com.phwang.core.utils.Abend;
import com.phwang.core.utils.ThreadMgr;
import com.phwang.core.utils.ThreadEntityInt;
import com.phwang.core.utils.EncodeNumber;
import com.phwang.core.utils.LockedInteger;
import com.phwang.client.ClientRoot;
import com.phwang.client.ClientDExport;
import com.phwang.client.ClientFabricInfo;

class ClientTester implements ThreadEntityInt {
    private String objectName() {return "ClientTester";}
    private String clientTestCaseThreadName() { return "ClientTestCaseThread"; }
    
    private ClientTest clientTest_;
    private ClientRoot clientRoot_;
    private String myNameStr_;
    private String password_ = "Tennis";
    private LockedInteger caseIndex_;
        
    private ClientTest clientTest() { return this.clientTest_; }
    private ThreadMgr threadMgr() { return this.clientTest().threadMgr();}
    private ClientDExport clientDExport() { return this.clientRoot_.clientDExport(); }
    private ClientFabricInfo clientFabricInfo() { return this.clientRoot_.clientFabricInfo();}
    
    protected ClientTester(ClientTest client_test_val, ClientRoot client_root_val, int tester_index_val) {
        this.debug(false, "ClientTester", "init start");
        
        this.clientTest_ = client_test_val;
		this.clientRoot_ = client_root_val;
        this.myNameStr_ = "client_" + EncodeNumber.encode(tester_index_val, 5);
        this.caseIndex_ = new LockedInteger(0);
    }
    
    protected void startTest() {
    	this.threadMgr().createThreadObject(this.clientTestCaseThreadName(), this);
     }

	public void threadCallbackFunction() {
		this.clientTest().incrementThreadCount();
		this.clienterThreadFunc();
		this.clientTest().decrementThreadCount();
	}
    
    private void clienterThreadFunc() {
        try {
        	Thread.sleep(100);
        }
        catch (Exception ignore) {}
        
    	this.caseIndex_.increment();
    	int case_index = this.caseIndex_.get();
    	String my_name = this.myNameStr_ + EncodeNumber.encode(case_index, 5);
    	this.doSetupLink(my_name);
    	
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
    
    private void doSetupLink(String my_name_val) {
    	this.debug(false, "doSetupLink", "doSetupLink");
    	this.clientDExport().setupLink(my_name_val, this.password_);
    }
    
    public void parserSetupLinkResponse() {
    	this.debug(true, "*****parserSetupLinkResponse", "linkIdStr=" + this.clientFabricInfo().linkIdStr());
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

