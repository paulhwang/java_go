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
import Phwang.Protocols.ThemeEngineProtocolClass;
import Phwang.Engine.DEngine.DEngineClass;
import Phwang.Theme.ThemeRootClass;

public class UThemeClass {
    private String objectName() {return "UThemeClass";}

    private ThemeRootClass themeRootObject;
    private UThemeParserClass uThemeParserObject;
    public BinderClass binderObject;
    private Thread receiveThread;
    private UThemeReceiveRunnable receiveRunable;

    public ThemeRootClass ThemeRootObject() { return this.themeRootObject; }

    public UThemeClass(ThemeRootClass theme_root_object_val) {
        this.debugIt(false, "UThemeClass", "init start");

        this.themeRootObject = theme_root_object_val;
        this.uThemeParserObject = new UThemeParserClass(this);
        this.binderObject = new BinderClass(this.objectName());
        //this.binderObject.BindAsTcpServer(ThemeEngineProtocolClass.BASE_MGR_PROTOCOL_TRANSPORT_PORT_NUMBER);
    }

    public void startThreads() {
        this.receiveRunable = new UThemeReceiveRunnable(this);
        this.receiveThread = new Thread(this.receiveRunable);
        this.receiveThread.start();
     }

    public void uThemeRreceiveThreadFunc() {
        this.debugIt(true, "uThemeRreceiveThreadFunc", "start thread ***");

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

class UThemeReceiveRunnable implements Runnable
{
	UThemeClass theUThemeObject;
	
	public UThemeReceiveRunnable(UThemeClass u_theme_object_val) {
		this.theUThemeObject = u_theme_object_val;
	}
	
	public void run() {
		this.theUThemeObject.uThemeRreceiveThreadFunc();
	}
}

