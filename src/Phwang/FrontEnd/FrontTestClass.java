/*
 ******************************************************************************
 *                                       
 *  Copyright (c) 2018 phwang. All rights reserved.
 *
 ******************************************************************************
 */

package Phwang.FrontEnd;

import Phwang.Utils.AbendClass;
import Phwang.Utils.UtilsClass;
import Phwang.Utils.ThreadMgr.ThreadInterface;
import Phwang.Utils.ThreadMgr.ThreadMgrClass;

public class FrontTestClass implements ThreadInterface {
    private String objectName() {return "FrontTestClass";}
    private String FrontTestThreadName() { return "FrontTestThread"; }

    private FrontEndRootClass frontEndRootObject;
    
    public FrontEndRootClass FrontEndRootObject() { return this.frontEndRootObject; }
    private ThreadMgrClass ThreadMgrObject() { return this.FrontEndRootObject().ThreadMgrObject();}
    private UFrontClass UFrontObject() { return this.FrontEndRootObject().UFrontObject();}

    public FrontTestClass(FrontEndRootClass root_object_val) {
        this.debugIt(false, "FrontTestClass", "init start");
        
        this.frontEndRootObject = root_object_val;
    }
    
    public void StartTest() {
    	this.ThreadMgrObject().CreateThreadObject(this.FrontTestThreadName(), this);
     }
    
	public void ThreadCallbackFunction() {
		this.UFrontReceiveThreadFunc();
	}
    
    private void UFrontReceiveThreadFunc() {
        this.debugIt(false, "UFrontReceiveThreadFunc", "start " + this.FrontTestThreadName());
    	UtilsClass.sleep(2000);
    	
    	this.DoTest();
    }
    
    private void DoTest() {
    	//String ajex_response_str = this.UFrontObject().ProcessAjaxRequestPacket("aaa");
    }

    private void debugIt(Boolean on_off_val, String str0_val, String str1_val) { if (on_off_val) this.logitIt(str0_val, str1_val); }
    private void logitIt(String str0_val, String str1_val) { AbendClass.phwangLogit(this.objectName() + "." + str0_val + "()", str1_val); }
    public void abendIt(String str0_val, String str1_val) { AbendClass.phwangAbend(this.objectName() + "." + str0_val + "()", str1_val); }
}
