/*
 ******************************************************************************
 *                                       
 *  Copyright (c) 2021 phwang. All rights reserved.
 *
 ******************************************************************************
 */

package com.phwang.core.theme;

import com.phwang.core.utils.Binder;
import com.phwang.core.utils.ThreadMgr;
import com.phwang.core.utils.ThreadEntityInt;
import com.phwang.core.protocols.*;

public class ThemeDBinder implements ThreadEntityInt {
    private String objectName() {return "ThemeDBinder";}
    private String receiveThreadName() { return "DThemeReceiveThread"; }
    
	private static final int NUMBER_OF_U_WORK_THREADS = 5;

    private ThemeRoot themeRoot_;
    private Binder dBinder_;

    public ThemeRoot themeRoot() { return this.themeRoot_; }
    private ThreadMgr ThreadMgr() { return this.themeRoot().threadMgr();}
    private ThemeUParser themeUParser() { return this.themeRoot().themeUParser(); }
    private Binder dBinder() { return this.dBinder_; }

    public ThemeDBinder(ThemeRoot root_val) {
        this.debug(false, "ThemeDBinder", "init start");

        this.themeRoot_ = root_val;
        this.dBinder_ = new Binder(this.objectName());

        this.dBinder().bindAsTcpClient(true, FabricThemeProtocolClass.FABRIC_THEME_PROTOCOL_SERVER_IP_ADDRESS, FabricThemeProtocolClass.FABRIC_THEME_PROTOCOL_TRANSPORT_PORT_NUMBER);
    }

    public void startThreads() {
    	for (int i = 0; i < NUMBER_OF_U_WORK_THREADS; i++) {
    		this.ThreadMgr().createThreadObject(this.receiveThreadName(), this);
    	}
     }
    
	public void threadCallbackFunction() {
		this.dThemeRreceiveThreadFunc();
	}

    public void dThemeRreceiveThreadFunc() {
        this.debug(false, "dThemeRreceiveThreadFunc", "start " + this.receiveThreadName());

        String data;
        while (true) {
            data = this.dBinder().receiveData();
            if (data == null) {
                this.abend("dThemeRreceiveThreadFunc", "null data");
                continue;
            }
            
            this.debug(false, "dThemeRreceiveThreadFunc", "data = " + data);
            this.themeUParser().parseInputPacket(data);
        }
    }
    
    public void transmitData(String data_val) {
        this.debug(false, "transmitData", "data=" + data_val);
        this.dBinder().transmitData(data_val);
    }
    
    private void debug(Boolean on_off, String s0, String s1) { if (on_off) this.log(s0, s1); }
    private void log(String s0, String s1) { this.themeRoot().logIt(this.objectName() + "." + s0 + "()", s1); }
    public void abend(String s0, String s1) { this.themeRoot().abendIt(this.objectName() + "." + s0 + "()", s1); }
}
