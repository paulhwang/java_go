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

public class FabricLSession implements ListEntryInt {
    private String objectName() {return "FabricLSession";}

    private FabricLink link_;
    private ListEntry listEntry_;
    private String browserThemeIdStr_;
    private FabricGroup fabricGroup_;
    private ListQueue pendingDownLinkDataQueue_;

    protected FabricLink link() { return this.link_; }
    protected ListEntry listEntry() { return this.listEntry_; }
    public int sessionId() { return this.listEntry().id(); }
    protected String sessionIdStr() { return this.listEntry().idStr(); }
    protected String browserThemeIdStr() { return this.browserThemeIdStr_; }
    protected FabricGroup fabricGroup() { return this.fabricGroup_; }
    protected ListQueue pendingDownLinkDataQueue() { return this.pendingDownLinkDataQueue_; }

    protected FabricLSession(FabricLink link_val) {
        this.link_ = link_val;
        this.pendingDownLinkDataQueue_ = new ListQueue(false, 0);
    }

    public void bindListEntry(ListEntry list_entry_object_val) {
        this.listEntry_ = list_entry_object_val;
    }

    public void unBindListEntry() {
        this.listEntry_ = null;
    }

    protected void bindGroup(FabricGroup group_object_val) {
        this.fabricGroup_ = group_object_val;
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
