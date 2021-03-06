/*
 ******************************************************************************
 *                                       
 *  Copyright (c) 2021 phwang. All rights reserved.
 *
 ******************************************************************************
 */

package phwang.theme;

import phwang.utils.*;
import phwang.protocols.ThemeEngineProtocolClass;

public class ThemeUBinder implements ThreadInterface {
    private String objectName() {return "ThemeUBinder";}
    private String receiveThreadName() { return "UThemeReceiveThread"; }

	private static final int NUMBER_OF_D_WORK_THREADS = 5;

    private ThemeRoot themeRoot_;
    public Binder uBinder_;

    public ThemeRoot themeRoot() { return this.themeRoot_; }
    public ThemeDParser themeDParser() { return this.themeRoot().themeDParser(); }
    private ThreadMgrClass threadMgr() { return this.themeRoot().threadMgr();}
    private Binder uBinder() { return this.uBinder_; }

    public ThemeUBinder(ThemeRoot root_val) {
        this.debug(false, "ThemeUBinder", "init start");

        this.themeRoot_ = root_val;
        this.uBinder_ = new Binder(this.objectName());
        
        this.uBinder().bindAsTcpServer(true, ThemeEngineProtocolClass.THEME_ENGINE_PROTOCOL_TRANSPORT_PORT_NUMBER);
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
            data = this.uBinder().receiveData();
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
        this.uBinder().transmitData(data_val);
    }
    
    private void debug(Boolean on_off, String s0, String s1) { if (on_off) this.log(s0, s1); }
    private void log(String s0, String s1) { Abend.log(this.objectName() + "." + s0 + "()", s1); }
    public void abend(String s0, String s1) { Abend.abend(this.objectName() + "." + s0 + "()", s1); }
}
