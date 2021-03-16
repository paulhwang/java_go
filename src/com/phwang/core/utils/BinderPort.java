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

public class BinderPort implements ThreadEntityInt {
    private String objectName() {return "BinderPort";}
    private String binderTransmitThreadName() { return "BinderTransmitThread"; }
    private String binderReceiveThreadName() { return "BinderReceiveThread"; }
    
    private BinderPortMgr portMgr_;
    private Socket tcpConnection_;
    private DataInputStream inputStream_;
    private DataOutputStream outputStream_;
    private InputStreamReader inputReader_;
    private OutputStreamWriter outputWriter_;
    private ListQueue receiveQueue_;
    private ListQueue transmitQueue_;
    private String whichThread_ = null;
    private ThreadEntity binderReceiveThread_;
    private ThreadEntity binderTransmitThread_;
    
    protected BinderPortMgr portMgr() { return this.portMgr_; }
    protected String ownerObjectName() { return this.portMgr_.ownerObjectName(); }
    private Binder binder() { return portMgr_.binder(); }
    private Boolean useIOnotReaderWriter() { return this.binder().useIOnotReaderWriter(); }
    protected DataInputStream inputStream() { return this.inputStream_; }
    protected DataOutputStream outputStream() { return this.outputStream_; }
    protected InputStreamReader inputReader() { return this.inputReader_; }
    protected OutputStreamWriter outputWriter() { return this.outputWriter_; }

    protected BinderPort(BinderPortMgr port_mgr_val, Socket tcp_connection_val) {
        this.portMgr_ = port_mgr_val;
        this.tcpConnection_ = tcp_connection_val;
        this.receiveQueue_ = new ListQueue(true, 0);
        this.transmitQueue_ = new ListQueue(true, 0);
        this.setupIo();
		this.createWorkingThreads();
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
    
	public void threadCallbackFunction() {
		if (this.whichThread_.equals(this.binderReceiveThreadName())) {
			this.binderReceiveThreadFunc();
			return;
		}
		
		
		if (this.whichThread_.equals(this.binderTransmitThreadName())) {
			this.binderTransmitThreadFunc();
			return;
		}
		
        this.abend("binderReceiveThreadFunc", "not server or client");
	}

    private void createWorkingThreads() {
    	this.whichThread_ = this.binderReceiveThreadName();
		this.binderReceiveThread_ = new ThreadEntity(this.binderReceiveThreadName(), this);
    }

    private void binderReceiveThreadFunc() {
        this.debug(false, "binderReceiveThreadFunc", "start thread ***");
        
    	this.whichThread_ = this.binderTransmitThreadName();
		this.binderTransmitThread_ = new ThreadEntity(this.binderTransmitThreadName(), this);
        
        if (this.tcpConnection_ == null) {
            this.abend("binderReceiveThreadFunc", "null networkStream");
            return;
        }
        
        String data;
        while (true) {
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
        			catch (Exception e) {}
        		}
        	}
        	catch (Exception e) {}
        }
    }

    protected String receiveData() {
    	while (true) {
    		String data = (String) this.receiveQueue_.dequeue();
    		if (data == null) {
    			try {
    				this.receiveQueue_.setPendingThread(Thread.currentThread());
    				Thread.sleep(5000);
    			}
    			catch (InterruptedException e) {
    	    		this.debug(false, "ReceivData", "interrupted*****");
    			}
    			continue;
    		}
    		
    		this.debug(false, "ReceivData", "data = " + data);
    		return data;
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
			String data = (String) this.transmitQueue_.dequeue();
			if (data == null) {
				try {
    				this.transmitQueue_.setPendingThread(Thread.currentThread());
					Thread.sleep(5000);
				}
				catch (InterruptedException e) {
    	    		this.debug(false, "binderTransmitThreadFunc", "interrupted*****");
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

    protected void transmitData(String data_val) {
        this.debug(false, "TransmitData", "data = " + data_val);
        this.transmitQueue_.enqueue(data_val);
    }
    
    private void debug(Boolean on_off, String s0, String s1) { if (on_off) this.log(s0, s1); }
    private void log(String s0, String s1) { Abend.log(this.objectName() + "." + s0 + "()", s1); }
    protected void abend(String s0, String s1) { Abend.abend(this.objectName() + "." + s0 + "()", s1); }
}
