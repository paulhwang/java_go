/*
 ******************************************************************************
 *                                       
 *  Copyright (c) 2021 phwang. All rights reserved.
 *
 ******************************************************************************
 */

package com.phwang.core.fabric;

import com.phwang.core.utils.Abend;
import com.phwang.core.utils.ListMgr;
import com.phwang.core.utils.ListEntry;

public class FabricSessionMgr {
    private String objectName() {return "FabricSessionMgr";}
    
    protected static final int FABRIC_SESSION_ID_SIZE_ = 4;
    protected static final int FABRIC_SESSION_ID_SIZE = FABRIC_SESSION_ID_SIZE_ * 2;

	private static final int LIST_MGR_ARRAY_SIZE = 8;
    private static final int FIRST_SESSION_ID = 3000;

    private FabricLink link_;
    private ListMgr listMgr_;

    private FabricLink link() { return this.link_; }
    protected ListMgr listMgr() { return this.listMgr_; }
    protected int getSessionArrayMaxIndex() { return this.listMgr_.MaxIndex(); }
    protected ListEntry[] getSessionArrayEntryTable() { return this.listMgr().EntryTableArray(); }

    protected FabricSessionMgr(FabricLink link_val) {
        this.debug(false, "FabricSessionMgr", "init start");
        
        this.link_ = link_val;
        this.listMgr_ = new ListMgr(FABRIC_SESSION_ID_SIZE_, LIST_MGR_ARRAY_SIZE, this.objectName(), FIRST_SESSION_ID);
    }

    protected FabricSession mallocSession() {
    	FabricSession session = new FabricSession(this.link());
    	ListEntry list_entry = this.listMgr().malloc(session);
        return session;
    }

    protected void freeSession(FabricSession session_val) {
    	this.listMgr_.free(session_val.listEntry());
    }

    protected FabricSession getSessionByIdStr(String session_id_str_val) {
    	ListEntry list_entry = this.listMgr().getEntryByIdStr(session_id_str_val);
        if (list_entry == null) {
            return null;
        }
        return (FabricSession)list_entry.data();
    }
    
    private void debug(Boolean on_off, String s0, String s1) { if (on_off) this.log(s0, s1); }
    private void log(String s0, String s1) { Abend.log(this.objectName() + "." + s0 + "()", s1); }
    protected void abend(String s0, String s1) { Abend.abend(this.objectName() + "." + s0 + "()", s1); }
}
