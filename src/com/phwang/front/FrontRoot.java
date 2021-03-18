/*
 ******************************************************************************
 *                                       
 *  Copyright (c) 2021 phwang. All rights reserved.
 *
 ******************************************************************************
 */

package com.phwang.front;

import com.phwang.test.FrontTest;
import com.phwang.core.utils.Abend;
import com.phwang.core.utils.ThreadMgr;

public class FrontRoot {
    private String objectName() {return "FrontRoot";}
    
    private ThreadMgr threadMgr_;
    private FrontUBinder frontUBinder_;
    private FrontUParser frontUParser_;
    private FrontDParser frontDParser_;
    private FrontDExport frontDExport_;
    private FrontJobMgr jobMgr_;
    
    protected ThreadMgr threadMgr() { return this.threadMgr_; }
    protected FrontUBinder frontUBinder() { return this.frontUBinder_; }
    protected FrontUParser frontUParser() { return this.frontUParser_; }
    protected FrontDParser frontDParser() { return this.frontDParser_; }
    public FrontDExport frontDExport() { return this.frontDExport_; }
    protected FrontJobMgr jobMgr() { return this.jobMgr_; }

    public FrontRoot() {
        this.debug(false, "FrontRoot", "init start");
        
        this.threadMgr_ = new ThreadMgr();
        this.frontUBinder_ = new FrontUBinder(this);
        this.frontDExport_ = new FrontDExport(this);
        this.frontUParser_ = new FrontUParser(this);
        this.frontDParser_ = new FrontDParser(this);
        this.jobMgr_ = new FrontJobMgr(this);
        
        this.frontUBinder_.startThreads();
    }
    
    public void startTest(Boolean front_test_on) {
		if (front_test_on) {
			new FrontTest(this.frontDExport()).startTest();
		}
    }
    
    private void debug(Boolean on_off, String s0, String s1) { if (on_off) this.log(s0, s1); }
    private void log(String s0, String s1) { this.logIt(this.objectName() + "." + s0 + "()", s1); }
    protected void abend(String s0, String s1) { this.abendIt(this.objectName() + "." + s0 + "()", s1); }

    private Boolean debug_on = true;
    protected void logIt(String s0, String s1) { if (this.debug_on) Abend.log(s0, s1); }
    protected void abendIt(String s0, String s1) { if (this.debug_on) Abend.abend(s0, s1); }
}
