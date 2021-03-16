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

public class Binder implements ThreadEntityInt {
    private String objectName() {return "Binder";}
    public String binderServerThreadName() { return "BinderServerThread"; }
    public String binderClientThreadName() { return "BinderClientThread"; }
    public String binderTransmitThreadName() { return "BinderTransmitThread"; }
    public String binderReceiveThreadName() { return "BinderReceiveThread"; }

    private String ownerName_;
    private String whichThread = null;
    private ThreadEntity binderServerThread_;
    private ThreadEntity binderClientThread_;
    private ThreadEntity binderReceiveThread_;
    private ThreadEntity binderTransmitThread_;
    
    private String serverIpAddr_;
    private short tcpPort_;
    private Socket tcpConnection_;
    private DataInputStream inputStream;
    private DataOutputStream outputStream;
    private InputStreamReader inputReader;
    private OutputStreamWriter outputWriter;
    private Boolean useIOnotReaderWriter_ = true;
    private BinderPortMgr portMgr_;
    private BinderPort binderPort_;
    
    private ListQueue receiveQueue;
    private ListQueue transmitQueue;
    
    protected String ownerName()  {return this.ownerName_; }
    protected Boolean useIOnotReaderWriter() { return this.useIOnotReaderWriter_; }
    public short tcpPort() { return this.tcpPort_; }
    public String serverIpAddr() { return this.serverIpAddr_; }
    public Socket tcpConnection() { return this.tcpConnection_; }
    private BinderPortMgr portMgr() { return this.portMgr_; }
    private BinderPort binderPort() { return this.binderPort_; }
    
    public String TcpClientName() { return (this.tcpConnection_ != null) ? this.tcpConnection_.getInetAddress().getHostName() : ""; }
    public String TcpClientAddress() { return (this.tcpConnection_ != null) ? this.tcpConnection_.getInetAddress().getHostAddress() : ""; }

    public Binder(String owner_object_name_val) {
        this.ownerName_ = owner_object_name_val;
        this.portMgr_ = new BinderPortMgr(this);
        this.receiveQueue = new ListQueue(true, 0);
        this.transmitQueue = new ListQueue(true, 0);
    }
    
	public void threadCallbackFunction() {
		if (this.whichThread.equals(this.binderServerThreadName())) {
			this.tcpServerThreadFunc();
			return;
		}
		
		if (this.whichThread.equals(this.binderClientThreadName())) {
			this.tcpClientThreadFunc();
			return;
		}
		
		
		if (this.whichThread.equals(this.binderReceiveThreadName())) {
			this.binderReceiveThreadFunc();
			return;
		}
		
		
		if (this.whichThread.equals(this.binderTransmitThreadName())) {
			this.binderTransmitThreadFunc();
			return;
		}
		
        this.abend("binderReceiveThreadFunc", "not server or client");
	}

    public Boolean bindAsTcpServer(Boolean create_server_thread_val, short port_val) {
    	if (this.whichThread != null) {
            this.abend("BindAsTcpServer", "bindAs is not null");
    		return false;
    	}
    	
		this.tcpPort_ = port_val;
		
		if (create_server_thread_val) {
	    	this.whichThread = this.binderServerThreadName();
			this.binderServerThread_ = new ThreadEntity(this.binderServerThreadName(), this);
			return true;
		}
		else {
			return tcpServerThreadFunc();
		}
    }
    
    private Boolean tcpServerThreadFunc() {
        this.debug(false, "TcpServerThreadFunc", "start (" + this.ownerName() + " " + this.binderServerThreadName() + ")");
        this.whichThread = null;
        
    	try {
    		ServerSocket ss = new ServerSocket(this.tcpPort());
    		this.tcpConnection_ = ss.accept();
    		this.debug(false, "BindAsTcpServer", this.ownerName() + " server accepted");
    		this.debug(false, "BindAsTcpServer", "clientAddress = " + this.TcpClientName());
    		this.debug(false, "BindAsTcpServer", "clientName = " + this.TcpClientAddress());
    		//this.portMgr_.malloc(this.tcpConnection());
            this.outputStream = new DataOutputStream(this.tcpConnection().getOutputStream());
            this.inputStream = new DataInputStream(this.tcpConnection().getInputStream());
            this.outputWriter = new OutputStreamWriter(this.tcpConnection().getOutputStream());  
            this.inputReader = new InputStreamReader(this.tcpConnection().getInputStream());  
            this.createWorkingThreads();
            ss.close();
            return true;
    	}
    	catch (Exception e) {
    		return false;
    	}
    }

