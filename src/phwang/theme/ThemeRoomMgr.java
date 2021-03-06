/*
 ******************************************************************************
 *                                       
 *  Copyright (c) 2021 phwang. All rights reserved.
 *
 ******************************************************************************
 */

package phwang.theme;

import phwang.utils.*;

public class ThemeRoomMgr {
    private String objectName() {return "ThemeRoomMgr";}

    public static final int THEME_ROOM_ID_SIZE_ = 4;
    public static final int THEME_ROOM_ID_SIZE = THEME_ROOM_ID_SIZE_ * 2;
    
    private static final int LIST_MGR_ARRAY_SIZE = 128;
    private static final int FIRST_ROOM_ID = 7000;

    private ThemeRoot themeRoot_;
    private ListMgrClass listMgr_;

    public ThemeRoomMgr(ThemeRoot theme_root_object_val) {
        this.debug(false, "ThemeRoomMgr", "init start");

        this.themeRoot_ = theme_root_object_val;
        this.listMgr_ = new ListMgrClass(THEME_ROOM_ID_SIZE_, LIST_MGR_ARRAY_SIZE, this.objectName(), FIRST_ROOM_ID);
    }
    
    public ThemeRoom mallocRoom(String group_id_str_val) {
    	ThemeRoom room = new ThemeRoom(group_id_str_val);
        ListEntryClass list_entry = this.listMgr_.malloc(room);
        room.bindListEntry(list_entry);
        return room;
    }

    public ThemeRoom getRoomByIdStr(String room_id_str_val) {
        ListEntryClass list_entry = this.listMgr_.getEntryByIdStr(room_id_str_val);
        if (list_entry == null) {
            return null;
        }
        return (ThemeRoom)list_entry.data();
    }
    
    private void debug(Boolean on_off, String s0, String s1) { if (on_off) this.log(s0, s1); }
    private void log(String s0, String s1) { Abend.log(this.objectName() + "." + s0 + "()", s1); }
    public void abend(String s0, String s1) { Abend.abend(this.objectName() + "." + s0 + "()", s1); }
}
