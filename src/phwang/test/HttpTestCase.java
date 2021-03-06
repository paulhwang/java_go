/*  Copyright (c) 2021 phwang. All rights reserved.
 *
 ******************************************************************************
 */

package phwang.test;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import phwang.utils.*;
import phwang.front.FrontDExportInterface;

/*
 ******************************************************************************
 *                                       
 *  Copyright (c) 2018 phwang. All rights reserved.
 *
 ******************************************************************************
 */

class HttpTestCase implements ThreadInterface {
    private String objectName() {return "HttpTestCase";}
    private String frontTestCaseThreadName() { return "FrontTestCaseThread"; }
    
    private HttpTest frontTestObject_;
    private String indexString;
    private String myNameString;
    private String password = "Tennis";
    private String themeData = "88889999G009090000";///////////////////
    private String themeData2 = "G009090000";
    private String acceptString = "Yes";
    private String xmtSeqString = "1111";
    private String goMoveString = "GM01021001";
    private String nameListTagString = "0000";///////////////////////////////////////////////////////different from brouser which is int
    private String linkIdString;
    private String sessionIdString;
    private String themeIdString = "33333333";///////////////////////////////////////////////
    
    private HttpTest frontTestObject() { return this.frontTestObject_; }
    private ThreadMgrClass threadMgrObject() { return this.frontTestObject().threadMgrObject();}
    private FrontDExportInterface frontExportInterface() { return this.frontTestObject().frontExportInterface();}

    public HttpTestCase(HttpTest http_test_val, int index_val) {
        this.debug(false, "HttpTestCase", "init start");
        
        this.frontTestObject_ = http_test_val;
        this.indexString = EncodeNumberClass.encodeNumber(index_val, 6);
        this.myNameString = "Test_" + this.indexString;
    }
    
    public void startTestTest() {
    	this.threadMgrObject().createThreadObject(this.frontTestCaseThreadName(), this);
     }

	public void threadCallbackFunction() {
		this.frontTestObject().incrementThreadCount();
		this.frontTestCaseThreadFunc();
		this.frontTestObject().decrementThreadCount();
	}
    
    private void frontTestCaseThreadFunc() {
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
    	
    	UtilsClass.sleep(100);
    	this.doSetupSession3();
    	
    	UtilsClass.sleep(100);
    	this.doPutSessionData();
    	
    	UtilsClass.sleep(100);
    	this.doGetSessionData();
    	
    	UtilsClass.sleep(100);
    	//this.doSetupSession2();
    }
    
    private void doSetupLink() {
    	JSONObject json_data = new JSONObject();
    	json_data.put("my_name", this.myNameString);
    	json_data.put("password", this.password);
    	String str_json_data = json_data.toJSONString();
    	
    	JSONObject json_request = new JSONObject();
    	json_request.put("command", "setup_link");
    	json_request.put("data", str_json_data);
    	String str_json_request = json_request.toJSONString();
    	
    	String str_json_ajex_response = this.frontExportInterface().processHttpRequestPacket(str_json_request);
        this.debug(false, "doSetupLink", "str_json_ajex_response=" + str_json_ajex_response);
    	
        try {
            JSONParser parser = new JSONParser();
        	JSONObject json_ajex_response = (JSONObject) parser.parse(str_json_ajex_response); 
        	
        	String command = (String) json_ajex_response.get("command");
        	String str_json_response_data = (String) json_ajex_response.get("data");
        	JSONObject json_response_data = (JSONObject) parser.parse(str_json_response_data); 
        	

            String name = (String) json_response_data.get("my_name");
            this.linkIdString = (String) json_response_data.get("link_id");
            if (!this.myNameString.equals(name)) {
            	this.abend("doSetupLink", "name not match");
            }
        } catch (Exception e) {
        	this.abend("doSetupLink", "***Exception*** str_json_ajex_response=" + str_json_ajex_response);
        }
    }
    
    private void doGetLinkData() {
    	JSONObject json_data = new JSONObject();
    	json_data.put("link_id", this.linkIdString);
    	String str_json_data = json_data.toJSONString();
    	
    	JSONObject json_request = new JSONObject();
    	json_request.put("command", "get_link_data");
    	json_request.put("data", str_json_data);
    	String str_json_request = json_request.toJSONString();
    	
    	String str_json_ajex_response = this.frontExportInterface().processHttpRequestPacket(str_json_request);
        this.debug(false, "doGetLinkData", "ajex_response data=" + str_json_ajex_response);
    }
    
    private void doGetNameList() {
    	JSONObject json_data = new JSONObject();
    	json_data.put("link_id", this.linkIdString);
    	json_data.put("name_list_tag", this.nameListTagString);
    	String str_json_data = json_data.toJSONString();
    	
    	JSONObject json_request = new JSONObject();
    	json_request.put("command", "get_name_list");
    	json_request.put("data", str_json_data);
    	String str_json_request = json_request.toJSONString();
    	
    	String str_json_ajex_response = this.frontExportInterface().processHttpRequestPacket(str_json_request);
        this.debug(false, "doGetNameList", "ajex_response data=" + str_json_ajex_response);
    }
    
