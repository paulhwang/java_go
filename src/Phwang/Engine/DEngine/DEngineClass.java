/*
 ******************************************************************************
 *                                       
 *  Copyright (c) 2018 phwang. All rights reserved.
 *
 ******************************************************************************
 */

package Phwang.Engine.DEngine;

import Phwang.Engine.EngineRootClass;
import Phwang.Utils.AbendClass;

public class DEngineClass {
    private String objectName() {return "DEngineClass";}
    
    public DEngineClass(EngineRootClass engine_root_object_val)
    {
        this.debugIt(true, "DEngineClass", "init start");
        
        //this.engineRootObject = engine_root_object_val;
        //this.dEngineParserObject = new DEngineParserClass(this);
        //this.binderObject = new PhwangUtils.BinderClass(this.objectName);

        //this.receiveThread = new Thread(this.receiveThreadFunc);
        //this.receiveThread.Start();

        //this.binderObject.BindAsTcpClient("127.0.0.1", Protocols.ThemeEngineProtocolClass.BASE_MGR_PROTOCOL_TRANSPORT_PORT_NUMBER);
        //this.debugIt(true, "DEngineClass", "init done");
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
