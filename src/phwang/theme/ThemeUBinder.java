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
    private ThemeDParser uThemeParserObject;
    public BinderClass uBinder_;

    public ThemeRoot ThemeRootObject() { return this.themeRoot_; }
    private ThreadMgrClass ThreadMgrObject() { return this.ThemeRootObject().ThreadMgrObject();}
    private BinderClass uBinder() { return this.uBinder_; }

    public ThemeUBinder(ThemeRoot theme_root_object_val) {
        this.debug(false, "ThemeUBinder", "init start");

        this.themeRoot_ = theme_root_object_val;
        this.uThemeParserObject = new ThemeDParser(this);
        this.uBinder_ = new BinderClass(this.objectName());
        this.uBinder().bindAsTcpServer(true, ThemeEngineProtocolClass.THEME_ENGINE_PROTOCOL_TRANSPORT_PORT_NUMBER);
    }

    public void startThreads() {
    	for (int i = 0; i < NUMBER_OF_D_WORK_THREADS; i++) {
    		this.ThreadMgrObject().createThreadObject(this.receiveThreadName(), this);
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
            this.uThemeParserObject.parseInputPacket(data);
        }
    }

    public void transmitData(String data_val) {
        this.debug(false, "transmitData", "data=" + data_val);
        this.uBinder().transmitData(data_val);
    }
    
    private void debug(Boolean on_off, String s0, String s1) { if (on_off) this.log(s0, s1); }
    private void log(String s0, String s1) { AbendClass.log(this.objectName() + "." + s0 + "()", s1); }
    public void abend(String s0, String s1) { AbendClass.abend(this.objectName() + "." + s0 + "()", s1); }
}
