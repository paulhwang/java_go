/*
 ******************************************************************************
 *                                       
 *  Copyright (c) 2021 phwang. All rights reserved.
 *
 ******************************************************************************
 */

package com.phwang.core.android;

import com.phwang.core.utils.Abend;
import com.phwang.core.utils.ThreadMgr;

public class AndroidRoot {
    private String objectName() {return "AndroidRoot";}

    private ThreadMgr threadMgr_;
    private AndroidFabricInfo androidFabricInfo_;
    private AndroidUBinder androidUBinder_;
    private AndroidDExport androidDExport_;
    private AndroidDParser androidDParser_;
    
    public AndroidDExport androidDExport() { return this.androidDExport_; }
    protected ThreadMgr threadMgr() { return this.threadMgr_; }
    protected AndroidFabricInfo androidFabricInfo() { return this.androidFabricInfo_; }
    protected AndroidUBinder androidUBinder() { return this.androidUBinder_; }
    protected AndroidDParser androidDParser() { return this.androidDParser_; }
    
    public AndroidRoot() {
        this.debug(false, "AndroidRoot", "init start");

        this.androidFabricInfo_ = new AndroidFabricInfo();
        this.threadMgr_ = new ThreadMgr();
        this.androidUBinder_ = new AndroidUBinder(this);
        this.androidDExport_ = new AndroidDExport(this);
        this.androidDParser_ = new AndroidDParser(this);
        
        this.androidUBinder_.startThreads();
	}
    
    private void debug(Boolean on_off, String s0, String s1) { if (on_off) this.log(s0, s1); }
    private void log(String s0, String s1) { this.logIt(this.objectName() + "." + s0 + "()", s1); }
    protected void abend(String s0, String s1) { this.abendIt(this.objectName() + "." + s0 + "()", s1); }

    private Boolean debug_on = true;
    protected void logIt(String s0, String s1) { if (this.debug_on) Abend.log(s0, s1); }
    protected void abendIt(String s0, String s1) { if (this.debug_on) Abend.abend(s0, s1); }
}
