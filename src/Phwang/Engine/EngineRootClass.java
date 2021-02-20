/*
 ******************************************************************************
 *                                       
 *  Copyright (c) 2018 phwang. All rights reserved.
 *
 ******************************************************************************
 */

package Phwang.Engine;

import Phwang.Engine.DEngine.DEngineClass;
import Phwang.Utils.AbendClass;

public class EngineRootClass {
    private String objectName() {return "EngineRootClass";}

    private DEngineClass dEngineObject;
    private BaseMgrClass baseMgrObject;
    
    public BaseMgrClass BaseMgrObject() { return this.baseMgrObject; }


    public EngineRootClass() {
        this.debugIt(false, "EngineRootClass", "init start");
        this.dEngineObject = new DEngineClass(this);
        this.baseMgrObject = new BaseMgrClass(this);
        this.dEngineObject.startThreads();
	}
    
    private void debugIt(Boolean on_off_val, String str0_val, String str1_val)
    {
        if (on_off_val)
            this.logitIt(str0_val, str1_val);
    }

    private void logitIt(String str0_val, String str1_val)
    {
        AbendClass.phwangLogit(this.objectName() + "." + str0_val + "()", str1_val);
    }

    private void abendIt(String str0_val, String str1_val)
    {
        AbendClass.phwangAbend(this.objectName() + "." + str0_val + "()", str1_val);
    }
}
