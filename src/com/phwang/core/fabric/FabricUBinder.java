/*
 ******************************************************************************
 *                                       
 *  Copyright (c) 2021 phwang. All rights reserved.
 *
 ******************************************************************************
 */

package com.phwang.core.fabric;

import com.phwang.core.utils.Binder;
import com.phwang.core.utils.ThreadMgr;
import com.phwang.core.utils.ThreadEntityInt;

public class FabricUBinder implements ThreadEntityInt {
    private String objectName() {return "FabricUBinder";}
    private String receiveThreadName() { return "UFabricReceiveThread"; }
    
	private static final int NUMBER_OF_D_WORK_THREADS = 5;

    private FabricRoot fabricRoot_;
    private Binder uBinder_;
    
    protected FabricRoot fabricRoot() { return this.fabricRoot_; }
    protected FabricDParser fabricDParser() { return this.fabricRoot().fabricDParser(); }
    private ThreadMgr ThreadMgr() { return this.fabricRoot().threadMgr();}
    private Binder uBinder() { return this.uBinder_; }

    protected FabricUBinder(FabricRoot root_val) {
        this.debug(false, "FabricUBinder", "init start");
        this.fabricRoot_ = root_val;
        this.uBinder_ = new Binder(this.objectName());
        this.uBinder().bindAsTcpServer(true, FabricExport.FABRIC_THEME_PORT, true);
    }

    protected void startThreads() {
    	for (int i = 0; i < NUMBER_OF_D_WORK_THREADS; i++) {
    		this.ThreadMgr().createThreadObject(this.receiveThreadName(), this);
    	}
     }
    
	public void threadCallbackFunction() {
		this.uBinderRreceiveThreadFunc();
	}

    private void uBinderRreceiveThreadFunc() {
        this.debug(false, "uBinderRreceiveThreadFunc", "start " + this.receiveThreadName());

        String data;
        while (true) {
            data = this.uBinder().receiveStringData();
            if (data == null) {
                this.abend("uBinderRreceiveThreadFunc", "null data");
                continue;
            }
            
            this.debug(false, "uBinderRreceiveThreadFunc", "data=" + data);
            this.fabricDParser().parseInputPacket(data);
        }
    }

    protected void transmitData(String data_val) {
        this.debug(false, "transmitData", "data=" + data_val);
        this.uBinder().transmitStringData(data_val);
    }
    
    private void debug(Boolean on_off, String s0, String s1) { if (on_off) this.log(s0, s1); }
    private void log(String s0, String s1) { this.fabricRoot().logIt(this.objectName() + "." + s0 + "()", s1); }
    protected void abend(String s0, String s1) { this.fabricRoot().abendIt(this.objectName() + "." + s0 + "()", s1); }
}
