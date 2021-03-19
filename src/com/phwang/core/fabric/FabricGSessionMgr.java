/*
 ******************************************************************************
 *                                       
 *  Copyright (c) 2018 phwang. All rights reserved.
 *
 ******************************************************************************
 */

package com.phwang.core.fabric;

import com.phwang.core.utils.Abend;
import com.phwang.core.utils.ArrayMgrClass;

public class FabricGSessionMgr {
    private String objectName() {return "FabricGSessionMgr";}

    private static final int MAX_SESSION_ARRAY_SIZE = 10;

    private FabricGroup group_;
    private ArrayMgrClass arrayMgr_;

    protected FabricGSessionMgr(FabricGroup group_val) {
        this.group_ = group_val;
        this.arrayMgr_ = new ArrayMgrClass(this.objectName(), 'o', MAX_SESSION_ARRAY_SIZE);
    }

    protected void insertSession(FabricLSession session_val) {
        this.arrayMgr_.insertObjectElement(session_val);
    }
    protected void removeSession(FabricLSession session_val) {
        this.arrayMgr_.removeObjectElement(session_val);
    }

    protected int getSessionArraySize() {
        return this.arrayMgr_.ArraySize();
    }

    protected Object[] getSessionArray() {
        return this.arrayMgr_.ObjectArrayTable();
    }
    
    private void debug(Boolean on_off, String s0, String s1) { if (on_off) this.log(s0, s1); }
    private void log(String s0, String s1) { Abend.log(this.objectName() + "." + s0 + "()", s1); }
    protected void abend(String s0, String s1) { Abend.abend(this.objectName() + "." + s0 + "()", s1); }
}
