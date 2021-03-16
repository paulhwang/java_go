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
    private BinderPort binderPort_;
    
    protected Binder binder() { return this.binder_; }
    protected String ownerObjectName() { return this.binder_.ownerName(); }

    protected BinderPortMgr(Binder binder_val) {
        this.binder_ = binder_val;
    	
    }
    
    protected BinderPort malloc(Socket tcp_connection_val) {
    	BinderPort port = new BinderPort(this, tcp_connection_val);
    	
    	this.binderPort_ = port;
    	return port;
    }
    
    protected void free(BinderPort port_val) {
    	
    }
    
    protected String receiveData() {
    	while (binderPort_ == null) {
    		Utils.sleep(1);
    	}
    	return this.binderPort_.receiveData();
    }
    
    protected void transmitData(String data_val) {
    	while (binderPort_ == null) {
    		Utils.sleep(1);
    	}
    	this.binderPort_.transmitData(data_val);
    }
    
    private void debug(Boolean on_off, String s0, String s1) { if (on_off) this.log(s0, s1); }
    private void log(String s0, String s1) { Abend.log(this.objectName() + "." + s0 + "()", s1); }
    protected void abend(String s0, String s1) { Abend.abend(this.objectName() + "." + s0 + "()", s1); }
}
