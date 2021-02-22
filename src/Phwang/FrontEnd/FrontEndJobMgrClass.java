/*
 ******************************************************************************
 *                                       
 *  Copyright (c) 2018 phwang. All rights reserved.
 *
 ******************************************************************************
 */

package Phwang.FrontEnd;

import Phwang.Utils.AbendClass;
import Phwang.Utils.Encode.EncodeNumberClass;
import Phwang.Protocols.FabricFrontEndProtocolClass;

public class FrontEndJobMgrClass {
    private String objectName() {return "FrontEndJobMgrClass";}

    private static final int MAX_AJAX_ENTRY_ARRAY_SIZE = 1000;

    private UFrontClass frontEndFabricObject;
    private int nextAvailableJobId;
    private int maxAllowedJobId;
    private int maxJobArrayIndex;
    private FrontEndJobClass[] jobArray;

    public FrontEndJobMgrClass(UFrontClass fabric_object_val) {
        this.debugIt(false, "FrontEndJobMgrClass", "init start");

        this.frontEndFabricObject = fabric_object_val;

        this.nextAvailableJobId = 0;
        this.setMaxAllowedJobId(FabricFrontEndProtocolClass.AJAX_MAPING_ID_SIZE);

        this.maxJobArrayIndex = 0;
        this.jobArray = new FrontEndJobClass[MAX_AJAX_ENTRY_ARRAY_SIZE];
    }

    private void setMaxAllowedJobId(int ajax_id_size_val) {
        this.maxAllowedJobId = 1;
        for (var i = 0; i < ajax_id_size_val; i++) {
            this.maxAllowedJobId *= 10;
        }
        this.maxAllowedJobId -= 1;
    }

    public FrontEndJobClass MallocJobObject() {
        this.incrementNextAvailableJobId();
        String ajax_id_str = EncodeNumberClass.EncodeNumber(this.nextAvailableJobId, FabricFrontEndProtocolClass.AJAX_MAPING_ID_SIZE);
        FrontEndJobClass ajax_entry_object = new FrontEndJobClass(ajax_id_str);
        this.putJobObject(ajax_entry_object);
        return ajax_entry_object;
    }

    private void incrementNextAvailableJobId() {
        this.nextAvailableJobId++;
        if (this.nextAvailableJobId > this.maxAllowedJobId) {
            this.nextAvailableJobId = 1;
        }
    }

    public void putJobObject(FrontEndJobClass val) {
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

    public FrontEndJobClass GetJobObject(String ajax_id_str_val) {
        int index;

        var found = false;
        for (index = 0; index < this.maxJobArrayIndex; index++) {
            if (this.jobArray[index] != null) {
                if (this.jobArray[index].ajaxIdStr == ajax_id_str_val) {
                    found = true;
                    break;
                }
            }
        }

        if (!found) {
            this.abendIt("getAjaxEntryObject", "not found" + ajax_id_str_val);
            return null;
        }

        FrontEndJobClass element = this.jobArray[index];
        this.jobArray[index] = null;
        return element;
    }

    private void debugIt(Boolean on_off_val, String str0_val, String str1_val) { if (on_off_val) this.logitIt(str0_val, str1_val); }
    private void logitIt(String str0_val, String str1_val) { AbendClass.phwangLogit(this.objectName() + "." + str0_val + "()", str1_val); }
    public void abendIt(String str0_val, String str1_val) { AbendClass.phwangAbend(this.objectName() + "." + str0_val + "()", str1_val); }
}
