/*
 ******************************************************************************
 *                                       
 *  Copyright (c) 2018 phwang. All rights reserved.
 *
 ******************************************************************************
 */

package Phwang.Utils.Binder;

import Phwang.Engine.DEngine.DEngineClass;
import java.net.*;
import Phwang.Utils.AbendClass;
import Phwang.Utils.Tcp.*;
import Phwang.Utils.Queue.ListQueueClass;

public class BinderClass {
    private String objectName() {return "BinderClass";}

    private ListQueueClass receiveQueue;
    private String ownerObject;
    //private NetworkStream networkStream;
    private Thread receiveThread;
    private Thread transmitThread;
    private BinderReceiveRunnable receiveRunable;
    private BinderTransmitRunnable transmitRunable;


    public BinderClass(String owner_object_var) {
        this.ownerObject = owner_object_var;
        this.receiveQueue = new ListQueueClass(true, 0);
    }

    public Boolean BindAsTcpClient(String ip_addr_var, short port_var) {
        //TcpClient client = new TcpClient(ip_addr_var, port_var);
    	try {
    		Socket connection = new Socket(ip_addr_var, port_var);
    		this.debugIt(true, "BindAsTcpClient", "connected!");
    		//this.networkStream = client.GetStream();
    		//createWorkingThreads();
    		return true;
    	}
    	catch (Exception e) {
    		return false;
    	}
    }

    public Boolean BindAsTcpServer(short port_val) {
        //TcpApiClass.MallocTcpServer(this, port_val, binderTcpServerAcceptFunc /*, this, binderTcpReceiveDataFunc, this*/, this.objectName);
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
        this.debugIt(true, "binderReceiveThreadFunc", "start thread ***");
        
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
        this.debugIt(true, "binderTransmitThreadFunc", "start thread ***");
        
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
