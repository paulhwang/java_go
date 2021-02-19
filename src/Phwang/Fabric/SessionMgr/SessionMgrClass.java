/*
 ******************************************************************************
 *                                       
 *  Copyright (c) 2018 phwang. All rights reserved.
 *
 ******************************************************************************
 */

package Phwang.Fabric.SessionMgr;

import Phwang.Utils.ListMgr.ListEntryClass;
import Phwang.Fabric.LinkMgr.LinkClass;
import Phwang.Utils.AbendClass;
import Phwang.Utils.ListMgr.ListMgrClass;

public class SessionMgrClass {
    private String objectName() {return "SessionMgrClass";}
    private static final int FIRST_SESSION_ID = 3000;

    private LinkClass linkObject;
    private ListMgrClass listMgr;

    public ListMgrClass ListMgr() { return this.listMgr; }
    public int GetSessionArrayMaxIndex() { return this.listMgr.MaxIndex(); }
    public ListEntryClass[] GetSessionArrayEntryTable() { return this.listMgr.EntryTableArray(); }

    public SessionMgrClass(LinkClass link_object_val)
    {
        this.linkObject = link_object_val;
        this.listMgr = new ListMgrClass(this.objectName(), FIRST_SESSION_ID);
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
