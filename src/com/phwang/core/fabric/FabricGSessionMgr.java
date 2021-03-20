/*
 ******************************************************************************
 *                                       
 *  Copyright (c) 2018 phwang. All rights reserved.
 *
 ******************************************************************************
 */

package com.phwang.core.fabric;

import com.phwang.core.utils.Abend;
import com.phwang.core.utils.ArrayMgrClass;
import com.phwang.core.utils.ListEntry;
import com.phwang.core.utils.ListMgr;

public class FabricGSessionMgr {
    protected static String objectName() {return "FabricGSessionMgr";}

    protected static final int FABRIC_G_SESSION_ID_SIZE_ = 4;
    protected static final int FABRIC_G_SESSION_ID_SIZE = FABRIC_G_SESSION_ID_SIZE_ * 2;

	private static final int G_SESSION_LIST_MGR_ARRAY_SIZE = 8;
    private static final int FIRST_G_SESSION_ID = 3000;
    
    private static final int MAX_SESSION_ARRAY_SIZE = 10;/////////////////////////

    private FabricGroup group_;
    private ListMgr listMgr_;
    private ArrayMgrClass arrayMgr_;

    protected ListMgr listMgr() { return this.listMgr_; }

    protected FabricGSessionMgr(FabricGroup group_val) {
        this.group_ = group_val;
        this.listMgr_ = new ListMgr(FABRIC_G_SESSION_ID_SIZE_, G_SESSION_LIST_MGR_ARRAY_SIZE, this.objectName(), FIRST_G_SESSION_ID);
        
        this.arrayMgr_ = new ArrayMgrClass(this.objectName(), 'o', MAX_SESSION_ARRAY_SIZE);//////////////////
    }

    protected void insertSession(FabricSession session_val) {
        this.arrayMgr_.insertObjectElement(session_val);
    }
    protected void removeSession(FabricSession session_val) {
        this.arrayMgr_.removeObjectElement(session_val);
    }

    protected int getSessionArraySize() {
        return this.arrayMgr_.ArraySize();
    }

    protected Object[] getSessionArray() {
        return this.arrayMgr_.ObjectArrayTable();
    }


    protected void bindSession(FabricSession session_val) {
    	this.listMgr_.malloc(session_val);
    }

    protected void unbindSession(FabricSession session_val) {
    	this.listMgr_.free(session_val.lListEntry());
    }

    protected FabricSession getSessionByIdStr(String session_id_str_val) {
    	ListEntry list_entry = this.listMgr_.getEntryByIdStr(session_id_str_val);
        if (list_entry == null) {
            return null;
        }
        return (FabricSession)list_entry.data();
    }
    
    
    private void debug(Boolean on_off, String s0, String s1) { if (on_off) this.log(s0, s1); }
    private void log(String s0, String s1) { Abend.log(this.objectName() + "." + s0 + "()", s1); }
    protected void abend(String s0, String s1) { Abend.abend(this.objectName() + "." + s0 + "()", s1); }
}
