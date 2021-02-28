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
import phwang.protocols.FabricFrontEndProtocolClass;

public class FrontJobMgrClass {
    private String objectName() {return "FrontJobMgrClass";}

    private static final int FIRST_JOB_ID = 0;
    private static final int MAX_AJAX_ENTRY_ARRAY_SIZE = 1000;

    private FrontRootClass frontRootObject_;
    private int nextAvailableJobId;
    private int maxAllowedJobId;
    private int maxJobArrayIndex;
    private FrontJobClass[] jobArray;
    private Lock theLock;
    private ListMgrClass listMgr;

    public FrontJobMgrClass(FrontRootClass front_root_object_val) {
        this.debug(false, "FrontJobMgrClass", "init start");

        this.frontRootObject_ = front_root_object_val;
        this.listMgr = new ListMgrClass(FrontDefineClass.FRONT_JOB_ID_SIZE, this.objectName(), FIRST_JOB_ID);
        
        this.theLock = new ReentrantLock();

        this.nextAvailableJobId = 0;
        this.setMaxAllowedJobId(FrontDefineClass.FRONT_JOB_ID_SIZE);

        this.maxJobArrayIndex = 0;
        this.jobArray = new FrontJobClass[MAX_AJAX_ENTRY_ARRAY_SIZE];
    }

    private void setMaxAllowedJobId(int ajax_id_size_val) {
        this.maxAllowedJobId = 1;
        for (var i = 0; i < ajax_id_size_val; i++) {
            this.maxAllowedJobId *= 10;
        }
        this.maxAllowedJobId -= 1;
    }

    public FrontJobClass mallocJobObject() {
    	this.theLock.lock();
    	FrontJobClass front_jab_object = this.doMallocJobObject();
    	this.theLock.unlock();
    	return front_jab_object;
    }

    private FrontJobClass doMallocJobObject() {
        this.incrementNextAvailableJobId();
        String ajax_id_str = EncodeNumberClass.encodeNumber(this.nextAvailableJobId, FrontDefineClass.FRONT_JOB_ID_SIZE);
        FrontJobClass ajax_entry_object = new FrontJobClass(ajax_id_str);
        this.putJobObject(ajax_entry_object);
        return ajax_entry_object;
    }

    private void incrementNextAvailableJobId() {
        this.nextAvailableJobId++;
        if (this.nextAvailableJobId > this.maxAllowedJobId) {
            this.nextAvailableJobId = 1;
        }
    }

    private void putJobObject(FrontJobClass val) {
        for (var i = 0; i < this.maxJobArrayIndex; i++) {
            if (this.jobArray[i] == null) {
                this.jobArray[i] = val;
                return;
            }
        }
        this.jobArray[this.maxJobArrayIndex] = val;
        this.incrementMaxAjaxMapIndex();
    }

    private void incrementMaxAjaxMapIndex() {
        this.maxJobArrayIndex++;
    }

    public FrontJobClass getJobObject(String ajax_id_str_val) {
    	this.theLock.lock();
    	FrontJobClass front_job_object = this.doGetJobObject(ajax_id_str_val);
    	this.theLock.unlock();
    	return front_job_object;
    }

    private FrontJobClass doGetJobObject(String ajax_id_str_val) {
        int index;

        var found = false;
        for (index = 0; index < this.maxJobArrayIndex; index++) {
            if (this.jobArray[index] != null) {
                if (this.jobArray[index].ajaxIdStr.equals(ajax_id_str_val)) {
                    found = true;
                    break;
                }
            }
        }

        if (!found) {
            this.abend("doGetJobObject", "not found" + ajax_id_str_val);
            return null;
        }

        FrontJobClass element = this.jobArray[index];
        this.jobArray[index] = null;
        return element;
    }
    
    private void debug(Boolean on_off, String s0, String s1) { if (on_off) this.log(s0, s1); }
    private void log(String s0, String s1) { AbendClass.log(this.objectName() + "." + s0 + "()", s1); }
    public void abend(String s0, String s1) { AbendClass.abend(this.objectName() + "." + s0 + "()", s1); }
}
