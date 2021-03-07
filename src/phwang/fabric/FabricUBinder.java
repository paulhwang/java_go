/*
 ******************************************************************************
 *                                       
 *  Copyright (c) 2021 phwang. All rights reserved.
 *
 ******************************************************************************
 */

package phwang.fabric;

import phwang.utils.*;
import phwang.protocols.FabricThemeProtocolClass;

public class FabricUBinder implements ThreadEntityInt {
    private String objectName() {return "FabricUBinder";}
    private String receiveThreadName() { return "UFabricReceiveThread"; }
    
	private static final int NUMBER_OF_D_WORK_THREADS = 5;

    private FabricRoot fabricRoot_;
    public Binder uBinder_;
    
    public FabricRoot fabricRoot() { return this.fabricRoot_; }
    public FabricDParser fabricDParser() { return this.fabricRoot().fabricDParser(); }
    private ThreadMgr ThreadMgr() { return this.fabricRoot().threadMgr();}
    private Binder uBinder() { return this.uBinder_; }

    public FabricUBinder(FabricRoot root_val) {
        this.debug(false, "FabricUBinder", "init start");
        this.fabricRoot_ = root_val;
        this.uBinder_ = new Binder(this.objectName());
        this.uBinder().bindAsTcpServer(true, FabricThemeProtocolClass.FABRIC_THEME_PROTOCOL_TRANSPORT_PORT_NUMBER);
    }

    public void startThreads() {
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
            data = this.uBinder().receiveData();
            if (data == null) {
                this.abend("uBinderRreceiveThreadFunc", "null data");
                continue;
            }
            
            this.debug(false, "uBinderRreceiveThreadFunc", "data=" + data);
            this.fabricDParser().parseInputPacket(data);
        }
    }

    public void transmitData(String data_val) {
        this.debug(false, "transmitData", "data=" + data_val);
        this.uBinder().transmitData(data_val);
    }
    
    private void debug(Boolean on_off, String s0, String s1) { if (on_off) this.log(s0, s1); }
    private void log(String s0, String s1) { this.fabricRoot().logIt(this.objectName() + "." + s0 + "()", s1); }
    public void abend(String s0, String s1) { this.fabricRoot().abendIt(this.objectName() + "." + s0 + "()", s1); }
}
