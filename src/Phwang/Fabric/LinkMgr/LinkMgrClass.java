/*
 ******************************************************************************
 *                                       
 *  Copyright (c) 2018 phwang. All rights reserved.
 *
 ******************************************************************************
 */

package Phwang.Fabric.LinkMgr;

import Phwang.Utils.AbendClass;
import Phwang.Utils.ListMgr.ListMgrInterface;
import Phwang.Utils.Encode.EncodeNumberClass;
import Phwang.Utils.ListMgr.ListMgrClass;
import Phwang.Utils.ListMgr.ListEntryClass;
import Phwang.Fabric.FabricRootClass;
import Phwang.Fabric.NameList.NameListClass;

public class LinkMgrClass implements ListMgrInterface {
    private String objectName() {return "LinkMgrClass";}

    private static final int FIRST_LINK_ID = 1000;

    private FabricRootClass fabricRootObject;
    private ListMgrClass listMgr;

    public ListMgrClass ListMgr() { return this.listMgr; }
    private NameListClass nameListObject() { return this.fabricRootObject.NameListObject(); }

    public LinkMgrClass(FabricRootClass root_fabric_object_val) {
        this.debugIt(false, "LinkMgrClass", "init start");
        
        this.fabricRootObject = root_fabric_object_val;
        this.listMgr = new ListMgrClass(this.objectName(), FIRST_LINK_ID);
    }

    public LinkClass MallocLink(String my_name_val) {
        LinkClass link = new LinkClass(my_name_val);
        ListEntryClass list_entry = this.listMgr.MallocEntry(link);
        link.BindListEntry(list_entry);
        this.nameListObject().UpdateNameList();
        return link;
    }

    public void FreeLink(LinkClass link_val) {

    }
    
    public LinkClass GetLinkByIdStr(String link_id_str_val) {
        int link_id = EncodeNumberClass.DecodeNumber(link_id_str_val);

        return this.GetLinkById(link_id);
    }

    public LinkClass GetLinkById(int id_val) {
        ListEntryClass list_entry = this.listMgr.GetEntryById(id_val);
        if (list_entry == null) {
            return null;
        }
        LinkClass link = (LinkClass) list_entry.Data();

        return link;
    }

    private Boolean CompareMyNameFunc(Object object_val, String my_name_val) {
        LinkClass link = (LinkClass) object_val;
        return (link.MyName().equals(my_name_val));
    }
    
    public Boolean CompareObjectFunc(Object obj_val, String str_val) {
    	return CompareMyNameFunc(obj_val, str_val);
    }

    public LinkClass GetLinkByMyName(String my_name_val) {
        ListEntryClass list_entry = this.listMgr.GetEntryByCompare(this, my_name_val);
        if (list_entry == null) {
            return null;
        }
        LinkClass link = (LinkClass)list_entry.Data();

        return link;
    }

    private void debugIt(Boolean on_off_val, String str0_val, String str1_val) { if (on_off_val) this.logitIt(str0_val, str1_val); }
    private void logitIt(String str0_val, String str1_val) { AbendClass.phwangLogit(this.objectName() + "." + str0_val + "()", str1_val); }
    public void abendIt(String str0_val, String str1_val) { AbendClass.phwangAbend(this.objectName() + "." + str0_val + "()", str1_val); }
}
