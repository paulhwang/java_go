/*
 ******************************************************************************
 *                                       
 *  Copyright (c) 2018 phwang. All rights reserved.
 *
 ******************************************************************************
 */

package phwang.front;

import phwang.utils.*;

public class FrontRootClass {
    private String objectName() {return "FrontRootClass";}
    
    private ThreadMgrClass threadMgrObject_;
    private FrontJobMgrClass frontJobMgrObject_;
    private UFrontClass uFrontObject_;
    private DFrontClass dFrontObject_;
    private FrontExternalClass frontExternalObject_;
    private FrontTestClass frontTestObject_;
    
    public ThreadMgrClass threadMgrObject() { return this.threadMgrObject_; }
    public UFrontClass uFrontObject() { return this.uFrontObject_; }
    public DFrontClass dFrontObject() { return this.dFrontObject_; }
    public FrontJobMgrClass frontJobMgrObject() { return this.frontJobMgrObject_; }
    public FrontExternalClass frontExternalObject() { return this.frontExternalObject_; }
    private FrontTestClass frontTestObject() { return frontTestObject_; }

    public FrontRootClass() {
        this.debugIt(false, "FrontRootClass", "init start");
        
        this.threadMgrObject_ = new ThreadMgrClass();
        this.uFrontObject_ = new UFrontClass(this);
        this.dFrontObject_ = new DFrontClass(this);
        this.frontJobMgrObject_ = new FrontJobMgrClass(this);
        this.frontExternalObject_ = new FrontExternalClass(this);
        this.frontTestObject_ = new FrontTestClass(this);
        
        this.uFrontObject().startThreads();
        this.frontTestObject().startTest();
    }

    private void debugIt(Boolean on_off_val, String str0_val, String str1_val) { if (on_off_val) this.logitIt(str0_val, str1_val); }
    private void logitIt(String str0_val, String str1_val) { AbendClass.phwangLogit(this.objectName() + "." + str0_val + "()", str1_val); }
    public void abendIt(String str0_val, String str1_val) { AbendClass.phwangAbend(this.objectName() + "." + str0_val + "()", str1_val); }
}
