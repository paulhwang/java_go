/*
 ******************************************************************************
 *                                       
 *  Copyright (c) 2018 phwang. All rights reserved.
 *
 ******************************************************************************
 */

package com.phwang.core.root;

import com.phwang.core.utils.Abend;
import com.phwang.core.engine.EngineRoot;
import com.phwang.core.fabric.FabricRoot;
import com.phwang.core.models.ModelRootClass;
import com.phwang.core.theme.ThemeRoot;

public class CoreRoot {
    private String objectName() {return "CoreRoot";}
    
    private FabricRoot fabricRoot_;
    private ThemeRoot themeRoot_;
    private EngineRoot engineRoot_;
    private ModelRootClass modelRoot_;
    
    protected FabricRoot fabricRoo() { return this.fabricRoot_; }
    protected ThemeRoot themeRoot() { return this.themeRoot_; }
    protected EngineRoot engineRoot() {return this.engineRoot_; }
    protected ModelRootClass modelRoot() { return this.modelRoot_; }

    public CoreRoot() {
        this.debug(false, "CoreRoot", "init start");
    	this.fabricRoot_ = new FabricRoot();
    	this.themeRoot_ = new ThemeRoot();
    	this.engineRoot_ = new EngineRoot();
    	this.modelRoot_ = new ModelRootClass();
	}
    
    private void debug(Boolean on_off, String s0, String s1) { if (on_off) this.log(s0, s1); }
    private void log(String s0, String s1) { Abend.log(this.objectName() + "." + s0 + "()", s1); }
    protected void abend(String s0, String s1) { Abend.abend(this.objectName() + "." + s0 + "()", s1); }
}

