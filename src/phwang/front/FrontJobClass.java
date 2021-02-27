/*
 ******************************************************************************
 *                                       
 *  Copyright (c) 2018 phwang. All rights reserved.
 *
 ******************************************************************************
 */

package phwang.front;

import phwang.utils.*;
import phwang.protocols.FabricFrontEndProtocolClass;

public class FrontJobClass {
    private String objectName() {return "FrontJobClass";}

    private ListEntryClass listEntryObject;
    private int jobId;
    private String jobIdStr;
    
    public String ajaxIdStr;
    private String theData;
    private Thread pendingThread;

    public FrontJobClass(String ajax_id_str_val) {
        this.ajaxIdStr = ajax_id_str_val;
    }

    public void bindListEntry(ListEntryClass list_entry_objectg_val) {
        this.listEntryObject = list_entry_objectg_val;
        this.jobId = this.listEntryObject.Id();
        this.jobIdStr = EncodeNumberClass.encodeNumber(this.jobId, FrontDefineClass.FRONT_JOB_ID_SIZE);
    }

    public String readData() {
        while (this.theData == null) {
        	try {
                this.debugIt(false, "readData", "***sleep");
                this.pendingThread = Thread.currentThread();
        		Thread.sleep(10000);
        	}
        	catch (InterruptedException e) {
        	}
            continue;
        }
        this.debugIt(false, "readData", "theData=" + this.theData);
        return this.theData;
    }

    public void WriteData(String data_val) {
        this.theData = data_val;
        if (this.pendingThread != null) {
        	this.pendingThread.interrupt();
        }
    }

    private void debugIt(Boolean on_off_val, String str0_val, String str1_val) { if (on_off_val) this.logitIt(str0_val, str1_val); }
    private void logitIt(String str0_val, String str1_val) { AbendClass.phwangLogit(this.objectName() + "." + str0_val + "()", str1_val); }
    public void abendIt(String str0_val, String str1_val) { AbendClass.phwangAbend(this.objectName() + "." + str0_val + "()", str1_val); }
}
