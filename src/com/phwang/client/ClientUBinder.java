/*
 ******************************************************************************
 *                                       
 *  Copyright (c) 2021 phwang. All rights reserved.
 *
 ******************************************************************************
 */

package com.phwang.client;

import com.phwang.core.utils.Binder;
import com.phwang.core.utils.ThreadEntityInt;
import com.phwang.core.utils.ThreadMgr;
import com.phwang.core.utils.Binder;

public class ClientUBinder implements ThreadEntityInt {
    private String objectName() {return "ClientUBinder";}
    private String receiveThreadName() { return "UClientReceiveThread"; }

	private static final int NUMBER_OF_D_WORK_THREADS = 5;
    
    private ClientRoot clientRoot_;
    private Binder uBinder_;
    private Boolean stopReceiveThreadFlag = false;
        
    private ClientRoot clientRoot() { return this.clientRoot_; }
    private ThreadMgr threadMgr() { return this.clientRoot().threadMgr();}
    private ClientDParser clientDParser() { return this.clientRoot().clientDParser();}
    protected Binder uBinder() { return this.uBinder_; }
    
    protected ClientUBinder(ClientRoot root_val) {
        this.debug(false, "ClientUBinder", "init start");
        
    	this.clientRoot_ = root_val;
        this.uBinder_ = new Binder(this.objectName());
        this.uBinder_.bindAsTcpClient(true, ClientImport.FABRIC_ANDROID_SERVER_IP_ADDRESS, ClientImport.FABRIC_ANDROID_PORT);
    }

    protected void startThreads() {
    	for (int i = 0; i < NUMBER_OF_D_WORK_THREADS; i++) {
    		this.threadMgr().createThreadObject(this.receiveThreadName(), this);
    	}
     }
    
	public void threadCallbackFunction() {
		this.uClientReceiveThreadFunc();
	}
    
    private void uClientReceiveThreadFunc() {
        this.debug(false, "uClientReceiveThreadFunc", "start " + this.receiveThreadName());
        
        while (true) {
            if (this.stopReceiveThreadFlag) {
                break;
            }

            String received_data = this.uBinder().receiveStringData();
            if (received_data == null) {
                this.abend("uClientReceiveThreadFunc", "null data");
            	continue;
            }

            this.debug(true, "uClientReceiveThreadFunc", "received_data=" + received_data);
            this.clientDParser().parserResponseData(received_data);
        }
        this.debug(true, "uClientReceiveThreadFunc", "exit");
    }
    
    protected void StopReceiveThread() {
        this.stopReceiveThreadFlag = true;
    }
    
    private void debug(Boolean on_off, String s0, String s1) { if (on_off) this.log(s0, s1); }
    private void log(String s0, String s1) { this.clientRoot().logIt(this.objectName() + "." + s0 + "()", s1); }
    protected void abend(String s0, String s1) { this.clientRoot().abendIt(this.objectName() + "." + s0 + "()", s1); }
}
