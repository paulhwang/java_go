/*
 ******************************************************************************
 *                                       
 *  Copyright (c) 2018 phwang. All rights reserved.
 *
 ******************************************************************************
 */

package phwang.theme;

import phwang.fabric.FabricDefineClass;
import phwang.utils.*;

public class RoomMgrClass {
    private String objectName() {return "RoomMgrClass";}

    private static final int FIRST_ROOM_ID = 7000;

    private ThemeRootClass themeRootObject;
    private ListMgrClass listMgr;

    public RoomMgrClass(ThemeRootClass theme_root_object_val) {
        this.debug(false, "RoomMgrClass", "init start");

        this.themeRootObject = theme_root_object_val;
        this.listMgr = new ListMgrClass(ThemeDefineClass.THEME_ROOM_ID_SIZE, this.objectName(), FIRST_ROOM_ID);
    }
    
    public RoomClass mallocRoom(String group_id_str_val) {
        RoomClass room = new RoomClass(group_id_str_val);
        ListEntryClass list_entry = this.listMgr.mallocEntry(room);
        room.bindListEntry(list_entry);
        return room;
    }

    public RoomClass getRoomByRoomIdStr(String room_id_str_val) {
        this.debug(false, "getRoomByRoomIdStr", "room_id_str_val=" + room_id_str_val);
        int room_id = EncodeNumberClass.decodeNumber(room_id_str_val);

        return this.getRoomByRoomId(room_id);
     }

    public RoomClass getRoomByRoomId(int id_val) {
        this.debug(false, "getRoomByRoomId", "id_val=" + id_val);
        ListEntryClass list_entry = this.listMgr.getEntryById(id_val);
        if (list_entry == null) {
            return null;
        }
        RoomClass room_object = (RoomClass)list_entry.data();

        return room_object;
    }
    
    private void debug(Boolean on_off, String s0, String s1) { if (on_off) this.log(s0, s1); }
    private void log(String s0, String s1) { AbendClass.log(this.objectName() + "." + s0 + "()", s1); }
    public void abend(String s0, String s1) { AbendClass.abend(this.objectName() + "." + s0 + "()", s1); }
}