    public Boolean bindAsTcpClient(Boolean create_client_thread_val, String ip_addr_val, short port_val) {
    	if (this.whichThread != null) {
            this.abend("BindAsTcpServer", "bindAs is not null");
    		return false;
    	}

    	this.tcpPort_ = port_val;
		this.serverIpAddr_ = ip_addr_val;
		
		if (create_client_thread_val) {
	    	this.whichThread = this.binderClientThreadName();
			this.binderClientThread_ = new ThreadEntity(this.binderClientThreadName(), this);
			return true;
		}
		else {
			return tcpClientThreadFunc();
		}
    }

    private Boolean tcpClientThreadFunc() {
        this.debug(false, "TcpClientThreadFunc", "start (" + this.ownerName() + " " + this.binderClientThreadName() + ")");
        this.whichThread = null;

        try {
    		this.tcpConnection_ = new Socket(this.serverIpAddr(), this.tcpPort());
    		this.debug(false, "BindAsTcpClient", this.ownerName() + " client connected");
    		//this.portMgr_.malloc(this.tcpConnection_);
            this.outputStream = new DataOutputStream(this.tcpConnection_.getOutputStream());
            this.inputStream = new DataInputStream(this.tcpConnection_.getInputStream());
            this.outputWriter = new OutputStreamWriter(this.tcpConnection_.getOutputStream());  
            this.inputReader = new InputStreamReader(this.tcpConnection_.getInputStream());  
    		this.createWorkingThreads();
    		return true;
    	}
    	catch (Exception e) {
    		return false;
    	}
    }

    private void createWorkingThreads() {
    	this.whichThread = this.binderReceiveThreadName();
		this.binderReceiveThread_ = new ThreadEntity(this.binderReceiveThreadName(), this);
    }

    private void binderReceiveThreadFunc() {
        this.debug(false, "binderReceiveThreadFunc", "start thread ***");
        
    	this.whichThread = this.binderTransmitThreadName();
		this.binderTransmitThread_ = new ThreadEntity(this.binderTransmitThreadName(), this);
        
        if (this.tcpConnection_ == null) {
            this.abend("binderReceiveThreadFunc", "null networkStream");
            return;
        }
        
        String data;
        while (true) {
        	try {
        		if (this.useIOnotReaderWriter_) {
        			data = this.inputStream.readUTF();
        		}
        		else {
        			data = this.inputStream.readUTF();
        			//data = this.inputReader.read();
        		}
        		
        		if (data != null) {
        			this.debug(false, "binderReceiveThreadFunc", "data = " + data);
        			this.receiveQueue.enqueue(data);
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

    public String receiveData() {
    	//return this.portMgr_.receiveData();
    	
    	while (true) {
    		String data = (String) this.receiveQueue.dequeue();
    		if (data == null) {
    			try {
    				this.receiveQueue.setPendingThread(Thread.currentThread());
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
        this.whichThread = null;
        
        if (this.tcpConnection_ == null) {
            this.abend("binderTransmitThreadFunc", "null networkStream");
            return;
        }
        
		while (true) {
			String data = (String) this.transmitQueue.dequeue();
			if (data == null) {
				try {
    				this.transmitQueue.setPendingThread(Thread.currentThread());
					Thread.sleep(5000);
				}
				catch (InterruptedException e) {
    	    		this.debug(false, "binderTransmitThreadFunc", "interrupted*****");
				}
				continue;
        	}
			
	        try {
        		if (this.useIOnotReaderWriter_) {
    	        	this.outputStream.writeUTF(data);
        		}
        		else {
        			this.outputWriter.write(data);
        			this.outputWriter.flush();
        		}
	        }
	        catch (Exception e) { }
        }
    }

    public void transmitData(String data_val) {
    	//this.portMgr_.transmitData(data_val);
        this.debug(false, "TransmitData", "data = " + data_val);
        this.transmitQueue.enqueue(data_val);
    }
    
    private void debug(Boolean on_off, String s0, String s1) { if (on_off) this.log(s0, s1); }
    private void log(String s0, String s1) { Abend.log(this.objectName() + "." + s0 + "()", s1); }
    public void abend(String s0, String s1) { Abend.abend(this.objectName() + "." + s0 + "()", s1); }
}
