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
import phwang.front.FrontDExportInterface;

public class FrontTestClass implements ThreadInterface {
    private String objectName() {return "FrontTestClass";}
    private String frontTestThreadName() { return "FrontTestThread"; }

    private FrontDExportInterface frontExportIntervace_;
    private ThreadMgrClass threadMgrObject_;
    private LockIntegerClass threadCount;
    
    public FrontDExportInterface frontExportInterface() { return this.frontExportIntervace_;}
    public ThreadMgrClass threadMgrObject() { return this.threadMgrObject_; }

    public FrontTestClass(FrontDExportInterface front_export_interface_val) {
        this.debug(false, "FrontTestClass", "init start");
        
        this.frontExportIntervace_ = front_export_interface_val;
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
        UtilsClass.sleep(100);  
        
        for (int j = 0; j < 1; j++) {
        	for (int i = 0; i < 2; i++) {
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
