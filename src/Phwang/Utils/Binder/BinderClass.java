/*
 ******************************************************************************
 *                                       
 *  Copyright (c) 2018 phwang. All rights reserved.
 *
 ******************************************************************************
 */

package Phwang.Utils.Binder;

import Phwang.Engine.DEngine.DEngineClass;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.*;
import Phwang.Utils.AbendClass;
import Phwang.Utils.UtilsClass;
import Phwang.Utils.Queue.ListQueueClass;
import Phwang.Utils.ThreadMgr.ThreadMgrClass;
import Phwang.Utils.ThreadMgr.ThreadClass;
import Phwang.Utils.ThreadMgr.ThreadInterface;

public class BinderClass implements ThreadInterface {
    private String objectName() {return "BinderClass";}
    public String binderServerThreadName() { return "BinderServerThread"; }
    public String binderClientThreadName() { return "BinderClientThread"; }
    public String binderTransmitThreadName() { return "BinderTransmitThread"; }
    public String binderReceiveThreadName() { return "BinderReceiveThread"; }

    private String ownerObjectName;
    private String whichThread = null;
    private ThreadClass binderServerThreadObject;
    private ThreadClass binderClientThreadObject;
    private ThreadClass binderReceiveThreadObject;
    private ThreadClass binderTransmitThreadObject;
    
    private String theServerIpAddress;
    private short thePort;
    private Socket theTcpConnection;
    private DataInputStream theInputStream;
    private DataOutputStream theOutputStream;
    
    private ListQueueClass receiveQueue;
    
    private String OwnerObjectName()  {return this.ownerObjectName; }
    public short Port() { return this.thePort; }
    public String ServerIpAddress() { return this.theServerIpAddress; }
    public Socket TcpConnection() { return this.theTcpConnection; }
    private DataInputStream InputStream() { return this.theInputStream; }
    private DataOutputStream OutputStream() { return this.theOutputStream; }
    
    public String TcpClientName() { return (this.TcpConnection() != null) ? this.TcpConnection().getInetAddress().getHostName() : ""; }
    public String TcpClientAddress() { return (this.TcpConnection() != null) ? this.TcpConnection().getInetAddress().getHostAddress() : ""; }

    public BinderClass(String owner_object_name_val) {
        this.ownerObjectName = owner_object_name_val;
        this.receiveQueue = new ListQueueClass(true, 0);
    }
    
