/*
 ******************************************************************************
 *                                       
 *  Copyright (c) 2021 phwang. All rights reserved.
 *
 ******************************************************************************
 */

package phwang.engine;

import phwang.utils.*;
import phwang.protocols.*;

public class EngineDBinder implements ThreadInterface {
    private String objectName() {return "EngineDBinder";}
    private String receiveThreadName() { return "DEngineReceiveThread"; }
    
	private static final int NUMBER_OF_U_WORK_THREADS = 5;
    
    private EngineRoot engineRoot_;
    private BinderClass dBinder_;

    public EngineRoot engineRoot() { return this.engineRoot_; }
    public EngineUParser engineUParser() { return this.engineRoot().engineUParser(); }
    private ThreadMgrClass threadMgr() { return this.engineRoot().threadMgr();}
    private BinderClass dBinder() { return this.dBinder_; }
    
    public EngineDBinder(EngineRoot root_val) {
        this.debug(false, "EngineDBinder", "init start");
        
        this.engineRoot_ = root_val;
        this.dBinder_ = new BinderClass(this.objectName());

        this.dBinder().bindAsTcpClient(true, ThemeEngineProtocolClass.THEME_ENGINE_PROTOCOL_PROTOCOL_SERVER_IP_ADDRESS, ThemeEngineProtocolClass.THEME_ENGINE_PROTOCOL_TRANSPORT_PORT_NUMBER);
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
            data = this.dBinder().receiveData();
            if (data == null) {
                this.abend("dEngineReceiveThreadFunc", "null data");
                continue;
            }
            
            this.debug(false, "dEngineReceiveThreadFunc", "data = " + data);
            this.engineUParser().ParseInputPacket(data);
        }
    }

    public void TransmitData(String data_val) {
        this.dBinder().transmitData(data_val);
    }
    
    private void debug(Boolean on_off, String s0, String s1) { if (on_off) this.log(s0, s1); }
    private void log(String s0, String s1) { AbendClass.log(this.objectName() + "." + s0 + "()", s1); }
    public void abend(String s0, String s1) { AbendClass.abend(this.objectName() + "." + s0 + "()", s1); }
}
