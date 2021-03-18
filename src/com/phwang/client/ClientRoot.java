/*
 ******************************************************************************
 *                                       
 *  Copyright (c) 2021 phwang. All rights reserved.
 *
 ******************************************************************************
 */

package com.phwang.client;

import com.phwang.core.utils.Abend;
import com.phwang.core.utils.ThreadMgr;

public class ClientRoot {
    private String objectName() {return "ClientRoot";}

    private ThreadMgr threadMgr_;
    private ClientFabricInfo androidFabricInfo_;
    private ClientUBinder androidUBinder_;
    private ClientDExport androidDExport_;
    private ClientDParser androidDParser_;
    
    public ClientDExport androidDExport() { return this.androidDExport_; }
    protected ThreadMgr threadMgr() { return this.threadMgr_; }
    protected ClientFabricInfo androidFabricInfo() { return this.androidFabricInfo_; }
    protected ClientUBinder androidUBinder() { return this.androidUBinder_; }
    protected ClientDParser androidDParser() { return this.androidDParser_; }
    
    public ClientRoot() {
        this.debug(false, "ClientRoot", "init start");

        this.androidFabricInfo_ = new ClientFabricInfo();
        this.threadMgr_ = new ThreadMgr();
        this.androidUBinder_ = new ClientUBinder(this);
        this.androidDExport_ = new ClientDExport(this);
        this.androidDParser_ = new ClientDParser(this);
        
        this.androidUBinder_.startThreads();
	}
    
    private void debug(Boolean on_off, String s0, String s1) { if (on_off) this.log(s0, s1); }
    private void log(String s0, String s1) { this.logIt(this.objectName() + "." + s0 + "()", s1); }
    protected void abend(String s0, String s1) { this.abendIt(this.objectName() + "." + s0 + "()", s1); }

    private Boolean debug_on = true;
    protected void logIt(String s0, String s1) { if (this.debug_on) Abend.log(s0, s1); }
    protected void abendIt(String s0, String s1) { if (this.debug_on) Abend.abend(s0, s1); }
}
