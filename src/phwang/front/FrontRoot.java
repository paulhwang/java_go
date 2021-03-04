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
    
    private ThreadMgrClass threadMgrObject_;
    private FrontJobMgrClass frontJobMgrObject_;
    private UFrontClass uFrontObject_;
    private DFrontClass dFrontObject_;
    private FrontUParser frontUParser_;
    private FrontDParser uFrontParserObject_;
    private FrontExportClass frontExportObject_;
    private FrontTestClass frontTestObject_;
    
    public ThreadMgrClass threadMgrObject() { return this.threadMgrObject_; }
    public UFrontClass uFrontObject() { return this.uFrontObject_; }
    public DFrontClass dFrontObject() { return this.dFrontObject_; }
    public FrontJobMgrClass frontJobMgrObject() { return this.frontJobMgrObject_; }
    public FrontUParser frontUParser() { return this.frontUParser_; }
    public FrontDParser frontDParser() { return this.uFrontParserObject_; }
    public FrontExportClass frontExportObject() { return this.frontExportObject_; }
    private FrontTestClass frontTestObject() { return frontTestObject_; }

    public FrontRoot() {
        this.debug(false, "FrontRoot", "init start");
        
        this.threadMgrObject_ = new ThreadMgrClass();
        this.uFrontObject_ = new UFrontClass(this);
        this.dFrontObject_ = new DFrontClass(this);
        this.frontUParser_ = new FrontUParser(this);
        this.uFrontParserObject_ = new FrontDParser(this);
        this.frontJobMgrObject_ = new FrontJobMgrClass(this);
        this.frontExportObject_ = new FrontExportClass(this);
        this.frontTestObject_ = new FrontTestClass(this.frontExportObject());
        
        this.uFrontObject().startThreads();
        
        this.frontTestObject().startTest();
    }
    
    private void debug(Boolean on_off, String s0, String s1) { if (on_off) this.log(s0, s1); }
    private void log(String s0, String s1) { AbendClass.log(this.objectName() + "." + s0 + "()", s1); }
    public void abend(String s0, String s1) { AbendClass.abend(this.objectName() + "." + s0 + "()", s1); }
}
