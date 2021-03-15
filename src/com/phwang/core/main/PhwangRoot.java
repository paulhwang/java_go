/*
 ******************************************************************************
 *                                       
 *  Copyright (c) 2018 phwang. All rights reserved.
 *
 ******************************************************************************
 */

package com.phwang.core.main;

import com.phwang.core.utils.Abend;
import com.phwang.core.engine.EngineRoot;
import com.phwang.core.fabric.FabricRoot;
import com.phwang.core.front.FrontRoot;
import com.phwang.core.android.AndroidRoot;
import com.phwang.core.models.ModelRootClass;
import com.phwang.core.test.AndroidTest;
import com.phwang.core.test.HttpTest;
import com.phwang.core.theme.ThemeRoot;

public class PhwangRoot {
    private String objectName() {return "PhwangRoot";}
    
    private FrontRoot frontRoot_;
    private FabricRoot fabricRoot_;
    private ThemeRoot themeRoot_;
    private EngineRoot engineRoot_;
    private AndroidRoot androidRoot_;
    private ModelRootClass modelRoot_;
    
    protected FrontRoot frontRoot() { return this.frontRoot_; }
    protected FabricRoot fabricRoo() { return this.fabricRoot_; }
    protected ThemeRoot themeRoot() { return this.themeRoot_; }
    protected EngineRoot engineRoot() {return this.engineRoot_; }
    protected AndroidRoot androidRoot() { return this.androidRoot_; }
    protected ModelRootClass modelRoot() { return this.modelRoot_; }

    public PhwangRoot() {
        this.debug(false, "PhwangRoot", "init start");
    	this.fabricRoot_ = new FabricRoot();
    	this.frontRoot_ = new FrontRoot();
    	this.themeRoot_ = new ThemeRoot();
    	this.engineRoot_ = new EngineRoot();
    	this.androidRoot_ = new AndroidRoot();
    	this.modelRoot_ = new ModelRootClass();
	}
    
    public void startTest(Boolean http_test_on, Boolean android_test_on) {
		if (http_test_on) {
			new HttpTest(this.frontRoot_.frontDExport()).startTest();
		}
		
		if (android_test_on) {
			new AndroidTest(this.androidRoot().androidDExport()).startTest();
		}
    }
    
    private void debug(Boolean on_off, String s0, String s1) { if (on_off) this.log(s0, s1); }
    private void log(String s0, String s1) { Abend.log(this.objectName() + "." + s0 + "()", s1); }
    protected void abend(String s0, String s1) { Abend.abend(this.objectName() + "." + s0 + "()", s1); }
}

