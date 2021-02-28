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

    private static final int FIRST_LINK_ID = 1000;

    private FabricRootClass fabricRootObject_;
    private ListMgrClass listMgr_;

    private FabricRootClass fabricRootObject() { return this.fabricRootObject_; }
    public ListMgrClass listMgr() { return this.listMgr_; }
    private NameListClass nameListObject() { return this.fabricRootObject().nameListObject(); }

    public LinkMgrClass(FabricRootClass root_fabric_object_val) {
        this.debug(false, "LinkMgrClass", "init start");
        
        this.fabricRootObject_ = root_fabric_object_val;
        this.listMgr_ = new ListMgrClass(FabricDefineClass.FABRIC_LINK_ID_SIZE, this.objectName(), FIRST_LINK_ID);
    }

    public LinkClass mallocLink(String my_name_val) {
        LinkClass link = new LinkClass(my_name_val);
        ListEntryClass list_entry = this.listMgr().malloc(link);
        link.bindListEntry(list_entry);
        this.nameListObject().updateNameList();
        return link;
    }

    public void freeLink(LinkClass link_val) {

    }
    
    public LinkClass getLinkByIdStr(String link_id_str_val) {
        int link_id = EncodeNumberClass.decodeNumber(link_id_str_val);

        return this.getLinkById(link_id);
    }

    public LinkClass getLinkById(int id_val) {
        ListEntryClass list_entry = this.listMgr().getEntryById(id_val);
        if (list_entry == null) {
            return null;
        }
        LinkClass link = (LinkClass) list_entry.data();

        return link;
    }

    private Boolean compareMyNameFunc(Object object_val, String my_name_val) {
        LinkClass link = (LinkClass) object_val;
        return (link.myName().equals(my_name_val));
    }
    
    public Boolean compareObjectFunc(Object obj_val, String str_val) {
    	return compareMyNameFunc(obj_val, str_val);
    }

    public LinkClass GetLinkByMyName(String my_name_val) {
        ListEntryClass list_entry = this.listMgr().getEntryByCompare(this, my_name_val);
        if (list_entry == null) {
            return null;
        }
        LinkClass link = (LinkClass)list_entry.data();

        return link;
    }
    
    private void debug(Boolean on_off, String s0, String s1) { if (on_off) this.log(s0, s1); }
    private void log(String s0, String s1) { AbendClass.log(this.objectName() + "." + s0 + "()", s1); }
    public void abend(String s0, String s1) { AbendClass.abend(this.objectName() + "." + s0 + "()", s1); }
}
