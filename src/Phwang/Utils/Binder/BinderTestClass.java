/*
 ******************************************************************************
 *                                       
 *  Copyright (c) 2018 phwang. All rights reserved.
 *
 ******************************************************************************
 */

package Phwang.Utils.Binder;

import Phwang.Utils.UtilsClass;
import Phwang.Utils.AbendClass;

public class BinderTestClass {
	private int port = 8001;
	
    public BinderTestClass() {
        new BinderServerTestClass(this.port);
        UtilsClass.sleep(1000);
        new BinderClientTestClass(this.port);
    }
}

////////////////////////////////////////////////////////////////////////////////////////
//Server start here
////////////////////////////////////////////////////////////////////////////////////////

class BinderServerTestClass {
    private String objectName() {return "BinderServerTestClass";}

	private int port;
    private Thread serverThread;
    private BinderTestServerRunnable serverRunnable;

    public BinderServerTestClass(int port_val) {
        this.debugIt(false, "BinderServerTestClass", "init start");
        
        this.port = port_val;
        this.createServerThread();
    }

    private void createServerThread() {
        this.serverRunnable = new BinderTestServerRunnable(this);
        this.serverThread = new Thread(this.serverRunnable);
        this.serverThread.start();
    }
    
    public void binderTestServerThreadFunc() {
        this.debugIt(true, "binderTestServerThreadFunc", "start thread ***");
        
        
    	
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

	private int port;
    private Thread clientThread;
    private BinderTestClientRunnable clientRunnable;
	
    public BinderClientTestClass(int port_val) {
        this.debugIt(false, "BinderClientTestClass", "init start");
        
        this.port = port_val;
        this.createClientThread();
    }
    
    private void createClientThread() {
        this.clientRunnable = new BinderTestClientRunnable(this);
        this.clientThread = new Thread(this.clientRunnable);
        this.clientThread.start();
    }
    
    public void binderTestClientThreadFunc() {
        this.debugIt(true, "binderTestClientThreadFunc", "start thread ***");
    	
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
