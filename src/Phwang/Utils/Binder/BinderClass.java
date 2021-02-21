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
import Phwang.Utils.Tcp.*;
import Phwang.Utils.Queue.ListQueueClass;

public class BinderClass {
    private String objectName() {return "BinderClass";}

    private String ownerObject;
    private Thread receiveThread;
    private Thread transmitThread;
    private BinderReceiveRunnable receiveRunable;
    private BinderTransmitRunnable transmitRunable;
    
    //private NetworkStream networkStream;
    private String theServerIpAddress;
    private short thePort;
    private Socket theTcpConnection;
    private DataInputStream theInputStream;
    private DataOutputStream theOutputStream;
    
    private ListQueueClass receiveQueue;
    
    public short Port() { return this.thePort; }
    public String ServerIpAddress() { return this.theServerIpAddress; }
    public Socket TcpConnection() { return this.theTcpConnection; }
    private DataInputStream InputStream() { return this.theInputStream; }
    private DataOutputStream OutputStream() { return this.theOutputStream; }
    
    public String TcpClientName() { return (this.TcpConnection() != null) ? this.TcpConnection().getInetAddress().getHostName() : ""; }
    public String TcpClientAddress() { return (this.TcpConnection() != null) ? this.TcpConnection().getInetAddress().getHostAddress() : ""; }

    public BinderClass(String owner_object_var) {
        this.ownerObject = owner_object_var;
        this.receiveQueue = new ListQueueClass(true, 0);
    }

    public Boolean BindAsTcpClient(String ip_addr_val, short port_val) {
		this.thePort = port_val;
		this.theServerIpAddress = ip_addr_val;
    	try {
    		this.theTcpConnection = new Socket(this.ServerIpAddress(), this.Port());
    		this.debugIt(true, "BindAsTcpClient", "connected!");
            this.theOutputStream = new DataOutputStream(this.TcpConnection().getOutputStream());
            this.theInputStream = new DataInputStream(this.TcpConnection().getInputStream());
    		createWorkingThreads();
    		return true;
    	}
    	catch (Exception e) {
    		return false;
    	}
    }

    public Boolean BindAsTcpServer(short port_val) {
		this.thePort = port_val;
        //TcpApiClass.MallocTcpServer(this, port_val, binderTcpServerAcceptFunc /*, this, binderTcpReceiveDataFunc, this*/, this.objectName);
    	try {
    		ServerSocket ss = new ServerSocket(this.Port());
    		this.theTcpConnection = ss.accept();
    		this.debugIt(true, "BindAsTcpServer", "accepted!");
    		this.debugIt(false, "BindAsTcpServer", "clientAddress = " + this.TcpClientName());
    		this.debugIt(false, "BindAsTcpServer", "clientName = " + this.TcpClientAddress());
            this.theOutputStream = new DataOutputStream(this.TcpConnection().getOutputStream());
            this.theInputStream = new DataInputStream(this.TcpConnection().getInputStream());
            this.createWorkingThreads();

           
            ss.close();
    	}
    	catch (Exception e) {
    	}
        return true;
    }
    
    /*
    private void binderTcpServerAcceptFunc(Object d_fabric_object_val, NetworkStream netwrok_stream_val) {
        this.debugIt(true, "binderTcpServerAcceptFunc", "accepted!");
        //this.networkStream = netwrok_stream_val;
        this.createWorkingThreads();
    }
*/
    private void createWorkingThreads() {
        this.receiveRunable = new BinderReceiveRunnable(this);
        this.receiveThread = new Thread(this.receiveRunable);
        this.receiveThread.start();

        this.transmitRunable = new BinderTransmitRunnable(this);
        this.transmitThread = new Thread(this.transmitRunable);
        this.transmitThread.start();
    }

    public void binderReceiveThreadFunc() {
        this.debugIt(false, "binderReceiveThreadFunc", "start thread ***");
        
        return;//////////////////////////////////////////////////
        
        /*
        if (this.networkStream == null) {
            this.abendIt("receiveThreadFunc", "null networkStream");

        }
        String data;
        while (true)
        {
            data = PhwangUtils.TcpServerClass.TcpReceiveData(this.networkStream);
            if (data != null)
            {
                this.debugIt(false, "receiveThreadFunc", "data = " + data);
                this.receiveQueue.EnqueueData(data);
            }
            else
            {
                this.abendIt("receiveThreadFunc", "data is null=====================================");
                Thread.Sleep(1);
            }
        }
        */
    }

    public void binderTransmitThreadFunc() {
        this.debugIt(false, "binderTransmitThreadFunc", "start thread ***");
        
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
            this.debugIt(false, "ReceivData", "data = " + data);
        }
        return data;
    }

    public void TransmitRawData(String data_var) {
        this.debugIt(false, "TransmitData", "data = " + data_var);
        //TcpServerClass.TcpTransmitData(this.networkStream, data_var);
    }

    public void TransmitData(String data_var) {
        this.debugIt(false, "TransmitData", "data = " + data_var);
        this.TransmitRawData(data_var);
    }
    
    private void debugIt(Boolean on_off_val, String str0_val, String str1_val) {
        if (on_off_val)
            this.logitIt(str0_val, str1_val);
    }

    private void logitIt(String str0_val, String str1_val) {
        AbendClass.phwangLogit(this.objectName() + "." + str0_val + "()", str1_val);
    }

    public void abendIt(String str0_val, String str1_val) {
        AbendClass.phwangAbend(this.objectName() + "." + str0_val + "()", str1_val);
    }
}

class BinderReceiveRunnable implements Runnable
{
	BinderClass theBinderObject;
	
	public BinderReceiveRunnable(BinderClass binder_object_val) {
		this.theBinderObject = binder_object_val;
	}
	
	public void run() {
		this.theBinderObject.binderReceiveThreadFunc();
	}
}

class BinderTransmitRunnable implements Runnable
{
	BinderClass theBinderObject;
	
	public BinderTransmitRunnable(BinderClass binder_object_val) {
		this.theBinderObject = binder_object_val;
	}
	
	public void run() {
		this.theBinderObject.binderTransmitThreadFunc();
	}
}
