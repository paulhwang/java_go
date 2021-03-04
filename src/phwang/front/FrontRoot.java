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
    private FrontJobMgr frontJobMgrObject_;
    private FrontUBinder uFrontObject_;
    private FrontDExport dFrontObject_;
    private FrontUParser frontUParser_;
    private FrontDParser uFrontParserObject_;
    private FrontDExport frontExportObject_;
    private FrontTestClass frontTestObject_;
    
    public ThreadMgrClass threadMgrObject() { return this.threadMgrObject_; }
    public FrontUBinder uFrontObject() { return this.uFrontObject_; }
    public FrontDExport dFrontObject() { return this.dFrontObject_; }
    public FrontJobMgr frontJobMgrObject() { return this.frontJobMgrObject_; }
    public FrontUParser frontUParser() { return this.frontUParser_; }
    public FrontDParser frontDParser() { return this.uFrontParserObject_; }
    public FrontDExport frontExportObject() { return this.frontExportObject_; }
    private FrontTestClass frontTestObject() { return frontTestObject_; }

    public FrontRoot() {
        this.debug(false, "FrontRoot", "init start");
        
        this.threadMgrObject_ = new ThreadMgrClass();
        this.uFrontObject_ = new FrontUBinder(this);
        this.dFrontObject_ = new FrontDExport(this);
        this.frontUParser_ = new FrontUParser(this);
        this.uFrontParserObject_ = new FrontDParser(this);
        this.frontJobMgrObject_ = new FrontJobMgr(this);
        this.frontExportObject_ = new FrontDExport(this);
        this.frontTestObject_ = new FrontTestClass(this.frontExportObject());
        
        this.uFrontObject().startThreads();
        
        this.frontTestObject().startTest();
    }
    
    private void debug(Boolean on_off, String s0, String s1) { if (on_off) this.log(s0, s1); }
    private void log(String s0, String s1) { AbendClass.log(this.objectName() + "." + s0 + "()", s1); }
    public void abend(String s0, String s1) { AbendClass.abend(this.objectName() + "." + s0 + "()", s1); }
}
