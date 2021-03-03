/*
 ******************************************************************************
 *                                       
 *  Copyright (c) 2018 phwang. All rights reserved.
 *
 ******************************************************************************
 */

package phwang.front;

import phwang.utils.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import phwang.fabric.LinkClass;
import phwang.protocols.FabricFrontEndProtocolClass;

public class FrontJobMgrClass {
    private String objectName() {return "FrontJobMgrClass";}

    private static final int LIST_MGR_ARRAY_SIZE = 128;
    private static final int FIRST_JOB_ID = 0;

    private FrontRootClass frontRootObject_;
    private ListMgrClass listMgr_;

    private ListMgrClass listMgr() { return this.listMgr_; }

    public FrontJobMgrClass(FrontRootClass front_root_object_val) {
        this.debug(false, "FrontJobMgrClass", "init start");

        this.frontRootObject_ = front_root_object_val;
        this.listMgr_ = new ListMgrClass(FrontDefineClass.FRONT_JOB_ID_SIZE, LIST_MGR_ARRAY_SIZE, this.objectName(), FIRST_JOB_ID);
    }

    public FrontJobClass mallocJob() {
    	FrontJobClass job = new FrontJobClass();
        ListEntryClass list_entry = this.listMgr().malloc(job);
        job.bindListEntry(list_entry);
        return job;
    }

    public void freeJob(FrontJobClass job_val) {

    }
    
    public FrontJobClass getJobByIdStr(String job_id_str_val) {
        int job_id = EncodeNumberClass.decodeNumber(job_id_str_val);

        return this.getJobById(job_id);
    }

    public FrontJobClass getJobById(int id_val) {
    	ListEntryClass list_entry = this.listMgr().getEntryById(id_val);
        if (list_entry == null) {
            return null;
        }
        FrontJobClass job = (FrontJobClass) list_entry.data();

        return job;
    }
    
    private void debug(Boolean on_off, String s0, String s1) { if (on_off) this.log(s0, s1); }
    private void log(String s0, String s1) { AbendClass.log(this.objectName() + "." + s0 + "()", s1); }
    public void abend(String s0, String s1) { AbendClass.abend(this.objectName() + "." + s0 + "()", s1); }
}
