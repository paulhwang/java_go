/*
 ******************************************************************************
 *                                       
 *  Copyright (c) 2021 phwang. All rights reserved.
 *
 ******************************************************************************
 */

package phwang.theme;

import phwang.utils.*;
import phwang.protocols.FabricThemeProtocolClass;

public class ThemeRoom {
    private String objectName() {return "ThemeRoom";}

    private ListEntryClass listEntryObject_;
    private String groupIdStr_;
    private String baseIdStr_;

    private ListEntryClass listEntryObject() { return this.listEntryObject_; }
    private int roomId() { return this.listEntryObject().id(); }
    public String RoomIdStr() { return this.listEntryObject().idStr(); }
    public String groupIdStr() { return this.groupIdStr_; }
    public String baseIdStr() { return this.baseIdStr_; }

    public ThemeRoom(String group_id_str_val) {
        this.debug(false, "ThemeRoom", "init start");
        
        this.groupIdStr_ = group_id_str_val;
    }

    public void bindListEntry(ListEntryClass list_entry_object_val) {
        this.listEntryObject_ = list_entry_object_val;
    }

    public void setBaseIdStr(String base_id_str_val) {
        this.baseIdStr_ = base_id_str_val;
    }
    
    private void debug(Boolean on_off, String s0, String s1) { if (on_off) this.log(s0, s1); }
    private void log(String s0, String s1) { AbendClass.log(this.objectName() + "." + s0 + "()", s1); }
    public void abend(String s0, String s1) { AbendClass.abend(this.objectName() + "." + s0 + "()", s1); }
}