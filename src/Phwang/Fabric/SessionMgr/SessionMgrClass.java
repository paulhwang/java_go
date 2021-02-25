/*
 ******************************************************************************
 *                                       
 *  Copyright (c) 2018 phwang. All rights reserved.
 *
 ******************************************************************************
 */

package Phwang.Fabric.SessionMgr;

import Phwang.Utils.AbendClass;
import Phwang.Utils.Encode.EncodeNumberClass;
import Phwang.Utils.ListMgr.ListMgrClass;
import Phwang.Utils.ListMgr.ListEntryClass;
import Phwang.Fabric.LinkMgr.LinkClass;

public class SessionMgrClass {
    private String objectName() {return "SessionMgrClass";}
    private static final int FIRST_SESSION_ID = 3000;

    private LinkClass linkObject;
    private ListMgrClass listMgr;

    public ListMgrClass ListMgr() { return this.listMgr; }
    public int GetSessionArrayMaxIndex() { return this.listMgr.MaxIndex(); }
    public ListEntryClass[] GetSessionArrayEntryTable() { return this.listMgr.EntryTableArray(); }

    public SessionMgrClass(LinkClass link_object_val) {
        this.debugIt(false, "SessionMgrClass", "init start");
        
        this.linkObject = link_object_val;
        this.listMgr = new ListMgrClass(this.objectName(), FIRST_SESSION_ID);
    }

    public SessionClass mallocSession() {
        SessionClass session = new SessionClass(this.linkObject);
        ListEntryClass list_entry = this.listMgr.mallocEntry(session);
        session.bindListEntry(list_entry);
        return session;
    }

    public SessionClass getSessionByIdStr(String session_id_str_val) {
        int session_id = EncodeNumberClass.decodeNumber(session_id_str_val);

        return this.getSessionBySessionId(session_id);
    }
    
    public SessionClass getSessionBySessionIdStr(String session_id_str_val) {
        int session_id = EncodeNumberClass.decodeNumber(session_id_str_val);

        return this.getSessionBySessionId(session_id);
    }

    public SessionClass getSessionBySessionId(int id_val) {
        ListEntryClass list_entry = this.listMgr.getEntryById(id_val);
        if (list_entry == null) {
            return null;
        }
        SessionClass session = (SessionClass)list_entry.Data();

        return session;
    }

    private void debugIt(Boolean on_off_val, String str0_val, String str1_val) { if (on_off_val) this.logitIt(str0_val, str1_val); }
    private void logitIt(String str0_val, String str1_val) { AbendClass.phwangLogit(this.objectName() + "." + str0_val + "()", str1_val); }
    public void abendIt(String str0_val, String str1_val) { AbendClass.phwangAbend(this.objectName() + "." + str0_val + "()", str1_val); }
}
