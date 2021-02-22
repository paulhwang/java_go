/*
 ******************************************************************************
 *                                       
 *  Copyright (c) 2018 phwang. All rights reserved.
 *
 ******************************************************************************
 */

package Phwang.FrontEnd;

import Phwang.Utils.AbendClass;

public class FrontEndRootClass {
    private String objectName() {return "FrontEndRootClass";}
    
    public FrontEndFabricClass frontEndFabricObject;

    public FrontEndRootClass()
    {
        this.debugIt(false, "FrontEndRootClass", "init start");
        this.frontEndFabricObject = new FrontEndFabricClass(this);
    }

    private void debugIt(Boolean on_off_val, String str0_val, String str1_val) { if (on_off_val) this.logitIt(str0_val, str1_val); }
    private void logitIt(String str0_val, String str1_val) { AbendClass.phwangLogit(this.objectName() + "." + str0_val + "()", str1_val); }
    public void abendIt(String str0_val, String str1_val) { AbendClass.phwangAbend(this.objectName() + "." + str0_val + "()", str1_val); }
}
