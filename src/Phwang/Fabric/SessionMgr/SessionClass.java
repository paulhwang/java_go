/*
 ******************************************************************************
 *                                       
 *  Copyright (c) 2018 phwang. All rights reserved.
 *
 ******************************************************************************
 */

package Phwang.Fabric.SessionMgr;

import Phwang.Utils.AbendClass;
import Phwang.Utils.ListMgr.ListEntryClass;
import Phwang.Utils.Queue.ListQueueClass;
import Phwang.Fabric.LinkMgr.LinkClass;
import Phwang.Fabric.GroupMgr.GroupClass;

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

    public SessionClass(LinkClass link_object_val)
    {
        this.linkObject = link_object_val;
        this.pendingDownLinkDataQueue = new ListQueueClass(false, 0);
    }

    public void BindListEntry(ListEntryClass list_entry_objectg_val)
    {
        this.listEntryObject = list_entry_objectg_val;
        this.sessionId = this.listEntryObject.Id();
        //this.sessionIdStr = PhwangUtils.EncodeNumberClass.EncodeNumber(this.sessionId, Protocols.FabricFrontEndProtocolClass.FABRIC_SESSION_ID_SIZE);
    }

    public void BindGroup(GroupClass group_object_val)
    {
        this.groupObject = group_object_val;
    }

    public void SetBrowserThemeIdStr(String str_val)
    {
        this.browserThemeIdStr = str_val;
    }

    public void EnqueuePendingDownLinkData(String data_val)
    {
        //this.pendingDownLinkDataQueue.EnqueueData(data_val);
    }

    public String GetPendingDownLinkData()
    {
        return null;//**********************************temp for now
        //return (String) this.pendingDownLinkDataQueue.DequeueData();
    }

    public int GetPendingDownLinkDataCount()
    {
        return this.pendingDownLinkDataQueue.QueueLength();
    }

    private void debugIt(Boolean on_off_val, String str0_val, String str1_val)
    {
        if (on_off_val)
            this.logitIt(str0_val, str1_val);
    }

    private void logitIt(String str0_val, String str1_val)
    {
        AbendClass.phwangLogit(this.objectName() + "." + str0_val + "()", str1_val);
    }

    private void abendIt(String str0_val, String str1_val)
    {
        AbendClass.phwangAbend(this.objectName() + "." + str0_val + "()", str1_val);
    }
}
