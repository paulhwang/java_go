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
    private ClientFabricInfo clientFabricInfo_;
    private ClientUBinder clientUBinder_;
    private ClientDExport clientDExport_;
    private ClientDParser clientDParser_;
    
    public ClientDExport clientDExport() { return this.clientDExport_; }
    protected ThreadMgr threadMgr() { return this.threadMgr_; }
    public ClientFabricInfo clientFabricInfo() { return this.clientFabricInfo_; }
    protected ClientUBinder clientUBinder() { return this.clientUBinder_; }
    protected ClientDParser clientDParser() { return this.clientDParser_; }
    
    public ClientRoot() {
        this.debug(false, "ClientRoot", "init start");

        this.clientFabricInfo_ = new ClientFabricInfo();
        this.threadMgr_ = new ThreadMgr();
        this.clientUBinder_ = new ClientUBinder(this);
        this.clientDExport_ = new ClientDExport(this);
        this.clientDParser_ = new ClientDParser(this);
        
        this.clientUBinder_.startThreads();
	}
    
    private void debug(Boolean on_off, String s0, String s1) { if (on_off) this.log(s0, s1); }
    private void log(String s0, String s1) { this.logIt(this.objectName() + "." + s0 + "()", s1); }
    protected void abend(String s0, String s1) { this.abendIt(this.objectName() + "." + s0 + "()", s1); }

    private Boolean debug_on = true;
    protected void logIt(String s0, String s1) { if (this.debug_on) Abend.log(s0, s1); }
    protected void abendIt(String s0, String s1) { if (this.debug_on) Abend.abend(s0, s1); }
}
