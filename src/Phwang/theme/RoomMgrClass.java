/*
 ******************************************************************************
 *                                       
 *  Copyright (c) 2018 phwang. All rights reserved.
 *
 ******************************************************************************
 */

package Phwang.theme;

import Phwang.Utils.AbendClass;
import Phwang.Utils.*;

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
    
    public RoomClass mallocRoom(String group_id_str_val) {
        RoomClass room = new RoomClass(group_id_str_val);
        ListEntryClass list_entry = this.listMgr.mallocEntry(room);
        room.bindListEntry(list_entry);
        return room;
    }

    public RoomClass getRoomByRoomIdStr(String room_id_str_val) {
        this.debugIt(false, "getRoomByRoomIdStr", "room_id_str_val=" + room_id_str_val);
        int room_id = EncodeNumberClass.decodeNumber(room_id_str_val);

        return this.getRoomByRoomId(room_id);
     }

    public RoomClass getRoomByRoomId(int id_val) {
        this.debugIt(false, "getRoomByRoomId", "id_val=" + id_val);
        ListEntryClass list_entry = this.listMgr.getEntryById(id_val);
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
