/*
 ******************************************************************************
 *                                       
 *  Copyright (c) 2018 phwang. All rights reserved.
 *
 ******************************************************************************
 */

package Phwang.Fabric.GroupMgr;

import Phwang.Utils.ListMgr.ListEntryClass;

public class GroupClass {
    private String objectName() {return "GroupClass";}
    private static final int GROUP_MGR_PROTOCOL_GROUP_ID_SIZE = 4;

    private ListEntryClass listEntryObject;
    private int groupId;
    private String groupIdStr;
    private String roomIdStr;
    private String themeData;
    private GroupSessionMgrClass groupSessionMgrObject;

    public String ThemeData() { return this.themeData; }
    public int GroupId() { return this.groupId; }
    public String GroupIdStr() { return this.groupIdStr; }
    public String RoomIdStr() { return this.roomIdStr; }

    public GroupClass(String theme_data_val)
    {
        this.themeData = theme_data_val;
        this.groupSessionMgrObject = new GroupSessionMgrClass(this);

    }

}
