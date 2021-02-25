/*
 ******************************************************************************
 *                                       
 *  Copyright (c) 2018 phwang. All rights reserved.
 *
 ******************************************************************************
 */

package Phwang.FrontEnd;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import Phwang.Utils.AbendClass;
import Phwang.Utils.UtilsClass;
import Phwang.Utils.Encode.EncodeNumberClass;
import Phwang.Utils.ThreadMgr.ThreadInterface;
import Phwang.Utils.ThreadMgr.ThreadMgrClass;

/*
 ******************************************************************************
 *                                       
 *  Copyright (c) 2018 phwang. All rights reserved.
 *
 ******************************************************************************
 */

class FrontTestCaseClass implements ThreadInterface {
    private String objectName() {return "FrontTestClass";}
    private String frontTestCaseThreadName() { return "FrontTestCaseThread"; }
    
    private FrontTestClass frontTestObject;
    private String indexString;
    private String myNameString;
    private String password = "Tennis";
    private String themeData = "9999G009090000";
    private String themeData2 = "G009090000";
    private String acceptString = "Yes";
    private String xmtSeqString = "1111";
    private String goMoveString = "GM01021001";
    private String nameListTagString = "000";///////////////////////////////////////////////////////different from brouser which is int
    private JSONParser parserObject;
    private String linkIdString;
    private String sessionIdString;
    private String themeIdString = "3333";
    
    public FrontRootClass FrontEndRootObject() { return this.frontTestObject.FrontEndRootObject(); }
    private ThreadMgrClass ThreadMgrObject() { return this.FrontEndRootObject().ThreadMgrObject();}
    private UFrontClass UFrontObject() { return this.FrontEndRootObject().UFrontObject();}

    public FrontTestCaseClass(FrontTestClass FrontTestClass, int index_val) {
        this.debugIt(false, "FrontTestClass", "init start");
        
        this.frontTestObject = FrontTestClass;
        this.indexString = EncodeNumberClass.encodeNumber(index_val, 3);
        this.myNameString = "Test_" + this.indexString;
        this.parserObject = new JSONParser();
    }
    
    public void startTestTest() {
    	this.ThreadMgrObject().createThreadObject(this.frontTestCaseThreadName(), this);
     }

	public void threadCallbackFunction() {
		this.frontTestCaseThreadFunc();
	}
    
    private void frontTestCaseThreadFunc() {
        try {
        	Thread.sleep(100);
        }
        catch (Exception ignore) {}
    	
    	this.doSetupLink();
    	
    	UtilsClass.sleep(100);
    	this.doGetLinkData();
    	
    	UtilsClass.sleep(100);
    	this.doGetNameList();
    	
    	this.doSetupSession();
    	
    	UtilsClass.sleep(1000);
    	this.doSetupSession3();
    	
    	this.doPutSessionData();
    	
    	UtilsClass.sleep(1000);
    	this.doGetSessionData();
    	
    	UtilsClass.sleep(1000);
    	this.doSetupSession2();
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
    	
    	String str_json_ajex_response = this.UFrontObject().processAjaxRequestPacket(str_json_request);
        this.debugIt(true, "doSetupLink", "ajex_response data=" + str_json_ajex_response);
    	
        try {
        	JSONObject json_ajex_response = (JSONObject) this.parserObject.parse(str_json_ajex_response);

            String name = (String) json_ajex_response.get("my_name");
            this.linkIdString = (String) json_ajex_response.get("link_id");
            if (!this.myNameString.equals(name)) {
            	this.abendIt("doSetupLink", "name not match");
            }
        } catch (Exception e) {
        	this.abendIt("doSetupLink", "***Exception***");
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
    	
    	String str_json_ajex_response = this.UFrontObject().processAjaxRequestPacket(str_json_request);
        this.debugIt(true, "doGetLinkData", "ajex_response data=" + str_json_ajex_response);
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
    	
    	String str_json_ajex_response = this.UFrontObject().processAjaxRequestPacket(str_json_request);
        this.debugIt(true, "doGetNameList", "ajex_response data=" + str_json_ajex_response);
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
    	
    	String str_json_ajex_response = this.UFrontObject().processAjaxRequestPacket(str_json_request);
        this.debugIt(true, "doSetupSession", "ajex_response data=" + str_json_ajex_response);
    	
        try {
        	JSONObject json_ajex_response = (JSONObject) this.parserObject.parse(str_json_ajex_response);

            String link_id = (String) json_ajex_response.get("link_id");
            this.sessionIdString = (String) json_ajex_response.get("session_id");
            if (!this.linkIdString.equals(link_id)) {
            	this.abendIt("doSetupSession", "link_id not match");
            }
        } catch (Exception e) {
        	this.abendIt("doSetupSession", "***Exception***");
        }
    }
    
    private void doSetupSession2() {
    	JSONObject json_data = new JSONObject();
    	json_data.put("link_id", this.linkIdString);
    	json_data.put("session_id", this.sessionIdString);
    	json_data.put("theme_id_str", this.themeIdString);
    	json_data.put("accept", this.acceptString);
    	json_data.put("theme_data", this.themeData2);
    	String str_json_data = json_data.toJSONString();
    	
    	JSONObject json_request = new JSONObject();
    	json_request.put("command", "setup_session2");
    	json_request.put("data", str_json_data);
    	String str_json_request = json_request.toJSONString();
    	
    	String str_json_ajex_response = this.UFrontObject().processAjaxRequestPacket(str_json_request);
        this.debugIt(true, "doSetupSession2", "ajex_response data=" + str_json_ajex_response);
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
    	
    	String str_json_ajex_response = this.UFrontObject().processAjaxRequestPacket(str_json_request);
        this.debugIt(true, "doSetupSession3", "ajex_response data=" + str_json_ajex_response);
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
    	
    	String str_json_ajex_response = this.UFrontObject().processAjaxRequestPacket(str_json_request);
        this.debugIt(true, "doPutSessionData", "ajex_response data=" + str_json_ajex_response);
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
    	
    	String str_json_ajex_response = this.UFrontObject().processAjaxRequestPacket(str_json_request);
        this.debugIt(true, "doGetSessionData", "ajex_response data=" + str_json_ajex_response);
    }
    
    private void debugIt(Boolean on_off_val, String str0_val, String str1_val) { if (on_off_val) this.logitIt(str0_val, str1_val); }
    private void logitIt(String str0_val, String str1_val) { AbendClass.phwangLogit(this.objectName() + "." + str0_val + "()", str1_val); }
    public void abendIt(String str0_val, String str1_val) { AbendClass.phwangAbend(this.objectName() + "." + str0_val + "()", str1_val); }
}

