/*
 ******************************************************************************
 *                                       
 *  Copyright (c) 2021 phwang. All rights reserved.
 *
 ******************************************************************************
 */

package com.phwang.core.utils;

import java.net.*;

public class Binder implements ThreadEntityInt {
    private String objectName() {return "Binder";}
    private String binderServerThreadName() { return "BinderServerThread"; }
    private String binderClientThreadName() { return "BinderClientThread"; }

    private String ownerName_;
    private String whichThread_ = null;
    private ThreadEntity serverThread_;
    private ThreadEntity clientThread_;
    //private Boolean destructorOn = false;
    
    private String serverIpAddr_;
    private short tcpPort_;
    private Socket tcpConnection_;
    private Boolean useIOnotReaderWriter_ = true;
    private BinderPortMgr portMgr_;
    
    protected String ownerName()  {return this.ownerName_; }
    protected Boolean useIOnotReaderWriter() { return this.useIOnotReaderWriter_; }
    public short tcpPort() { return this.tcpPort_; }
    public String serverIpAddr() { return this.serverIpAddr_; }
    public Socket tcpConnection() { return this.tcpConnection_; }
    
    public String tcpClientName() { return (this.tcpConnection_ != null) ? this.tcpConnection_.getInetAddress().getHostName() : ""; }
    public String tcpClientAddress() { return (this.tcpConnection_ != null) ? this.tcpConnection_.getInetAddress().getHostAddress() : ""; }

    public Binder(String owner_object_name_val) {
        this.ownerName_ = owner_object_name_val;
        this.portMgr_ = new BinderPortMgr(this);
    }
    
    public void destructor() {
    	//this.destructorOn = true;
    	
    	this.portMgr_.destructor();
    	
    	if (serverThread_ != null) {
    		this.serverThread_.thread().interrupt();
    		this.serverThread_ = null;
    	}
    	
    	if (clientThread_ != null) {
    		this.clientThread_.thread().interrupt();
    		this.clientThread_ = null;
    	}
    }
    
	public void threadCallbackFunction() {
		if (this.whichThread_.equals(this.binderServerThreadName())) {
			this.tcpServerThreadFunc();
			return;
		}
		
		if (this.whichThread_.equals(this.binderClientThreadName())) {
			this.tcpClientThreadFunc();
			return;
		}
		
        this.abend("binderReceiveThreadFunc", "not server or client");
	}

    public Boolean bindAsTcpServer(Boolean create_server_thread_val, short port_val) {
    	if (this.whichThread_ != null) {
            this.abend("BindAsTcpServer", "bindAs is not null");
    		return false;
    	}
    	
		this.tcpPort_ = port_val;
		
		if (create_server_thread_val) {
	    	this.whichThread_ = this.binderServerThreadName();
			this.serverThread_ = new ThreadEntity(this.binderServerThreadName(), this);
			return true;
		}
		else {
			return tcpServerThreadFunc();
		}
    }
    
    private Boolean tcpServerThreadFunc() {
        this.debug(false, "TcpServerThreadFunc", "start (" + this.ownerName() + " " + this.binderServerThreadName() + ")");
        this.whichThread_ = null;
        
    	try {
    		ServerSocket ss = new ServerSocket(this.tcpPort());
    		this.tcpConnection_ = ss.accept();
    		this.debug(false, "BindAsTcpServer", this.ownerName() + " server accepted");
    		this.debug(false, "BindAsTcpServer", "clientAddress = " + this.tcpClientName());
    		this.debug(false, "BindAsTcpServer", "clientName = " + this.tcpClientAddress());
    		this.portMgr_.malloc(this.tcpConnection());
            ss.close();
            return true;
    	}
    	catch (Exception e) {
    		return false;
    	}
    }

    public Boolean bindAsTcpClient(Boolean create_client_thread_val, String ip_addr_val, short port_val) {
    	if (this.whichThread_ != null) {
            this.abend("BindAsTcpServer", "bindAs is not null");
    		return false;
    	}

    	this.tcpPort_ = port_val;
		this.serverIpAddr_ = ip_addr_val;
		
		if (create_client_thread_val) {
	    	this.whichThread_ = this.binderClientThreadName();
			this.clientThread_ = new ThreadEntity(this.binderClientThreadName(), this);
			return true;
		}
		else {
			return tcpClientThreadFunc();
		}
    }

    private Boolean tcpClientThreadFunc() {
        this.debug(false, "TcpClientThreadFunc", "start (" + this.ownerName() + " " + this.binderClientThreadName() + ")");
        this.whichThread_ = null;

        try {
    		this.tcpConnection_ = new Socket(this.serverIpAddr(), this.tcpPort());
    		this.debug(false, "BindAsTcpClient", this.ownerName() + " client connected");
    		this.portMgr_.malloc(this.tcpConnection_);
    		return true;
    	}
    	catch (Exception e) {
    		return false;
    	}
    }

    public String receiveData() {
    	return this.portMgr_.receiveData();
    }

    public void transmitData(String data_val) {
    	this.portMgr_.transmitData(data_val);
    }
    
    private void debug(Boolean on_off, String s0, String s1) { if (on_off) this.log(s0, s1); }
    private void log(String s0, String s1) { Abend.log(this.objectName() + "." + s0 + "()", s1); }
    protected void abend(String s0, String s1) { Abend.abend(this.objectName() + "." + s0 + "()", s1); }
}
