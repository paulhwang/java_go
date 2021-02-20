/*
 ******************************************************************************
 *                                       
 *  Copyright (c) 2018 phwang. All rights reserved.
 *
 ******************************************************************************
 */

package Phwang.Fabric.DFabric;

import Phwang.Utils.AbendClass;
import Phwang.Fabric.FabricRootClass;

public class DFabricResponseClass {
    private String objectName() {return "DFabricResponseClass";}
    
    private DFabricParserClass dFabricParserObject;
    private FabricRootClass fabricRootObject() { return this.dFabricParserObject.FabricRootObject(); }

    public DFabricResponseClass(DFabricParserClass dfabric_parser_object_val) {
        this.debugIt(false, "DFabricResponseClass", "init start");

        this.dFabricParserObject = dfabric_parser_object_val;
    }

    private void debugIt(Boolean on_off_val, String str0_val, String str1_val) {
        if (on_off_val)
            this.logitIt(str0_val, str1_val);
    }

    private void logitIt(String str0_val, String str1_val) {
        AbendClass.phwangLogit(this.objectName() + "." + str0_val + "()", str1_val);
    }

    public void abendIt(String str0_val, String str1_val) {
        AbendClass.phwangAbend(this.objectName() + "." + str0_val + "()", str1_val);
    }
}
