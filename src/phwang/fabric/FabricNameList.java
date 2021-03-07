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

    private FabricRoot fabricRoot_;
    private int nameListTag_;
    private String nameListTagStr_;
    private String nameList_;

    public FabricRoot fabricRoot() { return this.fabricRoot_; }
    public String nameListTagStr() { return this.nameListTagStr_; }
    public String nameList() { return this.nameList_; }

    public FabricNameList(FabricRoot root_val) {
        this.debug(false, "FabricNameList", "init start");
        
        this.fabricRoot_ = root_val;
    }
    
    public void updateNameList() {
    	FabricLinkMgr link_list_mgr = this.fabricRoot_.linkMgr();

        int max_index = link_list_mgr.listMgr().MaxIndex();
        ListEntry[] list_entry_array = link_list_mgr.listMgr().EntryTableArray();

        this.nameListTag_++;
        if (this.nameListTag_ > NAME_LIST_CLASS_MAX_NAME_LIST_TAG) {
            this.nameListTag_ = 1;
        }
        this.nameListTagStr_ = EncodeNumber.encodeNumber(this.nameListTag_, FabricExport.NAME_LIST_TAG_SIZE);

        this.nameList_ = "";
        for (int i = max_index; i >= 0; i--) {
            if (list_entry_array[i] != null) {
                if (this.nameList_.length() == 0) {
                    this.nameList_ = EncodeNumber.encodeNumber(this.nameListTag_, FabricExport.NAME_LIST_TAG_SIZE);
                }
                else {
                    this.nameList_ = this.nameList_ + ",";
                }
                FabricLink link = (FabricLink) list_entry_array[i].data();
                this.nameList_ = this.nameList_ + '"' + link.myName() + '"';
                
                if (this.nameList_.length() > 1000) {
                	return;/////////////////////////////////////for now
                }
            }
        }

        this.debug(false, "updateNameList", this.nameList_);
    }

    public String getNameList(int tag_val) {
        if (this.nameListTag_ == tag_val) {
            return null;
        }
        return this.nameList_;
    }
    
    private void debug(Boolean on_off, String s0, String s1) { if (on_off) this.log(s0, s1); }
    private void log(String s0, String s1) { this.fabricRoot().logIt(this.objectName() + "." + s0 + "()", s1); }
    public void abend(String s0, String s1) { this.fabricRoot().abendIt(this.objectName() + "." + s0 + "()", s1); }
}
