/*
 ******************************************************************************
 *                                       
 *  Copyright (c) 2021 phwang. All rights reserved.
 *
 ******************************************************************************
 */

package phwang.front;

import phwang.utils.ListEntry;
import phwang.utils.ListMgr;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import phwang.fabric.FabricLink;
import phwang.protocols.FabricFrontEndProtocolClass;

public class FrontJobMgr {
    private String objectName() {return "FrontJobMgr";}

    public static final int FRONT_JOB_ID_SIZE_ = 4;
    public static final int FRONT_JOB_ID_SIZE = FRONT_JOB_ID_SIZE_ * 2;

    private static final int LIST_MGR_ARRAY_SIZE = 128;
    private static final int FIRST_JOB_ID = 0;

    private FrontRoot frontRoot_;
    private ListMgr listMgr_;

    private FrontRoot frontRoot() { return this.frontRoot_; }
    private ListMgr listMgr() { return this.listMgr_; }

    public FrontJobMgr(FrontRoot front_root_object_val) {
        this.debug(false, "FrontJobMgr", "init start");

        this.frontRoot_ = front_root_object_val;
        this.listMgr_ = new ListMgr(FRONT_JOB_ID_SIZE_, LIST_MGR_ARRAY_SIZE, this.objectName(), FIRST_JOB_ID);
    }

    public FrontJob mallocJob() {
    	FrontJob job = new FrontJob();
    	ListEntry list_entry = this.listMgr().malloc(job);
        job.bindListEntry(list_entry);
        return job;
    }

    public void freeJob(FrontJob job_val) {

    }
    
    public FrontJob getJobByIdStr(String job_id_str_val) {
    	ListEntry list_entry = this.listMgr().getEntryByIdStr(job_id_str_val);
        if (list_entry == null) {
            return null;
        }
        return (FrontJob) list_entry.data();
    }
    
    private void debug(Boolean on_off, String s0, String s1) { if (on_off) this.log(s0, s1); }
    private void log(String s0, String s1) { this.frontRoot().logIt(this.objectName() + "." + s0 + "()", s1); }
    public void abend(String s0, String s1) { this.frontRoot().abendIt(this.objectName() + "." + s0 + "()", s1); }
}
