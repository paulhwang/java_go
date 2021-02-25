/*
 ******************************************************************************
 *                                       
 *  Copyright (c) 2018 phwang. All rights reserved.
 *
 ******************************************************************************
 */

package Phwang.Fabric.NameList;

import Phwang.Utils.AbendClass;
import Phwang.Utils.Encode.EncodeNumberClass;
import Phwang.Utils.ListMgr.ListEntryClass;
import Phwang.Protocols.FabricFrontEndProtocolClass;
import Phwang.Fabric.LinkMgr.LinkMgrClass;
import Phwang.Fabric.LinkMgr.LinkClass;
import Phwang.Fabric.FabricRootClass;

public class NameListClass {
    private String objectName() {return "NameListClass";}

    final int NAME_LIST_CLASS_NAME_LIST_TAG_SIZE = FabricFrontEndProtocolClass.WEB_FABRIC_PROTOCOL_NAME_LIST_TAG_SIZE;
    final int NAME_LIST_CLASS_MAX_NAME_LIST_TAG = 999;

    private FabricRootClass fabricRootObject;
    private int nameListTag;
    private String nameListTagStr;
    private String nameList;

    public String NameListTagStr() { return this.nameListTagStr; }
    public String NameList() { return this.nameList; }

    public NameListClass(FabricRootClass root_fabric_object_val) {
        this.debugIt(false, "NameListClass", "init start");
        
        this.fabricRootObject = root_fabric_object_val;
    }
    
    public void updateNameList() {
        LinkMgrClass link_list_mgr = this.fabricRootObject.LinkMgrObject();

        int max_index = link_list_mgr.ListMgr().MaxIndex();
        ListEntryClass[] list_entry_array = link_list_mgr.ListMgr().EntryTableArray();

        this.nameListTag++;
        if (this.nameListTag > NAME_LIST_CLASS_MAX_NAME_LIST_TAG) {
            this.nameListTag = 1;
        }
        this.nameListTagStr = EncodeNumberClass.encodeNumber(this.nameListTag, NAME_LIST_CLASS_NAME_LIST_TAG_SIZE);

        this.nameList = "";
        for (int i = max_index; i >= 0; i--) {
            if (list_entry_array[i] != null) {
                if (this.nameList.length() == 0) {
                    this.nameList = EncodeNumberClass.encodeNumber(this.nameListTag, NAME_LIST_CLASS_NAME_LIST_TAG_SIZE);
                }
                else {
                    this.nameList = this.nameList + ",";
                }
                LinkClass link = (LinkClass) list_entry_array[i].Data();
                this.nameList = this.nameList + '"' + link.MyName() + '"';
            }
        }

        this.debugIt(false, "updateNameList", this.nameList);
    }

    public String getNameList(int tag_val) {
        if (this.nameListTag == tag_val) {
            return null;
        }
        return this.nameList;
    }

    private void debugIt(Boolean on_off_val, String str0_val, String str1_val) { if (on_off_val) this.logitIt(str0_val, str1_val); }
    private void logitIt(String str0_val, String str1_val) { AbendClass.phwangLogit(this.objectName() + "." + str0_val + "()", str1_val); }
    public void abendIt(String str0_val, String str1_val) { AbendClass.phwangAbend(this.objectName() + "." + str0_val + "()", str1_val); }
}
