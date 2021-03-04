/*
 ******************************************************************************
 *                                       
 *  Copyright (c) 2018 phwang. All rights reserved.
 *
 ******************************************************************************
 */

package phwang.fabric;

import phwang.utils.*;

public class SessionClass {
    private String objectName() {return "SessionClass";}

    private FabricLink linkObject_;
    private ListEntryClass listEntryObject_;
    private String browserThemeIdStr;
    private GroupClass groupObject;
    ListQueueClass pendingDownLinkDataQueue_;

    public FabricLink linkObject() { return this.linkObject_; }
    private ListEntryClass listEntryObject() { return this.listEntryObject_; }
    public int sessionId() { return this.listEntryObject().id(); }
    public String SessionIdStr() { return this.listEntryObject().idStr(); }
    public String BrowserThemeIdStr() { return this.browserThemeIdStr; }
    public GroupClass GroupObject() { return this.groupObject; }
    ListQueueClass pendingDownLinkDataQueue() { return this.pendingDownLinkDataQueue_; }

    public SessionClass(FabricLink link_object_val) {
        this.linkObject_ = link_object_val;
        this.pendingDownLinkDataQueue_ = new ListQueueClass(false, 0);
    }

    public void bindListEntry(ListEntryClass list_entry_object_val) {
        this.listEntryObject_ = list_entry_object_val;
    }

    public void bindGroup(GroupClass group_object_val) {
        this.groupObject = group_object_val;
    }

    public void setBrowserThemeIdStr(String str_val) {
        this.browserThemeIdStr = str_val;
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
    private void log(String s0, String s1) { AbendClass.log(this.objectName() + "." + s0 + "()", s1); }
    public void abend(String s0, String s1) { AbendClass.abend(this.objectName() + "." + s0 + "()", s1); }
}
