/*
 ******************************************************************************
 *                                       
 *  Copyright (c) 2018 phwang. All rights reserved.
 *
 ******************************************************************************
 */

package phwang.fabric;

import phwang.utils.*;

public class GroupMgrClass {
    private String objectName() {return "GroupMgrClass";}

    private static final int FIRST_GROUP_ID = 5000;

    private FabricRootClass fabricRootObject_;
    private ListMgrClass listMgr_;

    private FabricRootClass fabricRootObject() { return this.fabricRootObject_; }
    private ListMgrClass listMgr() { return this.listMgr_; }

    public GroupMgrClass(FabricRootClass root_fabric_object_val) {
        this.debug(false, "GroupMgrClass", "init start");
        
        this.fabricRootObject_ = root_fabric_object_val;
        this.listMgr_ = new ListMgrClass(FabricDefineClass.FABRIC_GROUP_ID_SIZE, this.objectName(), FIRST_GROUP_ID);
    }

    public GroupClass mallocGroup(String theme_data_val) {
        GroupClass group = new GroupClass(theme_data_val);
        ListEntryClass list_entry = this.listMgr().malloc(group);
        group.bindListEntry(list_entry);
        return group;
    }

    public void freeLink(LinkClass link_val) {

    }

    public GroupClass getGroupByGroupIdStr(String group_id_str_val) {
        int group_id = EncodeNumberClass.decodeNumber(group_id_str_val);

        return this.GetGroupByGroupId(group_id);
    }

    public GroupClass GetGroupByGroupId(int group_id_val) {
        ListEntryClass list_entry = this.listMgr().getEntryById(group_id_val);
        if (list_entry == null)
        {
            return null;
        }
        GroupClass room_object = (GroupClass)list_entry.data();

        return room_object;
    }

    public LinkClass GetLinkById(int id_val) {
        ListEntryClass list_entry = this.listMgr().getEntryById(id_val);
        if (list_entry == null)
        {
            return null;
        }
        LinkClass link = (LinkClass)list_entry.data();

        return link;
    }

    public LinkClass GetLinkByMyName(String my_name_val) {
        LinkClass link = null;

        return link;
    }
    
    private void debug(Boolean on_off, String s0, String s1) { if (on_off) this.log(s0, s1); }
    private void log(String s0, String s1) { AbendClass.log(this.objectName() + "." + s0 + "()", s1); }
    public void abend(String s0, String s1) { AbendClass.abend(this.objectName() + "." + s0 + "()", s1); }
}
