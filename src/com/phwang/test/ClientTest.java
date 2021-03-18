/*
 ******************************************************************************
 *                                       
 *  Copyright (c) 2021 phwang. All rights reserved.
 *
 ******************************************************************************
 */


package com.phwang.test;

import com.phwang.core.utils.Abend;
import com.phwang.core.utils.ThreadEntityInt;
import com.phwang.core.utils.ThreadMgr;
import com.phwang.core.utils.LockedInteger;
import com.phwang.core.utils.Utils;
import com.phwang.client.ClientRoot;

public class ClientTest implements ThreadEntityInt {
    private String objectName() {return "ClientTest";}
    private String clientTesterThreadName() { return "ClientTesterThread"; }

    private int numberOfTesterThread_ = 2;
    private int numberOfCasePerTester = 2;
    private LockedInteger testerIndex_;
    private ThreadMgr threadMgr_;
    private LockedInteger threadCount_;
    
    protected ThreadMgr threadMgr() { return this.threadMgr_; }

    public ClientTest() {
        this.debug(false, "ClientTest", "init start");
        
        this.threadMgr_ = new ThreadMgr();
        this.threadCount_ = new LockedInteger(0);
        this.testerIndex_ = new LockedInteger(0);
    }
    
    public void startTest(Boolean test_on_val) {
    	if (!test_on_val) {
    		return;
    	}
    	
    	for (int i = 0; i < this.numberOfTesterThread_; i++) {
            Utils.sleep(10);
    		this.threadMgr().createThreadObject(this.clientTesterThreadName(), this);
    	}
    }
    
	public void threadCallbackFunction() {
		this.incrementThreadCount();
		this.clientTesterThreadFunc();
		this.decrementThreadCount();
	}
    
    private void clientTesterThreadFunc() {
        this.debug(true, "client_root_val", "*******start " + this.clientTesterThreadName());
        
    	this.testerIndex_.increment();
    	int tester_index = this.testerIndex_.get();
    	
    	ClientRoot client_root = new ClientRoot();
        ClientTester tester = new ClientTester(this, client_root, tester_index);

    	for (int i = 0; i < this.numberOfCasePerTester; i++) {
       		tester.startTest();
       		//UtilsClass.sleep(1);
        }
    }
    
    protected void incrementThreadCount() {
  		this.threadCount_.increment();
    	this.debug(false, "incrementThreadCount", "*************************" + this.threadCount_.get());
    }
    
    protected void decrementThreadCount() {
  		this.threadCount_.decrement();
  		
  		if (this.threadCount_.get() < 2) {
  			this.debug(true, "decrementThreadCount", "*************************" + this.threadCount_.get());
  		}
  		
    	if (this.threadCount_.get() < 0) {
    		this.abend("decrementThreadCount", "smaller than 0");
    	}
    }
    
    private void debug(Boolean on_off, String s0, String s1) { if (on_off) this.log(s0, s1); }
    private void log(String s0, String s1) { Abend.log(this.objectName() + "." + s0 + "()", s1); }
    protected void abend(String s0, String s1) { Abend.abend(this.objectName() + "." + s0 + "()", s1); }
}
