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
    //private Thread receiveThread { get; set; }

    public ThemeRootClass ThemeRootObject() { return this.themeRootObject; }

    public DThemeClass(ThemeRootClass theme_root_object_val)
    {
        this.debugIt(true, "DThemeClass", "init start");

        this.themeRootObject = theme_root_object_val;
        //this.dThemeParserObject = new DThemeParserClass(this);
        this.binderObject = new BinderClass(this.objectName());

        //this.receiveThread = new Thread(this.receiveThreadFunc);
        //this.receiveThread.Start();

        //this.binderObject.BindAsTcpClient("127.0.0.1", Protocols.FabricThemeProtocolClass.GROUP_ROOM_PROTOCOL_TRANSPORT_PORT_NUMBER);

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
