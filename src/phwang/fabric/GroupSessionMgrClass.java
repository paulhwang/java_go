/*
 ******************************************************************************
 *                                       
 *  Copyright (c) 2018 phwang. All rights reserved.
 *
 ******************************************************************************
 */

package phwang.fabric;

import phwang.utils.*;

public class GroupSessionMgrClass {
    private String objectName() {return "GroupSessionMgrClass";}

    private static final int MAX_SESSION_ARRAY_SIZE = 10;

    private FabricGroup groupObject;
    private ArrayMgrClass arrayMgrObject;

    public GroupSessionMgrClass(FabricGroup group_object_val) {
        this.groupObject = group_object_val;
        this.arrayMgrObject = new ArrayMgrClass(this.objectName(), 'o', MAX_SESSION_ARRAY_SIZE);
    }

    public void insertSession(FabricSession session_val) {
        this.arrayMgrObject.insertObjectElement(session_val);
    }
    public void removeSession(FabricSession session_val) {
        this.arrayMgrObject.removeObjectElement(session_val);
    }

    public int getSessionArraySize() {
        return this.arrayMgrObject.ArraySize();
    }

    public Object[] getSessionArray() {
        return this.arrayMgrObject.ObjectArrayTable();
    }
    
    private void debug(Boolean on_off, String s0, String s1) { if (on_off) this.log(s0, s1); }
    private void log(String s0, String s1) { AbendClass.log(this.objectName() + "." + s0 + "()", s1); }
    public void abend(String s0, String s1) { AbendClass.abend(this.objectName() + "." + s0 + "()", s1); }
}
