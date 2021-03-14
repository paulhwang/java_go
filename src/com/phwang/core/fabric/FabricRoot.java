/*
 ******************************************************************************
 *                                       
 *  Copyright (c) 2021 phwang. All rights reserved.
 *
 ******************************************************************************
 */

package com.phwang.core.fabric;

import com.phwang.core.utils.Abend;
import com.phwang.core.utils.ThreadMgr;

public class FabricRoot {
    private String objectName() {return "FabricRoot";}
    
    private ThreadMgr threadMgr_;
    private FabricUBinder fabricUBinder_;
    private FabricDBinder fabricDBinder_;
    private FabricLinkMgr linkMgr_;
    private FabricGroupMgr groupMgr_;
    private FabricNameList nameList_;
    private FabricDParser fabricDParser_;
    private FabricUParser fabricUParser_;

    protected ThreadMgr threadMgr() { return this.threadMgr_; }
    protected FabricUBinder fabricUBinder() { return this.fabricUBinder_; }
    protected FabricDBinder fabricDBinder() { return this.fabricDBinder_; }
    protected FabricDParser fabricDParser() { return this.fabricDParser_; }
    protected FabricUParser fabricUParser() { return this.fabricUParser_; }
    protected FabricLinkMgr linkMgr() { return this.linkMgr_; }
    protected FabricGroupMgr groupMgr() { return this.groupMgr_; }
    protected FabricNameList nameList() { return this.nameList_; }

    public FabricRoot () {
        this.debug(false, "FabricRoot", "init start");
        
        this.threadMgr_ = new ThreadMgr();
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
    protected void logIt(String s0, String s1) { if (this.debug_on) Abend.log(s0, s1); }
    protected void abendIt(String s0, String s1) { if (this.debug_on) Abend.abend(s0, s1); }
}
