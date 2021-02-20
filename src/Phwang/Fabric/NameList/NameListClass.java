/*
 ******************************************************************************
 *                                       
 *  Copyright (c) 2018 phwang. All rights reserved.
 *
 ******************************************************************************
 */

package Phwang.Fabric.NameList;

import Phwang.Fabric.FabricRootClass;
import Phwang.Utils.AbendClass;

public class NameListClass {
    private String objectName() {return "NameListClass";}

    //final int NAME_LIST_CLASS_NAME_LIST_TAG_SIZE = Protocols.FabricFrontEndProtocolClass.WEB_FABRIC_PROTOCOL_NAME_LIST_TAG_SIZE;
    final int NAME_LIST_CLASS_MAX_NAME_LIST_TAG = 999;

    private FabricRootClass fabricRootObject;
    private int nameListTag;
    private String nameListTagStr;
    private String nameList;

    public String NameListTagStr() { return this.nameListTagStr; }
    public String NameList() { return this.nameList; }

    public NameListClass(FabricRootClass root_fabric_object_val)
    {
        this.debugIt(false, "NameListClass", "init start");
        
        this.fabricRootObject = root_fabric_object_val;
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
