/*
 ******************************************************************************
 *                                       
 *  Copyright (c) 2021 phwang. All rights reserved.
 *
 ******************************************************************************
 */

package phwang.front;

import phwang.utils.ListEntry;

public class FrontJob {
    private String objectName() {return "FrontJob";}

    private ListEntry listEntry_;
    private String data_;
    private Thread pendingThread_;
    
    public int jobId() { return this.listEntry().id(); }
    public String jobIdStr() { return this.listEntry().idStr(); }
    public ListEntry listEntry() { return this.listEntry_; }

    public void bindListEntry(ListEntry list_entry_object_val) {
        this.listEntry_ = list_entry_object_val;
    }

    public String readData() {
        while (this.data_ == null) {
        	try {
                //this.debug(false, "readData", "***sleep");
                this.pendingThread_ = Thread.currentThread();
        		Thread.sleep(10000);
        	}
        	catch (InterruptedException e) {
        	}
            continue;
        }
        //this.debug(false, "readData", "theData=" + this.data_);
        return this.data_;
    }

    public void WriteData(String data_val) {
        this.data_ = data_val;
        if (this.pendingThread_ != null) {
        	this.pendingThread_.interrupt();
        }
    }
    
    //private void debug(Boolean on_off, String s0, String s1) { if (on_off) this.log(s0, s1); }
    //private void log(String s0, String s1) { this.frontRoot().logIt(this.objectName() + "." + s0 + "()", s1); }
    //public void abend(String s0, String s1) { this.frontRoot().abendIt(this.objectName() + "." + s0 + "()", s1); }
}
