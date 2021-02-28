/*
 ******************************************************************************
 *                                       
 *  Copyright (c) 2018 phwang. All rights reserved.
 *
 ******************************************************************************
 */

package phwang.fabric;

import phwang.utils.*;

public class SessionMgrClass {
    private String objectName() {return "SessionMgrClass";}
    private static final int FIRST_SESSION_ID = 3000;

    private LinkClass linkObject_;
    private ListMgrClass listMgr_;

    private LinkClass linkObject() { return this.linkObject_; }
    public ListMgrClass listMgr() { return this.listMgr_; }
    public int GetSessionArrayMaxIndex() { return this.listMgr_.MaxIndex(); }
    public ListEntryClass[] GetSessionArrayEntryTable() { return this.listMgr().EntryTableArray(); }

    public SessionMgrClass(LinkClass link_object_val) {
        this.debug(false, "SessionMgrClass", "init start");
        
        this.linkObject_ = link_object_val;
        this.listMgr_ = new ListMgrClass(this.objectName(), FIRST_SESSION_ID);
    }

    public SessionClass mallocSession() {
        SessionClass session = new SessionClass(this.linkObject());
        ListEntryClass list_entry = this.listMgr().mallocEntry(session);
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
        ListEntryClass list_entry = this.listMgr().getEntryById(id_val);
        if (list_entry == null) {
            return null;
        }
        SessionClass session = (SessionClass)list_entry.data();

        return session;
    }
    
    private void debug(Boolean on_off, String s0, String s1) { if (on_off) this.log(s0, s1); }
    private void log(String s0, String s1) { AbendClass.log(this.objectName() + "." + s0 + "()", s1); }
    public void abend(String s0, String s1) { AbendClass.abend(this.objectName() + "." + s0 + "()", s1); }
}
