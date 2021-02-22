/*
 ******************************************************************************
 *                                       
 *  Copyright (c) 2018 phwang. All rights reserved.
 *
 ******************************************************************************
 */

package Phwang.Theme.Utheme;

import Phwang.Utils.AbendClass;
import Phwang.Utils.Binder.BinderClass;
import Phwang.Utils.ThreadMgr.ThreadInterface;
import Phwang.Utils.ThreadMgr.ThreadMgrClass;
import Phwang.Protocols.ThemeEngineProtocolClass;
import Phwang.Engine.DEngine.DEngineClass;
import Phwang.Theme.ThemeRootClass;

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
        //this.binderObject.BindAsTcpServer(ThemeEngineProtocolClass.BASE_MGR_PROTOCOL_TRANSPORT_PORT_NUMBER);
    }

    public void startThreads() {
    	this.ThreadMgrObject().CreateThreadObject(this.receiveThreadName(), this);
     }
    
	public void ThreadCallbackFunction() {
		this.uThemeRreceiveThreadFunc();
	}

    public void uThemeRreceiveThreadFunc() {
        this.debugIt(true, "dEngineReceiveThreadFunc", "start (" + this.receiveThreadName() + ")");

        return;////////////////////////////////
        
        /*
        String data;
        while (true)
        {
            data = this.binderObject.ReceiveData();
            if (data == null)
            {
                this.abendIt("receiveThreadFunc", "null data");
                continue;
            }
            this.debugIt(true, "receiveThreadFunc", "data = " + data);
            this.uThemeParserObject.ParseInputPacket(data);
        }
        */
    }

    public void TransmitData(String data_val) {
        this.binderObject.TransmitData(data_val);
    }

    private void debugIt(Boolean on_off_val, String str0_val, String str1_val) {
        if (on_off_val)
            this.logitIt(str0_val, str1_val);
    }

    private void logitIt(String str0_val, String str1_val) {
        AbendClass.phwangLogit(this.objectName() + "." + str0_val + "()", str1_val);
    }

    public void abendIt(String str0_val, String str1_val) {
        AbendClass.phwangAbend(this.objectName() + "." + str0_val + "()", str1_val);
    }
}
