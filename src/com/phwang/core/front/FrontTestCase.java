/*  Copyright (c) 2021 phwang. All rights reserved.
 *
 ******************************************************************************
 */

package com.phwang.core.front;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import com.phwang.core.utils.Abend;
import com.phwang.core.utils.ThreadMgr;
import com.phwang.core.utils.ThreadEntityInt;
import com.phwang.core.utils.EncodeNumber;
import com.phwang.core.front.FrontDExportInt;

class FrontTestCase implements ThreadEntityInt {
    private String objectName() {return "FrontTestCase";}
    private String httpTestThreadName() { return "HttpTestThread"; }
    
    private FrontTest httpTest_;
    private String indexString_;
    
    private String myNameString_;
    private String password_ = "Tennis";
    private String themeData = "88889999G009090000";///////////////////
    private String themeData2 = "G009090000";
    private String acceptString = "Yes";
    private String xmtSeqString = "1111";
    private String goMoveString = "GM01021001";
    private String nameListTagString = "0000";///////////////////////////////////////////////////////different from brouser which is int
    private String linkIdString;
    private String sessionIdString;
    private String themeIdString = "33333333";///////////////////////////////////////////////
    
    private FrontTest httpTest() { return this.httpTest_; }
    private ThreadMgr threadMgr() { return this.httpTest().threadMgr();}
    private FrontDExportInt frontExportInt() { return this.httpTest().frontExportInt();}

    protected FrontTestCase(FrontTest http_test_val, int index_val) {
        this.debug(false, "FrontTestCase", "init start");
        
        this.httpTest_ = http_test_val;
        this.indexString_ = EncodeNumber.encode(index_val, 6);
        this.myNameString_ = "Http_" + this.indexString_;
    }
    
    protected void startTestTest() {
    	this.threadMgr().createThreadObject(this.httpTestThreadName(), this);
     }

	public void threadCallbackFunction() {
		this.httpTest().incrementThreadCount();
		this.httpTestCaseThreadFunc();
		this.httpTest().decrementThreadCount();
	}
    
    private void httpTestCaseThreadFunc() {
        try {
        	Thread.sleep(100);
        }
        catch (Exception ignore) {}
    	
    	this.doSetupLink();
    	
    	//UtilsClass.sleep(100);
    	//this.doGetLinkData();
    	
    	//UtilsClass.sleep(100);
    	//this.doGetNameList();
    	
    	//UtilsClass.sleep(100);
    	this.doSetupSession();
    	
    	//Utils.sleep(100);
    	this.doSetupSession3();
    	
    	//Utils.sleep(100);
    	this.doPutSessionData();
    	
    	//Utils.sleep(100);
    	this.doGetSessionData();
    	
    	//Utils.sleep(100);
    	//this.doSetupSession2();
    }
    
    private void doSetupLink() {
    	JSONObject json_data = new JSONObject();
    	json_data.put("my_name", this.myNameString_);
    	json_data.put("password", this.password_);
    	String str_json_data = json_data.toJSONString();
    	
    	JSONObject json_request = new JSONObject();
    	json_request.put("command", "setup_link");
    	json_request.put("data", str_json_data);
    	String str_json_request = json_request.toJSONString();
    	
    	String str_json_ajex_response = this.frontExportInt().processHttpRequestPacket(str_json_request);
        this.debug(false, "doSetupLink", "str_json_ajex_response=" + str_json_ajex_response);
    	
        try {
            JSONParser parser = new JSONParser();
        	JSONObject json_ajex_response = (JSONObject) parser.parse(str_json_ajex_response); 
        	
        	String command = (String) json_ajex_response.get("command");
        	String str_json_response_data = (String) json_ajex_response.get("data");
        	JSONObject json_response_data = (JSONObject) parser.parse(str_json_response_data); 
        	

            String name = (String) json_response_data.get("my_name");
            this.linkIdString = (String) json_response_data.get("link_id");
            if (!this.myNameString_.equals(name)) {
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
    	
    	String str_json_ajex_response = this.frontExportInt().processHttpRequestPacket(str_json_request);
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
    	
    	String str_json_ajex_response = this.frontExportInt().processHttpRequestPacket(str_json_request);
        this.debug(false, "doGetNameList", "ajex_response data=" + str_json_ajex_response);
    }
    
    private void doSetupSession() {
    	JSONObject json_data = new JSONObject();
    	json_data.put("link_id", this.linkIdString);
    	json_data.put("his_name", this.myNameString_);
    	json_data.put("theme_data", this.themeData);
    	String str_json_data = json_data.toJSONString();
    	
    	JSONObject json_request = new JSONObject();
    	json_request.put("command", "setup_session");
    	json_request.put("data", str_json_data);
    	String str_json_request = json_request.toJSONString();
    	
    	String str_json_ajex_response = this.frontExportInt().processHttpRequestPacket(str_json_request);
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
    	
    	String str_json_ajex_response = this.frontExportInt().processHttpRequestPacket(str_json_request);
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
    	
    	String str_json_ajex_response = this.frontExportInt().processHttpRequestPacket(str_json_request);
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
    	
    	String str_json_ajex_response = this.frontExportInt().processHttpRequestPacket(str_json_request);
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
    	
    	String str_json_ajex_response = this.frontExportInt().processHttpRequestPacket(str_json_request);
        this.debug(false, "doGetSessionData", "ajex_response data=" + str_json_ajex_response);
    }
    
    private void debug(Boolean on_off, String s0, String s1) { if (on_off) this.log(s0, s1); }
    private void log(String s0, String s1) { Abend.log(this.objectName() + "." + s0 + "()", s1); }
    protected void abend(String s0, String s1) { Abend.abend(this.objectName() + "." + s0 + "()", s1); }
}

