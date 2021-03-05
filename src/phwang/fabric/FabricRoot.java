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
    
    private ThreadMgrClass threadMgr_;
    private FabricUBinder fabricUBinder_;
    private FabricDBinder fabricDBinder_;
    private FabricLinkMgr linkMgr_;
    private FabricGroupMgr groupMgr_;
    private FabricNameList nameListObject_;
    private FabricDParser fabricDParser_;
    private FabricUParser fabricUParser_;

    public ThreadMgrClass threadMgr() { return this.threadMgr_; }
    public FabricUBinder fabricUBinder() { return this.fabricUBinder_; }
    public FabricDBinder fabricDBinder() { return this.fabricDBinder_; }
    public FabricDParser fabricDParser() { return this.fabricDParser_; }
    public FabricUParser fabricUParser() { return this.fabricUParser_; }
    public FabricLinkMgr linkMgr() { return this.linkMgr_; }
    public FabricGroupMgr groupMgr() { return this.groupMgr_; }
    public FabricNameList nameListObject() { return this.nameListObject_; }

    public FabricRoot () {
        this.debug(false, "FabricRoot", "init start");
        
        this.threadMgr_ = new ThreadMgrClass();
        this.fabricUBinder_ = new FabricUBinder(this);
        this.fabricDBinder_ = new FabricDBinder(this);
        this.fabricUParser_ = new FabricUParser(this);
        this.fabricDParser_ = new FabricDParser(this);
        this.linkMgr_ = new FabricLinkMgr(this);
        this.groupMgr_ = new FabricGroupMgr(this);
        this.nameListObject_ = new FabricNameList(this);
        
        this.fabricUBinder().startThreads();
        this.fabricDBinder().startThreads();
	}
    
    private void debug(Boolean on_off, String s0, String s1) { if (on_off) this.log(s0, s1); }
    private void log(String s0, String s1) { AbendClass.log(this.objectName() + "." + s0 + "()", s1); }
    public void abend(String s0, String s1) { AbendClass.abend(this.objectName() + "." + s0 + "()", s1); }
}
