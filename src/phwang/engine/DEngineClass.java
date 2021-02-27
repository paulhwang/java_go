/*
 ******************************************************************************
 *                                       
 *  Copyright (c) 2018 phwang. All rights reserved.
 *
 ******************************************************************************
 */

package phwang.engine;

import phwang.utils.*;
import phwang.protocols.*;

public class DEngineClass implements ThreadInterface {
    private String objectName() {return "DEngineClass";}
    private String receiveThreadName() { return "DEngineReceiveThread"; }
    
    private EngineRootClass engineRootObject;
    private DEngineParserClass dEngineParserObject;
    private BinderClass dBinderObject_;

    public EngineRootClass EngineRootObject() { return this.engineRootObject; }
    private ThreadMgrClass ThreadMgrObject() { return this.EngineRootObject().ThreadMgrObject();}
    private BinderClass dBinderObject() { return this.dBinderObject_; }
    
    public DEngineClass(EngineRootClass engine_root_object_val) {
        this.debugIt(false, "DEngineClass", "init start");
        
        this.engineRootObject = engine_root_object_val;
        this.dEngineParserObject = new DEngineParserClass(this);
        this.dBinderObject_ = new BinderClass(this.objectName());

        this.dBinderObject().bindAsTcpClient(true, ThemeEngineProtocolClass.THEME_ENGINE_PROTOCOL_PROTOCOL_SERVER_IP_ADDRESS, ThemeEngineProtocolClass.THEME_ENGINE_PROTOCOL_TRANSPORT_PORT_NUMBER);
        this.debugIt(false, "DEngineClass", "init done");
    }

    public void startThreads() {
    	this.ThreadMgrObject().createThreadObject(this.receiveThreadName(), this);
     }
    
	public void threadCallbackFunction() {
		this.dEngineReceiveThreadFunc();
	}
    
    public void dEngineReceiveThreadFunc() {
        this.debugIt(false, "dEngineReceiveThreadFunc", "start " + this.receiveThreadName());

        String data;
        while (true) {
            data = this.dBinderObject().receiveData();
            if (data == null) {
                this.abendIt("dEngineReceiveThreadFunc", "null data");
                continue;
            }
            
            this.debugIt(false, "dEngineReceiveThreadFunc", "data = " + data);
            this.dEngineParserObject.ParseInputPacket(data);
        }
    }

    public void TransmitData(String data_val) {
        this.dBinderObject().transmitData(data_val);
    }

    private void debugIt(Boolean on_off_val, String str0_val, String str1_val) { if (on_off_val) this.logitIt(str0_val, str1_val); }
    private void logitIt(String str0_val, String str1_val) { AbendClass.phwangLogit(this.objectName() + "." + str0_val + "()", str1_val); }
    public void abendIt(String str0_val, String str1_val) { AbendClass.phwangAbend(this.objectName() + "." + str0_val + "()", str1_val); }
}
