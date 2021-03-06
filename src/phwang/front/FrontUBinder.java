/*
 ******************************************************************************
 *                                       
 *  Copyright (c) 2021 phwang. All rights reserved.
 *
 ******************************************************************************
 */

package phwang.front;

import phwang.utils.BinderClass;
import phwang.utils.ThreadMgrClass;
import phwang.utils.ThreadInterface;

public class FrontUBinder implements ThreadInterface {
    private String objectName() {return "FrontUBinder";}
    private String receiveThreadName() { return "UFrontReceiveThread"; }
    
	private static final int NUMBER_OF_D_WORK_THREADS = 5;

    private FrontRoot frontRoot_;
    private BinderClass uBinder_;
    private Boolean stopReceiveThreadFlag = false;
    
    private FrontDParser frontDParser() { return this.frontRoot().frontDParser(); }
    public FrontRoot frontRoot() { return this.frontRoot_; }
    private FrontJobMgr jobMgr() { return this.frontRoot().jobMgr(); }
    private ThreadMgrClass threadMgr() { return this.frontRoot().threadMgr();}
    public BinderClass uBinder() { return this.uBinder_; }

    public FrontUBinder(FrontRoot root_val) {
        this.debug(false, "FrontUBinder", "init start");
        
        this.frontRoot_ = root_val;
        this.uBinder_ = new BinderClass(this.objectName());
        this.uBinder().bindAsTcpClient(true, FrontImport.FABRIC_FRONT_SERVER_IP_ADDRESS, FrontImport.FABRIC_FRONT_PORT);
    }

    public void startThreads() {
    	for (int i = 0; i < NUMBER_OF_D_WORK_THREADS; i++) {
    		this.threadMgr().createThreadObject(this.receiveThreadName(), this);
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

            String received_data = this.uBinder().receiveData();
            if (received_data == null) {
                this.abend("UFrontReceiveThreadFunc", "null data");
            	continue;
            }

            this.debug(false, "UFrontReceiveThreadFunc", "received_data=" + received_data);

            String ajax_id_str = received_data.substring(0, FrontExport.FRONT_JOB_ID_SIZE);
            FrontJob job_entry = this.jobMgr().getJobByIdStr(ajax_id_str);
            if (job_entry == null) {
                this.abend("UFrontReceiveThreadFunc", "null ajax_entry");
                continue;
            }

            String response_data = received_data.substring(FrontExport.FRONT_JOB_ID_SIZE);
            String json_response_data = this.frontDParser().parserResponseData(response_data);
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
    private void log(String s0, String s1) { this.frontRoot().logIt(this.objectName() + "." + s0 + "()", s1); }
    public void abend(String s0, String s1) { this.frontRoot().abendIt(this.objectName() + "." + s0 + "()", s1); }
}
