/*
 ******************************************************************************
 *                                       
 *  Copyright (c) 2018 phwang. All rights reserved.
 *
 ******************************************************************************
 */

package Phwang.FrontEnd;

import org.json.simple.JSONObject;
//import org.json.simple.parser.JSONParser;
import Phwang.Utils.AbendClass;
import Phwang.Utils.Encode.EncodeNumberClass;
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
    
    public void startTest() {
    	this.ThreadMgrObject().createThreadObject(this.frontTestThreadName(), this);
     }
    
	public void ThreadCallbackFunction() {
		this.frontTestThreadFunc();
	}
    
    private void frontTestThreadFunc() {
        this.debugIt(true, "frontTestThreadFunc", "*******start " + this.frontTestThreadName());
        try {
        	Thread.sleep(1000);
        }
        catch (Exception ignore) {}
    	
        for (int j = 0; j <1; j++) {
        	for (int i = 0; i < 1; i++) {
        		FrontTestCaseClass test_case = new FrontTestCaseClass(this, i);
        		test_case.startTestTest();
        	}
        	
        	try {
        		Thread.sleep(1000);
        	}
        	catch (Exception ignore) {}
        }
    }

    private void debugIt(Boolean on_off_val, String str0_val, String str1_val) { if (on_off_val) this.logitIt(str0_val, str1_val); }
    private void logitIt(String str0_val, String str1_val) { AbendClass.phwangLogit(this.objectName() + "." + str0_val + "()", str1_val); }
    public void abendIt(String str0_val, String str1_val) { AbendClass.phwangAbend(this.objectName() + "." + str0_val + "()", str1_val); }
}
