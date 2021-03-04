/*
 ******************************************************************************
 *                                       
 *  Copyright (c) 2021 phwang. All rights reserved.
 *
 ******************************************************************************
 */

package phwang.fabric;

import phwang.utils.*;
import phwang.protocols.FabricFrontEndProtocolClass;

public class FabricNameList {
    private String objectName() {return "FabricNameList";}

    public static final int NAME_LIST_TAG_SIZE = 4;

    final int NAME_LIST_CLASS_MAX_NAME_LIST_TAG = 999;

    private FabricRootClass fabricRootObject;
    private int nameListTag;
    private String nameListTagStr;
    private String nameList;

    public String NameListTagStr() { return this.nameListTagStr; }
    public String NameList() { return this.nameList; }

    public FabricNameList(FabricRootClass root_fabric_object_val) {
        this.debug(false, "FabricNameList", "init start");
        
        this.fabricRootObject = root_fabric_object_val;
    }
    
    public void updateNameList() {
    	FabricLinkMgr link_list_mgr = this.fabricRootObject.linkMgrObject();

        int max_index = link_list_mgr.listMgr().MaxIndex();
        ListEntryClass[] list_entry_array = link_list_mgr.listMgr().EntryTableArray();

        this.nameListTag++;
        if (this.nameListTag > NAME_LIST_CLASS_MAX_NAME_LIST_TAG) {
            this.nameListTag = 1;
        }
        this.nameListTagStr = EncodeNumberClass.encodeNumber(this.nameListTag, FabricExport.NAME_LIST_TAG_SIZE);

        this.nameList = "";
        for (int i = max_index; i >= 0; i--) {
            if (list_entry_array[i] != null) {
                if (this.nameList.length() == 0) {
                    this.nameList = EncodeNumberClass.encodeNumber(this.nameListTag, FabricExport.NAME_LIST_TAG_SIZE);
                }
                else {
                    this.nameList = this.nameList + ",";
                }
                FabricLink link = (FabricLink) list_entry_array[i].data();
                this.nameList = this.nameList + '"' + link.myName() + '"';
                
                if (this.nameList.length() > 1000) {
                	return;/////////////////////////////////////for now
                }
            }
        }

        this.debug(false, "updateNameList", this.nameList);
    }

    public String getNameList(int tag_val) {
        if (this.nameListTag == tag_val) {
            return null;
        }
        return this.nameList;
    }
    
    private void debug(Boolean on_off, String s0, String s1) { if (on_off) this.log(s0, s1); }
    private void log(String s0, String s1) { AbendClass.log(this.objectName() + "." + s0 + "()", s1); }
    public void abend(String s0, String s1) { AbendClass.abend(this.objectName() + "." + s0 + "()", s1); }
}
