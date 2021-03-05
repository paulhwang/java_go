/*
 ******************************************************************************
 *                                       
 *  Copyright (c) 2021 phwang. All rights reserved.
 *
 ******************************************************************************
 */

package phwang.front;

import phwang.utils.*;

public class FrontUBinder implements ThreadInterface {
    private String objectName() {return "FrontUBinder";}
    private String receiveThreadName() { return "UFrontReceiveThread"; }
    
	private static final int NUMBER_OF_D_WORK_THREADS = 5;

    private FrontRoot frontRoot_;
    private BinderClass uBinderObject_;
    private Boolean stopReceiveThreadFlag = false;
    
    private FrontDParser uFrontParserObject() { return this.frontRootObject().frontDParser(); }
    public FrontRoot frontRootObject() { return this.frontRoot_; }
    private FrontJobMgr frontJobMgr() { return this.frontRootObject().JobMgr(); }
    private ThreadMgrClass threadMgrObject() { return this.frontRootObject().threadMgr();}
    public BinderClass uBinderObject() { return this.uBinderObject_; }

    public FrontUBinder(FrontRoot root_object_val) {
        this.debug(false, "FrontUBinder", "init start");
        
        this.frontRoot_ = root_object_val;
        this.uBinderObject_ = new BinderClass(this.objectName());
        this.uBinderObject().bindAsTcpClient(true, FrontImport.FABRIC_FRONT_SERVER_IP_ADDRESS, FrontImport.FABRIC_FRONT_PORT);
    }

    public void startThreads() {
    	for (int i = 0; i < NUMBER_OF_D_WORK_THREADS; i++) {
    		this.threadMgrObject().createThreadObject(this.receiveThreadName(), this);
    	}
     }
    
	public void threadCallbackFunction() {
		this.uFrontReceiveThreadFunc();
	}
    
    private void uFrontReceiveThreadFunc() {
        this.debug(false, "UFrontReceiveThreadFunc", "start " + this.receiveThreadName());
        
        while (true) {
            if (this.stopReceiveThreadFlag) {
                break;
            }

            String received_data = this.uBinderObject().receiveData();
            if (received_data == null) {
                this.abend("UFrontReceiveThreadFunc", "null data");
            	continue;
            }

            this.debug(false, "UFrontReceiveThreadFunc", "received_data=" + received_data);

            String ajax_id_str = received_data.substring(0, FrontExport.FRONT_JOB_ID_SIZE);
            FrontJob job_entry = this.frontJobMgr().getJobByIdStr(ajax_id_str);
            if (job_entry == null) {
                this.abend("UFrontReceiveThreadFunc", "null ajax_entry");
                continue;
            }

            String response_data = received_data.substring(FrontExport.FRONT_JOB_ID_SIZE);
            String json_response_data = this.uFrontParserObject().parserResponseData(response_data);
            if (json_response_data != null) {
                job_entry.WriteData(json_response_data);
            }
            else {
            	job_entry.WriteData(response_data);
            }
        }

        this.debug(true, "UFrontReceiveThreadFunc", "exit");
    }
    
    public void StopReceiveThread() {
        this.stopReceiveThreadFlag = true;
    }
    
    private void debug(Boolean on_off, String s0, String s1) { if (on_off) this.log(s0, s1); }
    private void log(String s0, String s1) { AbendClass.log(this.objectName() + "." + s0 + "()", s1); }
    public void abend(String s0, String s1) { AbendClass.abend(this.objectName() + "." + s0 + "()", s1); }
}
