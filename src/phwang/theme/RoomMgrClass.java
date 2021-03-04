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

    public static final int THEME_ROOM_ID_SIZE_ = 4;
    public static final int THEME_ROOM_ID_SIZE = THEME_ROOM_ID_SIZE_ * 2;
    
    private static final int LIST_MGR_ARRAY_SIZE = 128;
    private static final int FIRST_ROOM_ID = 7000;

    private ThemeRootClass themeRootObject;
    private ListMgrClass listMgr;

    public RoomMgrClass(ThemeRootClass theme_root_object_val) {
        this.debug(false, "RoomMgrClass", "init start");

        this.themeRootObject = theme_root_object_val;
        this.listMgr = new ListMgrClass(THEME_ROOM_ID_SIZE_, LIST_MGR_ARRAY_SIZE, this.objectName(), FIRST_ROOM_ID);
    }
    
    public RoomClass mallocRoom(String group_id_str_val) {
        RoomClass room = new RoomClass(group_id_str_val);
        ListEntryClass list_entry = this.listMgr.malloc(room);
        room.bindListEntry(list_entry);
        return room;
    }

    public RoomClass getRoomByIdStr(String room_id_str_val) {
        ListEntryClass list_entry = this.listMgr.getEntryByIdStr(room_id_str_val);
        if (list_entry == null) {
            return null;
        }
        return (RoomClass)list_entry.data();
    }
    
    private void debug(Boolean on_off, String s0, String s1) { if (on_off) this.log(s0, s1); }
    private void log(String s0, String s1) { AbendClass.log(this.objectName() + "." + s0 + "()", s1); }
    public void abend(String s0, String s1) { AbendClass.abend(this.objectName() + "." + s0 + "()", s1); }
}
