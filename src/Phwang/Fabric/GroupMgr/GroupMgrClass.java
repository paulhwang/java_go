/*
 ******************************************************************************
 *                                       
 *  Copyright (c) 2018 phwang. All rights reserved.
 *
 ******************************************************************************
 */

package Phwang.Fabric.GroupMgr;

import Phwang.Utils.AbendClass;
import Phwang.Utils.ListMgr.ListMgrClass;
import Phwang.Utils.ListMgr.ListEntryClass;
import Phwang.Utils.Encode.EncodeNumberClass;
import Phwang.Fabric.LinkMgr.LinkClass;
import Phwang.Fabric.FabricRootClass;

public class GroupMgrClass {
    private String objectName() {return "GroupMgrClass";}

    private static final int FIRST_GROUP_ID = 5000;

    private FabricRootClass fabricRootObject;
    private ListMgrClass listMgr;

    public ListMgrClass ListMgr() { return this.listMgr; }

    public GroupMgrClass(FabricRootClass root_fabric_object_val) {
        this.debugIt(false, "GroupMgrClass", "init start");
        
        this.fabricRootObject = root_fabric_object_val;
        this.listMgr = new ListMgrClass(this.objectName(), FIRST_GROUP_ID);
    }

    public GroupClass mallocGroup(String theme_data_val) {
        GroupClass group = new GroupClass(theme_data_val);
        ListEntryClass list_entry = this.listMgr.MallocEntry(group);
        group.bindListEntry(list_entry);
        return group;
    }

    public void freeLink(LinkClass link_val) {

    }

    public GroupClass getGroupByGroupIdStr(String group_id_str_val) {
        int group_id = EncodeNumberClass.DecodeNumber(group_id_str_val);

        return this.GetGroupByGroupId(group_id);
    }

    public GroupClass GetGroupByGroupId(int group_id_val) {
        ListEntryClass list_entry = this.listMgr.GetEntryById(group_id_val);
        if (list_entry == null)
        {
            return null;
        }
        GroupClass room_object = (GroupClass)list_entry.Data();

        return room_object;
    }

    public LinkClass GetLinkById(int id_val) {
        ListEntryClass list_entry = this.listMgr.GetEntryById(id_val);
        if (list_entry == null)
        {
            return null;
        }
        LinkClass link = (LinkClass)list_entry.Data();

        return link;
    }

    public LinkClass GetLinkByMyName(String my_name_val) {
        LinkClass link = null;

        return link;
    }

    private void debugIt(Boolean on_off_val, String str0_val, String str1_val) { if (on_off_val) this.logitIt(str0_val, str1_val); }
    private void logitIt(String str0_val, String str1_val) { AbendClass.phwangLogit(this.objectName() + "." + str0_val + "()", str1_val); }
    public void abendIt(String str0_val, String str1_val) { AbendClass.phwangAbend(this.objectName() + "." + str0_val + "()", str1_val); }
}
