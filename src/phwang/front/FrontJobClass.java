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
        this.jobId = this.listEntryObject.id();
        this.jobIdStr = EncodeNumberClass.encodeNumber(this.jobId, FrontDefineClass.FRONT_JOB_ID_SIZE);
    }

    public String readData() {
        while (this.theData == null) {
        	try {
                this.debug(false, "readData", "***sleep");
                this.pendingThread = Thread.currentThread();
        		Thread.sleep(10000);
        	}
        	catch (InterruptedException e) {
        	}
            continue;
        }
        this.debug(false, "readData", "theData=" + this.theData);
        return this.theData;
    }

    public void WriteData(String data_val) {
        this.theData = data_val;
        if (this.pendingThread != null) {
        	this.pendingThread.interrupt();
        }
    }
    
    private void debug(Boolean on_off, String s0, String s1) { if (on_off) this.log(s0, s1); }
    private void log(String s0, String s1) { AbendClass.log(this.objectName() + "." + s0 + "()", s1); }
    public void abend(String s0, String s1) { AbendClass.abend(this.objectName() + "." + s0 + "()", s1); }
}
