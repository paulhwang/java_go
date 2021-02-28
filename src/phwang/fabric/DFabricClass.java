/*
 ******************************************************************************
 *                                       
 *  Copyright (c) 2018 phwang. All rights reserved.
 *
 ******************************************************************************
 */

package phwang.fabric;

import phwang.utils.*;
import phwang.protocols.*;

public class DFabricClass implements ThreadInterface {
    private String objectName() {return "DFabricClass";}
    private String receiveThreadName() { return "DFabricReceiveThread"; }
    
    private FabricRootClass fabricRootObject_;
    private DFabricParserClass dFabricParserObject_;
    public BinderClass dBinderObject_;

    public FabricRootClass fabricRootObject() { return this.fabricRootObject_; }
    private ThreadMgrClass ThreadMgrObject() { return this.fabricRootObject().threadMgrObject();}
    private DFabricParserClass dFabricParserObject() { return this.dFabricParserObject_; }
    private BinderClass dBinderObject() { return this.dBinderObject_; }
  
    public DFabricClass(FabricRootClass fabric_root_class_val) {
        this.debug(false, "DFabricClass", "init start");
        
        this.fabricRootObject_ = fabric_root_class_val;
        this.dFabricParserObject_ = new DFabricParserClass(this);
        this.dBinderObject_ = new BinderClass(this.objectName());
        
        this.dBinderObject().bindAsTcpServer(true, FabricFrontEndProtocolClass.FABRIC_FRONT_PROTOCOL_TRANSPORT_PORT_NUMBER);
    }

    public void startThreads() {
    	this.ThreadMgrObject().createThreadObject(this.receiveThreadName(), this);
    }
    
	public void threadCallbackFunction() {
		this.dFabricRreceiveThreadFunc();
	}

    public void dFabricRreceiveThreadFunc() {
        this.debug(false, "dEngineReceiveThreadFunc", "start " + this.receiveThreadName());

        String data;
        while (true) {
            data = this.dBinderObject().receiveData();
            if (data == null) {
                this.abend("dFabricRreceiveThreadFunc", "null data");
                continue;
            }
            this.debug(false, "dFabricRreceiveThreadFunc", "data=" + data);
            this.dFabricParserObject().parseInputPacket(data);
        }
    }

    public void transmitData(String data_val) {
        this.dBinderObject().transmitData(data_val);
    }
    
    private void debug(Boolean on_off, String s0, String s1) { if (on_off) this.log(s0, s1); }
    private void log(String s0, String s1) { AbendClass.log(this.objectName() + "." + s0 + "()", s1); }
    public void abend(String s0, String s1) { AbendClass.abend(this.objectName() + "." + s0 + "()", s1); }
}
