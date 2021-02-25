/*
 ******************************************************************************
 *                                       
 *  Copyright (c) 2018 phwang. All rights reserved.
 *
 ******************************************************************************
 */

package Phwang.Fabric;

import Phwang.Utils.AbendClass;
import Phwang.Utils.UtilsClass;
import Phwang.Utils.Binder.BinderClass;
import Phwang.Utils.ThreadMgr.ThreadInterface;
import Phwang.Utils.ThreadMgr.ThreadMgrClass;
import Phwang.Protocols.FabricFrontEndProtocolClass;
import Phwang.Fabric.FabricRootClass;

public class DFabricClass implements ThreadInterface {
    private String objectName() {return "DFabricClass";}
    private String receiveThreadName() { return "DFabricReceiveThread"; }
    
    private FabricRootClass fabricRootObject;
    private DFabricParserClass dFabricParserObject;
    public BinderClass binderObject;

    public FabricRootClass FabricRootObject() { return this.fabricRootObject; }
    private ThreadMgrClass ThreadMgrObject() { return this.FabricRootObject().ThreadMgrObject();}
  
    public DFabricClass(FabricRootClass fabric_root_class_val) {
        this.debugIt(false, "DFabricClass", "init start");
        
        this.fabricRootObject = fabric_root_class_val;
        this.dFabricParserObject = new DFabricParserClass(this);
        this.binderObject = new BinderClass(this.objectName());
        
        this.binderObject.bindAsTcpServer(true, FabricFrontEndProtocolClass.FABRIC_FRONT_PROTOCOL_TRANSPORT_PORT_NUMBER);
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
            data = this.binderObject.receiveData();
            if (data == null) {
                this.abendIt("dFabricRreceiveThreadFunc", "null data");
                continue;
            }
            this.debugIt(false, "dFabricRreceiveThreadFunc", "data = " + data);
            this.dFabricParserObject.parseInputPacket(data);
        }
    }

    public void transmitData(String data_val) {
        this.binderObject.transmitData(data_val);
    }

    private void debugIt(Boolean on_off_val, String str0_val, String str1_val) { if (on_off_val) this.logitIt(str0_val, str1_val); }
    private void logitIt(String str0_val, String str1_val) { AbendClass.phwangLogit(this.objectName() + "." + str0_val + "()", str1_val); }
    public void abendIt(String str0_val, String str1_val) { AbendClass.phwangAbend(this.objectName() + "." + str0_val + "()", str1_val); }
}
