/*
 ******************************************************************************
 *                                       
 *  Copyright (c) 2021 phwang. All rights reserved.
 *
 ******************************************************************************
 */

package com.phwang.core.theme;

import com.phwang.core.utils.*;

public class ThemeUBinder implements ThreadEntityInt {
    private String objectName() {return "ThemeUBinder";}
    private String receiveThreadName() { return "UThemeReceiveThread"; }

	private static final int NUMBER_OF_D_WORK_THREADS = 5;

    private ThemeRoot themeRoot_;
    public Binder uBinder_;

    public ThemeRoot themeRoot() { return this.themeRoot_; }
    public ThemeDParser themeDParser() { return this.themeRoot().themeDParser(); }
    private ThreadMgr threadMgr() { return this.themeRoot().threadMgr();}
    private Binder uBinder() { return this.uBinder_; }

    public ThemeUBinder(ThemeRoot root_val) {
        this.debug(false, "ThemeUBinder", "init start");

        this.themeRoot_ = root_val;
        this.uBinder_ = new Binder(this.objectName());
        
        this.uBinder().bindAsTcpServer(true, ThemeExport.THEME_ENGINE_PORT, true);
    }

    public void startThreads() {
    	for (int i = 0; i < NUMBER_OF_D_WORK_THREADS; i++) {
    		this.threadMgr().createThreadObject(this.receiveThreadName(), this);
    	}
     }
    
	public void threadCallbackFunction() {
		this.uThemeRreceiveThreadFunc();
	}

    public void uThemeRreceiveThreadFunc() {
        this.debug(false, "dEngineReceiveThreadFunc", "start " + this.receiveThreadName());

        String data;
        while (true) {
            data = this.uBinder().receiveStringData();
            if (data == null) {
                this.abend("uThemeRreceiveThreadFunc", "null data");
                continue;
            }
            
            this.debug(false, "uThemeRreceiveThreadFunc", "data = " + data);
            this.themeDParser().parseInputPacket(data);
        }
    }

    public void transmitData(String data_val) {
        this.debug(false, "transmitData", "data=" + data_val);
        this.uBinder().transmitStringData(data_val);
    }
    
    private void debug(Boolean on_off, String s0, String s1) { if (on_off) this.log(s0, s1); }
    private void log(String s0, String s1) { this.themeRoot().logIt(this.objectName() + "." + s0 + "()", s1); }
    public void abend(String s0, String s1) { this.themeRoot().abendIt(this.objectName() + "." + s0 + "()", s1); }
}
