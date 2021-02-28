/*
 ******************************************************************************
 *                                       
 *  Copyright (c) 2018 phwang. All rights reserved.
 *
 ******************************************************************************
 */

package phwang.fabric;

import phwang.utils.*;

public class DFabricResponseClass {
    private String objectName() {return "DFabricResponseClass";}
    
    private DFabricParserClass dFabricParserObject;
    private FabricRootClass fabricRootObject() { return this.dFabricParserObject.fabricRootObject(); }

    public DFabricResponseClass(DFabricParserClass dfabric_parser_object_val) {
        this.debug(false, "DFabricResponseClass", "init start");

        this.dFabricParserObject = dfabric_parser_object_val;
    }
    
    private void debug(Boolean on_off, String s0, String s1) { if (on_off) this.log(s0, s1); }
    private void log(String s0, String s1) { AbendClass.log(this.objectName() + "." + s0 + "()", s1); }
    public void abend(String s0, String s1) { AbendClass.abend(this.objectName() + "." + s0 + "()", s1); }
}
