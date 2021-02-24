/*
 ******************************************************************************
 *                                       
 *  Copyright (c) 2018 phwang. All rights reserved.
 *
 ******************************************************************************
 */

package Phwang.Engine.DEngine;

import Phwang.Utils.AbendClass;
import Phwang.Utils.UtilsClass;
import Phwang.Utils.ThreadMgr.ThreadMgrClass;
import Phwang.Utils.ThreadMgr.ThreadInterface;
import Phwang.Utils.Binder.BinderClass;
import Phwang.Protocols.ThemeEngineProtocolClass;
import Phwang.Engine.EngineRootClass;

public class DEngineClass implements ThreadInterface {
    private String objectName() {return "DEngineClass";}
    private String receiveThreadName() { return "DEngineReceiveThread"; }
    
    private EngineRootClass engineRootObject;
    private DEngineParserClass dEngineParserObject;
    private BinderClass binderObject;

    public EngineRootClass EngineRootObject() { return this.engineRootObject; }
    private ThreadMgrClass ThreadMgrObject() { return this.EngineRootObject().ThreadMgrObject();}
    
    public DEngineClass(EngineRootClass engine_root_object_val) {
        this.debugIt(false, "DEngineClass", "init start");
        
        this.engineRootObject = engine_root_object_val;
        this.dEngineParserObject = new DEngineParserClass(this);
        this.binderObject = new BinderClass(this.objectName());

        this.binderObject.BindAsTcpClient(true, ThemeEngineProtocolClass.THEME_ENGINE_PROTOCOL_PROTOCOL_SERVER_IP_ADDRESS, ThemeEngineProtocolClass.THEME_ENGINE_PROTOCOL_TRANSPORT_PORT_NUMBER);
        this.debugIt(false, "DEngineClass", "init done");
    }

    public void startThreads() {
    	this.ThreadMgrObject().CreateThreadObject(this.receiveThreadName(), this);
     }
    
	public void ThreadCallbackFunction() {
		this.dEngineReceiveThreadFunc();
	}
    
    public void dEngineReceiveThreadFunc() {
        this.debugIt(false, "dEngineReceiveThreadFunc", "start " + this.receiveThreadName());

        String data;
        while (true) {
            data = this.binderObject.ReceiveData();
            if (data == null) {
                this.abendIt("dEngineReceiveThreadFunc", "null data");
                continue;
            }
            
            this.debugIt(true, "dEngineReceiveThreadFunc", "data = " + data);
            this.dEngineParserObject.ParseInputPacket(data);
        }
    }

    public void TransmitData(String data_val) {
        this.binderObject.TransmitData(data_val);
    }

    private void debugIt(Boolean on_off_val, String str0_val, String str1_val) { if (on_off_val) this.logitIt(str0_val, str1_val); }
    private void logitIt(String str0_val, String str1_val) { AbendClass.phwangLogit(this.objectName() + "." + str0_val + "()", str1_val); }
    public void abendIt(String str0_val, String str1_val) { AbendClass.phwangAbend(this.objectName() + "." + str0_val + "()", str1_val); }
}
