/*
 ******************************************************************************
 *                                       
 *  Copyright (c) 2021 phwang. All rights reserved.
 *
 ******************************************************************************
 */

package phwang.fabric;

import phwang.utils.*;

public class FabricSession implements ListEntryInt {
    private String objectName() {return "FabricSession";}

    private FabricLink link_;
    private ListEntry listEntry_;
    private String browserThemeIdStr_;
    private FabricGroup fabricGroup_;
    private ListQueue pendingDownLinkDataQueue_;

    public FabricLink link() { return this.link_; }
    public ListEntry listEntry() { return this.listEntry_; }
    public int sessionId() { return this.listEntry().id(); }
    public String sessionIdStr() { return this.listEntry().idStr(); }
    public String browserThemeIdStr() { return this.browserThemeIdStr_; }
    public FabricGroup fabricGroup() { return this.fabricGroup_; }
    public ListQueue pendingDownLinkDataQueue() { return this.pendingDownLinkDataQueue_; }

    public FabricSession(FabricLink link_val) {
        this.link_ = link_val;
        this.pendingDownLinkDataQueue_ = new ListQueue(false, 0);
    }

    public void bindListEntry(ListEntry list_entry_object_val) {
        this.listEntry_ = list_entry_object_val;
    }

    public void bindGroup(FabricGroup group_object_val) {
        this.fabricGroup_ = group_object_val;
    }

    public void setBrowserThemeIdStr(String str_val) {
        this.browserThemeIdStr_ = str_val;
    }

    public void enqueuePendingDownLinkData(String data_val) {
        this.pendingDownLinkDataQueue().enqueue(data_val);
    }

    public String getPendingDownLinkData() {
        return (String) this.pendingDownLinkDataQueue().dequeue();
    }

    public int getPendingDownLinkDataCount() {
        return this.pendingDownLinkDataQueue().length();
    }
    
    private void debug(Boolean on_off, String s0, String s1) { if (on_off) this.log(s0, s1); }
    private void log(String s0, String s1) { Abend.log(this.objectName() + "." + s0 + "()", s1); }
    public void abend(String s0, String s1) { Abend.abend(this.objectName() + "." + s0 + "()", s1); }
}
