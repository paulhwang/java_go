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
import Phwang.Utils.ListMgr.ListEntryClass;
import Phwang.Protocols.FabricFrontEndProtocolClass;

public class RoomClass {
    private String objectName() {return "RoomClass";}

    private ListEntryClass listEntryObject;
    private String groupIdStr;
    private int roomId;
    private String roomIdStr;
    private String baseIdStr;

    public String RoomIdStr() { return this.roomIdStr; }
    public String GroupIdStr() { return this.groupIdStr; }
    public String BaseIdStr() { return this.baseIdStr; }

    public RoomClass(String group_id_str_val)
    {
        this.debugIt(true, "RoomClass", "init start");
        
        this.groupIdStr = group_id_str_val;
    }

    public void BindListEntry(ListEntryClass list_entry_objectg_val)
    {
        this.listEntryObject = list_entry_objectg_val;
        this.roomId = this.listEntryObject.Id();
        this.roomIdStr = EncodeNumberClass.EncodeNumber(this.roomId, FabricFrontEndProtocolClass.FABRIC_LINK_ID_SIZE);
    }

    public void PutBaseIdStr(String base_id_str_val)
    {
        this.baseIdStr = base_id_str_val;
    }

    private void debugIt(Boolean on_off_val, String str0_val, String str1_val)
    {
        if (on_off_val)
            this.logitIt(str0_val, str1_val);
    }

    private void logitIt(String str0_val, String str1_val)
    {
        AbendClass.phwangLogit(this.objectName() + "." + str0_val + "()", str1_val);
    }

    public void abendIt(String str0_val, String str1_val)
    {
        AbendClass.phwangAbend(this.objectName() + "." + str0_val + "()", str1_val);
    }
}
