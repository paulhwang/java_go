/*
 ******************************************************************************
 *                                       
 *  Copyright (c) 2018 phwang. All rights reserved.
 *
 ******************************************************************************
 */

package Phwang.Fabric.LinkMgr;

import Phwang.Fabric.SessionMgr.SessionMgrClass;
import Phwang.Utils.AbendClass;
import Phwang.Utils.ListMgr.ListMgrClass;
import Phwang.Utils.ListMgr.ListEntryClass;
import Phwang.Utils.Queue.ListQueueClass;

public class LinkClass {
    private String objectName() {return "LinkClass";}

    //private ListEntryClass listEntryObject;
    private int theLinkId;
    private String theLinkIdStr;
    private String myName;
    private SessionMgrClass sessionMgrObject;
    private ListQueueClass pendingSessionSetupQueue;
    private ListQueueClass pendingSessionSetupQueue3;

    public String MyName() { return this.myName; }
    public int LinkId() { return this.theLinkId; }
    public String LinkIdStr() { return this.theLinkIdStr; }
    public SessionMgrClass SessionMgrObject() { return this.sessionMgrObject; }

    public int GetSessionArrayMaxIndex() { return this.sessionMgrObject.GetSessionArrayMaxIndex(); }
    public ListEntryClass[] GetSessionArrayEntryTable() { return this.sessionMgrObject.GetSessionArrayEntryTable(); }

    public LinkClass(String my_name_val)
    {
        this.myName = my_name_val;

        this.pendingSessionSetupQueue = new ListQueueClass(false, 0);
        this.pendingSessionSetupQueue3 = new ListQueueClass(false, 0);
        this.sessionMgrObject = new SessionMgrClass(this);
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
