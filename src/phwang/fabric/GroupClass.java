/*
 ******************************************************************************
 *                                       
 *  Copyright (c) 2018 phwang. All rights reserved.
 *
 ******************************************************************************
 */

package phwang.fabric;

import phwang.utils.*;
import phwang.protocols.FabricThemeProtocolClass;

public class GroupClass {
    private String objectName() {return "GroupClass";}

    private ListEntryClass listEntryObject;
    private int groupId;
    private String groupIdStr;
    private String roomIdStr;
    private String themeData_;
    private GroupSessionMgrClass groupSessionMgrObject_;

    public String ThemeData() { return this.themeData_; }
    public int GroupId() { return this.groupId; }
    public String GroupIdStr() { return this.groupIdStr; }
    public String RoomIdStr() { return this.roomIdStr; }
    private GroupSessionMgrClass groupSessionMgrObject() { return this.groupSessionMgrObject_; }

    public GroupClass(String theme_data_val) {
        this.debug(false, "GroupClass", "init start");
        
        this.themeData_ = theme_data_val;
        this.groupSessionMgrObject_ = new GroupSessionMgrClass(this);
    }

    public void bindListEntry(ListEntryClass list_entry_objectg_val) {
        this.listEntryObject = list_entry_objectg_val;
        this.groupId = this.listEntryObject.id();
        this.groupIdStr = EncodeNumberClass.encodeNumber(this.groupId, FabricDefineClass.FABRIC_GROUP_ID_SIZE);
    }

    public void insertSession(SessionClass session_val) {
        this.groupSessionMgrObject().insertSession(session_val);
    }
    
    public void removeSession(SessionClass session_val) {
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
