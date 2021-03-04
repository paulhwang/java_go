/*
 ******************************************************************************
 *                                       
 *  Copyright (c) 2018 phwang. All rights reserved.
 *
 ******************************************************************************
 */

package phwang.fabric;

import phwang.utils.*;

public class LinkMgrClass implements ListMgrInterface {
    private String objectName() {return "LinkMgrClass";}

	public static final int FABRIC_LINK_ID_SIZE_ = 4;
	public static final int FABRIC_LINK_ID_SIZE = FABRIC_LINK_ID_SIZE_ * 2;
	
    private static final int LIST_MGR_ARRAY_SIZE = 256;
    private static final int FIRST_LINK_ID = 1000;

    private FabricRootClass fabricRootObject_;
    private ListMgrClass listMgr_;

    private FabricRootClass fabricRootObject() { return this.fabricRootObject_; }
    public ListMgrClass listMgr() { return this.listMgr_; }
    private NameListClass nameListObject() { return this.fabricRootObject().nameListObject(); }

    public LinkMgrClass(FabricRootClass root_fabric_object_val) {
        this.debug(false, "LinkMgrClass", "init start");
        
        this.fabricRootObject_ = root_fabric_object_val;
        this.listMgr_ = new ListMgrClass(FABRIC_LINK_ID_SIZE_, LIST_MGR_ARRAY_SIZE, this.objectName(), FIRST_LINK_ID);
    }

    public FabricLink mallocLink(String my_name_val) {
    	FabricLink link = new FabricLink(my_name_val);
        ListEntryClass list_entry = this.listMgr().malloc(link);
        link.bindListEntry(list_entry);
        this.nameListObject().updateNameList();
        return link;
    }

    public void freeLink(FabricLink link_val) {

    }
    
    public FabricLink getLinkByIdStr(String link_id_str_val) {
        ListEntryClass list_entry = this.listMgr().getEntryByIdStr(link_id_str_val);
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

    public FabricLink GetLinkByMyName(String my_name_val) {
        ListEntryClass list_entry = this.listMgr().getEntryByCompare(this, my_name_val);
        if (list_entry == null) {
            return null;
        }
        FabricLink link = (FabricLink)list_entry.data();

        return link;
    }
    
    private void debug(Boolean on_off, String s0, String s1) { if (on_off) this.log(s0, s1); }
    private void log(String s0, String s1) { AbendClass.log(this.objectName() + "." + s0 + "()", s1); }
    public void abend(String s0, String s1) { AbendClass.abend(this.objectName() + "." + s0 + "()", s1); }
}
