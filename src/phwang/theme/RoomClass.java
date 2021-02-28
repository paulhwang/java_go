/*
 ******************************************************************************
 *                                       
 *  Copyright (c) 2018 phwang. All rights reserved.
 *
 ******************************************************************************
 */

package phwang.theme;

import phwang.utils.*;
import phwang.protocols.FabricThemeProtocolClass;

public class RoomClass {
    private String objectName() {return "RoomClass";}

    private ListEntryClass listEntryObject_;
    private String groupIdStr_;
    //private int roomId;
    private String roomIdStr;
    private String baseIdStr;

    private ListEntryClass listEntryObject() { return this.listEntryObject_; }
    private int roomId() { return this.listEntryObject().id(); }
    public String RoomIdStr() { return this.roomIdStr; }
    public String groupIdStr() { return this.groupIdStr_; }
    public String BaseIdStr() { return this.baseIdStr; }

    public RoomClass(String group_id_str_val) {
        this.debug(false, "RoomClass", "init start");
        
        this.groupIdStr_ = group_id_str_val;
    }

    public void bindListEntry(ListEntryClass list_entry_objectg_val) {
        this.listEntryObject_ = list_entry_objectg_val;
        this.roomIdStr = EncodeNumberClass.encodeNumber(this.roomId(), FabricThemeProtocolClass.FABRIC_LINK_ID_SIZE);
    }

    public void setBaseIdStr(String base_id_str_val) {
        this.baseIdStr = base_id_str_val;
    }
    
    private void debug(Boolean on_off, String s0, String s1) { if (on_off) this.log(s0, s1); }
    private void log(String s0, String s1) { AbendClass.log(this.objectName() + "." + s0 + "()", s1); }
    public void abend(String s0, String s1) { AbendClass.abend(this.objectName() + "." + s0 + "()", s1); }
}
