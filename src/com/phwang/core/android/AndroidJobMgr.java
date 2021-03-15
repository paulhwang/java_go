/*
 ******************************************************************************
 *                                       
 *  Copyright (c) 2021 phwang. All rights reserved.
 *
 ******************************************************************************
 */

package com.phwang.core.android;

import com.phwang.core.utils.ListEntry;
import com.phwang.core.utils.ListMgr;

public class AndroidJobMgr {
    private String objectName() {return "AndroidJobMgr";}

    private static final int LIST_MGR_ARRAY_SIZE_ = 128;
    private static final int FIRST_JOB_ID_ = 0;

    private AndroidRoot androidRoot_;
    private ListMgr listMgr_;

    private AndroidRoot frontRoot() { return this.androidRoot_; }
    private ListMgr listMgr() { return this.listMgr_; }

    protected AndroidJobMgr(AndroidRoot android_root_val) {
        this.debug(false, "AndroidJobMgr", "init start");

        this.androidRoot_ = android_root_val;
        this.listMgr_ = new ListMgr(AndroidImport.FRONT_JOB_ID_SIZE, LIST_MGR_ARRAY_SIZE_, this.objectName(), FIRST_JOB_ID_);
    }

    protected AndroidJob mallocJob() {
    	AndroidJob job = new AndroidJob();
    	ListEntry list_entry = this.listMgr().malloc(job);
        return job;
    }

    protected void freeJob(AndroidJob job_val) {
    	this.listMgr_.free(job_val.listEntry());
    }
    
    protected AndroidJob getJobByIdStr(String job_id_str_val) {
        this.debug(false, "getJobByIdStr", "job_id_str_val=" + job_id_str_val);

    	ListEntry list_entry = this.listMgr().getEntryByIdStr(job_id_str_val);
        if (list_entry == null) {
        	this.abend("getJobByIdStr", "null data");
            return null;
        }
        return (AndroidJob) list_entry.data();
    }
    
    private void debug(Boolean on_off, String s0, String s1) { if (on_off) this.log(s0, s1); }
    private void log(String s0, String s1) { this.frontRoot().logIt(this.objectName() + "." + s0 + "()", s1); }
    protected void abend(String s0, String s1) { this.frontRoot().abendIt(this.objectName() + "." + s0 + "()", s1); }
}
