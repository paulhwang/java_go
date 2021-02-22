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

public class UFrontClass implements ThreadInterface {
    private String objectName() {return "UFrontClass";}
    private String receiveThreadName() { return "UFrontReceiveThread"; }

    private FrontEndRootClass frontEndRootObject;
    private BinderClass binderObject;
    private FrontEndJobMgrClass frontEndJobMgrObject;
    
    public FrontEndRootClass FrontEndRootObject() { return this.frontEndRootObject; }
    private ThreadMgrClass ThreadMgrObject() { return this.FrontEndRootObject().ThreadMgrObject();}

    public UFrontClass(FrontEndRootClass root_object_val) {
        this.debugIt(false, "UFrontClass", "init start");
        
        this.frontEndRootObject = root_object_val;
        this.binderObject = new BinderClass(this.objectName());
        this.frontEndJobMgrObject = new FrontEndJobMgrClass(this);
        this.binderObject.BindAsTcpClient("127.0.0.1", FabricFrontEndProtocolClass.LINK_MGR_PROTOCOL_TRANSPORT_PORT_NUMBER);
    }

    public void startThreads() {
    	this.ThreadMgrObject().CreateThreadObject(this.receiveThreadName(), this);
     }
    
	public void ThreadCallbackFunction() {
		this.UFrontReceiveThreadFunc();
	}
    
    private void UFrontReceiveThreadFunc() {
        this.debugIt(true, "UFrontReceiveThreadFunc", "start " + this.receiveThreadName());
    }

    private void debugIt(Boolean on_off_val, String str0_val, String str1_val) { if (on_off_val) this.logitIt(str0_val, str1_val); }
    private void logitIt(String str0_val, String str1_val) { AbendClass.phwangLogit(this.objectName() + "." + str0_val + "()", str1_val); }
    public void abendIt(String str0_val, String str1_val) { AbendClass.phwangAbend(this.objectName() + "." + str0_val + "()", str1_val); }
}
