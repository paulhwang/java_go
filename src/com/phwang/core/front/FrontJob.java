/*
 ******************************************************************************
 *                                       
 *  Copyright (c) 2021 phwang. All rights reserved.
 *
 ******************************************************************************
 */

package com.phwang.core.front;

import com.phwang.core.utils.ListEntry;
import com.phwang.core.utils.ListEntryInt;

public class FrontJob implements ListEntryInt {
    private String objectName() {return "FrontJob";}

    private ListEntry listEntry_;
    private String data_;
    private Thread pendingThread_;
    
    protected int jobId() { return this.listEntry().id(); }
    protected String jobIdStr() { return this.listEntry().idStr(); }
    protected ListEntry listEntry() { return this.listEntry_; }

    public void bindListEntry(ListEntry list_entry_val) {
        this.listEntry_ = list_entry_val;
    }

    public void unBindListEntry() {
        this.listEntry_ = null;
    }

    protected String readData() {
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

    protected void WriteData(String data_val) {
        this.data_ = data_val;
        if (this.pendingThread_ != null) {
        	this.pendingThread_.interrupt();
        }
    }
    
    //private void debug(Boolean on_off, String s0, String s1) { if (on_off) this.log(s0, s1); }
    //private void log(String s0, String s1) { this.frontRoot().logIt(this.objectName() + "." + s0 + "()", s1); }
    //public void abend(String s0, String s1) { this.frontRoot().abendIt(this.objectName() + "." + s0 + "()", s1); }
}
