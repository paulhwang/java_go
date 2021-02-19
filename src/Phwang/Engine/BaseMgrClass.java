/*
 ******************************************************************************
 *                                       
 *  Copyright (c) 2018 phwang. All rights reserved.
 *
 ******************************************************************************
 */

package Phwang.Engine;

import Phwang.Utils.AbendClass;

public class BaseMgrClass {
    private String objectName() {return "BaseMgrClass";}
    
    public BaseMgrClass(EngineRootClass engine_root_object_val)
    {
        this.debugIt(true, "BaseMgrClass", "init start");

        //this.engineRootObject = engine_root_object_val;
        //this.listMgr = new PhwangUtils.ListMgrClass(this.objectName, FIRST_BASE_ID);
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