	public void ThreadCallbackFunction() {
		if (this.whichThread.equals(this.binderServerThreadName())) {
			this.TcpServerThreadFunc();
			return;
		}
		
		if (this.whichThread.equals(this.binderClientThreadName())) {
			this.TcpClientThreadFunc();
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

    public Boolean BindAsTcpServer(Boolean create_server_thread_val, short port_val) {
    	if (this.whichThread != null) {
            this.abendIt("BindAsTcpServer", "bindAs is not null");
    		return false;
    	}
    	
		this.thePort = port_val;
		
		if (create_server_thread_val) {
	    	this.whichThread = this.binderServerThreadName();
			this.binderServerThreadObject = new ThreadClass(this.binderServerThreadName(), this);
			return true;
		}
		else {
			return TcpServerThreadFunc();
		}
    }
    
    public Boolean TcpServerThreadFunc() {
        this.debugIt(false, "TcpServerThreadFunc", "start (" + this.OwnerObjectName() + " " + this.binderServerThreadName() + ")");
        this.whichThread = null;
        
    	try {
    		ServerSocket ss = new ServerSocket(this.Port());
    		this.theTcpConnection = ss.accept();
    		this.debugIt(true, "BindAsTcpServer", this.OwnerObjectName() + " server accepted");
    		this.debugIt(false, "BindAsTcpServer", "clientAddress = " + this.TcpClientName());
    		this.debugIt(false, "BindAsTcpServer", "clientName = " + this.TcpClientAddress());
            this.theOutputStream = new DataOutputStream(this.TcpConnection().getOutputStream());
            this.theInputStream = new DataInputStream(this.TcpConnection().getInputStream());
            this.createWorkingThreads();
            ss.close();
            return true;
    	}
    	catch (Exception e) {
    		return false;
    	}
    }

    public Boolean BindAsTcpClient(Boolean create_client_thread_val, String ip_addr_val, short port_val) {
    	if (this.whichThread != null) {
            this.abendIt("BindAsTcpServer", "bindAs is not null");
    		return false;
    	}

    	this.thePort = port_val;
		this.theServerIpAddress = ip_addr_val;
		
		if (create_client_thread_val) {
	    	this.whichThread = this.binderClientThreadName();
			this.binderClientThreadObject = new ThreadClass(this.binderClientThreadName(), this);
			return true;
		}
		else {
			return TcpClientThreadFunc();
		}
    }

    public Boolean TcpClientThreadFunc() {
        this.debugIt(false, "TcpClientThreadFunc", "start (" + this.OwnerObjectName() + " " + this.binderClientThreadName() + ")");
        this.whichThread = null;

        try {
    		this.theTcpConnection = new Socket(this.ServerIpAddress(), this.Port());
    		this.debugIt(true, "BindAsTcpClient", this.OwnerObjectName() + " client connected");
            this.theOutputStream = new DataOutputStream(this.TcpConnection().getOutputStream());
            this.theInputStream = new DataInputStream(this.TcpConnection().getInputStream());
    		createWorkingThreads();
    		return true;
    	}
    	catch (Exception e) {
    		return false;
    	}
    }
    
    /*
    private void binderTcpServerAcceptFunc(Object d_fabric_object_val, NetworkStream netwrok_stream_val) {
        this.debugIt(true, "binderTcpServerAcceptFunc", "accepted!");
        //this.networkStream = netwrok_stream_val;
        this.createWorkingThreads();
    }
*/
    private void createWorkingThreads() {
    	this.whichThread = this.binderReceiveThreadName();
		this.binderReceiveThreadObject = new ThreadClass(this.binderReceiveThreadName(), this);

        //this.transmitRunable = new BinderTransmitRunnable(this);
        //this.transmitThread = new Thread(this.transmitRunable);
        //this.transmitThread.start();
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
        			this.debugIt(true, "binderReceiveThreadFunc", "data = " + data);
        			this.receiveQueue.EnqueueData(data);
        		}
        		else {
        			this.abendIt("binderReceiveThreadFunc", "data is null=====================================");
        			UtilsClass.sleep(1);
        		}
        	}
        	catch (Exception e) {}
        }
    }

    public void binderTransmitThreadFunc() {
        this.debugIt(false, "binderTransmitThreadFunc", "start thread ***");
        this.whichThread = null;
        
        if (this.TcpConnection() == null) {
            this.abendIt("binderTransmitThreadFunc", "null networkStream");
            return;
        }
        return;///////////////////////////////////
        
        /*
        while (true) {
            //Thread.Sleep(10000);
        }
        */
    }

    public String ReceiveData() {
        String data = (String) this.receiveQueue.DequeueData();
        if (data != null) {
            this.debugIt(true, "ReceivData", "data = " + data);
        }
        return data;
    }

    public void TransmitRawData(String data_val) {
        this.debugIt(false, "TransmitRawData", "data = " + data_val);
        try {
        	this.OutputStream().writeUTF(data_val);
        }
        catch (Exception e) { }

        //TcpServerClass.TcpTransmitData(this.networkStream, data_var);
    }

    public void TransmitData(String data_val) {
        this.debugIt(false, "TransmitData", "data = " + data_val);
        this.TransmitRawData(data_val);
    }

    private void debugIt(Boolean on_off_val, String str0_val, String str1_val) { if (on_off_val) this.logitIt(str0_val, str1_val); }
    private void logitIt(String str0_val, String str1_val) { AbendClass.phwangLogit(this.objectName() + "." + str0_val + "()", str1_val); }
    public void abendIt(String str0_val, String str1_val) { AbendClass.phwangAbend(this.objectName() + "." + str0_val + "()", str1_val); }
}
