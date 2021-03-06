/*
 ******************************************************************************
 *                                       
 *  Copyright (c) 2021 phwang. All rights reserved.
 *
 ******************************************************************************
 */

package phwang.engine;

import phwang.utils.AbendClass;
import phwang.utils.ThreadMgrClass;

public class EngineRoot {
    private String objectName() {return "EngineRoot";}

    private ThreadMgrClass threadMgr_;
    private EngineDBinder engineDBinder_;
    private EngineUParser engineUParser_;
    private EngineBaseMgr baseMgr_;
    
    public ThreadMgrClass threadMgr() { return this.threadMgr_; }
    public EngineBaseMgr baseMgr() { return this.baseMgr_; }
    public EngineUParser engineUParser() { return this.engineUParser_; }
    public EngineDBinder engineDBinder() { return this.engineDBinder_; }


    public EngineRoot() {
        this.debug(false, "EngineRoot", "init start");

        this.threadMgr_ = new ThreadMgrClass();
        this.engineUParser_ = new EngineUParser(this);
        this.engineDBinder_ = new EngineDBinder(this);
        this.baseMgr_ = new EngineBaseMgr(this);
        
        this.engineDBinder_.startThreads();
	}
    
    private void debug(Boolean on_off, String s0, String s1) { if (on_off) this.log(s0, s1); }
    private void log(String s0, String s1) { this.logIt(this.objectName() + "." + s0 + "()", s1); }
    public void abend(String s0, String s1) { this.abendIt(this.objectName() + "." + s0 + "()", s1); }

    private Boolean debug_on = true;
    public void logIt(String s0, String s1) { if (this.debug_on) AbendClass.log(s0, s1); }
    public void abendIt(String s0, String s1) { if (this.debug_on) AbendClass.abend(s0, s1); }
}
