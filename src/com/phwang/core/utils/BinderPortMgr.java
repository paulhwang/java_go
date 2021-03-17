/*
 ******************************************************************************
 *                                       
 *  Copyright (c) 2021 phwang. All rights reserved.
 *
 ******************************************************************************
 */

package com.phwang.core.utils;

import java.net.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class BinderPortMgr {
    private String objectName() {return "BinderPortMgr";}
    protected static final int BINDER_PORT_ID_SIZE_ = 4;
    protected static final int BINDER_PORT_ID_SIZE = BINDER_PORT_ID_SIZE_ * 2;
    private static final int LIST_MGR_ARRAY_SIZE = 256;
    private static final int FIRST_LINK_ID = 1000;

    private static BinderBundle freeBundleList;
    private static int freeListLength;
    private static Lock freeListLock = new ReentrantLock();
    private static int MAX_FREE_LIST_LENGTH = 1000;
    
    private Binder binder_;
    private BinderPort singleBinderPort_ = null;
    private ListMgr listMgr_;
    //private Boolean destructorOn_ = false;
    
    protected Binder binder() { return this.binder_; }
    protected String ownerName() { return this.binder_.ownerName(); }
    protected ListMgr listMgr() { return this.listMgr_; }
    protected Boolean isSinglePort() { return this.binder().isSinglePort(); }
    private int portCount() { return this.listMgr_.entryCount(); }

    protected BinderPortMgr(Binder binder_val) {
        this.binder_ = binder_val;
        this.listMgr_ = new ListMgr(BINDER_PORT_ID_SIZE_, LIST_MGR_ARRAY_SIZE, this.objectName(), FIRST_LINK_ID);
    }
    
    protected void destructor() {
    	//this.destructorOn_ = true;
    	
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
        	this.debug(true, "************mallocPort", "portCount=" + this.portCount());
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
        int max_index = this.listMgr_.maxIndex();
        ListEntry[] list_entry_array = this.listMgr_.entryArray();
        for (int i = 0; i <= max_index; i++) {
            if (list_entry_array[i] != null) {
            	BinderPort port = (BinderPort) list_entry_array[i].data();
            	if (port.receiveQueue().length() != 0) {
            		String data = (String) port.receiveQueue().dequeue();
            		if (data != null) {
            			BinderBundle bundle = mallocBundle();
            			bundle.setData(data);
            			bundle.setBinderPort(port);
            			return bundle;
            		}
            	}
            }
        }
    	
    	return null;
    }

    public void transmitBundleData(BinderBundle bundle_val) {
    	bundle_val.binderPort().transmitStringData(bundle_val.data());
    	freeBundle(bundle_val);
    }

    public static BinderBundle mallocBundle() {
    	BinderBundle bundle;
        
        freeListLock.lock();
    	if (freeBundleList != null) {
    		bundle = freeBundleList;
    		freeBundleList = freeBundleList.next;
    		freeListLength--;
    	}
    	else {
    		bundle = null;
    	}
    	freeListLock.unlock();
    	
    	if (bundle != null) {
    		return bundle;
    	}
    	else {
    		return new BinderBundle();
    	}
    }
    
    public static void freeBundle(BinderBundle bundle_val) {
    	if (freeListLength > MAX_FREE_LIST_LENGTH) {
    		bundle_val.clear();
    		return;
    	}
    	
    	freeListLock.lock();
    	bundle_val.next = freeBundleList;
    	freeBundleList = bundle_val;
    	freeListLength++;
    	freeListLock.unlock();
    }
    
    public static void releaseBundleFreeEntryList() {
    	freeListLock.lock();
    	while (freeBundleList != null) {
    		BinderBundle bundle = freeBundleList;
    		freeBundleList = freeBundleList.next;
    		bundle.clear();
    	}
    	freeListLock.unlock();
    }
    
    private void debug(Boolean on_off, String s0, String s1) { if (on_off) this.log(s0, s1); }
    private void log(String s0, String s1) { Abend.log(this.objectName() + "." + s0 + "()", s1); }
    protected void abend(String s0, String s1) { Abend.abend(this.objectName() + "." + s0 + "()", s1); }
}
