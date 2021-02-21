/*
 ******************************************************************************
 *                                       
 *  Copyright (c) 2018 phwang. All rights reserved.
 *
 ******************************************************************************
 */

package Phwang.Utils.Binder;

import java.net.*;
import java.io.*;

import Phwang.Utils.UtilsClass;
import Phwang.Utils.AbendClass;

public class BinderTestClass {
    private Boolean useBinder;
	private String host = "localhost";
	private short port = 8001;
	
    public BinderTestClass() {
    	this.useBinder = true;
        new BinderServerTestClass(this.useBinder, this.port);
        UtilsClass.sleep(1000);
        new BinderClientTestClass(this.useBinder, this.host, this.port);
    }
}

////////////////////////////////////////////////////////////////////////////////////////
//Server start here
////////////////////////////////////////////////////////////////////////////////////////

class BinderServerTestClass {
    private String objectName() {return "BinderServerTestClass";}

    private Boolean useBinder;
	private short thePort;
	private String clientName;
	private String clientAddress;
	private DataInputStream inputStream;
    private DataOutputStream outputStream;
    private String inputMessage;
    private BinderClass theBinderObject;
    private Thread serverThread;
    private BinderTestServerRunnable serverRunnable;
    
    private short Port() { return this.thePort; }
    private BinderClass BinderObject() { return this.theBinderObject; }

    public BinderServerTestClass(Boolean use_binder_val, short port_val) {
        this.debugIt(false, "BinderServerTestClass", "init start");
        
        this.useBinder = use_binder_val;
        this.thePort = port_val;
        this.createServerThread();
    }

    private void createServerThread() {
        this.serverRunnable = new BinderTestServerRunnable(this);
        this.serverThread = new Thread(this.serverRunnable);
        this.serverThread.start();
    }
    
    public void binderTestServerThreadFunc() {
        this.debugIt(true, "binderTestServerThreadFunc", "start thread ***");
        
        if (this.useBinder) {
        	this.theBinderObject = new BinderClass("BinderTestServer");
        	if (this.BinderObject().BindAsTcpServer(this.Port())) {
        		this.BinderObject().TransmitData("Welcome!!");
        		String data = this.BinderObject().ReceiveData();
                this.debugIt(true, "binderTestServerThreadFunc", "received data = " + data);
        	}
        }
        else {
        	try {
        		ServerSocket ss = new ServerSocket(this.Port());
        		Socket connection = ss.accept();
        		this.debugIt(true, "binderTestServerThreadFunc", "accepted");
        		this.clientName = connection.getInetAddress().getHostName();
        		this.clientAddress = connection.getInetAddress().getHostAddress();
        		this.debugIt(true, "binderTestServerThreadFunc", "clientAddress = " + this.clientAddress);
        		this.debugIt(true, "binderTestServerThreadFunc", "clientName = " + this.clientName);
                ss.close();

                inputStream = new DataInputStream(connection.getInputStream());
                String data = inputStream.readUTF();
                this.debugIt(true, "binderTestServerThreadFunc", "received data = " + data);
                outputStream = new DataOutputStream(connection.getOutputStream());
                outputStream.writeUTF("Welcome!");
        	}
        	catch (Exception e) {
        	}
        }
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

class BinderTestServerRunnable implements Runnable {
	BinderServerTestClass theBinderTestObject;
	
	public BinderTestServerRunnable(BinderServerTestClass binder_test_object_val) {
		this.theBinderTestObject = binder_test_object_val;
	}
	
	public void run() {
		this.theBinderTestObject.binderTestServerThreadFunc();
	}
}

////////////////////////////////////////////////////////////////////////////////////////
//Client start here
////////////////////////////////////////////////////////////////////////////////////////

class BinderClientTestClass {
    private String objectName() {return "BinderClientTestClass";}

    private Boolean useBinder;
    private String theHost;
	private short thePort;
    private DataInputStream inputStream;
    private DataOutputStream outputStream;
    private Thread clientThread;
    private BinderClass theBinderObject;
    private BinderTestClientRunnable clientRunnable;

    private String Host() { return theHost; }
    private short Port() { return this.thePort; }
    private BinderClass BinderObject() { return this.theBinderObject; }

    public BinderClientTestClass(Boolean use_binder_val, String host_val, short port_val) {
        this.debugIt(false, "BinderClientTestClass", "init start");
        
        this.useBinder = use_binder_val;
        this.theHost = host_val;
        this.thePort = port_val;
        this.createClientThread();
    }
    
    private void createClientThread() {
        this.clientRunnable = new BinderTestClientRunnable(this);
        this.clientThread = new Thread(this.clientRunnable);
        this.clientThread.start();
    }
    
    public void binderTestClientThreadFunc() {
        this.debugIt(true, "binderTestClientThreadFunc", "start thread ***");
    	
        if (this.useBinder) {
        	this.theBinderObject = new BinderClass("BinderTestServer");
        	if (this.theBinderObject.BindAsTcpClient(this.Host(), this.Port())) {
        		this.BinderObject().TransmitData("Hello!!");
        		String data = this.BinderObject().ReceiveData();
                this.debugIt(true, "binderTestClientThreadFunc", "received data = " + data);
        	}
      	
        }
        else {
        	try {
        		Socket connection = new Socket(this.Host(), this.Port());
        		this.debugIt(true, "binderTestClientThreadFunc", "cconnected");
        		
                outputStream = new DataOutputStream(connection.getOutputStream());
                outputStream.writeUTF("Hello!");
                inputStream = new DataInputStream(connection.getInputStream());
                String data = inputStream.readUTF();
                this.debugIt(true, "binderTestClientThreadFunc", "received data = " + data);
                
        	}
        	catch (Exception e) {
        	}
        }
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

class BinderTestClientRunnable implements Runnable {
	BinderClientTestClass theBinderTestObject;
	
	public BinderTestClientRunnable(BinderClientTestClass binder_test_object_val) {
		this.theBinderTestObject = binder_test_object_val;
	}
	
	public void run() {
		this.theBinderTestObject.binderTestClientThreadFunc();
	}
}
