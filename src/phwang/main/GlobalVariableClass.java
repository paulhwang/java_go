/*
 ******************************************************************************
 *                                       
 *  Copyright (c) 2018 phwang. All rights reserved.
 *
 ******************************************************************************
 */

package phwang.main;

import phwang.utils.AbendClass;
import phwang.engine.EngineRoot;
import phwang.fabric.FabricRoot;
import phwang.front.FrontRoot;
import phwang.android.AndroidRoot;
import phwang.models.ModelRootClass;
import phwang.theme.ThemeRoot;

public class GlobalVariableClass {
    private String objectName() {return "GlobalVariableClass";}
    
    private FrontRoot frontRoot_;
    private FabricRoot fabricRoot_;
    private ThemeRoot themeRoot_;
    private EngineRoot engineRoot_;
    private AndroidRoot androidRoot_;
    private ModelRootClass modelRoot_;

    public GlobalVariableClass() {
        this.debug(false, "EngineRootClass", "init start");
    	this.fabricRoot_ = new FabricRoot();
    	this.frontRoot_ = new FrontRoot();
    	this.themeRoot_ = new ThemeRoot();
    	this.engineRoot_ = new EngineRoot();
    	this.androidRoot_ = new AndroidRoot();
    	this.modelRoot_ = new ModelRootClass();
	}
    
    private void debug(Boolean on_off, String s0, String s1) { if (on_off) this.log(s0, s1); }
    private void log(String s0, String s1) { AbendClass.log(this.objectName() + "." + s0 + "()", s1); }
    public void abend(String s0, String s1) { AbendClass.abend(this.objectName() + "." + s0 + "()", s1); }
}

