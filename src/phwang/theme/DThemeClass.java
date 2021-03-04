/*
 ******************************************************************************
 *                                       
 *  Copyright (c) 2018 phwang. All rights reserved.
 *
 ******************************************************************************
 */

package phwang.theme;

import phwang.utils.*;
import phwang.protocols.*;

public class DThemeClass implements ThreadInterface {
    private String objectName() {return "DThemeClass";}
    private String receiveThreadName() { return "DThemeReceiveThread"; }
    
	private static final int NUMBER_OF_U_WORK_THREADS = 5;

    private ThemeRootClass themeRootObject;
    private DThemeParserClass dThemeParserObject;
    private BinderClass dBinderObject_;

    public ThemeRootClass ThemeRootObject() { return this.themeRootObject; }
    private ThreadMgrClass ThreadMgrObject() { return this.ThemeRootObject().ThreadMgrObject();}
    private BinderClass dBinderObject() { return this.dBinderObject_; }

    public DThemeClass(ThemeRootClass theme_root_object_val) {
        this.debug(false, "DThemeClass", "init start");

        this.themeRootObject = theme_root_object_val;
        this.dThemeParserObject = new DThemeParserClass(this);
        this.dBinderObject_ = new BinderClass(this.objectName());

        this.dBinderObject().bindAsTcpClient(true, FabricThemeProtocolClass.FABRIC_THEME_PROTOCOL_SERVER_IP_ADDRESS, FabricThemeProtocolClass.FABRIC_THEME_PROTOCOL_TRANSPORT_PORT_NUMBER);
    }

    public void startThreads() {
    	for (int i = 0; i < NUMBER_OF_U_WORK_THREADS; i++) {
    		this.ThreadMgrObject().createThreadObject(this.receiveThreadName(), this);
    	}
     }
    
	public void threadCallbackFunction() {
		this.dThemeRreceiveThreadFunc();
	}

    public void dThemeRreceiveThreadFunc() {
        this.debug(false, "dThemeRreceiveThreadFunc", "start " + this.receiveThreadName());

        String data;
        while (true) {
            data = this.dBinderObject().receiveData();
            if (data == null) {
                this.abend("dThemeRreceiveThreadFunc", "null data");
                continue;
            }
            
            this.debug(false, "dThemeRreceiveThreadFunc", "data = " + data);
            this.dThemeParserObject.parseInputPacket(data);
        }
    }

    public void transmitData(String data_val) {
        this.debug(false, "transmitData", "data=" + data_val);
        this.dBinderObject().transmitData(data_val);
    }
    
    private void debug(Boolean on_off, String s0, String s1) { if (on_off) this.log(s0, s1); }
    private void log(String s0, String s1) { AbendClass.log(this.objectName() + "." + s0 + "()", s1); }
    public void abend(String s0, String s1) { AbendClass.abend(this.objectName() + "." + s0 + "()", s1); }
}
