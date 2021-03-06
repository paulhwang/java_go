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
    private FabricNameList nameList_;
    private FabricDParser fabricDParser_;
    private FabricUParser fabricUParser_;

    public ThreadMgrClass threadMgr() { return this.threadMgr_; }
    public FabricUBinder fabricUBinder() { return this.fabricUBinder_; }
    public FabricDBinder fabricDBinder() { return this.fabricDBinder_; }
    public FabricDParser fabricDParser() { return this.fabricDParser_; }
    public FabricUParser fabricUParser() { return this.fabricUParser_; }
    public FabricLinkMgr linkMgr() { return this.linkMgr_; }
    public FabricGroupMgr groupMgr() { return this.groupMgr_; }
    public FabricNameList nameList() { return this.nameList_; }

    public FabricRoot () {
        this.debug(false, "FabricRoot", "init start");
        
        this.threadMgr_ = new ThreadMgrClass();
        this.fabricUParser_ = new FabricUParser(this);
        this.fabricDParser_ = new FabricDParser(this);
        this.fabricUBinder_ = new FabricUBinder(this);
        this.fabricDBinder_ = new FabricDBinder(this);
        this.linkMgr_ = new FabricLinkMgr(this);
        this.groupMgr_ = new FabricGroupMgr(this);
        this.nameList_ = new FabricNameList(this);
        
        this.fabricUBinder().startThreads();
        this.fabricDBinder().startThreads();
	}
    
    private void debug(Boolean on_off, String s0, String s1) { if (on_off) this.log(s0, s1); }
    private void log(String s0, String s1) { this.logIt(this.objectName() + "." + s0 + "()", s1); }
    public void abend(String s0, String s1) { this.abendIt(this.objectName() + "." + s0 + "()", s1); }

    private Boolean debug_on = true;
    public void logIt(String s0, String s1) { if (this.debug_on) Abend.log(s0, s1); }
    public void abendIt(String s0, String s1) { if (this.debug_on) Abend.abend(s0, s1); }
}
