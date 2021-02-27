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
    private ThreadMgrClass ThreadMgrObject() { return this.fabricRootObject().ThreadMgrObject();}
    private DFabricParserClass dFabricParserObject() { return this.dFabricParserObject_; }
    private BinderClass dBinderObject() { return this.dBinderObject_; }
  
    public DFabricClass(FabricRootClass fabric_root_class_val) {
        this.debugIt(false, "DFabricClass", "init start");
        
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
        this.debugIt(false, "dEngineReceiveThreadFunc", "start " + this.receiveThreadName());

        String data;
        while (true) {
            data = this.dBinderObject().receiveData();
            if (data == null) {
                this.abendIt("dFabricRreceiveThreadFunc", "null data");
                continue;
            }
            this.debugIt(false, "dFabricRreceiveThreadFunc", "data = " + data);
            this.dFabricParserObject().parseInputPacket(data);
        }
    }

    public void transmitData(String data_val) {
        this.dBinderObject().transmitData(data_val);
    }

    private void debugIt(Boolean on_off_val, String str0_val, String str1_val) { if (on_off_val) this.logitIt(str0_val, str1_val); }
    private void logitIt(String str0_val, String str1_val) { AbendClass.phwangLogit(this.objectName() + "." + str0_val + "()", str1_val); }
    public void abendIt(String str0_val, String str1_val) { AbendClass.phwangAbend(this.objectName() + "." + str0_val + "()", str1_val); }
}
