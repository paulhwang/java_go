/*
 ******************************************************************************
 *                                       
 *  Copyright (c) 2018 phwang. All rights reserved.
 *
 ******************************************************************************
 */

package phwang.front;

import java.util.concurrent.locks.*;
import phwang.utils.*;
import org.json.simple.JSONObject;
//import org.json.simple.parser.JSONParser;
import phwang.utils.*;

public class FrontTestClass implements ThreadInterface {
    private String objectName() {return "FrontTestClass";}
    private String frontTestThreadName() { return "FrontTestThread"; }

    private FrontRootClass frontEndRootObject;
    private int threadCount = 0;
    private Lock threadCountLock;
    
    public FrontRootClass FrontEndRootObject() { return this.frontEndRootObject; }
    private ThreadMgrClass ThreadMgrObject() { return this.FrontEndRootObject().ThreadMgrObject();}
    private UFrontClass UFrontObject() { return this.FrontEndRootObject().UFrontObject();}

    public FrontTestClass(FrontRootClass root_object_val) {
        this.debug(false, "FrontTestClass", "init start");
        
        this.frontEndRootObject = root_object_val;
        this.threadCountLock= new ReentrantLock();
    }
    
    public void setThreadCount(boolean add) {
    	this.threadCountLock.lock();
    	if (add) {
    		this.threadCount++;
    	}
    	else {
    		this.threadCount--;
    	}
    	this.threadCountLock.unlock();
    	this.debug(true, "setThreadCount", "*************************" + this.threadCount);
    }
    
    public void startTest() {
    	this.ThreadMgrObject().createThreadObject(this.frontTestThreadName(), this);
     }
    
	public void threadCallbackFunction() {
		this.setThreadCount(true);
		this.frontTestThreadFunc();
		this.setThreadCount(false);
	}
    
    private void frontTestThreadFunc() {
        this.debug(true, "frontTestThreadFunc", "*******start " + this.frontTestThreadName());
        UtilsClass.sleep(1000);  
        
        for (int j = 0; j < 99; j++) {
        	for (int i = 0; i < 10; i++) {
        		FrontTestCaseClass test_case = new FrontTestCaseClass(this, i);
        		test_case.startTestTest();
        		
        		//UtilsClass.sleep(1);
        	}
        }
    }
    
    private void debug(Boolean on_off, String s0, String s1) { if (on_off) this.log(s0, s1); }
    private void log(String s0, String s1) { AbendClass.log(this.objectName() + "." + s0 + "()", s1); }
    public void abend(String s0, String s1) { AbendClass.abend(this.objectName() + "." + s0 + "()", s1); }
}
