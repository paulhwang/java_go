/*
 ******************************************************************************
 *                                       
 *  Copyright (c) 2021 phwang. All rights reserved.
 *
 ******************************************************************************
 */

package com.phwang.core.theme;

import com.phwang.core.utils.*;

public class ThemeRoom implements ListEntryInt {
    private String objectName() {return "ThemeRoom";}

    private ListEntry listEntry_;
    private String groupIdStr_;
    private String baseIdStr_;

    public ListEntry listEntry() { return this.listEntry_; }
    private int roomId() { return this.listEntry().id(); }
    public String roomIdStr() { return this.listEntry().idStr(); }
    public String groupIdStr() { return this.groupIdStr_; }
    public String baseIdStr() { return this.baseIdStr_; }

    public ThemeRoom(String group_id_str_val) {
        this.debug(false, "ThemeRoom", "init start");
        
        this.groupIdStr_ = group_id_str_val;
    }

    public void bindListEntry(ListEntry list_entry_val, String who_val) {
        this.listEntry_ = list_entry_val;
    }

    public void unBindListEntry(String who_val) {
        this.listEntry_ = null;
    }

    public void setBaseIdStr(String base_id_str_val) {
        this.baseIdStr_ = base_id_str_val;
    }
    
    private void debug(Boolean on_off, String s0, String s1) { if (on_off) this.log(s0, s1); }
    private void log(String s0, String s1) { Abend.log(this.objectName() + "." + s0 + "()", s1); }
    public void abend(String s0, String s1) { Abend.abend(this.objectName() + "." + s0 + "()", s1); }
}
