/*
 ******************************************************************************
 *                                       
 *  Copyright (c) 2021 phwang. All rights reserved.
 *
 ******************************************************************************
 */

package com.phwang.core.fabric;

import com.phwang.core.utils.ListEntry;
import com.phwang.core.utils.ListMgr;
import com.phwang.core.utils.ListMgrInt;

public class FabricLinkMgr implements ListMgrInt {
    private String objectName() {return "FabricLinkMgr";}

    protected static final int FABRIC_LINK_ID_SIZE_ = 4;
    protected static final int FABRIC_LINK_ID_SIZE = FABRIC_LINK_ID_SIZE_ * 2;
	
    private static final int LIST_MGR_ARRAY_SIZE = 256;
    private static final int FIRST_LINK_ID = 1000;

    private FabricRoot fabricRoot_;
    private ListMgr listMgr_;

    private FabricRoot fabricRoot() { return this.fabricRoot_; }
    protected ListMgr listMgr() { return this.listMgr_; }
    private FabricNameList nameList() { return this.fabricRoot().nameList(); }

    protected FabricLinkMgr(FabricRoot root_val) {
        this.debug(false, "FabricLinkMgr", "init start");
        
        this.fabricRoot_ = root_val;
        this.listMgr_ = new ListMgr(FABRIC_LINK_ID_SIZE_, LIST_MGR_ARRAY_SIZE, this.objectName(), FIRST_LINK_ID);
    }

    protected FabricLink mallocLink(char client_type_val, String my_name_val) {
    	FabricLink link = new FabricLink(client_type_val, my_name_val);
    	this.listMgr().malloc(link);
        
        this.nameList().updateNameList();
        return link;
    }

    protected void freeLink(FabricLink link_val) {
    	this.listMgr_.free(link_val.listEntry());
    }
    
    protected FabricLink getLinkByIdStr(String link_id_str_val) {
    	ListEntry list_entry = this.listMgr().getEntryByIdStr(link_id_str_val);
        if (list_entry == null) {
            return null;
        }
        return (FabricLink) list_entry.data();
    }

    private Boolean compareMyNameFunc(Object object_val, String my_name_val) {
    	FabricLink link = (FabricLink) object_val;
        return (link.myName().equals(my_name_val));
    }
    
    public Boolean compareObjectFunc(Object obj_val, String str_val) {
    	return compareMyNameFunc(obj_val, str_val);
    }

    protected FabricLink GetLinkByMyName(String my_name_val) {
    	ListEntry list_entry = this.listMgr().getEntryByCompare(this, my_name_val);
        if (list_entry == null) {
            return null;
        }
        FabricLink link = (FabricLink)list_entry.data();

        return link;
    }
    
    private void debug(Boolean on_off, String s0, String s1) { if (on_off) this.log(s0, s1); }
    private void log(String s0, String s1) { this.fabricRoot().logIt(this.objectName() + "." + s0 + "()", s1); }
    protected void abend(String s0, String s1) { this.fabricRoot().abendIt(this.objectName() + "." + s0 + "()", s1); }
}
