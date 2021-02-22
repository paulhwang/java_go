/*
 ******************************************************************************
 *                                       
 *  Copyright (c) 2018 phwang. All rights reserved.
 *
 ******************************************************************************
 */

package Phwang.FrontEnd;

import Phwang.Utils.AbendClass;
import Phwang.Utils.ThreadMgr.ThreadMgrClass;
import Phwang.Utils.ThreadMgr.ThreadInterface;
import Phwang.Utils.Binder.BinderClass;
import Phwang.Protocols.FabricFrontEndProtocolClass;

public class FrontEndFabricClass implements ThreadInterface {
    private String objectName() {return "UFrontEndClass";}
    private String receiveThreadName() { return "UFrontEndReceiveThread"; }

    private FrontEndRootClass frontEndRootObject;
    private BinderClass binderObject;
    private FrontEndJobMgrClass frontEndJobMgrObject;
    private boolean stopReceiveThreadFlag;
    
    public FrontEndRootClass FrontEndRootObject() { return this.frontEndRootObject; }
    private ThreadMgrClass ThreadMgrObject() { return this.FrontEndRootObject().ThreadMgrObject();}

    public FrontEndFabricClass(FrontEndRootClass root_object_val) {
        this.debugIt(false, "FrontEndFabricClass", "init start");
        
        this.frontEndRootObject = root_object_val;
        this.stopReceiveThreadFlag = false;
        this.binderObject = new BinderClass(this.objectName());
        this.frontEndJobMgrObject = new FrontEndJobMgrClass(this);
        this.binderObject.BindAsTcpClient("127.0.0.1", FabricFrontEndProtocolClass.LINK_MGR_PROTOCOL_TRANSPORT_PORT_NUMBER);
    }

    public void startThreads() {
    	this.ThreadMgrObject().CreateThreadObject(this.receiveThreadName(), this);
     }
    
	public void ThreadCallbackFunction() {
		this.UFrontEndReceiveThreadFunc();
	}
    
    private void UFrontEndReceiveThreadFunc() {
        this.debugIt(true, "UFrontEndReceiveThreadFunc", "start " + this.receiveThreadName());
    }
    
    public void StopReceiveThread() {
        this.stopReceiveThreadFlag = true;
    }

    private void debugIt(Boolean on_off_val, String str0_val, String str1_val) { if (on_off_val) this.logitIt(str0_val, str1_val); }
    private void logitIt(String str0_val, String str1_val) { AbendClass.phwangLogit(this.objectName() + "." + str0_val + "()", str1_val); }
    public void abendIt(String str0_val, String str1_val) { AbendClass.phwangAbend(this.objectName() + "." + str0_val + "()", str1_val); }
}
