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
    
	public static final int FABRIC_SESSION_ID_SIZE_ = 4;
	public static final int FABRIC_SESSION_ID_SIZE = FABRIC_SESSION_ID_SIZE_ * 2;

	private static final int LIST_MGR_ARRAY_SIZE = 8;
    private static final int FIRST_SESSION_ID = 3000;

    private FabricLink linkObject_;
    private ListMgrClass listMgr_;

    private FabricLink linkObject() { return this.linkObject_; }
    public ListMgrClass listMgr() { return this.listMgr_; }
    public int GetSessionArrayMaxIndex() { return this.listMgr_.MaxIndex(); }
    public ListEntryClass[] GetSessionArrayEntryTable() { return this.listMgr().EntryTableArray(); }

    public SessionMgrClass(FabricLink link_object_val) {
        this.debug(false, "SessionMgrClass", "init start");
        
        this.linkObject_ = link_object_val;
        this.listMgr_ = new ListMgrClass(FABRIC_SESSION_ID_SIZE_, LIST_MGR_ARRAY_SIZE, this.objectName(), FIRST_SESSION_ID);
    }

    public SessionClass mallocSession() {
        SessionClass session = new SessionClass(this.linkObject());
        ListEntryClass list_entry = this.listMgr().malloc(session);
        session.bindListEntry(list_entry);
        return session;
    }

    public SessionClass getSessionByIdStr(String session_id_str_val) {
        ListEntryClass list_entry = this.listMgr().getEntryByIdStr(session_id_str_val);
        if (list_entry == null) {
            return null;
        }
        return (SessionClass)list_entry.data();
    }
    
    private void debug(Boolean on_off, String s0, String s1) { if (on_off) this.log(s0, s1); }
    private void log(String s0, String s1) { AbendClass.log(this.objectName() + "." + s0 + "()", s1); }
    public void abend(String s0, String s1) { AbendClass.abend(this.objectName() + "." + s0 + "()", s1); }
}
