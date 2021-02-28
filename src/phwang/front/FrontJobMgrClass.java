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

    private static final int LIST_MGR_ARRAY_SIZE = 100;
    private static final int FIRST_JOB_ID = 1000;
    private static final int MAX_AJAX_ENTRY_ARRAY_SIZE = 1000;

    private FrontRootClass frontRootObject_;
    private int nextAvailableJobId;
    private int maxAllowedJobId;
    private int maxJobArrayIndex;
    private FrontJobClass[] jobArray;
    private Lock theLock;
    private ListMgrClass listMgr_;

    private ListMgrClass listMgr() { return this.listMgr_; }

    public FrontJobMgrClass(FrontRootClass front_root_object_val) {
        this.debug(false, "FrontJobMgrClass", "init start");

        this.frontRootObject_ = front_root_object_val;
        this.listMgr_ = new ListMgrClass(FrontDefineClass.FRONT_JOB_ID_SIZE, LIST_MGR_ARRAY_SIZE, this.objectName(), FIRST_JOB_ID);
        
        this.theLock = new ReentrantLock();

        this.nextAvailableJobId = 0;
        //this.setMaxAllowedJobId(FrontDefineClass.FRONT_JOB_ID_SIZE);

        this.maxJobArrayIndex = 0;
        this.jobArray = new FrontJobClass[MAX_AJAX_ENTRY_ARRAY_SIZE];
    }


    public FrontJobClass mallocLink() {
    	FrontJobClass job = new FrontJobClass();
        ListEntryClass list_entry = this.listMgr().malloc(job);
        job.bindListEntry(list_entry);
        return job;
    }

    public void freeLink(LinkClass link_val) {

    }
    
    public FrontJobClass getLinkByIdStr(String job_id_str_val) {
        int job_id = EncodeNumberClass.decodeNumber(job_id_str_val);

        return this.getLinkById(job_id);
    }

    public FrontJobClass getLinkById(int id_val) {
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
