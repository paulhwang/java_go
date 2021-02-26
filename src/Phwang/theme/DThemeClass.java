/*
 ******************************************************************************
 *                                       
 *  Copyright (c) 2018 phwang. All rights reserved.
 *
 ******************************************************************************
 */

package Phwang.theme;

import Phwang.Utils.AbendClass;
import Phwang.Utils.UtilsClass;
import Phwang.Utils.Binder.BinderClass;
import Phwang.Utils.ThreadMgr.ThreadInterface;
import Phwang.Utils.ThreadMgr.ThreadMgrClass;
import Phwang.protocols.FabricFrontEndProtocolClass;
import Phwang.protocols.FabricThemeProtocolClass;

public class DThemeClass implements ThreadInterface {
    private String objectName() {return "DThemeClass";}
    private String receiveThreadName() { return "DThemeReceiveThread"; }

    private ThemeRootClass themeRootObject;
    private DThemeParserClass dThemeParserObject;
    private BinderClass binderObject;

    public ThemeRootClass ThemeRootObject() { return this.themeRootObject; }
    private ThreadMgrClass ThreadMgrObject() { return this.ThemeRootObject().ThreadMgrObject();}

    public DThemeClass(ThemeRootClass theme_root_object_val) {
        this.debugIt(false, "DThemeClass", "init start");

        this.themeRootObject = theme_root_object_val;
        this.dThemeParserObject = new DThemeParserClass(this);
        this.binderObject = new BinderClass(this.objectName());

        this.binderObject.bindAsTcpClient(true, FabricThemeProtocolClass.FABRIC_THEME_PROTOCOL_SERVER_IP_ADDRESS, FabricThemeProtocolClass.FABRIC_THEME_PROTOCOL_TRANSPORT_PORT_NUMBER);
    }

    public void startThreads() {
    	this.ThreadMgrObject().createThreadObject(this.receiveThreadName(), this);
     }
    
	public void threadCallbackFunction() {
		this.dThemeRreceiveThreadFunc();
	}

    public void dThemeRreceiveThreadFunc() {
        this.debugIt(false, "dEngineReceiveThreadFunc", "start " + this.receiveThreadName());

        String data;
        while (true) {
            data = this.binderObject.receiveData();
            if (data == null) {
                this.abendIt("dThemeRreceiveThreadFunc", "null data");
                continue;
            }
            
            this.debugIt(false, "dThemeRreceiveThreadFunc", "data = " + data);
            this.dThemeParserObject.parseInputPacket(data);
        }
    }

    public void transmitData(String data_val) {
        this.debugIt(false, "transmitData", "data=" + data_val);
        this.binderObject.transmitData(data_val);
    }

    private void debugIt(Boolean on_off_val, String str0_val, String str1_val) { if (on_off_val) this.logitIt(str0_val, str1_val); }
    private void logitIt(String str0_val, String str1_val) { AbendClass.phwangLogit(this.objectName() + "." + str0_val + "()", str1_val); }
    public void abendIt(String str0_val, String str1_val) { AbendClass.phwangAbend(this.objectName() + "." + str0_val + "()", str1_val); }
}
