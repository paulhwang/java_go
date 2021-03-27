/*
 ******************************************************************************
 *                                       
 *  Copyright (c) 2021 phwang. All rights reserved.
 *
 ******************************************************************************
 */

package com.phwang.core.fabric;

import com.phwang.core.utils.ListMgr;
import com.phwang.core.utils.ListEntry;

public class FabricGroupMgr {
    private String objectName() {return "FabricGroupMgr";}

    protected static final int FABRIC_GROUP_ID_SIZE_ = 4;
    protected static final int FABRIC_GROUP_ID_SIZE = FABRIC_GROUP_ID_SIZE_ * 2 + 2;
	
    private static final int LIST_MGR_ARRAY_SIZE = 128;
    private static final int FIRST_GROUP_ID = 5000;

    private FabricRoot fabricRoot_;
    private ListMgr listMgr_;

    private FabricRoot fabricRoot() { return this.fabricRoot_; }
    private ListMgr listMgr() { return this.listMgr_; }

    protected FabricGroupMgr(FabricRoot root_val) {
        this.debug(false, "FabricGroupMgr", "init start");
        
        this.fabricRoot_ = root_val;
        this.listMgr_ = new ListMgr(FABRIC_GROUP_ID_SIZE_, LIST_MGR_ARRAY_SIZE, this.objectName(), FIRST_GROUP_ID);
    }

    public FabricGroup mallocGroup(String theme_data_val) {
    	FabricGroup group = new FabricGroup(theme_data_val);
    	ListEntry list_entry = this.listMgr().malloc(group);
        return group;
    }

    public void freeGroup(FabricGroup group_val) {
    	this.listMgr_.free(group_val.listEntry());
    }

    public FabricGroup getGroupByIdStr(String group_id_str_val) {
    	ListEntry list_entry = this.listMgr().getEntryByIdStr(group_id_str_val);
        if (list_entry == null) {
            return null;
        }
        return (FabricGroup)list_entry.data();
    }

    public FabricLink GetLinkByMyName(String my_name_val) {
    	FabricLink link = null;

        return link;
    }
    
    private void debug(Boolean on_off, String s0, String s1) { if (on_off) this.log(s0, s1); }
    private void log(String s0, String s1) { this.fabricRoot().logIt(this.objectName() + "." + s0 + "()", s1); }
    public void abend(String s0, String s1) { this.fabricRoot().abendIt(this.objectName() + "." + s0 + "()", s1); }
}
