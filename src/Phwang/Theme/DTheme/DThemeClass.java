/*
 ******************************************************************************
 *                                       
 *  Copyright (c) 2018 phwang. All rights reserved.
 *
 ******************************************************************************
 */

package Phwang.Theme.DTheme;

import Phwang.Utils.AbendClass;
import Phwang.Utils.Binder.BinderClass;
import Phwang.Theme.ThemeRootClass;

public class DThemeClass {
    private String objectName() {return "DThemeClass";}

    private ThemeRootClass themeRootObject;
    private DThemeParserClass dThemeParserObject;
    private BinderClass binderObject;
    private Thread receiveThread;
    private DThemeReceiveRunnable receiveRunable;

    public ThemeRootClass ThemeRootObject() { return this.themeRootObject; }

    public DThemeClass(ThemeRootClass theme_root_object_val)
    {
        this.debugIt(true, "DThemeClass", "init start");

        this.themeRootObject = theme_root_object_val;
        this.dThemeParserObject = new DThemeParserClass(this);
        this.binderObject = new BinderClass(this.objectName());

        //this.binderObject.BindAsTcpClient("127.0.0.1", Protocols.FabricThemeProtocolClass.GROUP_ROOM_PROTOCOL_TRANSPORT_PORT_NUMBER);
    }

    public void startThreads() {
        this.receiveRunable = new DThemeReceiveRunnable(this);
        this.receiveThread = new Thread(this.receiveRunable);
        this.receiveThread.start();
     }

    public void dThemeRreceiveThreadFunc()
    {
        this.debugIt(true, "dThemeRreceiveThreadFunc", "start thread");

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
            this.dThemeParserObject.ParseInputPacket(data);

        }
    }

    public void TransmitData(String data_val)
    {
        this.binderObject.TransmitData(data_val);
    }

    private void debugIt(Boolean on_off_val, String str0_val, String str1_val)
    {
        if (on_off_val)
            this.logitIt(str0_val, str1_val);
    }

    private void logitIt(String str0_val, String str1_val)
    {
        AbendClass.phwangLogit(this.objectName() + "." + str0_val + "()", str1_val);
    }

    public void abendIt(String str0_val, String str1_val)
    {
        AbendClass.phwangAbend(this.objectName() + "." + str0_val + "()", str1_val);
    }
}

class DThemeReceiveRunnable implements Runnable
{
	DThemeClass theDThemeObject;
	
	public DThemeReceiveRunnable(DThemeClass d_theme_object_val) {
		this.theDThemeObject = d_theme_object_val;
	}
	
	public void run() {
		this.theDThemeObject.dThemeRreceiveThreadFunc();
	}
}

