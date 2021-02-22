/*
 ******************************************************************************
 *                                       
 *  Copyright (c) 2018 phwang. All rights reserved.
 *
 ******************************************************************************
 */

package Phwang.Utils.ThreadMgr;

import Phwang.Engine.DEngine.DEngineClass;
import Phwang.Utils.AbendClass;

public class ThreadClass {
    private String objectName() {return "ThreadClass";}

    private String threadName;
    private Thread theThread;
    private ThreadClassRunnable theRunnable;
    
    public String ThreadName( ) { return this.threadName; }
    
    public ThreadClass(String thread_name_val) {
        this.debugIt(false, "ThreadClass", "init start");
    	
        this.threadName = thread_name_val;
    }
    
    public void StartThread(ThreadInterface calling_object_val) {
        this.debugIt(false, "StartThread", "Create thread (" + this.ThreadName() + ")");
        this.theRunnable = new ThreadClassRunnable(calling_object_val);
        this.theThread = new Thread(this.theRunnable);
        this.theThread.start();
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

class ThreadClassRunnable implements Runnable {
	private ThreadInterface callingObject;
	
	public ThreadClassRunnable(ThreadInterface interface_val) {
		this.callingObject = interface_val;
	}
	
	public void run() {
		this.callingObject.ThreadCallbackFunction();
	}
}
