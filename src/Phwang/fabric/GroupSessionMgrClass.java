/*
 ******************************************************************************
 *                                       
 *  Copyright (c) 2018 phwang. All rights reserved.
 *
 ******************************************************************************
 */

package Phwang.fabric;

import Phwang.Utils.*;
import Phwang.Utils.ArrayMgr.ArrayMgrClass;

public class GroupSessionMgrClass {
    private String objectName() {return "GroupSessionMgrClass";}

    private static final int MAX_SESSION_ARRAY_SIZE = 10;

    private GroupClass groupObject;
    private ArrayMgrClass arrayMgrObject;

    public GroupSessionMgrClass(GroupClass group_object_val) {
        this.groupObject = group_object_val;
        this.arrayMgrObject = new ArrayMgrClass(this.objectName(), 'o', MAX_SESSION_ARRAY_SIZE);
    }

    public void insertSession(SessionClass session_val) {
        this.arrayMgrObject.insertObjectElement(session_val);
    }
    public void removeSession(SessionClass session_val) {
        this.arrayMgrObject.removeObjectElement(session_val);
    }

    public int getSessionArraySize() {
        return this.arrayMgrObject.ArraySize();
    }

    public Object[] getSessionArray() {
        return this.arrayMgrObject.ObjectArrayTable();
    }

    private void debugIt(Boolean on_off_val, String str0_val, String str1_val) { if (on_off_val) this.logitIt(str0_val, str1_val); }
    private void logitIt(String str0_val, String str1_val) { AbendClass.phwangLogit(this.objectName() + "." + str0_val + "()", str1_val); }
    public void abendIt(String str0_val, String str1_val) { AbendClass.phwangAbend(this.objectName() + "." + str0_val + "()", str1_val); }
}
