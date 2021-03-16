/*
 ******************************************************************************
 *                                       
 *  Copyright (c) 2021 phwang. All rights reserved.
 *
 ******************************************************************************
 */

package com.phwang.core.utils;

import java.net.*;
import java.io.*;

import com.phwang.core.utils.*;

public class BinderTestClass {
    private Boolean useBinder;
	private String host = "localhost";
	private short port = 8001;
	
    public BinderTestClass() {
    	this.useBinder = true;
        new BinderServerTestClass(this.useBinder, this.port);
        Utils.sleep(1000);
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
    private Binder theBinderObject;
    private Thread serverThread;
    private BinderTestServerRunnable serverRunnable;
    
    private short Port() { return this.thePort; }
    private Binder BinderObject() { return this.theBinderObject; }

    public BinderServerTestClass(Boolean use_binder_val, short port_val) {
        this.debug(false, "BinderServerTestClass", "init start");
        
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
        this.debug(true, "binderTestServerThreadFunc", "start thread ***");
        
        if (this.useBinder) {
        	this.theBinderObject = new Binder("BinderTestServer");
        	if (this.BinderObject().bindAsTcpServer(true, this.Port(), true)) {
        		this.BinderObject().transmitStringData("Welcome!!");
        		String data = this.BinderObject().receiveStringData();
                this.debug(true, "binderTestServerThreadFunc", "received data = " + data);
        	}
        }
        else {
        	try {
        		ServerSocket ss = new ServerSocket(this.Port());
        		Socket connection = ss.accept();
        		this.debug(true, "binderTestServerThreadFunc", "accepted");
        		this.clientName = connection.getInetAddress().getHostName();
        		this.clientAddress = connection.getInetAddress().getHostAddress();
        		this.debug(true, "binderTestServerThreadFunc", "clientAddress = " + this.clientAddress);
        		this.debug(true, "binderTestServerThreadFunc", "clientName = " + this.clientName);
                ss.close();

                inputStream = new DataInputStream(connection.getInputStream());
                String data = inputStream.readUTF();
                this.debug(true, "binderTestServerThreadFunc", "received data = " + data);
                outputStream = new DataOutputStream(connection.getOutputStream());
                outputStream.writeUTF("Welcome!");
        	}
        	catch (Exception e) {
        	}
        }
    }
    
    private void debug(Boolean on_off, String s0, String s1) { if (on_off) this.log(s0, s1); }
    private void log(String s0, String s1) { Abend.log(this.objectName() + "." + s0 + "()", s1); }
    public void abend(String s0, String s1) { Abend.abend(this.objectName() + "." + s0 + "()", s1); }
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
    private Binder theBinderObject;
    private BinderTestClientRunnable clientRunnable;

    private String Host() { return theHost; }
    private short Port() { return this.thePort; }
    private Binder BinderObject() { return this.theBinderObject; }

    public BinderClientTestClass(Boolean use_binder_val, String host_val, short port_val) {
        this.debug(false, "BinderClientTestClass", "init start");
        
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
        this.debug(true, "binderTestClientThreadFunc", "start thread ***");
    	
        if (this.useBinder) {
        	this.theBinderObject = new Binder("BinderTestServer");
        	if (this.theBinderObject.bindAsTcpClient(false, this.Host(), this.Port())) {
        		this.BinderObject().transmitStringData("Hello!!");
        		String data = this.BinderObject().receiveStringData();
                this.debug(true, "binderTestClientThreadFunc", "received data = " + data);
        	}
      	
        }
        else {
        	try {
        		Socket connection = new Socket(this.Host(), this.Port());
        		this.debug(true, "binderTestClientThreadFunc", "cconnected");
        		
                outputStream = new DataOutputStream(connection.getOutputStream());
                outputStream.writeUTF("Hello!");
                inputStream = new DataInputStream(connection.getInputStream());
                String data = inputStream.readUTF();
                this.debug(true, "binderTestClientThreadFunc", "received data = " + data);
                
        	}
        	catch (Exception e) {
        	}
        }
    }
    
    private void debug(Boolean on_off, String s0, String s1) { if (on_off) this.log(s0, s1); }
    private void log(String s0, String s1) { Abend.log(this.objectName() + "." + s0 + "()", s1); }
    public void abend(String s0, String s1) { Abend.abend(this.objectName() + "." + s0 + "()", s1); }
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
