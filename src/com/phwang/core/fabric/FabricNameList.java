/*
 ******************************************************************************
 *                                       
 *  Copyright (c) 2021 phwang. All rights reserved.
 *
 ******************************************************************************
 */

package com.phwang.core.fabric;

import com.phwang.core.utils.*;

public class FabricNameList {
    private String objectName() {return "FabricNameList";}

    protected static final int NAME_LIST_TAG_SIZE = 4;

    private final int NAME_LIST_CLASS_MAX_NAME_LIST_TAG = 999;

    private FabricRoot fabricRoot_;
    private int nameListTag_;
    private String nameListTagStr_;
    private String nameList_;

    protected FabricRoot fabricRoot() { return this.fabricRoot_; }
    protected String nameListTagStr() { return this.nameListTagStr_; }
    protected String nameList() { return this.nameList_; }

    protected FabricNameList(FabricRoot root_val) {
        this.debug(false, "FabricNameList", "init start");
        
        this.fabricRoot_ = root_val;
    }
    
    protected void updateNameList() {
    	FabricLinkMgr link_list_mgr = this.fabricRoot_.linkMgr();

        this.nameListTag_++;
        if (this.nameListTag_ > NAME_LIST_CLASS_MAX_NAME_LIST_TAG) {
            this.nameListTag_ = 1;
        }
        this.nameListTagStr_ = Encoders.iEncode111(this.nameListTag_, FabricExport.NAME_LIST_TAG_SIZE);

        this.nameList_ = "";
        
        int max_index = link_list_mgr.listMgr().maxIndex();
        ListEntry[] list_entry_array = link_list_mgr.listMgr().entryArray();
        for (int i = max_index; i >= 0; i--) {
            if (list_entry_array[i] != null) {
                if (this.nameList_.length() == 0) {
                    this.nameList_ = Encoders.iEncode111(this.nameListTag_, FabricExport.NAME_LIST_TAG_SIZE);
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

    protected String getNameList(int tag_val) {
        if (this.nameListTag_ == tag_val) {
            return null;
        }
        return this.nameList_;
    }
    
    private void debug(Boolean on_off, String s0, String s1) { if (on_off) this.log(s0, s1); }
    private void log(String s0, String s1) { this.fabricRoot().logIt(this.objectName() + "." + s0 + "()", s1); }
    protected void abend(String s0, String s1) { this.fabricRoot().abendIt(this.objectName() + "." + s0 + "()", s1); }
}
