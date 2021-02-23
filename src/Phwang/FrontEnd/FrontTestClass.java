/*
 ******************************************************************************
 *                                       
 *  Copyright (c) 2018 phwang. All rights reserved.
 *
 ******************************************************************************
 */

package Phwang.FrontEnd;

import Phwang.Protocols.FabricFrontEndProtocolClass;
import Phwang.Utils.AbendClass;
import Phwang.Utils.Binder.BinderClass;
import Phwang.Utils.ThreadMgr.ThreadMgrClass;

public class FrontTestClass {
    private String objectName() {return "FrontTestClass";}
    private String FrontTestThreadName() { return "FrontTestThread"; }

    private FrontEndRootClass frontEndRootObject;
    
    public FrontEndRootClass FrontEndRootObject() { return this.frontEndRootObject; }
    private ThreadMgrClass ThreadMgrObject() { return this.FrontEndRootObject().ThreadMgrObject();}

    public FrontTestClass(FrontEndRootClass root_object_val) {
        this.debugIt(false, "FrontTestClass", "init start");
        
        this.frontEndRootObject = root_object_val;
    }


    private void debugIt(Boolean on_off_val, String str0_val, String str1_val) { if (on_off_val) this.logitIt(str0_val, str1_val); }
    private void logitIt(String str0_val, String str1_val) { AbendClass.phwangLogit(this.objectName() + "." + str0_val + "()", str1_val); }
    public void abendIt(String str0_val, String str1_val) { AbendClass.phwangAbend(this.objectName() + "." + str0_val + "()", str1_val); }
}
