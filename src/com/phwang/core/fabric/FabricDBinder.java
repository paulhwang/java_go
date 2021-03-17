/*
 ******************************************************************************
 *                                       
 *  Copyright (c) 2021 phwang. All rights reserved.
 *
 ******************************************************************************
 */

package com.phwang.core.fabric;

import com.phwang.core.utils.Binder;
import com.phwang.core.utils.BinderBundle;
import com.phwang.core.utils.ThreadEntityInt;
import com.phwang.core.utils.ThreadMgr;

public class FabricDBinder implements ThreadEntityInt {
    private String objectName() {return "FabricDBinder";}
    
	private static final int NUMBER_OF_U_WORK_THREADS = 5;
    private String receiveThreadName() { return "DFabricReceiveThread"; }
    
    private FabricRoot fabricRoot_;
    private Binder dBinder_;

    protected FabricRoot fabricRoot() { return this.fabricRoot_; }
    private ThreadMgr ThreadMgr() { return this.fabricRoot().threadMgr();}
    private FabricUParser fabricUParser() { return this.fabricRoot().fabricUParser(); }
    private Binder dBinder() { return this.dBinder_; }
  
    protected FabricDBinder(FabricRoot root_val) {
        this.debug(false, "FabricDBinder", "init start");
        
        this.fabricRoot_ = root_val;
        this.dBinder_ = new Binder(this.objectName());
        
        this.dBinder().bindAsTcpServer(true, FabricExport.FABRIC_FRONT_PORT, false);
    }

    protected void startThreads() {
    	for (int i = 0; i < NUMBER_OF_U_WORK_THREADS; i++) {
    		this.ThreadMgr().createThreadObject(this.receiveThreadName() + Integer.toString(i), this);
    	}
    }
    
    public void threadCallbackFunction() {
		this.dBinderRreceiveThreadFunc();
	}

    private void dBinderRreceiveThreadFunc() {
        this.debug(false, "dBinderRreceiveThreadFunc", "start " + this.receiveThreadName());

        BinderBundle bundle;
        while (true) {
            bundle = this.dBinder().receiveBundleData();
            if (bundle == null) {
                this.abend("dBinderRreceiveThreadFunc", "null bundle");
                continue;
            }
            this.debug(false, "dBinderRreceiveThreadFunc", "data=" + bundle.data());
            this.fabricUParser().parseInputPacket(bundle);
        }
    }

    protected void transmitBundleData(BinderBundle bundle_val) {
        this.dBinder().transmitBundleData(bundle_val);
    }
    
    private void debug(Boolean on_off, String s0, String s1) { if (on_off) this.log(s0, s1); }
    private void log(String s0, String s1) { this.fabricRoot().logIt(this.objectName() + "." + s0 + "()", s1); }
    protected void abend(String s0, String s1) { this.fabricRoot().abendIt(this.objectName() + "." + s0 + "()", s1); }
}
