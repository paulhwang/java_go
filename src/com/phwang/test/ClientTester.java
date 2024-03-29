/*  Copyright (c) 2021 phwang. All rights reserved.
 *
 ******************************************************************************
 */

package com.phwang.test;

import com.phwang.core.utils.Abend;
import com.phwang.core.utils.ThreadMgr;
import com.phwang.core.utils.Utils;
import com.phwang.core.utils.ThreadEntityInt;
import com.phwang.core.utils.Encoders;
import com.phwang.core.utils.LockedInteger;
import com.phwang.client.ClientRoot;
import com.phwang.client.ClientDExport;
import com.phwang.client.ClientFabricInfo;
import com.phwang.client.ClientDImportInt;
import com.phwang.client.ClientGoAct;

class ClientTester implements ClientDImportInt {
    private String objectName() {return "ClientTester";}
    
    private ClientTest clientTest_;
    private ClientRoot clientRoot_;
        
    private ClientTest clientTest() { return this.clientTest_; }
    private ClientDExport clientDExport() { return this.clientRoot_.clientDExport(); }
    private ClientFabricInfo clientFabricInfo() { return this.clientRoot_.clientFabricInfo();}
    private ClientGoAct goAct() { return this.clientRoot_.goAct();}
    
    protected ClientTester(ClientTest client_test_val, int tester_index_val) {
        this.debug(false, "ClientTester", "init start");
        
        this.clientTest_ = client_test_val;
		this.clientRoot_ = new ClientRoot(this);
        this.clientFabricInfo().setMyName("client_" + Encoders.iEncodeRaw5(tester_index_val));
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
    	this.doSetupSession3();
	}
    
    private void doSetupSession2() {
    	this.clientDExport().setupSession2();
    }
	
	public void handleSetupSession2Response() {
    	this.debug(true, "handleSetupSession2Response", "sessionIdStr=" + this.clientFabricInfo().sessionIdStr());
	}
    
    private void doSetupSession3() {
    	this.clientDExport().setupSession3();
    }
	
	public void handleSetupSession3Response() {
    	this.debug(true, "handleSetupSession3Response", "sessionIdStr=" + this.clientFabricInfo().sessionIdStr());
		this.doPutSessionData("0812345678");
	}

    private void doPutSessionData(String data_str_val) {
    	Utils.sleep(100);
    	this.clientDExport().putSessionData(data_str_val);
    }
	
	public void handlePutSessionDataResponse() {
    	this.debug(true, "handlePutSessionDataResponse", "sessionIdStr=" + this.clientFabricInfo().sessionIdStr());
		this.doGetSessionData();
	}

    private void doGetSessionData() {
    	Utils.sleep(100);
    	this.clientDExport().getSessionData();
    }
	
	public void handleGetSessionDataResponse(String data_str_val) {
    	this.debug(true, "handleGetSessionDataResponse", "sessionIdStr=" + this.clientFabricInfo().sessionIdStr());
	}
     
    private void debug(Boolean on_off, String s0, String s1) { if (on_off) this.log(s0, s1); }
    private void log(String s0, String s1) { Abend.log(this.objectName() + "." + s0 + "()", s1); }
    protected void abend(String s0, String s1) { Abend.abend(this.objectName() + "." + s0 + "()", s1); }
}

