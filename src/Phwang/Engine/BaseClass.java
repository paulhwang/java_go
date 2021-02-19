/*
 ******************************************************************************
 *                                       
 *  Copyright (c) 2018 phwang. All rights reserved.
 *
 ******************************************************************************
 */

package Phwang.Engine;

import Phwang.Engine.Go.GoRootClass;
import Phwang.Utils.AbendClass;
import Phwang.Utils.ListMgr.ListEntryClass;

public class BaseClass {
    private String objectName() {return "BaseClass";}

    private ListEntryClass listEntryObject;
    private String roomIdStr;
    private int baseId;
    private String baseIdStr;
    private GoRootClass goRootObject;

    public String RoomIdStr() { return this.roomIdStr; }
    public String BaseIdStr() { return this.baseIdStr; }

    public BaseClass(String room_id_str_val)
    {
        this.roomIdStr = room_id_str_val;
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

    private void abendIt(String str0_val, String str1_val)
    {
        AbendClass.phwangAbend(this.objectName() + "." + str0_val + "()", str1_val);
    }
}
