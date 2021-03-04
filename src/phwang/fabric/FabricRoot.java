/*
 ******************************************************************************
 *                                       
 *  Copyright (c) 2021 phwang. All rights reserved.
 *
 ******************************************************************************
 */

package phwang.fabric;

import phwang.utils.*;

public class FabricRoot {
    private String objectName() {return "FabricRoot";}
    
    private ThreadMgrClass threadMgrObject_;
    private FabricUBinder uFabricObject_;
    private FabricDBinder dFabricObject_;
    private FabricLinkMgr linkMgrObject_;
    private FabricGroupMgr groupMgrObject_;
    private FabricNameList nameListObject_;

    public ThreadMgrClass threadMgrObject() { return this.threadMgrObject_; }
    public FabricUBinder uFabricObject() { return this.uFabricObject_; }
    public FabricDBinder dFabricObject() { return this.dFabricObject_; }
    public FabricLinkMgr linkMgrObject() { return this.linkMgrObject_; }
    public FabricGroupMgr groupMgrObject() { return this.groupMgrObject_; }
    public FabricNameList nameListObject() { return this.nameListObject_; }

    public FabricRoot () {
        this.debug(false, "FabricRoot", "init start");
        
        this.threadMgrObject_ = new ThreadMgrClass();
        this.uFabricObject_ = new FabricUBinder(this);
        this.dFabricObject_ = new FabricDBinder(this);
        this.linkMgrObject_ = new FabricLinkMgr(this);
        this.groupMgrObject_ = new FabricGroupMgr(this);
        this.nameListObject_ = new FabricNameList(this);
        
        this.uFabricObject().startThreads();
        this.dFabricObject().startThreads();
	}
    
    private void debug(Boolean on_off, String s0, String s1) { if (on_off) this.log(s0, s1); }
    private void log(String s0, String s1) { AbendClass.log(this.objectName() + "." + s0 + "()", s1); }
    public void abend(String s0, String s1) { AbendClass.abend(this.objectName() + "." + s0 + "()", s1); }
}
