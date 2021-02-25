/*
 ******************************************************************************
 *                                       
 *  Copyright (c) 2018 phwang. All rights reserved.
 *
 ******************************************************************************
 */

package Phwang.Fabric.UFabric;

import Phwang.Utils.AbendClass;
import Phwang.Utils.UtilsClass;
import Phwang.Utils.Binder.BinderClass;
import Phwang.Utils.ThreadMgr.ThreadInterface;
import Phwang.Utils.ThreadMgr.ThreadMgrClass;
import Phwang.Protocols.FabricThemeProtocolClass;
import Phwang.Fabric.FabricRootClass;

public class UFabricClass implements ThreadInterface {
    private String objectName() {return "UFabricClass";}
    private String receiveThreadName() { return "UFabricReceiveThread"; }
    
    private static final String FABRIC_THEME_PROTOCOL_COMMAND_IS_SETUP_ROOM = "R";
    
    ///////////////#define FABRIC_THEME_PROTOCOL_RESPOND_IS_SETUP_ROOM 'r'
    //////////////////#define FABRIC_THEME_PROTOCOL_COMMAND_IS_PUT_ROOM_DATA 'D'
    ///////////////#define FABRIC_THEME_PROTOCOL_RESPOND_IS_PUT_ROOM_DATA 'd'

    private FabricRootClass fabricRootObject;
    private UFabricParserClass uFabricParserObject;
    public BinderClass binderObject;
    
    public FabricRootClass FabricRootObject() { return this.fabricRootObject; }
    private ThreadMgrClass ThreadMgrObject() { return this.FabricRootObject().ThreadMgrObject();}

    public UFabricClass(FabricRootClass fabric_root_class_val) {
        this.debugIt(false, "UFabricClass", "init start");
        this.fabricRootObject = fabric_root_class_val;
        this.uFabricParserObject = new UFabricParserClass(this);
        this.binderObject = new BinderClass(this.objectName());
        this.binderObject.bindAsTcpServer(true, FabricThemeProtocolClass.FABRIC_THEME_PROTOCOL_TRANSPORT_PORT_NUMBER);
    }

    public void startThreads() {
    	this.ThreadMgrObject().createThreadObject(this.receiveThreadName(), this);
     }
    
	public void threadCallbackFunction() {
		this.uFabricRreceiveThreadFunc();
	}

    public void uFabricRreceiveThreadFunc() {
        this.debugIt(false, "uFabricRreceiveThreadFunc", "start " + this.receiveThreadName());

        String data;
        while (true) {
            data = this.binderObject.receiveData();
            if (data == null) {
                this.abendIt("uFabricRreceiveThreadFunc", "null data");
                continue;
            }
            
            this.debugIt(false, "uFabricRreceiveThreadFunc", "data = " + data);
            this.uFabricParserObject.parseInputPacket(data);
        }
    }

    public void transmitData(String data_val) {
        this.binderObject.transmitData(data_val);
    }

    private void debugIt(Boolean on_off_val, String str0_val, String str1_val) { if (on_off_val) this.logitIt(str0_val, str1_val); }
    private void logitIt(String str0_val, String str1_val) { AbendClass.phwangLogit(this.objectName() + "." + str0_val + "()", str1_val); }
    public void abendIt(String str0_val, String str1_val) { AbendClass.phwangAbend(this.objectName() + "." + str0_val + "()", str1_val); }
}
