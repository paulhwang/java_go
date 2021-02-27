/*
 ******************************************************************************
 *                                       
 *  Copyright (c) 2018 phwang. All rights reserved.
 *
 ******************************************************************************
 */

package phwang.test;

//import java.util.concurrent.locks.*;
//import org.json.simple.JSONObject;
//import org.json.simple.parser.JSONParser;
import phwang.utils.*;
import phwang.front.FrontRootClass;
import phwang.front.FrontExportClass;

public class FrontTestClass implements ThreadInterface {
    private String objectName() {return "FrontTestClass";}
    private String frontTestThreadName() { return "FrontTestThread"; }

    private FrontRootClass frontEndRootObject_;
    private ThreadMgrClass threadMgrObject_;
    private LockIntegerClass threadCount;
    
    public FrontRootClass frontEndRootObject() { return this.frontEndRootObject_; }
    public FrontExportClass frontExportObject() { return this.frontEndRootObject().frontExportObject();}
    public ThreadMgrClass threadMgrObject() { return this.threadMgrObject_; }

    public FrontTestClass(FrontRootClass root_object_val) {
        this.debug(false, "FrontTestClass", "init start");
        
        this.frontEndRootObject_ = root_object_val;
        this.threadMgrObject_ = new ThreadMgrClass();
        this.threadCount = new LockIntegerClass(0);
    }
    
    public void incrementThreadCount() {
  		this.threadCount.increment();
    	this.debug(false, "incrementThreadCount", "*************************" + this.threadCount.get());
    }
    
    public void decrementThreadCount() {
  		this.threadCount.decrement();
    	this.debug(true, "decrementThreadCount", "*************************" + this.threadCount.get());
    	if (this.threadCount.get() < 0) {
    		this.abend("decrementThreadCount", "smaller than 0");
    	}
    }
    
    public void startTest() {
    	this.threadMgrObject().createThreadObject(this.frontTestThreadName(), this);
     }
    
	public void threadCallbackFunction() {
		this.incrementThreadCount();
		this.frontTestThreadFunc();
		this.decrementThreadCount();
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
