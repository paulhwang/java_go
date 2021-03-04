/*
 ******************************************************************************
 *                                       
 *  Copyright (c) 2021 phwang. All rights reserved.
 *
 ******************************************************************************
 */

package phwang.engine;

import phwang.utils.*;

public class EngineRoot {
    private String objectName() {return "EngineRoot";}

    private ThreadMgrClass threadMgrObject;
    private EngineDBinder engineDBinder;
    private EngineBaseMgr baseMgr;
    
    public ThreadMgrClass ThreadMgrObject() { return this.threadMgrObject; }
    public EngineBaseMgr BaseMgr() { return this.baseMgr; }


    public EngineRoot() {
        this.debug(false, "EngineRoot", "init start");

        this.threadMgrObject = new ThreadMgrClass();
        this.engineDBinder = new EngineDBinder(this);
        this.baseMgr = new EngineBaseMgr(this);
        this.engineDBinder.startThreads();
	}
    
    private void debug(Boolean on_off, String s0, String s1) { if (on_off) this.log(s0, s1); }
    private void log(String s0, String s1) { AbendClass.log(this.objectName() + "." + s0 + "()", s1); }
    public void abend(String s0, String s1) { AbendClass.abend(this.objectName() + "." + s0 + "()", s1); }
}
