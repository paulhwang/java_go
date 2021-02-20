/*
 ******************************************************************************
 *                                       
 *  Copyright (c) 2018 phwang. All rights reserved.
 *
 ******************************************************************************
 */

package Phwang.FrontEnd;

import Phwang.Utils.AbendClass;

public class FrontEndFabricClass {
    private String objectName() {return "FrontEndFabricClass";}

    private FrontEndRootClass frontEndRootObject;
    //private PhwangUtils.BinderClass binderObject { get; }
    private FrontEndJobMgrClass frontEndJobMgrObject;
    //private Thread receiveThread { get; set; }
    private boolean stopReceiveThreadFlag;

    public FrontEndFabricClass(FrontEndRootClass root_object_val)
    {
        this.debugIt(false, "FrontEndFabricClass", "init start");
        
        this.frontEndRootObject = root_object_val;
        this.stopReceiveThreadFlag = false;
        //this.binderObject = new PhwangUtils.BinderClass(this.objectName);
        this.frontEndJobMgrObject = new FrontEndJobMgrClass(this);
        //this.receiveThread = new Thread(this.receiveThreadFunc);
        //this.receiveThread.Start();
        //this.binderObject.BindAsTcpClient("127.0.0.1", Protocols.FabricFrontEndProtocolClass.LINK_MGR_PROTOCOL_TRANSPORT_PORT_NUMBER);
    }

    public void StopReceiveThread()
    {
        this.stopReceiveThreadFlag = true;
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
