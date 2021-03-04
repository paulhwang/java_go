/*
 ******************************************************************************
 *                                       
 *  Copyright (c) 2018 phwang. All rights reserved.
 *
 ******************************************************************************
 */

package phwang.engine;

import phwang.utils.*;

public class EngineRootClass {
    private String objectName() {return "EngineRootClass";}

    private ThreadMgrClass threadMgrObject;
    private EngineDBinder dEngineObject;
    private BaseMgrClass baseMgrObject;
    
    public ThreadMgrClass ThreadMgrObject() { return this.threadMgrObject; }
    public BaseMgrClass BaseMgrObject() { return this.baseMgrObject; }


    public EngineRootClass() {
        this.debug(false, "EngineRootClass", "init start");

        this.threadMgrObject = new ThreadMgrClass();
        this.dEngineObject = new EngineDBinder(this);
        this.baseMgrObject = new BaseMgrClass(this);
        this.dEngineObject.startThreads();
	}
    
    private void debug(Boolean on_off, String s0, String s1) { if (on_off) this.log(s0, s1); }
    private void log(String s0, String s1) { AbendClass.log(this.objectName() + "." + s0 + "()", s1); }
    public void abend(String s0, String s1) { AbendClass.abend(this.objectName() + "." + s0 + "()", s1); }
}
