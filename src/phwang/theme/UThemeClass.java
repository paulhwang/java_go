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

    private ThemeRootClass themeRootObject;
    private UThemeParserClass uThemeParserObject;
    public BinderClass binderObject;

    public ThemeRootClass ThemeRootObject() { return this.themeRootObject; }
    private ThreadMgrClass ThreadMgrObject() { return this.ThemeRootObject().ThreadMgrObject();}

    public UThemeClass(ThemeRootClass theme_root_object_val) {
        this.debugIt(false, "UThemeClass", "init start");

        this.themeRootObject = theme_root_object_val;
        this.uThemeParserObject = new UThemeParserClass(this);
        this.binderObject = new BinderClass(this.objectName());
        this.binderObject.bindAsTcpServer(true, ThemeEngineProtocolClass.THEME_ENGINE_PROTOCOL_TRANSPORT_PORT_NUMBER);
    }

    public void startThreads() {
    	this.ThreadMgrObject().createThreadObject(this.receiveThreadName(), this);
     }
    
	public void threadCallbackFunction() {
		this.uThemeRreceiveThreadFunc();
	}

    public void uThemeRreceiveThreadFunc() {
        this.debugIt(false, "dEngineReceiveThreadFunc", "start " + this.receiveThreadName());

        String data;
        while (true) {
            data = this.binderObject.receiveData();
            if (data == null) {
                this.abendIt("uThemeRreceiveThreadFunc", "null data");
                continue;
            }
            
            this.debugIt(false, "uThemeRreceiveThreadFunc", "data = " + data);
            this.uThemeParserObject.parseInputPacket(data);
        }
    }

    public void transmitData(String data_val) {
        this.binderObject.transmitData(data_val);
    }

    private void debugIt(Boolean on_off_val, String str0_val, String str1_val) { if (on_off_val) this.logitIt(str0_val, str1_val); }
    private void logitIt(String str0_val, String str1_val) { AbendClass.phwangLogit(this.objectName() + "." + str0_val + "()", str1_val); }
    public void abendIt(String str0_val, String str1_val) { AbendClass.phwangAbend(this.objectName() + "." + str0_val + "()", str1_val); }
}
