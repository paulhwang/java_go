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
    
    private EngineRoot engineRootObject;
    private DEngineParserClass dEngineParserObject;
    private BinderClass dBinderObject_;

    public EngineRoot EngineRootObject() { return this.engineRootObject; }
    private ThreadMgrClass ThreadMgrObject() { return this.EngineRootObject().ThreadMgrObject();}
    private BinderClass dBinderObject() { return this.dBinderObject_; }
    
    public EngineDBinder(EngineRoot engine_root_object_val) {
        this.debug(false, "EngineDBinder", "init start");
        
        this.engineRootObject = engine_root_object_val;
        this.dEngineParserObject = new DEngineParserClass(this);
        this.dBinderObject_ = new BinderClass(this.objectName());

        this.dBinderObject().bindAsTcpClient(true, ThemeEngineProtocolClass.THEME_ENGINE_PROTOCOL_PROTOCOL_SERVER_IP_ADDRESS, ThemeEngineProtocolClass.THEME_ENGINE_PROTOCOL_TRANSPORT_PORT_NUMBER);
        this.debug(false, "DEngineClass", "init done");
    }

    public void startThreads() {
    	for (int i = 0; i < NUMBER_OF_U_WORK_THREADS; i++) {
    		this.ThreadMgrObject().createThreadObject(this.receiveThreadName(), this);
    	}
     }
    
	public void threadCallbackFunction() {
		this.dEngineReceiveThreadFunc();
	}
    
    public void dEngineReceiveThreadFunc() {
        this.debug(false, "dEngineReceiveThreadFunc", "start " + this.receiveThreadName());

        String data;
        while (true) {
            data = this.dBinderObject().receiveData();
            if (data == null) {
                this.abend("dEngineReceiveThreadFunc", "null data");
                continue;
            }
            
            this.debug(false, "dEngineReceiveThreadFunc", "data = " + data);
            this.dEngineParserObject.ParseInputPacket(data);
        }
    }

    public void TransmitData(String data_val) {
        this.dBinderObject().transmitData(data_val);
    }
    
    private void debug(Boolean on_off, String s0, String s1) { if (on_off) this.log(s0, s1); }
    private void log(String s0, String s1) { AbendClass.log(this.objectName() + "." + s0 + "()", s1); }
    public void abend(String s0, String s1) { AbendClass.abend(this.objectName() + "." + s0 + "()", s1); }
}
