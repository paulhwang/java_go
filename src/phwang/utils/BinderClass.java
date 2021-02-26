/*
 ******************************************************************************
 *                                       
 *  Copyright (c) 2018 phwang. All rights reserved.
 *
 ******************************************************************************
 */

package phwang.utils;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.*;
import phwang.utils.*;

public class BinderClass implements ThreadInterface {
    private String objectName() {return "BinderClass";}
    public String binderServerThreadName() { return "BinderServerThread"; }
    public String binderClientThreadName() { return "BinderClientThread"; }
    public String binderTransmitThreadName() { return "BinderTransmitThread"; }
    public String binderReceiveThreadName() { return "BinderReceiveThread"; }

    private String ownerObjectName_;
    private String whichThread = null;
    private ThreadClass binderServerThreadObject;
    private ThreadClass binderClientThreadObject;
    private ThreadClass binderReceiveThreadObject;
    private ThreadClass binderTransmitThreadObject;
    
    private String serverIpAddr_;
    private short port_;
    private Socket theTcpConnection;
    private DataInputStream inputStream_;
    private DataOutputStream outputStream_;
    
    private ListQueueClass receiveQueue;
    private ListQueueClass transmitQueue;
    
    private String ownerObjectName()  {return this.ownerObjectName_; }
    public short port() { return this.port_; }
    public String serverIpAddr() { return this.serverIpAddr_; }
    public Socket TcpConnection() { return this.theTcpConnection; }
    private DataInputStream InputStream() { return this.inputStream_; }
    private DataOutputStream OutputStream() { return this.outputStream_; }
    
    public String TcpClientName() { return (this.TcpConnection() != null) ? this.TcpConnection().getInetAddress().getHostName() : ""; }
    public String TcpClientAddress() { return (this.TcpConnection() != null) ? this.TcpConnection().getInetAddress().getHostAddress() : ""; }

    public BinderClass(String owner_object_name_val) {
        this.ownerObjectName_ = owner_object_name_val;
        this.receiveQueue = new ListQueueClass(true, 0);
        this.transmitQueue = new ListQueueClass(true, 0);
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
		
        this.abendIt("binderReceiveThreadFunc", "not server or client");
	}

    public Boolean bindAsTcpServer(Boolean create_server_thread_val, short port_val) {
    	if (this.whichThread != null) {
            this.abendIt("BindAsTcpServer", "bindAs is not null");
    		return false;
    	}
    	
		this.port_ = port_val;
		
		if (create_server_thread_val) {
	    	this.whichThread = this.binderServerThreadName();
			this.binderServerThreadObject = new ThreadClass(this.binderServerThreadName(), this);
			return true;
		}
		else {
			return tcpServerThreadFunc();
		}
    }
    
    public Boolean tcpServerThreadFunc() {
        this.debugIt(false, "TcpServerThreadFunc", "start (" + this.ownerObjectName() + " " + this.binderServerThreadName() + ")");
        this.whichThread = null;
        
    	try {
    		ServerSocket ss = new ServerSocket(this.port());
    		this.theTcpConnection = ss.accept();
    		this.debugIt(false, "BindAsTcpServer", this.ownerObjectName() + " server accepted");
    		this.debugIt(false, "BindAsTcpServer", "clientAddress = " + this.TcpClientName());
    		this.debugIt(false, "BindAsTcpServer", "clientName = " + this.TcpClientAddress());
            this.outputStream_ = new DataOutputStream(this.TcpConnection().getOutputStream());
            this.inputStream_ = new DataInputStream(this.TcpConnection().getInputStream());
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
            this.abendIt("BindAsTcpServer", "bindAs is not null");
    		return false;
    	}

    	this.port_ = port_val;
		this.serverIpAddr_ = ip_addr_val;
		
		if (create_client_thread_val) {
	    	this.whichThread = this.binderClientThreadName();
			this.binderClientThreadObject = new ThreadClass(this.binderClientThreadName(), this);
			return true;
		}
		else {
			return tcpClientThreadFunc();
		}
    }

    public Boolean tcpClientThreadFunc() {
        this.debugIt(false, "TcpClientThreadFunc", "start (" + this.ownerObjectName() + " " + this.binderClientThreadName() + ")");
        this.whichThread = null;

        try {
    		this.theTcpConnection = new Socket(this.serverIpAddr(), this.port());
    		this.debugIt(false, "BindAsTcpClient", this.ownerObjectName() + " client connected");
            this.outputStream_ = new DataOutputStream(this.TcpConnection().getOutputStream());
            this.inputStream_ = new DataInputStream(this.TcpConnection().getInputStream());
    		createWorkingThreads();
    		return true;
    	}
    	catch (Exception e) {
    		return false;
    	}
    }

    private void createWorkingThreads() {
    	this.whichThread = this.binderReceiveThreadName();
		this.binderReceiveThreadObject = new ThreadClass(this.binderReceiveThreadName(), this);
    }

    public void binderReceiveThreadFunc() {
        this.debugIt(false, "binderReceiveThreadFunc", "start thread ***");
        
    	this.whichThread = this.binderTransmitThreadName();
		this.binderTransmitThreadObject = new ThreadClass(this.binderTransmitThreadName(), this);
        
        if (this.TcpConnection() == null) {
            this.abendIt("binderReceiveThreadFunc", "null networkStream");
            return;
        }
        
        String data;
        while (true) {
        	try {
        		data = this.InputStream().readUTF();
        		if (data != null) {
        			this.debugIt(false, "binderReceiveThreadFunc", "data = " + data);
        			this.receiveQueue.enqueue(data);
        		}
        		else {
        			this.abendIt("binderReceiveThreadFunc", "data is null=====================================");
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
    	while (true) {
    		String data = (String) this.receiveQueue.dequeue();
    		if (data == null) {
    			try {
    				this.receiveQueue.setPendingThread(Thread.currentThread());
    				Thread.sleep(5000);
    			}
    			catch (InterruptedException e) {
    	    		this.debugIt(false, "ReceivData", "interrupted*****");
    			}
    			continue;
    		}
    		
    		this.debugIt(false, "ReceivData", "data = " + data);
    		return data;
    	}
    }

    public void binderTransmitThreadFunc() {
        this.debugIt(false, "binderTransmitThreadFunc", "start thread ***");
        this.whichThread = null;
        
        if (this.TcpConnection() == null) {
            this.abendIt("binderTransmitThreadFunc", "null networkStream");
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
    	    		this.debugIt(false, "binderTransmitThreadFunc", "interrupted*****");
				}
				continue;
        	}
			
	        try {
	        	this.OutputStream().writeUTF(data);
	        }
	        catch (Exception e) { }
        }
    }

    public void transmitData(String data_val) {
        this.debugIt(false, "TransmitData", "data = " + data_val);
        this.transmitQueue.enqueue(data_val);
    }

    private void debugIt(Boolean on_off_val, String str0_val, String str1_val) { if (on_off_val) this.logitIt(str0_val, str1_val); }
    private void logitIt(String str0_val, String str1_val) { AbendClass.phwangLogit(this.objectName() + "." + str0_val + "()", str1_val); }
    public void abendIt(String str0_val, String str1_val) { AbendClass.phwangAbend(this.objectName() + "." + str0_val + "()", str1_val); }
}
