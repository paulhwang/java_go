/*
 ******************************************************************************
 *                                       
 *  Copyright (c) 2021 phwang. All rights reserved.
 *
 ******************************************************************************
 */

package com.phwang.core.utils;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.*;

public class BinderPort implements ThreadEntityInt, ListEntryInt {
    private String objectName() {return "BinderPort";}
    private String transmitThreadName() { return "PortTransmitThread"; }
    private String receiveThreadName() { return "PortReceiveThread"; }
    
    private BinderPortMgr portMgr_;
    private Socket tcpConnection_;
    private DataInputStream inputStream_;
    private DataOutputStream outputStream_;
    private InputStreamReader inputReader_;
    private OutputStreamWriter outputWriter_;
    private ListQueue receiveQueue_;
    private ListQueue transmitQueue_;
    private String whichThread_ = null;
    private ThreadEntity receiveThread_;
    private ThreadEntity transmitThread_;
    private Boolean destructorOn_ = false;
    private ListEntry listEntry_;
    
    protected BinderPortMgr portMgr() { return this.portMgr_; }
    protected String ownerName() { return this.portMgr_.ownerName(); }
    private Binder binder() { return portMgr_.binder(); }
    private Boolean useIOnotReaderWriter() { return this.binder().useIOnotReaderWriter(); }
    protected DataInputStream inputStream() { return this.inputStream_; }
    protected DataOutputStream outputStream() { return this.outputStream_; }
    protected InputStreamReader inputReader() { return this.inputReader_; }
    protected OutputStreamWriter outputWriter() { return this.outputWriter_; }
    protected int portId() { return this.listEntry_.id(); }
    protected String portIdStr() { return this.listEntry_.idStr(); }
    protected ListEntry listEntry() { return this.listEntry_; }
    protected ListQueue receiveQueue() { return this.receiveQueue_; }
    protected ListQueue transmitQueue() { return this.transmitQueue_; }

    protected BinderPort(BinderPortMgr port_mgr_val, Socket tcp_connection_val) {
        this.portMgr_ = port_mgr_val;
        this.tcpConnection_ = tcp_connection_val;
        this.receiveQueue_ = new ListQueue(true, 0);
        this.transmitQueue_ = new ListQueue(true, 0);
        this.setupIo();
		this.createWorkingThreads();
    }
    
    protected void destructor() {
    	this.destructorOn_ = true;
    	
    	this.receiveThread_.thread().interrupt();
    	this.receiveThread_ = null;
    	this.transmitThread_.thread().interrupt();
    	this.transmitThread_ = null;
    	
    	this.receiveQueue_.destructor();
    	this.receiveQueue_ = null;
    	this.transmitQueue_.destructor();
    	this.transmitQueue_ = null;
    	
    	try {
    		this.inputStream_.close();
    		this.inputStream_ = null;
    	}
    	catch (Exception i) {}
    	
    	try {
    		this.outputStream_.close();
    		this.outputStream_ = null;
    	}
    	catch (Exception i) {}
    	
    	try {
    		this.inputReader_.close();
    		this.inputReader_ = null;
    	}
    	catch (Exception i) {}
    	
    	try {
    		this.outputWriter_.close();
    		this.outputWriter_ = null;
    	}
    	catch (Exception i) {}
    	
    }
    
    private void setupIo() {
    	try {
    		this.outputStream_ = new DataOutputStream(this.tcpConnection_.getOutputStream());
    		this.inputStream_ = new DataInputStream(this.tcpConnection_.getInputStream());
    		this.outputWriter_ = new OutputStreamWriter(this.tcpConnection_.getOutputStream());  
    		this.inputReader_ = new InputStreamReader(this.tcpConnection_.getInputStream());
    	}
    	catch (Exception e) {
    	}
     }

    public void bindListEntry(ListEntry list_entry_object_val, String who_val) {
        this.listEntry_ = list_entry_object_val;
    }

    public void unBindListEntry(String who_val) {
        this.listEntry_ = null;
    }
    
	public void threadCallbackFunction() {
		if (this.whichThread_.equals(this.receiveThreadName())) {
			this.binderReceiveThreadFunc();
			return;
		}
		
		
		if (this.whichThread_.equals(this.transmitThreadName())) {
			this.binderTransmitThreadFunc();
			return;
		}
		
        this.abend("binderReceiveThreadFunc", "not server or client");
	}

    private void createWorkingThreads() {
    	this.whichThread_ = this.receiveThreadName();
		this.receiveThread_ = new ThreadEntity(this.receiveThreadName(), this);
    }

    private void binderReceiveThreadFunc() {
        this.debug(false, "binderReceiveThreadFunc", "start thread ***");
        
    	this.whichThread_ = this.transmitThreadName();
		this.transmitThread_ = new ThreadEntity(this.transmitThreadName(), this);
        
        if (this.tcpConnection_ == null) {
            this.abend("binderReceiveThreadFunc", "null networkStream");
            return;
        }
        
        String data;
        while (true) {
			if (this.destructorOn_) {
				return;
			}
			
         	try {
        		if (this.useIOnotReaderWriter()) {
        			data = this.inputStream_.readUTF();
        		}
        		else {
        			data = this.inputStream_.readUTF();
        			//data = this.inputReader.read();
        		}
        		
        		if (data != null) {
        			this.debug(false, "binderReceiveThreadFunc", "data = " + data);
        			this.receiveQueue_.enqueue(data);
        		}
        		else {
        			this.abend("binderReceiveThreadFunc", "data is null=====================================");
    				
       				try {
       					Thread.sleep(1000);
       				}
       				catch (InterruptedException e) {
       				}
        		}
        	}
        	catch (Exception e) {}
        }
    }

    protected String receiveStringData() {
    	while (true) {
			if (this.destructorOn_) {
				return null;
			}
			
    		String data = (String) this.receiveQueue_.dequeue();
    		if (data != null) {
        		this.debug(false, "receiveStringData", "data = " + data);
        		return data;
    		}
    		
			this.receiveQueue_.setPendingThread(Thread.currentThread());
				
   			try {
   				Thread.sleep(1000);
   			}
   			catch (InterruptedException e) {
   			}
    	}
    }

    private void binderTransmitThreadFunc() {
        this.debug(false, "binderTransmitThreadFunc", "start thread ***");
        this.whichThread_ = null;
        
        if (this.tcpConnection_ == null) {
            this.abend("binderTransmitThreadFunc", "null networkStream");
            return;
        }
        
		while (true) {
			if (this.destructorOn_) {
				return;
			}
			
			String data = (String) this.transmitQueue_.dequeue();
			if (data == null) {
   				this.transmitQueue_.setPendingThread(Thread.currentThread());
   				
   				try {
   					Thread.sleep(1000);
   				}
   				catch (InterruptedException e) {
   				}
   				
				continue;
        	}
			
	        try {
        		if (this.useIOnotReaderWriter()) {
    	        	this.outputStream_.writeUTF(data);
        		}
        		else {
        			this.outputWriter_.write(data);
        			this.outputWriter_.flush();
        		}
	        }
	        catch (Exception e) { }
        }
    }

    protected void transmitStringData(String data_val) {
        this.debug(false, "transmitStringData", "data = " + data_val);
        this.transmitQueue_.enqueue(data_val);
    }
    
    private void debug(Boolean on_off, String s0, String s1) { if (on_off) this.log(s0, s1); }
    private void log(String s0, String s1) { Abend.log(this.objectName() + "." + s0 + "()", s1); }
    protected void abend(String s0, String s1) { Abend.abend(this.objectName() + "." + s0 + "()", s1); }
}
