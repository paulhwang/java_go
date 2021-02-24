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
    private String themeData = "go game";
    private JSONParser parserObject;
    private String linkIdString;
    
    public FrontEndRootClass FrontEndRootObject() { return this.frontTestObject.FrontEndRootObject(); }
    private ThreadMgrClass ThreadMgrObject() { return this.FrontEndRootObject().ThreadMgrObject();}
    private UFrontClass UFrontObject() { return this.FrontEndRootObject().UFrontObject();}

    public FrontTestCaseClass(FrontTestClass FrontTestClass, int index_val) {
        this.debugIt(false, "FrontTestClass", "init start");
        
        this.frontTestObject = FrontTestClass;
        this.indexString = EncodeNumberClass.EncodeNumber(index_val, 3);
        this.myNameString = "Test_" + this.indexString;
        this.parserObject = new JSONParser();
    }
    
    public void startTestTest() {
    	this.ThreadMgrObject().CreateThreadObject(this.frontTestCaseThreadName(), this);
     }

	public void ThreadCallbackFunction() {
		this.frontTestCaseThreadFunc();
	}
    
    private void frontTestCaseThreadFunc() {
        try {
        	Thread.sleep(100);
        }
        catch (Exception ignore) {}
    	
    	this.doSetupLink();
    	this.doSetupSession();
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
    	
    	String str_json_ajex_response = this.UFrontObject().ProcessAjaxRequestPacket(str_json_request);
        this.debugIt(true, "doTest", "ajex_response data=" + str_json_ajex_response);
    	
        try {
        	JSONObject json_ajex_response = (JSONObject) this.parserObject.parse(str_json_ajex_response);

            String name = (String) json_ajex_response.get("my_name");
            this.linkIdString = (String) json_ajex_response.get("link_id");
            if (!this.myNameString.equals(name)) {
            	this.abendIt("doTest", "name not match");
            }
        } catch (Exception e) {
        	this.abendIt("parseInputPacket", "***Exception***");
        }
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
    	
    	String str_json_ajex_response = this.UFrontObject().ProcessAjaxRequestPacket(str_json_request);
        this.debugIt(true, "doTest", "ajex_response data=" + str_json_ajex_response);
    }

    private void debugIt(Boolean on_off_val, String str0_val, String str1_val) { if (on_off_val) this.logitIt(str0_val, str1_val); }
    private void logitIt(String str0_val, String str1_val) { AbendClass.phwangLogit(this.objectName() + "." + str0_val + "()", str1_val); }
    public void abendIt(String str0_val, String str1_val) { AbendClass.phwangAbend(this.objectName() + "." + str0_val + "()", str1_val); }
}

