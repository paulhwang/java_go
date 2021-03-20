/*
 ******************************************************************************
 *                                       
 *  Copyright (c) 2021 phwang. All rights reserved.
 *
 ******************************************************************************
 */

package com.phwang.core.engine;

import com.phwang.core.utils.*;

public class EngineDBinder implements ThreadEntityInt {
    private String objectName() {return "EngineDBinder";}
    private String receiveThreadName() { return "DEngineReceiveThread"; }
    
	private static final int NUMBER_OF_U_WORK_THREADS = 5;
    
    private EngineRoot engineRoot_;
    private Binder dBinder_;

    public EngineRoot engineRoot() { return this.engineRoot_; }
    public EngineUParser engineUParser() { return this.engineRoot().engineUParser(); }
    private ThreadMgr threadMgr() { return this.engineRoot().threadMgr();}
    private Binder dBinder() { return this.dBinder_; }
    
    public EngineDBinder(EngineRoot root_val) {
        this.debug(false, "EngineDBinder", "init start");
        
        this.engineRoot_ = root_val;
        this.dBinder_ = new Binder(this.objectName());

        this.dBinder().bindAsTcpClient(true, EngineImport.THEME_ENGINE_IP_ADDRESS, EngineImport.THEME_ENGINE_PORT);
        this.debug(false, "DEngineClass", "init done");
    }

    public void startThreads() {
    	for (int i = 0; i < NUMBER_OF_U_WORK_THREADS; i++) {
    		this.threadMgr().createThreadObject(this.receiveThreadName(), this);
    	}
     }
    
	public void threadCallbackFunction() {
		this.dEngineReceiveThreadFunc();
	}
    
    public void dEngineReceiveThreadFunc() {
        this.debug(false, "dEngineReceiveThreadFunc", "start " + this.receiveThreadName());

        String data;
        while (true) {
            data = this.dBinder().receiveStringData();
            if (data == null) {
                this.abend("dEngineReceiveThreadFunc", "null data");
                continue;
            }
            
            this.debug(false, "dEngineReceiveThreadFunc", "data = " + data);
            this.engineUParser().ParseInputPacket(data);
        }
    }

    public void TransmitData(String data_val) {
        this.dBinder().transmitStringData(data_val);
    }
    
    private void debug(Boolean on_off, String s0, String s1) { if (on_off) this.log(s0, s1); }
    private void log(String s0, String s1) { this.engineRoot().logIt(this.objectName() + "." + s0 + "()", s1); }
    public void abend(String s0, String s1) { this.engineRoot().abendIt(this.objectName() + "." + s0 + "()", s1); }
}
