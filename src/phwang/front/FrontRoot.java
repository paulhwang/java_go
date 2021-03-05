/*
 ******************************************************************************
 *                                       
 *  Copyright (c) 2021 phwang. All rights reserved.
 *
 ******************************************************************************
 */

package phwang.front;

import phwang.utils.*;
import phwang.test.FrontTestClass;

public class FrontRoot {
    private String objectName() {return "FrontRoot";}
    
    private ThreadMgrClass threadMgr_;
    private FrontJobMgr frontJobMgr_;
    private FrontUBinder frontUBinder_;
    private FrontUParser frontUParser_;
    private FrontDParser frontDParser_;
    private FrontDExport frontDExport_;
    private FrontTestClass frontTestObject_;
    
    public ThreadMgrClass threadMgr() { return this.threadMgr_; }
    public FrontUBinder frontUBinder() { return this.frontUBinder_; }
    public FrontJobMgr JobMgr() { return this.frontJobMgr_; }
    public FrontUParser frontUParser() { return this.frontUParser_; }
    public FrontDParser frontDParser() { return this.frontDParser_; }
    public FrontDExport frontDExport() { return this.frontDExport_; }
    private FrontTestClass frontTestObject() { return frontTestObject_; }

    public FrontRoot() {
        this.debug(false, "FrontRoot", "init start");
        
        this.threadMgr_ = new ThreadMgrClass();
        this.frontUBinder_ = new FrontUBinder(this);
        this.frontDExport_ = new FrontDExport(this);
        this.frontUParser_ = new FrontUParser(this);
        this.frontDParser_ = new FrontDParser(this);
        this.frontJobMgr_ = new FrontJobMgr(this);
        this.frontTestObject_ = new FrontTestClass(this.frontDExport());
        
        this.frontUBinder().startThreads();
        
        this.frontTestObject().startTest();
    }
    
    private void debug(Boolean on_off, String s0, String s1) { if (on_off) this.log(s0, s1); }
    private void log(String s0, String s1) { AbendClass.log(this.objectName() + "." + s0 + "()", s1); }
    public void abend(String s0, String s1) { AbendClass.abend(this.objectName() + "." + s0 + "()", s1); }
}
