/*
 ******************************************************************************
 *                                       
 *  Copyright (c) 2018 phwang. All rights reserved.
 *
 ******************************************************************************
 */

package Phwang.Fabric.GroupMgr;

import Phwang.Utils.AbendClass;
import Phwang.Utils.Encode.EncodeNumberClass;
import Phwang.Utils.ListMgr.ListEntryClass;
import Phwang.Fabric.SessionMgr.SessionClass;

public class GroupClass {
    private String objectName() {return "GroupClass";}
    private static final int GROUP_MGR_PROTOCOL_GROUP_ID_SIZE = 4;

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
        this.groupId = this.listEntryObject.Id();
        this.groupIdStr = EncodeNumberClass.EncodeNumber(this.groupId, GROUP_MGR_PROTOCOL_GROUP_ID_SIZE);
    }

    public void InsertSession(SessionClass session_val) {
        this.groupSessionMgrObject.InsertSession(session_val);
    }
    
    public void removeSession(SessionClass session_val) {
        this.groupSessionMgrObject.RemoveSession(session_val);
    }

    public void setRoomIdStr(String room_id_str_val) {
        this.roomIdStr = room_id_str_val;
    }

    public int getSessionArraySize() {
        return this.groupSessionMgrObject.GetSessionArraySize();
    }

    public Object[] getSessionArray() {
        return this.groupSessionMgrObject.GetSessionArray();
    }

    private void debugIt(Boolean on_off_val, String str0_val, String str1_val) { if (on_off_val) this.logitIt(str0_val, str1_val); }
    private void logitIt(String str0_val, String str1_val) { AbendClass.phwangLogit(this.objectName() + "." + str0_val + "()", str1_val); }
    public void abendIt(String str0_val, String str1_val) { AbendClass.phwangAbend(this.objectName() + "." + str0_val + "()", str1_val); }
}
