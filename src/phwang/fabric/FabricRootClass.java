/*
 ******************************************************************************
 *                                       
 *  Copyright (c) 2018 phwang. All rights reserved.
 *
 ******************************************************************************
 */

package phwang.fabric;

import phwang.utils.*;

public class FabricRootClass {
    private String objectName() {return "FabricRootClass";}
    
    private ThreadMgrClass threadMgrObject_;
    private UFabricClass uFabricObject_;
    private DFabricClass dFabricObject_;
    private FabricLinkMgr linkMgrObject_;
    private GroupMgrClass groupMgrObject_;
    private NameListClass nameListObject_;

    public ThreadMgrClass threadMgrObject() { return this.threadMgrObject_; }
    public UFabricClass uFabricObject() { return this.uFabricObject_; }
    public DFabricClass dFabricObject() { return this.dFabricObject_; }
    public FabricLinkMgr linkMgrObject() { return this.linkMgrObject_; }
    public GroupMgrClass groupMgrObject() { return this.groupMgrObject_; }
    public NameListClass nameListObject() { return this.nameListObject_; }

    public FabricRootClass () {
        this.debug(false, "FabricRootClass", "init start");
        
        this.threadMgrObject_ = new ThreadMgrClass();
        this.uFabricObject_ = new UFabricClass(this);
        this.dFabricObject_ = new DFabricClass(this);
        this.linkMgrObject_ = new FabricLinkMgr(this);
        this.groupMgrObject_ = new GroupMgrClass(this);
        this.nameListObject_ = new NameListClass(this);
        
        this.uFabricObject().startThreads();
        this.dFabricObject().startThreads();
	}
    
    private void debug(Boolean on_off, String s0, String s1) { if (on_off) this.log(s0, s1); }
    private void log(String s0, String s1) { AbendClass.log(this.objectName() + "." + s0 + "()", s1); }
    public void abend(String s0, String s1) { AbendClass.abend(this.objectName() + "." + s0 + "()", s1); }
}
