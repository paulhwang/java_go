/*
 ******************************************************************************
 *                                       
 *  Copyright (c) 2021 phwang. All rights reserved.
 *
 ******************************************************************************
 */

package com.phwang.core.android;

import com.phwang.core.utils.Binder;
import com.phwang.core.utils.ThreadEntityInt;
import com.phwang.core.utils.ThreadMgr;
import com.phwang.core.utils.Binder;

public class AndroidUBinder implements ThreadEntityInt {
    private String objectName() {return "AndroidUBinder";}
    private String receiveThreadName() { return "UAndroidReceiveThread"; }

	private static final int NUMBER_OF_D_WORK_THREADS = 5;
    
    private AndroidRoot androidRoot_;
    private Binder uBinder_;
    private Boolean stopReceiveThreadFlag = false;
        
    private AndroidRoot androidRoot() { return this.androidRoot_; }
    private ThreadMgr threadMgr() { return this.androidRoot().threadMgr();}
    private AndroidDParser androidDParser() { return this.androidRoot().androidDParser();}
    protected Binder uBinder() { return this.uBinder_; }
    
    protected AndroidUBinder(AndroidRoot root_val) {
        this.debug(false, "AndroidUBinder", "init start");
        
    	this.androidRoot_ = root_val;
        this.uBinder_ = new Binder(this.objectName());
        this.uBinder_.bindAsTcpClient(true, AndroidImport.FABRIC_ANDROID_SERVER_IP_ADDRESS, AndroidImport.FABRIC_ANDROID_PORT);
    }

    protected void startThreads() {
    	for (int i = 0; i < NUMBER_OF_D_WORK_THREADS; i++) {
    		this.threadMgr().createThreadObject(this.receiveThreadName(), this);
    	}
     }
    
	public void threadCallbackFunction() {
		this.uAndroidReceiveThreadFunc();
	}
    
    private void uAndroidReceiveThreadFunc() {
        this.debug(false, "uAndroidReceiveThreadFunc", "start " + this.receiveThreadName());
        
        while (true) {
            if (this.stopReceiveThreadFlag) {
                break;
            }

            String received_data = this.uBinder().receiveStringData();
            if (received_data == null) {
                this.abend("uAndroidReceiveThreadFunc", "null data");
            	continue;
            }

            this.debug(true, "uAndroidReceiveThreadFunc", "received_data=" + received_data);
            this.androidDParser().parserResponseData(received_data.substring(AndroidImport.FRONT_JOB_ID_SIZE));
        }
        this.debug(true, "uAndroidReceiveThreadFunc", "exit");
    }
    
    protected void StopReceiveThread() {
        this.stopReceiveThreadFlag = true;
    }
    
    private void debug(Boolean on_off, String s0, String s1) { if (on_off) this.log(s0, s1); }
    private void log(String s0, String s1) { this.androidRoot().logIt(this.objectName() + "." + s0 + "()", s1); }
    protected void abend(String s0, String s1) { this.androidRoot().abendIt(this.objectName() + "." + s0 + "()", s1); }
}
