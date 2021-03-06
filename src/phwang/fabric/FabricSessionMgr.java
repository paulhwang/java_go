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

    private FabricLink link_;
    private ListMgrClass listMgr_;

    private FabricLink link() { return this.link_; }
    public ListMgrClass listMgr() { return this.listMgr_; }
    public int getSessionArrayMaxIndex() { return this.listMgr_.MaxIndex(); }
    public ListEntryClass[] getSessionArrayEntryTable() { return this.listMgr().EntryTableArray(); }

    public FabricSessionMgr(FabricLink link_val) {
        this.debug(false, "FabricSessionMgr", "init start");
        
        this.link_ = link_val;
        this.listMgr_ = new ListMgrClass(FABRIC_SESSION_ID_SIZE_, LIST_MGR_ARRAY_SIZE, this.objectName(), FIRST_SESSION_ID);
    }

    public FabricSession mallocSession() {
    	FabricSession session = new FabricSession(this.link());
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
    private void log(String s0, String s1) { Abend.log(this.objectName() + "." + s0 + "()", s1); }
    public void abend(String s0, String s1) { Abend.abend(this.objectName() + "." + s0 + "()", s1); }
}
