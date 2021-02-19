/*
 ******************************************************************************
 *                                       
 *  Copyright (c) 2018 phwang. All rights reserved.
 *
 ******************************************************************************
 */

package Phwang.Fabric.GroupMgr;

import Phwang.Fabric.FabricRootClass;
import Phwang.Utils.AbendClass;
import Phwang.Utils.ListMgr.ListMgrClass;

public class GroupMgrClass {
    private String objectName() {return "GroupMgrClass";}

    private static final int FIRST_GROUP_ID = 5000;

    private FabricRootClass fabricRootObject;
    private ListMgrClass listMgr;

    public ListMgrClass ListMgr() { return this.listMgr; }

    public GroupMgrClass(FabricRootClass root_fabric_object_val)
    {
        this.debugIt(true, "GroupMgrClass", "init start");
        
        this.fabricRootObject = root_fabric_object_val;
        this.listMgr = new ListMgrClass(this.objectName(), FIRST_GROUP_ID);
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
