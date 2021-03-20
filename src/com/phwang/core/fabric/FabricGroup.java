/*
 ******************************************************************************
 *                                       
 *  Copyright (c) 2021 phwang. All rights reserved.
 *
 ******************************************************************************
 */

package com.phwang.core.fabric;

import com.phwang.core.utils.Abend;
import com.phwang.core.utils.ListEntry;
import com.phwang.core.utils.ListEntryInt;

public class FabricGroup implements ListEntryInt {
    private static String objectName() {return "FabricGroup";}

    private ListEntry listEntry_;
    private String roomIdStr_;
    private String themeData_;
    private FabricGSessionMgr gSessionMgr_;

    protected ListEntry listEntry() { return this.listEntry_; }
    protected String themeData() { return this.themeData_; }
    protected int groupId() { return this.listEntry().id(); }
    protected String groupIdStr() { return this.listEntry().idStr(); }
    protected String roomIdStr() { return this.roomIdStr_; }
    protected FabricGSessionMgr gSessionMgr() { return this.gSessionMgr_; }

    protected FabricGroup(String theme_data_val) {
        this.debug(false, "FabricGroup", "init start");
        
        this.themeData_ = theme_data_val;
        this.gSessionMgr_ = new FabricGSessionMgr(this);
    }

    public void bindListEntry(ListEntry list_entry_val, String who_val) {
        this.listEntry_ = list_entry_val;
    }

    public void unBindListEntry(String who_val) {
        this.listEntry_ = null;
    }

    protected void insertSession(FabricSession session_val) {
        this.gSessionMgr().insertSession(session_val);
    }
    
    protected void removeSession(FabricSession session_val) {
        this.gSessionMgr().removeSession(session_val);
    }

    protected void setRoomIdStr(String room_id_str_val) {
        this.roomIdStr_ = room_id_str_val;
    }

    protected int getSessionArraySize() {
        return this.gSessionMgr().getSessionArraySize();
    }

    protected Object[] getSessionArray() {
        return this.gSessionMgr().getSessionArray();
    }
    
    private void debug(Boolean on_off, String s0, String s1) { if (on_off) this.log(s0, s1); }
    private void log(String s0, String s1) { Abend.log(this.objectName() + "." + s0 + "()", s1); }
    protected void abend(String s0, String s1) { Abend.abend(this.objectName() + "." + s0 + "()", s1); }
}
