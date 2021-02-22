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
    private Boolean stopReceiveThreadFlag = false;
    
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
        
        return;/////////////////////////////////////////////
        /*
        while (true) {
            if (this.stopReceiveThreadFlag) {
                break;
            }

            String received_data = this.binderObject.ReceiveData();
            this.debugIt(true, "UFrontReceiveThreadFunc", "start 2 " + this.receiveThreadName());
            String ajax_id_str = received_data.substring(0, FabricFrontEndProtocolClass.AJAX_MAPING_ID_SIZE);
            this.debugIt(true, "UFrontReceiveThreadFunc", "start 3 " + this.receiveThreadName());
            String response_data = received_data.substring(FabricFrontEndProtocolClass.AJAX_MAPING_ID_SIZE);
            this.debugIt(true, "UFrontReceiveThreadFunc", "start 5 " + this.receiveThreadName());

            FrontEndJobClass job_entry = this.frontEndJobMgrObject.GetJobObject(ajax_id_str);
            if (job_entry == null) {
                this.abendIt("UFrontReceiveThreadFunc", "null ajax_entry");
                continue;
            }

            job_entry.WriteData(response_data);
        }

        this.debugIt(true, "UFrontReceiveThreadFunc", "exit");
        */
    }

    public void StopReceiveThread() {
        this.stopReceiveThreadFlag = true;
    }

    public String ProcessAjaxRequestPacket(String input_data_val) {
        this.debugIt(true, "ProcessAjaxRequestPacket", "input_data_val = " + input_data_val);
        
        FrontEndJobClass job_entry = this.frontEndJobMgrObject.MallocJobObject();
        this.binderObject.TransmitData(job_entry.ajaxIdStr + input_data_val);
        String response_data = job_entry.ReadData();
        this.debugIt(true, "ProcessAjaxRequestPacket", "response_data = " + response_data);
        return response_data;
    }

    private void debugIt(Boolean on_off_val, String str0_val, String str1_val) { if (on_off_val) this.logitIt(str0_val, str1_val); }
    private void logitIt(String str0_val, String str1_val) { AbendClass.phwangLogit(this.objectName() + "." + str0_val + "()", str1_val); }
    public void abendIt(String str0_val, String str1_val) { AbendClass.phwangAbend(this.objectName() + "." + str0_val + "()", str1_val); }
}
