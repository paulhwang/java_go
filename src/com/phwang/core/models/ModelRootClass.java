/*
 ******************************************************************************
 *                                       
 *  Copyright (c) 2018 phwang. All rights reserved.
 *
 ******************************************************************************
 */

package com.phwang.core.models;

import com.phwang.core.utils.*;

public class ModelRootClass {
    private String objectName() {return "ModelRootClass";}

    public ModelRootClass() {
        this.debug(false, "ModelRootClass", "init start");
	}
    
    private void debug(Boolean on_off, String s0, String s1) { if (on_off) this.log(s0, s1); }
    private void log(String s0, String s1) { Abend.log(this.objectName() + "." + s0 + "()", s1); }
    public void abend(String s0, String s1) { Abend.abend(this.objectName() + "." + s0 + "()", s1); }
}
