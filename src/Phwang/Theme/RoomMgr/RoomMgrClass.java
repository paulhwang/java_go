/*
 ******************************************************************************
 *                                       
 *  Copyright (c) 2018 phwang. All rights reserved.
 *
 ******************************************************************************
 */

package Phwang.Theme.RoomMgr;

import Phwang.Utils.AbendClass;
import Phwang.Utils.Encode.EncodeNumberClass;
import Phwang.Utils.ListMgr.ListMgrClass;
import Phwang.Utils.ListMgr.ListEntryClass;
import Phwang.Theme.ThemeRootClass;

public class RoomMgrClass {
    private String objectName() {return "RoomMgrClass";}

    private static final int FIRST_ROOM_ID = 7000;

    private ThemeRootClass themeRootObject;
    private ListMgrClass listMgr;

    public RoomMgrClass(ThemeRootClass theme_root_object_val) {
        this.debugIt(false, "RoomMgrClass", "init start");

        this.themeRootObject = theme_root_object_val;
        this.listMgr = new ListMgrClass(this.objectName(), FIRST_ROOM_ID);
    }
    
    public RoomClass MallocRoom(String group_id_str_val) {
        RoomClass room = new RoomClass(group_id_str_val);
        ListEntryClass list_entry = this.listMgr.MallocEntry(room);
        room.BindListEntry(list_entry);
        return room;
    }

    public RoomClass GetRoomByRoomIdStr(String room_id_str_val) {
        int room_id = EncodeNumberClass.DecodeNumber(room_id_str_val);

        return this.GetRoomByRoomId(room_id);
     }

    public RoomClass GetRoomByRoomId(int id_val) {
        ListEntryClass list_entry = this.listMgr.GetEntryById(id_val);
        if (list_entry == null) {
            return null;
        }
        RoomClass room_object = (RoomClass)list_entry.Data();

        return room_object;
    }

    private void debugIt(Boolean on_off_val, String str0_val, String str1_val) { if (on_off_val) this.logitIt(str0_val, str1_val); }
    private void logitIt(String str0_val, String str1_val) { AbendClass.phwangLogit(this.objectName() + "." + str0_val + "()", str1_val); }
    public void abendIt(String str0_val, String str1_val) { AbendClass.phwangAbend(this.objectName() + "." + str0_val + "()", str1_val); }
}
