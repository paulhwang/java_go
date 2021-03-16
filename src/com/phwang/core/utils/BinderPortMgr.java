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
    
    private Binder binder_;
    private BinderPort singleBinderPort_ = null;
    //private Boolean destructorOn = false;
    
    protected Binder binder() { return this.binder_; }
    protected String ownerName() { return this.binder_.ownerName(); }
    protected Boolean isSinglePort() { return this.binder().isSinglePort(); }

    protected BinderPortMgr(Binder binder_val) {
        this.binder_ = binder_val;
    }
    
    protected void destructor() {
    	//this.destructorOn = true;
    	if (this.isSinglePort()) {
    		this.singleBinderPort_.destructor();
    		this.singleBinderPort_ = null;
    	}
    }
    
    protected void malloc(Socket tcp_connection_val) {
    	BinderPort port = new BinderPort(this, tcp_connection_val);
    	
    	if (this.isSinglePort()) {
    		this.singleBinderPort_ = port;
    		return;
    	}
    }
    
    protected void free(BinderPort port_val) {
    	port_val.destructor();
    }
    
    protected String receiveData() {
    	if (this.isSinglePort()) {
    		return this.receiveData_();
    	}
    	else {
    		return this.receiveData__();
    	}
    }
    
    private String receiveData_() {
    	while (singleBinderPort_ == null) {
    		Utils.sleep(1);
    	}
    	return this.singleBinderPort_.receiveData();
    }
    
    private String receiveData__() {
    	this.abend("receiveData__", "TBD");
    	return null;
    }
    
    protected void transmitData(String data_val) {
    	if (this.isSinglePort()) {
    		this.transmitData_(data_val);
    	}
    	else {
    		this.transmitData__(data_val);
    	}
    }
    
    protected void transmitData_(String data_val) {
    	while (singleBinderPort_ == null) {
    		Utils.sleep(1);
    	}
    	this.singleBinderPort_.transmitData(data_val);
    }
    
    protected void transmitData__(String data_val) {
    	this.abend("transmitData__", "TBD");
    }
    
    private void debug(Boolean on_off, String s0, String s1) { if (on_off) this.log(s0, s1); }
    private void log(String s0, String s1) { Abend.log(this.objectName() + "." + s0 + "()", s1); }
    protected void abend(String s0, String s1) { Abend.abend(this.objectName() + "." + s0 + "()", s1); }
}
