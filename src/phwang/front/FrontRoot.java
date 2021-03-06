/*
 ******************************************************************************
 *                                       
 *  Copyright (c) 2021 phwang. All rights reserved.
 *
 ******************************************************************************
 */

package phwang.front;

import phwang.utils.Abend;
import phwang.utils.ThreadMgrClass;

public class FrontRoot {
    private String objectName() {return "FrontRoot";}
    
    private ThreadMgrClass threadMgr_;
    private FrontUBinder frontUBinder_;
    private FrontUParser frontUParser_;
    private FrontDParser frontDParser_;
    private FrontDExport frontDExport_;
    private FrontJobMgr jobMgr_;
    
    public ThreadMgrClass threadMgr() { return this.threadMgr_; }
    public FrontUBinder frontUBinder() { return this.frontUBinder_; }
    public FrontUParser frontUParser() { return this.frontUParser_; }
    public FrontDParser frontDParser() { return this.frontDParser_; }
    public FrontDExport frontDExport() { return this.frontDExport_; }
    public FrontJobMgr jobMgr() { return this.jobMgr_; }

    public FrontRoot() {
        this.debug(false, "FrontRoot", "init start");
        
        this.threadMgr_ = new ThreadMgrClass();
        this.frontUBinder_ = new FrontUBinder(this);
        this.frontDExport_ = new FrontDExport(this);
        this.frontUParser_ = new FrontUParser(this);
        this.frontDParser_ = new FrontDParser(this);
        this.jobMgr_ = new FrontJobMgr(this);
        
        this.frontUBinder().startThreads();
    }
    
    private void debug(Boolean on_off, String s0, String s1) { if (on_off) this.log(s0, s1); }
    private void log(String s0, String s1) { this.logIt(this.objectName() + "." + s0 + "()", s1); }
    public void abend(String s0, String s1) { this.abendIt(this.objectName() + "." + s0 + "()", s1); }

    private Boolean debug_on = true;
    public void logIt(String s0, String s1) { if (this.debug_on) Abend.log(s0, s1); }
    public void abendIt(String s0, String s1) { if (this.debug_on) Abend.abend(s0, s1); }
}
