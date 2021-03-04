/*
 ******************************************************************************
 *                                       
 *  Copyright (c) 2021 phwang. All rights reserved.
 *
 ******************************************************************************
 */

package phwang.fabric;

import phwang.utils.*;
import phwang.protocols.FabricThemeProtocolClass;

public class FabricGroup {
    private String objectName() {return "FabricGroup";}

    private ListEntryClass listEntryObject_;
    private String roomIdStr;
    private String themeData_;
    private GroupSessionMgrClass groupSessionMgrObject_;

    private ListEntryClass listEntryObject() { return this.listEntryObject_; }
    public String ThemeData() { return this.themeData_; }
    public int groupId() { return this.listEntryObject().id(); }
    public String GroupIdStr() { return this.listEntryObject().idStr(); }
    public String RoomIdStr() { return this.roomIdStr; }
    private GroupSessionMgrClass groupSessionMgrObject() { return this.groupSessionMgrObject_; }

    public FabricGroup(String theme_data_val) {
        this.debug(false, "FabricGroup", "init start");
        
        this.themeData_ = theme_data_val;
        this.groupSessionMgrObject_ = new GroupSessionMgrClass(this);
    }

    public void bindListEntry(ListEntryClass list_entry_object_val) {
        this.listEntryObject_ = list_entry_object_val;
    }

    public void insertSession(FabricSession session_val) {
        this.groupSessionMgrObject().insertSession(session_val);
    }
    
    public void removeSession(FabricSession session_val) {
        this.groupSessionMgrObject().removeSession(session_val);
    }

    public void setRoomIdStr(String room_id_str_val) {
        this.roomIdStr = room_id_str_val;
    }

    public int getSessionArraySize() {
        return this.groupSessionMgrObject().getSessionArraySize();
    }

    public Object[] getSessionArray() {
        return this.groupSessionMgrObject().getSessionArray();
    }
    
    private void debug(Boolean on_off, String s0, String s1) { if (on_off) this.log(s0, s1); }
    private void log(String s0, String s1) { AbendClass.log(this.objectName() + "." + s0 + "()", s1); }
    public void abend(String s0, String s1) { AbendClass.abend(this.objectName() + "." + s0 + "()", s1); }
}