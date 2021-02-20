/*
 ******************************************************************************
 *                                       
 *  Copyright (c) 2018 phwang. All rights reserved.
 *
 ******************************************************************************
 */

package Phwang.Utils.Binder;

import Phwang.Utils.AbendClass;

public class BinderTestClass {
    public BinderTestClass() {
        new BinderServerTestClass();
        new BinderClientTestClass();
    }
}

////////////////////////////////////////////////////////////////////////////////////////
//Server start here
////////////////////////////////////////////////////////////////////////////////////////

class BinderServerTestClass {
    private String objectName() {return "BinderServerTestClass";}

    private Thread serverThread;
    private BinderTestServerRunnable serverRunnable;

    public BinderServerTestClass() {
        this.debugIt(false, "BinderServerTestClass", "init start");
        
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

    private Thread clientThread;
    private BinderTestClientRunnable clientRunnable;
	
    public BinderClientTestClass() {
        this.debugIt(false, "BinderClientTestClass", "init start");
        
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
