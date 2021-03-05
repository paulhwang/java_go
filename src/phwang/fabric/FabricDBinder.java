/*
 ******************************************************************************
 *                                       
 *  Copyright (c) 2021 phwang. All rights reserved.
 *
 ******************************************************************************
 */

package phwang.fabric;

import phwang.utils.*;

public class FabricDBinder implements ThreadInterface {
    private String objectName() {return "FabricDBinder";}
    
	private static final int NUMBER_OF_U_WORK_THREADS = 5;
    private String receiveThreadName() { return "DFabricReceiveThread"; }
    
    private FabricRoot fabricRoot_;
    private BinderClass dBinder_;

    public FabricRoot fabricRoot() { return this.fabricRoot_; }
    private ThreadMgrClass ThreadMgr() { return this.fabricRoot().threadMgr();}
    private FabricUParser fabricUParser() { return this.fabricRoot().fabricUParser(); }
    private BinderClass dBinderObject() { return this.dBinder_; }
  
    public FabricDBinder(FabricRoot root_val) {
        this.debug(false, "FabricDBinder", "init start");
        
        this.fabricRoot_ = root_val;
        this.dBinder_ = new BinderClass(this.objectName());
        
        this.dBinderObject().bindAsTcpServer(true, FabricExport.FABRIC_FRONT_PORT);
    }

    public void startThreads() {
    	for (int i = 0; i < NUMBER_OF_U_WORK_THREADS; i++) {
    		this.ThreadMgr().createThreadObject(this.receiveThreadName() + Integer.toString(i), this);
    	}
    }
    
	public void threadCallbackFunction() {
		this.dFabricRreceiveThreadFunc();
	}

    public void dFabricRreceiveThreadFunc() {
        this.debug(false, "dFabricRreceiveThreadFunc", "start " + this.receiveThreadName());

        String data;
        while (true) {
            data = this.dBinderObject().receiveData();
            if (data == null) {
                this.abend("dFabricRreceiveThreadFunc", "null data");
                continue;
            }
            this.debug(false, "dFabricRreceiveThreadFunc", "data=" + data);
            this.fabricUParser().parseInputPacket(data);
        }
    }

    public void transmitData(String data_val) {
        this.dBinderObject().transmitData(data_val);
    }
    
    private void debug(Boolean on_off, String s0, String s1) { if (on_off) this.log(s0, s1); }
    private void log(String s0, String s1) { AbendClass.log(this.objectName() + "." + s0 + "()", s1); }
    public void abend(String s0, String s1) { AbendClass.abend(this.objectName() + "." + s0 + "()", s1); }
}
