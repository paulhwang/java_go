/*
 ******************************************************************************
 *                                       
 *  Copyright (c) 2018 phwang. All rights reserved.
 *
 ******************************************************************************
 */

package Phwang.Theme.RoomMgr;

import Phwang.Theme.ThemeRootClass;
import Phwang.Utils.ListMgr.ListMgrClass;
import Phwang.Utils.AbendClass;

public class RoomMgrClass {
    private String objectName() {return "RoomMgrClass";}

    private static final int FIRST_ROOM_ID = 7000;

    private ThemeRootClass themeRootObject;
    private ListMgrClass listMgr;

    public RoomMgrClass(ThemeRootClass theme_root_object_val)
    {
        this.debugIt(true, "RoomMgrClass", "init start");

        this.themeRootObject = theme_root_object_val;
        this.listMgr = new ListMgrClass(this.objectName(), FIRST_ROOM_ID);
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
