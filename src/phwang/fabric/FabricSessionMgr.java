/*
 ******************************************************************************
 *                                       
 *  Copyright (c) 2021 phwang. All rights reserved.
 *
 ******************************************************************************
 */

package phwang.fabric;

import phwang.utils.*;

public class FabricSessionMgr {
    private String objectName() {return "FabricSessionMgr";}
    
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

    public FabricSessionMgr(FabricLink link_object_val) {
        this.debug(false, "FabricSessionMgr", "init start");
        
        this.linkObject_ = link_object_val;
        this.listMgr_ = new ListMgrClass(FABRIC_SESSION_ID_SIZE_, LIST_MGR_ARRAY_SIZE, this.objectName(), FIRST_SESSION_ID);
    }

    public FabricSession mallocSession() {
    	FabricSession session = new FabricSession(this.linkObject());
        ListEntryClass list_entry = this.listMgr().malloc(session);
        session.bindListEntry(list_entry);
        return session;
    }

    public FabricSession getSessionByIdStr(String session_id_str_val) {
        ListEntryClass list_entry = this.listMgr().getEntryByIdStr(session_id_str_val);
        if (list_entry == null) {
            return null;
        }
        return (FabricSession)list_entry.data();
    }
    
    private void debug(Boolean on_off, String s0, String s1) { if (on_off) this.log(s0, s1); }
    private void log(String s0, String s1) { AbendClass.log(this.objectName() + "." + s0 + "()", s1); }
    public void abend(String s0, String s1) { AbendClass.abend(this.objectName() + "." + s0 + "()", s1); }
}
