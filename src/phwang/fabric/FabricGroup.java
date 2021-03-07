/*
 ******************************************************************************
 *                                       
 *  Copyright (c) 2021 phwang. All rights reserved.
 *
 ******************************************************************************
 */

package phwang.fabric;

import phwang.utils.*;
import phwang.utils.ListEntryInt;
import phwang.protocols.FabricThemeProtocolClass;

public class FabricGroup implements ListEntryInt {
    private String objectName() {return "FabricGroup";}

    private ListEntry listEntry_;
    private String roomIdStr_;
    private String themeData_;
    private GroupSessionMgrClass groupSessionMgr_;

    public ListEntry listEntry() { return this.listEntry_; }
    public String themeData() { return this.themeData_; }
    public int groupId() { return this.listEntry().id(); }
    public String groupIdStr() { return this.listEntry().idStr(); }
    public String roomIdStr() { return this.roomIdStr_; }
    private GroupSessionMgrClass groupSessionMgr() { return this.groupSessionMgr_; }

    public FabricGroup(String theme_data_val) {
        this.debug(false, "FabricGroup", "init start");
        
        this.themeData_ = theme_data_val;
        this.groupSessionMgr_ = new GroupSessionMgrClass(this);
    }

    public void bindListEntry(ListEntry list_entry_object_val) {
        this.listEntry_ = list_entry_object_val;
    }

    public void insertSession(FabricSession session_val) {
        this.groupSessionMgr().insertSession(session_val);
    }
    
    public void removeSession(FabricSession session_val) {
        this.groupSessionMgr().removeSession(session_val);
    }

    public void setRoomIdStr(String room_id_str_val) {
        this.roomIdStr_ = room_id_str_val;
    }

    public int getSessionArraySize() {
        return this.groupSessionMgr().getSessionArraySize();
    }

    public Object[] getSessionArray() {
        return this.groupSessionMgr().getSessionArray();
    }
    
    private void debug(Boolean on_off, String s0, String s1) { if (on_off) this.log(s0, s1); }
    private void log(String s0, String s1) { Abend.log(this.objectName() + "." + s0 + "()", s1); }
    public void abend(String s0, String s1) { Abend.abend(this.objectName() + "." + s0 + "()", s1); }
}
