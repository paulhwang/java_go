/*
 ******************************************************************************
 *                                       
 *  Copyright (c) 2021 phwang. All rights reserved.
 *
 ******************************************************************************
 */

package phwang.front;

import phwang.utils.*;

public class FrontJob {
    private String objectName() {return "FrontJob";}

    private ListEntryClass listEntryObject_;
    
    private String theData;
    private Thread pendingThread;
    
    public int jobId() { return this.listEntryObject().id(); }
    public String jobIdStr() { return this.listEntryObject().idStr(); }
    private ListEntryClass listEntryObject() { return this.listEntryObject_; }

    public void bindListEntry(ListEntryClass list_entry_object_val) {
        this.listEntryObject_ = list_entry_object_val;
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
