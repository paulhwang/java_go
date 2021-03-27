/*
 ******************************************************************************
 *                                       
 *  Copyright (c) 2021 phwang. All rights reserved.
 *
 ******************************************************************************
 */

package com.phwang.front;

import com.phwang.core.utils.ListEntry;
import com.phwang.core.utils.ListMgr;

public class FrontJobMgr {
    private String objectName() {return "FrontJobMgr";}

    private static final int LIST_MGR_ARRAY_SIZE_ = 128;
    private static final int FIRST_JOB_ID_ = 5000;

    protected static final int FRONT_JOB_ID_SIZE_ = 4;
    protected static final int FRONT_JOB_ID_SIZE = FRONT_JOB_ID_SIZE_ * 2 + 2;

    private FrontRoot frontRoot_;
    private ListMgr listMgr_;

    private FrontRoot frontRoot() { return this.frontRoot_; }
    private ListMgr listMgr() { return this.listMgr_; }

    protected FrontJobMgr(FrontRoot front_root_val) {
        this.debug(false, "FrontJobMgr", "init start");

        this.frontRoot_ = front_root_val;
        this.listMgr_ = new ListMgr(FRONT_JOB_ID_SIZE_, LIST_MGR_ARRAY_SIZE_, this.objectName(), FIRST_JOB_ID_);
    }

    protected FrontJob mallocJob() {
    	FrontJob job = new FrontJob();
    	ListEntry list_entry = this.listMgr().malloc(job);
        return job;
    }

    protected void freeJob(FrontJob job_val) {
    	this.listMgr_.free(job_val.listEntry());
    }
    
    protected FrontJob getJobByIdStr(String job_id_str_val) {
        this.debug(false, "getJobByIdStr", "job_id_str_val=" + job_id_str_val);

    	ListEntry list_entry = this.listMgr().getEntryByIdStr(job_id_str_val);
        if (list_entry == null) {
        	this.abend("getJobByIdStr", "null data");
            return null;
        }
        return (FrontJob) list_entry.data();
    }
    
    private void debug(Boolean on_off, String s0, String s1) { if (on_off) this.log(s0, s1); }
    private void log(String s0, String s1) { this.frontRoot().logIt(this.objectName() + "." + s0 + "()", s1); }
    protected void abend(String s0, String s1) { this.frontRoot().abendIt(this.objectName() + "." + s0 + "()", s1); }
}
