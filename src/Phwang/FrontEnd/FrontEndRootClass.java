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
    private FrontTestClass theFrontTestObject;
    
    public ThreadMgrClass ThreadMgrObject() { return this.threadMgrObject; }
    public UFrontClass UFrontObject() { return this.uFrontObject; }
    private FrontTestClass FrontTestObject() { return theFrontTestObject; }

    public FrontEndRootClass() {
        this.debugIt(false, "FrontEndRootClass", "init start");
        
        this.threadMgrObject = new ThreadMgrClass();
        this.uFrontObject = new UFrontClass(this);
        this.theFrontTestObject = new FrontTestClass(this);
        
        this.UFrontObject().startThreads();
        this.FrontTestObject().StartTest();
    }

    private void debugIt(Boolean on_off_val, String str0_val, String str1_val) { if (on_off_val) this.logitIt(str0_val, str1_val); }
    private void logitIt(String str0_val, String str1_val) { AbendClass.phwangLogit(this.objectName() + "." + str0_val + "()", str1_val); }
    public void abendIt(String str0_val, String str1_val) { AbendClass.phwangAbend(this.objectName() + "." + str0_val + "()", str1_val); }
}
