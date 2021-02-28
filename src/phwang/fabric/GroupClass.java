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
    private String themeData;
    private GroupSessionMgrClass groupSessionMgrObject;

    public String ThemeData() { return this.themeData; }
    public int GroupId() { return this.groupId; }
    public String GroupIdStr() { return this.groupIdStr; }
    public String RoomIdStr() { return this.roomIdStr; }

    public GroupClass(String theme_data_val) {
        this.debugIt(false, "GroupClass", "init start");
        
        this.themeData = theme_data_val;
        this.groupSessionMgrObject = new GroupSessionMgrClass(this);
    }

    public void bindListEntry(ListEntryClass list_entry_objectg_val) {
        this.listEntryObject = list_entry_objectg_val;
        this.groupId = this.listEntryObject.id();
        this.groupIdStr = EncodeNumberClass.encodeNumber(this.groupId, FabricDefineClass.FABRIC_GROUP_ID_SIZE);
    }

    public void insertSession(SessionClass session_val) {
        this.groupSessionMgrObject.insertSession(session_val);
    }
    
    public void removeSession(SessionClass session_val) {
        this.groupSessionMgrObject.removeSession(session_val);
    }

    public void setRoomIdStr(String room_id_str_val) {
        this.roomIdStr = room_id_str_val;
    }

    public int getSessionArraySize() {
        return this.groupSessionMgrObject.getSessionArraySize();
    }

    public Object[] getSessionArray() {
        return this.groupSessionMgrObject.getSessionArray();
    }

    private void debugIt(Boolean on_off_val, String str0_val, String str1_val) { if (on_off_val) this.logitIt(str0_val, str1_val); }
    private void logitIt(String str0_val, String str1_val) { AbendClass.phwangLogit(this.objectName() + "." + str0_val + "()", str1_val); }
    public void abendIt(String str0_val, String str1_val) { AbendClass.phwangAbend(this.objectName() + "." + str0_val + "()", str1_val); }
}
