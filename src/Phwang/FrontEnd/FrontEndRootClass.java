/*
 ******************************************************************************
 *                                       
 *  Copyright (c) 2018 phwang. All rights reserved.
 *
 ******************************************************************************
 */

package Phwang.FrontEnd;

import Phwang.Utils.AbendClass;
import Phwang.Utils.ThreadMgr.ThreadMgrClass;

public class FrontEndRootClass {
    private String objectName() {return "FrontEndRootClass";}
    
    private ThreadMgrClass threadMgrObject;
    private UFrontClass uFrontObject;
    
    public ThreadMgrClass ThreadMgrObject() { return this.threadMgrObject; }
    private UFrontClass UFrontObject() { return this.uFrontObject; }

    public FrontEndRootClass() {
        this.debugIt(false, "FrontEndRootClass", "init start");
        
        this.threadMgrObject = new ThreadMgrClass();
        this.uFrontObject = new UFrontClass(this);
        
        this.UFrontObject().startThreads();
    }

    private void debugIt(Boolean on_off_val, String str0_val, String str1_val) { if (on_off_val) this.logitIt(str0_val, str1_val); }
    private void logitIt(String str0_val, String str1_val) { AbendClass.phwangLogit(this.objectName() + "." + str0_val + "()", str1_val); }
    public void abendIt(String str0_val, String str1_val) { AbendClass.phwangAbend(this.objectName() + "." + str0_val + "()", str1_val); }
}
