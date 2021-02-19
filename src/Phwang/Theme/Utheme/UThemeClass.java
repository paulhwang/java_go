/*
 ******************************************************************************
 *                                       
 *  Copyright (c) 2018 phwang. All rights reserved.
 *
 ******************************************************************************
 */

package Phwang.Theme.Utheme;

import Phwang.Theme.ThemeRootClass;
import Phwang.Utils.AbendClass;

public class UThemeClass {
    private String objectName() {return "UThemeClass";}

    private ThemeRootClass themeRootObject;
    //private UThemeParserClass uThemeParserObject { get; }
    //public PhwangUtils.BinderClass binderObject { get; set; }
    //private Thread receiveThread { get; set; }

    public ThemeRootClass ThemeRootObject() { return this.themeRootObject; }

    public UThemeClass(ThemeRootClass theme_root_object_val)
    {
        this.debugIt(true, "UThemeClass", "init start");

        this.themeRootObject = theme_root_object_val;
        //this.uThemeParserObject = new UThemeParserClass(this);
        //this.binderObject = new PhwangUtils.BinderClass(this.objectName);
        //this.binderObject.BindAsTcpServer(Protocols.ThemeEngineProtocolClass.BASE_MGR_PROTOCOL_TRANSPORT_PORT_NUMBER);

        //this.receiveThread = new Thread(this.receiveThreadFunc);
        //this.receiveThread.Start();
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

    private void abendIt(String str0_val, String str1_val)
    {
        AbendClass.phwangAbend(this.objectName() + "." + str0_val + "()", str1_val);
    }
}
