/*
 ******************************************************************************
 *                                       
 *  Copyright (c) 2018 phwang. All rights reserved.
 *
 ******************************************************************************
 */

package phwang.main;

import phwang.utils.*;
import phwang.engine.EngineRoot;
import phwang.fabric.FabricRoot;
import phwang.front.FrontRoot;
import phwang.models.ModelRootClass;
import phwang.theme.ThemeRoot;

public class GlobalVariableClass {
    private String objectName() {return "GlobalVariableClass";}
    
    static public FrontRoot frontEndRootObject;
    static public FabricRoot fabricRootObject;
    static public ThemeRoot themeRootObject;
    static public EngineRoot engineRootObject;
    static public ModelRootClass modelRootObject;

    public GlobalVariableClass() {
        this.debug(false, "EngineRootClass", "init start");
	}
    
    public static void Initilization() {
        if (frontEndRootObject == null) {
            fabricRootObject = new FabricRoot();
            frontEndRootObject = new FrontRoot();
            themeRootObject = new ThemeRoot();
            engineRootObject = new EngineRoot();
            modelRootObject = new ModelRootClass();
        }
    }
    
    public static FrontRoot getGoRoot() {
        Initilization();
        return frontEndRootObject;
    }
	
	public void doTest(Boolean do_it_val) {
		if (do_it_val) {
	        this.debug(true, "doTest", "init start ****************************");
			new BinderTestClass();
		}
	}
    
    private void debug(Boolean on_off, String s0, String s1) { if (on_off) this.log(s0, s1); }
    private void log(String s0, String s1) { AbendClass.log(this.objectName() + "." + s0 + "()", s1); }
    public void abend(String s0, String s1) { AbendClass.abend(this.objectName() + "." + s0 + "()", s1); }
}

