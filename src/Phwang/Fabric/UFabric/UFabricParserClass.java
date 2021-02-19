/*
 ******************************************************************************
 *                                       
 *  Copyright (c) 2018 phwang. All rights reserved.
 *
 ******************************************************************************
 */

package Phwang.Fabric.UFabric;

import Phwang.Utils.AbendClass;
import Phwang.Fabric.FabricRootClass;
import Phwang.Fabric.GroupMgr.GroupMgrClass;

public class UFabricParserClass {
    private String objectName() {return "UFabricParserClass";}

    private UFabricClass uFabricObject;

    public FabricRootClass FabricRootObject() { return this.uFabricObject.FabricRootObject();}
    public GroupMgrClass GroupMgrObject() { return this.FabricRootObject().GroupMgrObject(); }

    public UFabricParserClass(UFabricClass ufabric_object_val)
    {
        this.uFabricObject = ufabric_object_val;
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
