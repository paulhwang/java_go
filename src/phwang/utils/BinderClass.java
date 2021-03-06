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
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
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
    private DataInputStream inputStream;
    private DataOutputStream outputStream;
    private InputStreamReader inputReader;
    private OutputStreamWriter outputWriter;
    private Boolean useIOnotReaderWriter = true;
    
    private ListQueue receiveQueue;
    private ListQueue transmitQueue;
    
    private String ownerObjectName()  {return this.ownerObjectName_; }
    public short port() { return this.port_; }
    public String serverIpAddr() { return this.serverIpAddr_; }
    public Socket TcpConnection() { return this.theTcpConnection; }
    
    public String TcpClientName() { return (this.TcpConnection() != null) ? this.TcpConnection().getInetAddress().getHostName() : ""; }
    public String TcpClientAddress() { return (this.TcpConnection() != null) ? this.TcpConnection().getInetAddress().getHostAddress() : ""; }

    public BinderClass(String owner_object_name_val) {
        this.ownerObjectName_ = owner_object_name_val;
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
    
    private Boolean tcpServerThreadFunc() {
        this.debug(false, "TcpServerThreadFunc", "start (" + this.ownerObjectName() + " " + this.binderServerThreadName() + ")");
        this.whichThread = null;
        
    	try {
    		ServerSocket ss = new ServerSocket(this.port());
    		this.theTcpConnection = ss.accept();
    		this.debug(false, "BindAsTcpServer", this.ownerObjectName() + " server accepted");
    		this.debug(false, "BindAsTcpServer", "clientAddress = " + this.TcpClientName());
    		this.debug(false, "BindAsTcpServer", "clientName = " + this.TcpClientAddress());
            this.outputStream = new DataOutputStream(this.TcpConnection().getOutputStream());
            this.inputStream = new DataInputStream(this.TcpConnection().getInputStream());
            this.outputWriter = new OutputStreamWriter(this.TcpConnection().getOutputStream());  
            this.inputReader = new InputStreamReader(this.TcpConnection().getInputStream());  
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

    private Boolean tcpClientThreadFunc() {
        this.debug(false, "TcpClientThreadFunc", "start (" + this.ownerObjectName() + " " + this.binderClientThreadName() + ")");
        this.whichThread = null;

        try {
    		this.theTcpConnection = new Socket(this.serverIpAddr(), this.port());
    		this.debug(false, "BindAsTcpClient", this.ownerObjectName() + " client connected");
            this.outputStream = new DataOutputStream(this.TcpConnection().getOutputStream());
            this.inputStream = new DataInputStream(this.TcpConnection().getInputStream());
            this.outputWriter = new OutputStreamWriter(this.TcpConnection().getOutputStream());  
            this.inputReader = new InputStreamReader(this.TcpConnection().getInputStream());  
    		this.createWorkingThreads();
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

    private void binderReceiveThreadFunc() {
        this.debug(false, "binderReceiveThreadFunc", "start thread ***");
        
    	this.whichThread = this.binderTransmitThreadName();
		this.binderTransmitThreadObject = new ThreadClass(this.binderTransmitThreadName(), this);
        
        if (this.TcpConnection() == null) {
            this.abend("binderReceiveThreadFunc", "null networkStream");
            return;
        }
        
        String data;
        while (true) {
        	try {
        		if (this.useIOnotReaderWriter) {
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
        
        if (this.TcpConnection() == null) {
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
        		if (this.useIOnotReaderWriter) {
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
        this.debug(false, "TransmitData", "data = " + data_val);
        this.transmitQueue.enqueue(data_val);
    }
    
    private void debug(Boolean on_off, String s0, String s1) { if (on_off) this.log(s0, s1); }
    private void log(String s0, String s1) { AbendClass.log(this.objectName() + "." + s0 + "()", s1); }
    public void abend(String s0, String s1) { AbendClass.abend(this.objectName() + "." + s0 + "()", s1); }
}
