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
    private EngineDBinder dEngineObject;
    private EngineBaseMgr baseMgrObject;
    
    public ThreadMgrClass ThreadMgrObject() { return this.threadMgrObject; }
    public EngineBaseMgr BaseMgrObject() { return this.baseMgrObject; }


    public EngineRoot() {
        this.debug(false, "EngineRoot", "init start");

        this.threadMgrObject = new ThreadMgrClass();
        this.dEngineObject = new EngineDBinder(this);
        this.baseMgrObject = new EngineBaseMgr(this);
        this.dEngineObject.startThreads();
	}
    
    private void debug(Boolean on_off, String s0, String s1) { if (on_off) this.log(s0, s1); }
    private void log(String s0, String s1) { AbendClass.log(this.objectName() + "." + s0 + "()", s1); }
    public void abend(String s0, String s1) { AbendClass.abend(this.objectName() + "." + s0 + "()", s1); }
}
