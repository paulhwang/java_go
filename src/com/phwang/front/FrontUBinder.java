/*
 ******************************************************************************
 *                                       
 *  Copyright (c) 2021 phwang. All rights reserved.
 *
 ******************************************************************************
 */

package com.phwang.front;

import com.phwang.core.utils.Binder;
import com.phwang.core.utils.Encoders;
import com.phwang.core.utils.ThreadMgr;
import com.phwang.core.utils.ThreadEntityInt;

public class FrontUBinder implements ThreadEntityInt {
    private String objectName() {return "FrontUBinder";}
    private String receiveThreadName() { return "UFrontReceiveThread"; }
    
	private static final int NUMBER_OF_D_WORK_THREADS = 5;

    private FrontRoot frontRoot_;
    private Binder uBinder_;
    private Boolean stopReceiveThreadFlag = false;
    
    private FrontDParser frontDParser() { return this.frontRoot().frontDParser(); }
    protected FrontRoot frontRoot() { return this.frontRoot_; }
    private FrontJobMgr jobMgr() { return this.frontRoot().jobMgr(); }
    private ThreadMgr threadMgr() { return this.frontRoot().threadMgr();}
    protected Binder uBinder() { return this.uBinder_; }

    protected FrontUBinder(FrontRoot root_val) {
        this.debug(false, "FrontUBinder", "init start");
        
        this.frontRoot_ = root_val;
        this.uBinder_ = new Binder(this.objectName());
        this.uBinder_.bindAsTcpClient(true, FrontImport.FABRIC_FRONT_SERVER_IP_ADDRESS, FrontImport.FABRIC_FRONT_PORT);
    }

    protected void startThreads() {
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

            String received_str = this.uBinder().receiveStringData();
            if (received_str == null) {
                this.abend("UFrontReceiveThreadFunc", "null data");
            	continue;
            }

            this.debug(true, "UFrontReceiveThreadFunc", "received_str=" + received_str);

            String rest_str = received_str;
            String job_id_str = Encoders.sSubstring2(rest_str);
            rest_str = Encoders.sSubstring2_(rest_str);

            FrontJob job_entry = this.jobMgr().getJobByIdStr(job_id_str);
            if (job_entry == null) {
                this.abend("UFrontReceiveThreadFunc", "null ajax_entry, job_id_str="  + job_id_str);
                continue;
            }
            this.jobMgr().freeJob(job_entry);

            String response_data = rest_str;
            //String response_data = Encoders.sSubstring2(rest_str);
            //rest_str = Encoders.sSubstring2_(rest_str);

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
    
    protected void StopReceiveThread() {
        this.stopReceiveThreadFlag = true;
    }
    
    private void debug(Boolean on_off, String s0, String s1) { if (on_off) this.log(s0, s1); }
    private void log(String s0, String s1) { this.frontRoot().logIt(this.objectName() + "." + s0 + "()", s1); }
    protected void abend(String s0, String s1) { this.frontRoot().abendIt(this.objectName() + "." + s0 + "()", s1); }
}