    private void doSetupSession() {
    	JSONObject json_data = new JSONObject();
    	json_data.put("link_id", this.linkIdString);
    	json_data.put("his_name", this.myNameString);
    	json_data.put("theme_data", this.themeData);
    	String str_json_data = json_data.toJSONString();
    	
    	JSONObject json_request = new JSONObject();
    	json_request.put("command", "setup_session");
    	json_request.put("data", str_json_data);
    	String str_json_request = json_request.toJSONString();
    	
    	String str_json_ajex_response = this.frontExportInterface().processHttpRequestPacket(str_json_request);
        this.debug(false, "doSetupSession", "ajex_response data=" + str_json_ajex_response);
    	
        try {
            JSONParser parser = new JSONParser();
        	JSONObject json_ajex_response = (JSONObject) parser.parse(str_json_ajex_response);

        	String command = (String) json_ajex_response.get("command");
        	String str_json_response_data = (String) json_ajex_response.get("data");
        	JSONObject json_response_data = (JSONObject) parser.parse(str_json_response_data); 
        	
            String link_id = (String) json_response_data.get("link_id");
            this.sessionIdString = (String) json_response_data.get("session_id");
            if (!this.linkIdString.equals(link_id)) {
            	this.abend("doSetupSession", "link_id not match");
            }
        } catch (Exception e) {
        	this.abend("doSetupSession", "***Exception***");
        }
    }
    
    private void doSetupSession2() {
    	JSONObject json_data = new JSONObject();
    	json_data.put("link_id", this.linkIdString);
    	json_data.put("session_id", this.sessionIdString);
    	json_data.put("theme_id", this.themeIdString);
    	json_data.put("accept", this.acceptString);
    	json_data.put("theme_data", this.themeData2);
    	String str_json_data = json_data.toJSONString();
    	
    	JSONObject json_request = new JSONObject();
    	json_request.put("command", "setup_session2");
    	json_request.put("data", str_json_data);
    	String str_json_request = json_request.toJSONString();
    	
    	String str_json_ajex_response = this.frontExportInterface().processHttpRequestPacket(str_json_request);
        this.debug(false, "doSetupSession2", "ajex_response data=" + str_json_ajex_response);
    }
    
    private void doSetupSession3() {
    	JSONObject json_data = new JSONObject();
    	json_data.put("link_id", this.linkIdString);
    	json_data.put("session_id", this.sessionIdString);
    	String str_json_data = json_data.toJSONString();
    	
    	JSONObject json_request = new JSONObject();
    	json_request.put("command", "setup_session3");
    	json_request.put("data", str_json_data);
    	String str_json_request = json_request.toJSONString();
    	
    	String str_json_ajex_response = this.frontExportInterface().processHttpRequestPacket(str_json_request);
        this.debug(false, "doSetupSession3", "ajex_response data=" + str_json_ajex_response);
    }

    private void doPutSessionData() {
    	JSONObject json_data = new JSONObject();
    	json_data.put("link_id", this.linkIdString);
    	json_data.put("session_id", this.sessionIdString);
    	json_data.put("xmt_seq", this.xmtSeqString);
    	json_data.put("data", this.goMoveString);
    	String str_json_data = json_data.toJSONString();
    	
    	JSONObject json_request = new JSONObject();
    	json_request.put("command", "put_session_data");
    	json_request.put("data", str_json_data);
    	String str_json_request = json_request.toJSONString();
    	
    	String str_json_ajex_response = this.frontExportInterface().processHttpRequestPacket(str_json_request);
        this.debug(false, "doPutSessionData", "ajex_response data=" + str_json_ajex_response);
    }

    private void doGetSessionData() {
    	JSONObject json_data = new JSONObject();
    	json_data.put("link_id", this.linkIdString);
    	json_data.put("session_id", this.sessionIdString);
    	String str_json_data = json_data.toJSONString();
    	
    	JSONObject json_request = new JSONObject();
    	json_request.put("command", "get_session_data");
    	json_request.put("data", str_json_data);
    	String str_json_request = json_request.toJSONString();
    	
    	String str_json_ajex_response = this.frontExportInterface().processHttpRequestPacket(str_json_request);
        this.debug(true, "doGetSessionData", "ajex_response data=" + str_json_ajex_response);
    }
    
    private void debug(Boolean on_off, String s0, String s1) { if (on_off) this.log(s0, s1); }
    private void log(String s0, String s1) { AbendClass.log(this.objectName() + "." + s0 + "()", s1); }
    public void abend(String s0, String s1) { AbendClass.abend(this.objectName() + "." + s0 + "()", s1); }
}
