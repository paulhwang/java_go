/*
 ******************************************************************************
 *                                       
 *  Copyright (c) 2021 phwang. All rights reserved.
 *
 ******************************************************************************
 */

package com.phwang.core.fabric;

import com.phwang.core.utils.Abend;
import com.phwang.core.utils.ListEntry;
import com.phwang.core.utils.ListEntryInt;
import com.phwang.core.utils.ListQueue;

public class FabricSession implements ListEntryInt {
    private String objectName() {return "FabricSession";}

    private FabricLink link_;
    private ListEntry lListEntry_;
    private ListEntry gListEntry_;
    private String browserThemeIdStr_;
    private FabricGroup group_;
    private ListQueue pendingDownLinkDataQueue_;

    protected FabricLink link() { return this.link_; }
    protected ListEntry lListEntry() { return this.lListEntry_; }
    public int lSessionId() { return this.lListEntry_.id(); }
    protected String lSessionIdStr() { return this.lListEntry_.idStr(); }
    protected FabricGroup group() { return this.group_; }
    protected ListEntry gListEntry() { return this.gListEntry_; }
    public int gSessionId() { return this.gListEntry_.id(); }
    protected String gSessionIdStr() { return this.gListEntry_.idStr(); }
    protected String browserThemeIdStr() { return this.browserThemeIdStr_; }
    protected ListQueue pendingDownLinkDataQueue() { return this.pendingDownLinkDataQueue_; }

    protected FabricSession(FabricLink link_val) {
        this.link_ = link_val;
        this.pendingDownLinkDataQueue_ = new ListQueue(false, 0);
    }

    public void bindListEntry(ListEntry list_entry_val, String who_val) {
    	this.debug(true, "*********************bindListEntry", "who_val=" + who_val);
    	if (who_val.equals(FabricLSessionMgr.objectName())) {
    		this.lListEntry_ = list_entry_val;
    	}
    	else if (who_val.equals(FabricGSessionMgr.objectName())) {
    		this.gListEntry_ = list_entry_val;
    	}
    }

    public void unBindListEntry(String who_val) {
    	if (who_val.equals(FabricLSessionMgr.objectName())) {
    		this.lListEntry_ = null;
    	}
    	else if (who_val.equals(FabricGSessionMgr.objectName())) {
    		this.gListEntry_ = null;
    	}
    }

    protected void bindGroup(FabricGroup group_object_val) {
        this.group_ = group_object_val;
    }

    protected void setBrowserThemeIdStr(String str_val) {
        this.browserThemeIdStr_ = str_val;
    }

    protected void enqueuePendingDownLinkData(String data_val) {
        this.pendingDownLinkDataQueue().enqueue(data_val);
    }

    protected String getPendingDownLinkData() {
        return (String) this.pendingDownLinkDataQueue().dequeue();
    }

    protected int getPendingDownLinkDataCount() {
        return this.pendingDownLinkDataQueue().length();
    }
    
    private void debug(Boolean on_off, String s0, String s1) { if (on_off) this.log(s0, s1); }
    private void log(String s0, String s1) { Abend.log(this.objectName() + "." + s0 + "()", s1); }
    protected void abend(String s0, String s1) { Abend.abend(this.objectName() + "." + s0 + "()", s1); }
}
