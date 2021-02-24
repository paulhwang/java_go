/*
 ******************************************************************************
 *                                       
 *  Copyright (c) 2018 phwang. All rights reserved.
 *
 ******************************************************************************
 */

package Phwang.FrontEnd;

//import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
//import org.json.simple.parser.JSONParser;
import Phwang.Utils.AbendClass;
import Phwang.Utils.UtilsClass;
import Phwang.Utils.ThreadMgr.ThreadInterface;
import Phwang.Utils.ThreadMgr.ThreadMgrClass;

public class FrontTestClass implements ThreadInterface {
    private String objectName() {return "FrontTestClass";}
    private String frontTestThreadName() { return "FrontTestThread"; }

    private FrontEndRootClass frontEndRootObject;
    
    public FrontEndRootClass FrontEndRootObject() { return this.frontEndRootObject; }
    private ThreadMgrClass ThreadMgrObject() { return this.FrontEndRootObject().ThreadMgrObject();}
    private UFrontClass UFrontObject() { return this.FrontEndRootObject().UFrontObject();}

    public FrontTestClass(FrontEndRootClass root_object_val) {
        this.debugIt(false, "FrontTestClass", "init start");
        
        this.frontEndRootObject = root_object_val;
    }
    
    public void StartTest() {
    	this.ThreadMgrObject().CreateThreadObject(this.frontTestThreadName(), this);
     }
    
	public void ThreadCallbackFunction() {
		this.frontTestThreadFunc();
	}
    
    private void frontTestThreadFunc() {
        this.debugIt(false, "frontTestThreadFunc", "*******start " + this.frontTestThreadName());
        try {
        	Thread.sleep(1000);
        }
        catch (Exception ignore) {}
        
        this.debugIt(true, "frontTestThreadFunc", "++++++++start " + this.frontTestThreadName());
    	
    	this.DoTest();
    }
    
    private void DoTest() {
    	JSONObject json_data = new JSONObject();
    	json_data.put("my_name", "phwang");
    	json_data.put("password", "Oaktree");
    	String json_str_data = json_data.toJSONString();
    	
    	JSONObject json = new JSONObject();
    	json.put("command", "setup_link");
    	json.put("data", json_str_data);
    	String json_str = json.toJSONString();
    	
    	String ajex_response_str = this.UFrontObject().ProcessAjaxRequestPacket(json_str);
    }

    private void debugIt(Boolean on_off_val, String str0_val, String str1_val) { if (on_off_val) this.logitIt(str0_val, str1_val); }
    private void logitIt(String str0_val, String str1_val) { AbendClass.phwangLogit(this.objectName() + "." + str0_val + "()", str1_val); }
    public void abendIt(String str0_val, String str1_val) { AbendClass.phwangAbend(this.objectName() + "." + str0_val + "()", str1_val); }
}
