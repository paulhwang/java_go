/*
 ******************************************************************************
 *                                       
 *  Copyright (c) 2018 phwang. All rights reserved.
 *
 ******************************************************************************
 */

package Phwang.Theme.DTheme;

import Phwang.Utils.AbendClass;
import Phwang.Utils.UtilsClass;
import Phwang.Utils.Binder.BinderClass;
import Phwang.Utils.ThreadMgr.ThreadInterface;
import Phwang.Utils.ThreadMgr.ThreadMgrClass;
import Phwang.Protocols.FabricFrontEndProtocolClass;
import Phwang.Protocols.FabricThemeProtocolClass;
import Phwang.Theme.ThemeRootClass;

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

        this.binderObject.BindAsTcpClient(true, FabricThemeProtocolClass.FABRIC_THEME_PROTOCOL_SERVER_IP_ADDRESS, FabricThemeProtocolClass.FABRIC_THEME_PROTOCOL_TRANSPORT_PORT_NUMBER);
    }

    public void startThreads() {
    	this.ThreadMgrObject().CreateThreadObject(this.receiveThreadName(), this);
     }
    
	public void ThreadCallbackFunction() {
		this.dThemeRreceiveThreadFunc();
	}

    public void dThemeRreceiveThreadFunc() {
        this.debugIt(false, "dEngineReceiveThreadFunc", "start " + this.receiveThreadName());

        String data;
        while (true) {
            data = this.binderObject.ReceiveData();
            if (data == null) {
                //this.abendIt("dThemeRreceiveThreadFunc", "null data");
            	UtilsClass.sleep(1000);
                continue;
            }
            
            this.debugIt(true, "dThemeRreceiveThreadFunc", "data = " + data);
            this.dThemeParserObject.ParseInputPacket(data);
        }
    }

    public void TransmitData(String data_val) {
        this.binderObject.TransmitData(data_val);
    }

    private void debugIt(Boolean on_off_val, String str0_val, String str1_val) { if (on_off_val) this.logitIt(str0_val, str1_val); }
    private void logitIt(String str0_val, String str1_val) { AbendClass.phwangLogit(this.objectName() + "." + str0_val + "()", str1_val); }
    public void abendIt(String str0_val, String str1_val) { AbendClass.phwangAbend(this.objectName() + "." + str0_val + "()", str1_val); }
}
