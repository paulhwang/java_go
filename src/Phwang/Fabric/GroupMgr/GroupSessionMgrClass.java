/*
 ******************************************************************************
 *                                       
 *  Copyright (c) 2018 phwang. All rights reserved.
 *
 ******************************************************************************
 */

package Phwang.Fabric.GroupMgr;

import Phwang.Utils.AbendClass;
import Phwang.Utils.ArrayMgr.ArrayMgrClass;
import Phwang.Fabric.SessionMgr.SessionClass;

public class GroupSessionMgrClass {
    private String objectName() {return "GroupSessionMgrClass";}

    private static final int MAX_SESSION_ARRAY_SIZE = 10;

    private GroupClass groupObject;
    private ArrayMgrClass arrayMgrObject;

    public GroupSessionMgrClass(GroupClass group_object_val) {
        this.groupObject = group_object_val;
        this.arrayMgrObject = new ArrayMgrClass(this.objectName(), 'o', MAX_SESSION_ARRAY_SIZE);
    }

    public void InsertSession(SessionClass session_val) {
        this.arrayMgrObject.InsertObjectElement(session_val);
    }
    public void RemoveSession(SessionClass session_val) {
        this.arrayMgrObject.RemoveObjectElement(session_val);
    }

    public int GetSessionArraySize() {
        return this.arrayMgrObject.ArraySize();
    }

    public Object[] GetSessionArray() {
        return this.arrayMgrObject.ObjectArrayTable();
    }

    private void debugIt(Boolean on_off_val, String str0_val, String str1_val) {
        if (on_off_val)
            this.logitIt(str0_val, str1_val);
    }

    private void logitIt(String str0_val, String str1_val) {
        AbendClass.phwangLogit(this.objectName() + "." + str0_val + "()", str1_val);
    }

    public void abendIt(String str0_val, String str1_val) {
        AbendClass.phwangAbend(this.objectName() + "." + str0_val + "()", str1_val);
    }
}
