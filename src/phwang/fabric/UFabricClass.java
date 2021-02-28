/*
 ******************************************************************************
 *                                       
 *  Copyright (c) 2018 phwang. All rights reserved.
 *
 ******************************************************************************
 */

package phwang.fabric;

import phwang.utils.*;
import phwang.protocols.FabricThemeProtocolClass;

public class UFabricClass implements ThreadInterface {
    private String objectName() {return "UFabricClass";}
    private String receiveThreadName() { return "UFabricReceiveThread"; }
    
    private static final String FABRIC_THEME_PROTOCOL_COMMAND_IS_SETUP_ROOM = "R";
    
    ///////////////#define FABRIC_THEME_PROTOCOL_RESPOND_IS_SETUP_ROOM 'r'
    //////////////////#define FABRIC_THEME_PROTOCOL_COMMAND_IS_PUT_ROOM_DATA 'D'
    ///////////////#define FABRIC_THEME_PROTOCOL_RESPOND_IS_PUT_ROOM_DATA 'd'

    private FabricRootClass fabricRootObject;
    private UFabricParserClass uFabricParserObject;
    public BinderClass uBinderObject_;
    
    public FabricRootClass FabricRootObject() { return this.fabricRootObject; }
    private ThreadMgrClass ThreadMgrObject() { return this.FabricRootObject().threadMgrObject();}
    private BinderClass uBinderObject() { return this.uBinderObject_; }

    public UFabricClass(FabricRootClass fabric_root_class_val) {
        this.debug(false, "UFabricClass", "init start");
        this.fabricRootObject = fabric_root_class_val;
        this.uFabricParserObject = new UFabricParserClass(this);
        this.uBinderObject_ = new BinderClass(this.objectName());
        this.uBinderObject().bindAsTcpServer(true, FabricThemeProtocolClass.FABRIC_THEME_PROTOCOL_TRANSPORT_PORT_NUMBER);
    }

    public void startThreads() {
    	this.ThreadMgrObject().createThreadObject(this.receiveThreadName(), this);
     }
    
	public void threadCallbackFunction() {
		this.uFabricRreceiveThreadFunc();
	}

    public void uFabricRreceiveThreadFunc() {
        this.debug(false, "uFabricRreceiveThreadFunc", "start " + this.receiveThreadName());

        String data;
        while (true) {
            data = this.uBinderObject().receiveData();
            if (data == null) {
                this.abend("uFabricRreceiveThreadFunc", "null data");
                continue;
            }
            
            this.debug(false, "uFabricRreceiveThreadFunc", "data = " + data);
            this.uFabricParserObject.parseInputPacket(data);
        }
    }

    public void transmitData(String data_val) {
        this.uBinderObject().transmitData(data_val);
    }
    
    private void debug(Boolean on_off, String s0, String s1) { if (on_off) this.log(s0, s1); }
    private void log(String s0, String s1) { AbendClass.log(this.objectName() + "." + s0 + "()", s1); }
    public void abend(String s0, String s1) { AbendClass.abend(this.objectName() + "." + s0 + "()", s1); }
}
