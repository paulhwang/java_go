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

public class PhwangRoot {
    private String objectName() {return "PhwangRoot";}
    
    private FrontRoot frontRoot_;
    private FabricRoot fabricRoot_;
    private ThemeRoot themeRoot_;
    private EngineRoot engineRoot_;
    private AndroidRoot androidRoot_;
    private ModelRootClass modelRoot_;
    
    public FrontRoot frontRoot() { return this.frontRoot_; }
    public FabricRoot fabricRoo() { return this.fabricRoot_; }
    public ThemeRoot themeRoot() { return this.themeRoot_; }
    public EngineRoot engineRoot() {return this.engineRoot_; }
    public AndroidRoot androidRoot() { return this.androidRoot_; }
    public ModelRootClass modelRoot() { return this.modelRoot_; }

    public PhwangRoot() {
        this.debug(false, "PhwangRoot", "init start");
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

