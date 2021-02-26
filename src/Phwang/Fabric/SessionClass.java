/*
 ******************************************************************************
 *                                       
 *  Copyright (c) 2018 phwang. All rights reserved.
 *
 ******************************************************************************
 */

package Phwang.Fabric;

import Phwang.Utils.AbendClass;
import Phwang.Utils.ListMgr.ListEntryClass;
import Phwang.Utils.Queue.ListQueueClass;
import Phwang.Utils.*;
import Phwang.Protocols.FabricFrontEndProtocolClass;

public class SessionClass {
    private String objectName() {return "SessionClass";}

    private LinkClass linkObject;
    private ListEntryClass listEntryObject;
    private int sessionId;
    private String sessionIdStr;
    private String browserThemeIdStr;
    private GroupClass groupObject;
    ListQueueClass pendingDownLinkDataQueue;

    public LinkClass LinkObject() { return this.linkObject; }
    public int SessionId() { return this.sessionId; }
    public String SessionIdStr() { return this.sessionIdStr; }
    public String BrowserThemeIdStr() { return this.browserThemeIdStr; }
    public GroupClass GroupObject() { return this.groupObject; }
    ListQueueClass PendingDownLinkDataQueue() { return this.pendingDownLinkDataQueue; }

    public SessionClass(LinkClass link_object_val) {
        this.linkObject = link_object_val;
        this.pendingDownLinkDataQueue = new ListQueueClass(false, 0);
    }

    public void bindListEntry(ListEntryClass list_entry_objectg_val) {
        this.listEntryObject = list_entry_objectg_val;
        this.sessionId = this.listEntryObject.Id();
        this.sessionIdStr = EncodeNumberClass.encodeNumber(this.sessionId, FabricFrontEndProtocolClass.FABRIC_SESSION_ID_SIZE);
    }

    public void bindGroup(GroupClass group_object_val) {
        this.groupObject = group_object_val;
    }

    public void setBrowserThemeIdStr(String str_val) {
        this.browserThemeIdStr = str_val;
    }

    public void enqueuePendingDownLinkData(String data_val) {
        this.pendingDownLinkDataQueue.enqueueData(data_val);
    }

    public String getPendingDownLinkData() {
        return (String) this.pendingDownLinkDataQueue.dequeueData();
    }

    public int getPendingDownLinkDataCount() {
        return this.pendingDownLinkDataQueue.QueueLength();
    }

    private void debugIt(Boolean on_off_val, String str0_val, String str1_val) { if (on_off_val) this.logitIt(str0_val, str1_val); }
    private void logitIt(String str0_val, String str1_val) { AbendClass.phwangLogit(this.objectName() + "." + str0_val + "()", str1_val); }
    public void abendIt(String str0_val, String str1_val) { AbendClass.phwangAbend(this.objectName() + "." + str0_val + "()", str1_val); }
}
