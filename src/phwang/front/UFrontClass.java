/*
 ******************************************************************************
 *                                       
 *  Copyright (c) 2018 phwang. All rights reserved.
 *
 ******************************************************************************
 */

package phwang.front;

import phwang.utils.*;
import phwang.protocols.FabricFrontEndProtocolClass;

public class UFrontClass implements ThreadInterface {
    private String objectName() {return "UFrontClass";}
    private String receiveThreadName() { return "UFrontReceiveThread"; }

    private FrontRootClass frontRootObject_;
    private BinderClass uBinderObject_;
    private Boolean stopReceiveThreadFlag = false;
    
    public FrontRootClass frontRootObject() { return this.frontRootObject_; }
    private FrontJobMgrClass frontJobMgrObject() { return this.frontRootObject().frontJobMgrObject(); }
    private ThreadMgrClass threadMgrObject() { return this.frontRootObject().threadMgrObject();}
    public BinderClass uBinderObject() { return this.uBinderObject_; }

    public UFrontClass(FrontRootClass root_object_val) {
        this.debugIt(false, "UFrontClass", "init start");
        
        this.frontRootObject_ = root_object_val;
        this.uBinderObject_ = new BinderClass(this.objectName());
        this.uBinderObject().bindAsTcpClient(true, FabricFrontEndProtocolClass.FABRIC_FRONT_PROTOCOL_SERVER_IP_ADDRESS, FabricFrontEndProtocolClass.FABRIC_FRONT_PROTOCOL_TRANSPORT_PORT_NUMBER);
    }

    public void startThreads() {
    	this.threadMgrObject().createThreadObject(this.receiveThreadName(), this);
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

            String received_data = this.uBinderObject().receiveData();
            if (received_data == null) {
                this.abendIt("UFrontReceiveThreadFunc", "null data");
            	continue;
            }

            this.debugIt(false, "UFrontReceiveThreadFunc", "received_data=" + received_data);

            String ajax_id_str = received_data.substring(0, FrontDefineClass.FRONT_JOB_ID_SIZE);
            String response_data = received_data.substring(FrontDefineClass.FRONT_JOB_ID_SIZE);

            FrontJobClass job_entry = this.frontJobMgrObject().getJobObject(ajax_id_str);
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

    private void debugIt(Boolean on_off_val, String str0_val, String str1_val) { if (on_off_val) this.logitIt(str0_val, str1_val); }
    private void logitIt(String str0_val, String str1_val) { AbendClass.phwangLogit(this.objectName() + "." + str0_val + "()", str1_val); }
    public void abendIt(String str0_val, String str1_val) { AbendClass.phwangAbend(this.objectName() + "." + str0_val + "()", str1_val); }
}
