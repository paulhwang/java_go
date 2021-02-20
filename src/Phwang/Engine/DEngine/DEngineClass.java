/*
 ******************************************************************************
 *                                       
 *  Copyright (c) 2018 phwang. All rights reserved.
 *
 ******************************************************************************
 */

package Phwang.Engine.DEngine;

import Phwang.Utils.AbendClass;
import Phwang.Utils.Binder.BinderClass;
import Phwang.Engine.EngineRootClass;

public class DEngineClass {
    private String objectName() {return "DEngineClass";}
    
    private EngineRootClass engineRootObject;
    private DEngineParserClass dEngineParserObject;
    public BinderClass binderObject;
    private Thread receiveThread;

    public EngineRootClass EngineRootObject() { return this.engineRootObject; }

    
    public DEngineClass(EngineRootClass engine_root_object_val)
    {
        this.debugIt(true, "DEngineClass", "init start");
        
        this.engineRootObject = engine_root_object_val;
        this.dEngineParserObject = new DEngineParserClass(this);
        this.binderObject = new BinderClass(this.objectName());

        //this.receiveThread = new Thread(this.receiveThreadFunc);
        //this.receiveThread.Start();

        //this.binderObject.BindAsTcpClient("127.0.0.1", Protocols.ThemeEngineProtocolClass.BASE_MGR_PROTOCOL_TRANSPORT_PORT_NUMBER);
        this.debugIt(true, "DEngineClass", "init done");
    }

    private void receiveThreadFunc()
    {
        this.debugIt(true, "receiveThreadFunc", "start");

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
            this.dEngineParserObject.ParseInputPacket(data);

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
