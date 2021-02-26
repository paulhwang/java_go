/*
 ******************************************************************************
 *                                       
 *  Copyright (c) 2018 phwang. All rights reserved.
 *
 ******************************************************************************
 */

package Phwang.front;

import Phwang.Utils.*;
import Phwang.Utils.ThreadMgr.ThreadMgrClass;
import Phwang.Utils.ThreadMgr.ThreadInterface;
import Phwang.Utils.Binder.BinderClass;
import Phwang.protocols.FabricFrontEndProtocolClass;

public class UFrontClass implements ThreadInterface {
    private String objectName() {return "UFrontClass";}
    private String receiveThreadName() { return "UFrontReceiveThread"; }

    private FrontRootClass frontEndRootObject;
    private BinderClass binderObject;
    private FrontJobMgrClass frontJobMgrObject;
    private Boolean stopReceiveThreadFlag = false;
    
    public FrontRootClass FrontEndRootObject() { return this.frontEndRootObject; }
    private ThreadMgrClass ThreadMgrObject() { return this.FrontEndRootObject().ThreadMgrObject();}

    public UFrontClass(FrontRootClass root_object_val) {
        this.debugIt(false, "UFrontClass", "init start");
        
        this.frontEndRootObject = root_object_val;
        this.binderObject = new BinderClass(this.objectName());
        this.frontJobMgrObject = new FrontJobMgrClass(this);
        this.binderObject.bindAsTcpClient(true, FabricFrontEndProtocolClass.FABRIC_FRONT_PROTOCOL_SERVER_IP_ADDRESS, FabricFrontEndProtocolClass.FABRIC_FRONT_PROTOCOL_TRANSPORT_PORT_NUMBER);
    }

    public void startThreads() {
    	this.ThreadMgrObject().createThreadObject(this.receiveThreadName(), this);
     }
    
	public void threadCallbackFunction() {
		this.uFrontReceiveThreadFunc();
	}
    
    private void uFrontReceiveThreadFunc() {
        this.debugIt(false, "UFrontReceiveThreadFunc", "start " + this.receiveThreadName());
        
        while (true) {
            if (this.stopReceiveThreadFlag) {
                break;
            }

            String received_data = this.binderObject.receiveData();
            if (received_data == null) {
                this.abendIt("UFrontReceiveThreadFunc", "null data");
            	continue;
            }

            this.debugIt(false, "UFrontReceiveThreadFunc", "received_data=" + received_data);

            String ajax_id_str = received_data.substring(0, FabricFrontEndProtocolClass.AJAX_MAPING_ID_SIZE);
            String response_data = received_data.substring(FabricFrontEndProtocolClass.AJAX_MAPING_ID_SIZE);

            FrontJobClass job_entry = this.frontJobMgrObject.getJobObject(ajax_id_str);
            if (job_entry == null) {
                this.abendIt("UFrontReceiveThreadFunc", "null ajax_entry");
                continue;
            }

            job_entry.WriteData(response_data);
        }

        this.debugIt(true, "UFrontReceiveThreadFunc", "exit");
    }

    public void StopReceiveThread() {
        this.stopReceiveThreadFlag = true;
    }

    public String processAjaxRequestPacket(String input_data_val) {
        this.debugIt(false, "processAjaxRequestPacket", "input_data_val = " + input_data_val);
        
        FrontJobClass job_entry = this.frontJobMgrObject.mallocJobObject();
        this.binderObject.transmitData(job_entry.ajaxIdStr + input_data_val);
        String response_data = job_entry.readData();
        
        this.debugIt(false, "processAjaxRequestPacket", "response_data = " + response_data);
        return response_data;
    }

    private void debugIt(Boolean on_off_val, String str0_val, String str1_val) { if (on_off_val) this.logitIt(str0_val, str1_val); }
    private void logitIt(String str0_val, String str1_val) { AbendClass.phwangLogit(this.objectName() + "." + str0_val + "()", str1_val); }
    public void abendIt(String str0_val, String str1_val) { AbendClass.phwangAbend(this.objectName() + "." + str0_val + "()", str1_val); }
}