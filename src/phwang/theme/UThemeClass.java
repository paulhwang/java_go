/*
 ******************************************************************************
 *                                       
 *  Copyright (c) 2018 phwang. All rights reserved.
 *
 ******************************************************************************
 */

package phwang.theme;

import phwang.utils.*;
import phwang.protocols.ThemeEngineProtocolClass;

public class UThemeClass implements ThreadInterface {
    private String objectName() {return "UThemeClass";}
    private String receiveThreadName() { return "UThemeReceiveThread"; }

	private static final int NUMBER_OF_D_WORK_THREADS = 5;

    private ThemeRootClass themeRootObject;
    private UThemeParserClass uThemeParserObject;
    public BinderClass uBinderObject_;

    public ThemeRootClass ThemeRootObject() { return this.themeRootObject; }
    private ThreadMgrClass ThreadMgrObject() { return this.ThemeRootObject().ThreadMgrObject();}
    private BinderClass uBinderObject() { return this.uBinderObject_; }

    public UThemeClass(ThemeRootClass theme_root_object_val) {
        this.debug(false, "UThemeClass", "init start");

        this.themeRootObject = theme_root_object_val;
        this.uThemeParserObject = new UThemeParserClass(this);
        this.uBinderObject_ = new BinderClass(this.objectName());
        this.uBinderObject().bindAsTcpServer(true, ThemeEngineProtocolClass.THEME_ENGINE_PROTOCOL_TRANSPORT_PORT_NUMBER);
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
            data = this.uBinderObject().receiveData();
            if (data == null) {
                this.abend("uThemeRreceiveThreadFunc", "null data");
                continue;
            }
            
            this.debug(false, "uThemeRreceiveThreadFunc", "data = " + data);
            this.uThemeParserObject.parseInputPacket(data);
        }
    }

    public void transmitData(String data_val) {
        this.uBinderObject().transmitData(data_val);
    }
    
    private void debug(Boolean on_off, String s0, String s1) { if (on_off) this.log(s0, s1); }
    private void log(String s0, String s1) { AbendClass.log(this.objectName() + "." + s0 + "()", s1); }
    public void abend(String s0, String s1) { AbendClass.abend(this.objectName() + "." + s0 + "()", s1); }
}
