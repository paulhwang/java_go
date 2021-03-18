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
import com.phwang.client.ClientDImportInt;

class ClientTester implements ClientDImportInt {
    private String objectName() {return "ClientTester";}
    
    private ClientTest clientTest_;
    private ClientRoot clientRoot_;
        
    private ClientTest clientTest() { return this.clientTest_; }
    private ClientDExport clientDExport() { return this.clientRoot_.clientDExport(); }
    private ClientFabricInfo clientFabricInfo() { return this.clientRoot_.clientFabricInfo();}
    
    protected ClientTester(ClientTest client_test_val, int tester_index_val) {
        this.debug(false, "ClientTester", "init start");
        
        this.clientTest_ = client_test_val;
		this.clientRoot_ = new ClientRoot(this);
        this.clientFabricInfo().setMyName("client_" + EncodeNumber.encode(tester_index_val, 5));
        this.clientFabricInfo().setHisName(this.clientFabricInfo().myName());
        this.clientFabricInfo().setPassword("TENNIS");
    }
    
    protected void startTest() {
    	this.doSetupLink();
     }
    
    private void doSetupLink() {
    	this.debug(false, "doSetupLink", "doSetupLink");
    	this.clientDExport().setupLink();
    }
    
	public void handleSetupLinkResponse() {
    	this.debug(true, "handleSetupLinkResponse", "linkIdStr" + this.clientFabricInfo().linkIdStr());
    	this.doGetLinkData();
	}
    
    private void doGetLinkData() {
    	this.clientDExport().getLinkData();
    }
	
	public void handleGetLinkDataResponse() {
    	this.debug(true, "handleGetLinkDataResponse", "***");
    	//this.doGetNameList();
		this.doSetupSession();
	}
    
    private void doGetNameList() {
    	this.clientDExport().getNameList();
    }
	
	public void handleGetNameListResponse() {
    	this.debug(true, "handleGetNameListResponse", "***");
	}
    
    private void doSetupSession() {
    	this.clientDExport().setupSession();
    }
	
	public void handleSetupSessionResponse() {
    	this.debug(true, "handleSetupSessionResponse", "sessionIdStr=" + this.clientFabricInfo().sessionIdStr());
		
	}
    
    private void doSetupSession2() {
    }
	
	public void handleSetupSession2Response() {
	
	}
    
    private void doSetupSession3() {
    }
	
	public void handleSetupSession3Response() {
		
	}

    private void doPutSessionData() {
    }
	
	public void handlePutSessionDataResponse() {
		
	}

    private void doGetSessionData() {
    }
	
	public void handleGetSessionDataResponse() {
		
	}
     
    private void debug(Boolean on_off, String s0, String s1) { if (on_off) this.log(s0, s1); }
    private void log(String s0, String s1) { Abend.log(this.objectName() + "." + s0 + "()", s1); }
    protected void abend(String s0, String s1) { Abend.abend(this.objectName() + "." + s0 + "()", s1); }
}

