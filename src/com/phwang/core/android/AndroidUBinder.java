package com.phwang.core.android;

import com.phwang.core.utils.Binder;
import com.phwang.core.utils.ThreadEntityInt;
import com.phwang.core.utils.ThreadMgr;
import com.phwang.core.utils.Binder;

public class AndroidUBinder implements ThreadEntityInt {
    private String objectName() {return "AndroidUBinder";}
    private String receiveThreadName() { return "UAndroidReceiveThread"; }

	private static final int NUMBER_OF_D_WORK_THREADS = 5;
    
    private AndroidRoot androidRoot_;
    private Binder uBinder_;
    private Boolean stopReceiveThreadFlag = false;
    
    private AndroidRoot androidRoot() { return this.androidRoot_; }
    private AndroidJobMgr jobMgr() { return this.androidRoot().jobMgr(); }
    private ThreadMgr threadMgr() { return this.androidRoot().threadMgr();}
    protected Binder uBinder() { return this.uBinder_; }
    
    protected AndroidUBinder(AndroidRoot root_val) {
        this.debug(false, "AndroidUBinder", "init start");
        
    	this.androidRoot_ = root_val;
        this.uBinder_ = new Binder(this.objectName());
        this.uBinder_.bindAsTcpClient(true, AndroidImport.FABRIC_ANDROID_SERVER_IP_ADDRESS, AndroidImport.FABRIC_ANDROID_PORT);
    }

    protected void startThreads() {
    	for (int i = 0; i < NUMBER_OF_D_WORK_THREADS; i++) {
    		this.threadMgr().createThreadObject(this.receiveThreadName(), this);
    	}
     }
    
	public void threadCallbackFunction() {
		this.uAndroidReceiveThreadFunc();
	}
    
    private void uAndroidReceiveThreadFunc() {
        this.debug(false, "uAndroidReceiveThreadFunc", "start " + this.receiveThreadName());
        
        while (true) {
            if (this.stopReceiveThreadFlag) {
                break;
            }

            String received_data = this.uBinder().receiveData();
            if (received_data == null) {
                this.abend("uAndroidReceiveThreadFunc", "null data");
            	continue;
            }

            this.debug(false, "uAndroidReceiveThreadFunc", "received_data=" + received_data);

            String job_id_str = received_data.substring(0, AndroidImport.FRONT_JOB_ID_SIZE);
            AndroidJob job_entry = this.jobMgr().getJobByIdStr(job_id_str);
            if (job_entry == null) {
                this.abend("UFrontReceiveThreadFunc", "null ajax_entry, job_id_str="  + job_id_str);
                continue;
            }
            this.jobMgr().freeJob(job_entry);
            
            String response_data = received_data.substring(AndroidImport.FRONT_JOB_ID_SIZE);
            String json_response_data = response_data;//////////////////this.frontDParser().parserResponseData(response_data);
            if (json_response_data != null) {
                job_entry.WriteData(json_response_data);
            }
            else {
            	job_entry.WriteData(response_data);
            }
        }
        this.debug(true, "uAndroidReceiveThreadFunc", "exit");
    }
    
    protected void StopReceiveThread() {
        this.stopReceiveThreadFlag = true;
    }
    
    private void debug(Boolean on_off, String s0, String s1) { if (on_off) this.log(s0, s1); }
    private void log(String s0, String s1) { this.androidRoot().logIt(this.objectName() + "." + s0 + "()", s1); }
    protected void abend(String s0, String s1) { this.androidRoot().abendIt(this.objectName() + "." + s0 + "()", s1); }
}
