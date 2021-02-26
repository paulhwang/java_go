/*
 ******************************************************************************
 *                                       
 *  Copyright (c) 2018 phwang. All rights reserved.
 *
 ******************************************************************************
 */

package Phwang.front;

import Phwang.Utils.*;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import Phwang.protocols.FabricFrontEndProtocolClass;

public class FrontJobMgrClass {
    private String objectName() {return "FrontJobMgrClass";}

    private static final int MAX_AJAX_ENTRY_ARRAY_SIZE = 1000;

    private UFrontClass frontEndFabricObject;
    private int nextAvailableJobId;
    private int maxAllowedJobId;
    private int maxJobArrayIndex;
    private FrontJobClass[] jobArray;
    private Lock theLock;

    public FrontJobMgrClass(UFrontClass fabric_object_val) {
        this.debugIt(false, "FrontJobMgrClass", "init start");

        this.frontEndFabricObject = fabric_object_val;
        this.theLock = new ReentrantLock();

        this.nextAvailableJobId = 0;
        this.setMaxAllowedJobId(FabricFrontEndProtocolClass.AJAX_MAPING_ID_SIZE);

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
        String ajax_id_str = EncodeNumberClass.encodeNumber(this.nextAvailableJobId, FabricFrontEndProtocolClass.AJAX_MAPING_ID_SIZE);
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
            this.abendIt("doGetJobObject", "not found" + ajax_id_str_val);
            return null;
        }

        FrontJobClass element = this.jobArray[index];
        this.jobArray[index] = null;
        return element;
    }

    private void debugIt(Boolean on_off_val, String str0_val, String str1_val) { if (on_off_val) this.logitIt(str0_val, str1_val); }
    private void logitIt(String str0_val, String str1_val) { AbendClass.phwangLogit(this.objectName() + "." + str0_val + "()", str1_val); }
    public void abendIt(String str0_val, String str1_val) { AbendClass.phwangAbend(this.objectName() + "." + str0_val + "()", str1_val); }
}
