/*
 ******************************************************************************
 *                                       
 *  Copyright (c) 2021 phwang. All rights reserved.
 *
 ******************************************************************************
 */

package com.phwang.core.utils;

import java.net.*;

public class BinderPortMgr {
    private String objectName() {return "BinderPortMgr";}
    protected static final int BINDER_PORT_ID_SIZE_ = 4;
    protected static final int BINDER_PORT_ID_SIZE = BINDER_PORT_ID_SIZE_ * 2;
    private static final int LIST_MGR_ARRAY_SIZE = 256;
    private static final int FIRST_LINK_ID = 1000;
    
    private Binder binder_;
    private BinderPort singleBinderPort_ = null;
    private ListMgr listMgr_;
    //private Boolean destructorOn = false;
    
    protected Binder binder() { return this.binder_; }
    protected String ownerName() { return this.binder_.ownerName(); }
    protected Boolean isSinglePort() { return this.binder().isSinglePort(); }

    protected BinderPortMgr(Binder binder_val) {
        this.binder_ = binder_val;
        this.listMgr_ = new ListMgr(BINDER_PORT_ID_SIZE_, LIST_MGR_ARRAY_SIZE, this.objectName(), FIRST_LINK_ID);
    }
    
    protected void destructor() {
    	//this.destructorOn = true;
    	if (this.isSinglePort()) {
    		this.singleBinderPort_.destructor();
    		this.singleBinderPort_ = null;
    	}
    	else {
    		
    	}
    }
    
    protected void mallocPort(Socket tcp_connection_val) {
    	BinderPort port = new BinderPort(this, tcp_connection_val);
    	
    	if (this.isSinglePort()) {
    		this.singleBinderPort_ = port;
    	}
    	else {
        	this.listMgr_.malloc(port);
    	}
    }
    
    protected void freePort(BinderPort port_val) {
    	this.listMgr_.free(port_val.listEntry());
    	port_val.destructor();
    }
    
    protected BinderPort getPortByIdStr(String port_id_str_val) {
    	ListEntry list_entry = this.listMgr_.getEntryByIdStr(port_id_str_val);
        if (list_entry == null) {
            return null;
        }
        return (BinderPort) list_entry.data();
    }
    
    protected String receiveStringData() {
    	while (singleBinderPort_ == null) {
    		Utils.sleep(1);
    	}
    	return this.singleBinderPort_.receiveStringData();
    }
    
    protected void transmitStringData(String data_val) {
    	while (singleBinderPort_ == null) {
    		Utils.sleep(1);
    	}
    	this.singleBinderPort_.transmitStringData(data_val);
    }

    public BinderBundle receiveBundleData() {
    	this.abend("receiveBundleData", "TBD");
    	return null;
    }

    public void transmitBundleData(BinderBundle bundle_val) {
    	this.abend("transmitBundleData", "TBD");
    }
    
    private void debug(Boolean on_off, String s0, String s1) { if (on_off) this.log(s0, s1); }
    private void log(String s0, String s1) { Abend.log(this.objectName() + "." + s0 + "()", s1); }
    protected void abend(String s0, String s1) { Abend.abend(this.objectName() + "." + s0 + "()", s1); }
}
