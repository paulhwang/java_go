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

public class FabricUBinder implements ThreadInterface {
    private String objectName() {return "FabricUBinder";}
    private String receiveThreadName() { return "UFabricReceiveThread"; }
    
	private static final int NUMBER_OF_D_WORK_THREADS = 5;
    //private static final String FABRIC_THEME_PROTOCOL_COMMAND_IS_SETUP_ROOM = "R";
    
    ///////////////#define FABRIC_THEME_PROTOCOL_RESPOND_IS_SETUP_ROOM 'r'
    //////////////////#define FABRIC_THEME_PROTOCOL_COMMAND_IS_PUT_ROOM_DATA 'D'
    ///////////////#define FABRIC_THEME_PROTOCOL_RESPOND_IS_PUT_ROOM_DATA 'd'

    private FabricRoot fabricRoot_;
    private FabricDParser uFabricParserObject;
    public BinderClass uBinderObject_;
    
    public FabricRoot fabricRoot() { return this.fabricRoot_; }
    private ThreadMgrClass ThreadMgrObject() { return this.fabricRoot().threadMgrObject();}
    private BinderClass uBinderObject() { return this.uBinderObject_; }

    public FabricUBinder(FabricRoot fabric_root_class_val) {
        this.debug(false, "FabricUBinder", "init start");
        this.fabricRoot_ = fabric_root_class_val;
        this.uFabricParserObject = new FabricDParser(this);
        this.uBinderObject_ = new BinderClass(this.objectName());
        this.uBinderObject().bindAsTcpServer(true, FabricThemeProtocolClass.FABRIC_THEME_PROTOCOL_TRANSPORT_PORT_NUMBER);
    }

    public void startThreads() {
    	for (int i = 0; i < NUMBER_OF_D_WORK_THREADS; i++) {
    		this.ThreadMgrObject().createThreadObject(this.receiveThreadName(), this);
    	}
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
            
            this.debug(false, "uFabricRreceiveThreadFunc", "data=" + data);
            this.uFabricParserObject.parseInputPacket(data);
        }
    }

    public void transmitData(String data_val) {
        this.debug(false, "transmitData", "data=" + data_val);
        this.uBinderObject().transmitData(data_val);
    }
    
    private void debug(Boolean on_off, String s0, String s1) { if (on_off) this.log(s0, s1); }
    private void log(String s0, String s1) { AbendClass.log(this.objectName() + "." + s0 + "()", s1); }
    public void abend(String s0, String s1) { AbendClass.abend(this.objectName() + "." + s0 + "()", s1); }
}
