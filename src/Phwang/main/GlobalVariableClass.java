/*
 ******************************************************************************
 *                                       
 *  Copyright (c) 2018 phwang. All rights reserved.
 *
 ******************************************************************************
 */

package Phwang.main;

import Phwang.Utils.*;
import Phwang.engine.EngineRootClass;
import Phwang.fabric.FabricRootClass;
import Phwang.front.FrontRootClass;
import Phwang.models.ModelRootClass;
import Phwang.theme.ThemeRootClass;

public class GlobalVariableClass {
    private String objectName() {return "GlobalVariableClass";}
    
    static public FrontRootClass frontEndRootObject;
    static public FabricRootClass fabricRootObject;
    static public ThemeRootClass themeRootObject;
    static public EngineRootClass engineRootObject;
    static public ModelRootClass modelRootObject;

    public GlobalVariableClass() {
        this.debugIt(false, "EngineRootClass", "init start");
	}
    
    public static void Initilization() {
        if (frontEndRootObject == null) {
            fabricRootObject = new FabricRootClass();
            frontEndRootObject = new FrontRootClass();
            themeRootObject = new ThemeRootClass();
            engineRootObject = new EngineRootClass();
            modelRootObject = new ModelRootClass();
        }
    }
    
    public static FrontRootClass getGoRoot() {
        Initilization();
        return frontEndRootObject;
    }
	
	public void doTest(Boolean do_it_val) {
		if (do_it_val) {
	        this.debugIt(true, "doTest", "init start ****************************");
			new BinderTestClass();
		}
	}

    private void debugIt(Boolean on_off_val, String str0_val, String str1_val) { if (on_off_val) this.logitIt(str0_val, str1_val); }
    private void logitIt(String str0_val, String str1_val) { AbendClass.phwangLogit(this.objectName() + "." + str0_val + "()", str1_val); }
    public void abendIt(String str0_val, String str1_val) { AbendClass.phwangAbend(this.objectName() + "." + str0_val + "()", str1_val); }
}

