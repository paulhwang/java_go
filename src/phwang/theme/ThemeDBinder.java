/*
 ******************************************************************************
 *                                       
 *  Copyright (c) 2021 phwang. All rights reserved.
 *
 ******************************************************************************
 */

package phwang.theme;

import phwang.utils.*;
import phwang.protocols.*;

public class ThemeDBinder implements ThreadInterface {
    private String objectName() {return "ThemeDBinder";}
    private String receiveThreadName() { return "DThemeReceiveThread"; }
    
	private static final int NUMBER_OF_U_WORK_THREADS = 5;

    private ThemeRoot themeRoot_;
    private ThemeUParser themeUParser;
    private BinderClass dBinderObject_;

    public ThemeRoot themeRoot() { return this.themeRoot_; }
    private ThreadMgrClass ThreadMgrObject() { return this.themeRoot().threadMgr();}
    private BinderClass dBinderObject() { return this.dBinderObject_; }

    public ThemeDBinder(ThemeRoot theme_root_object_val) {
        this.debug(false, "ThemeDBinder", "init start");

        this.themeRoot_ = theme_root_object_val;
        this.themeUParser = new ThemeUParser(this);
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
            this.themeUParser.parseInputPacket(data);
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
