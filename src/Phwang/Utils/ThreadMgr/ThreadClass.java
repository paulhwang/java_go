/*
 ******************************************************************************
 *                                       
 *  Copyright (c) 2018 phwang. All rights reserved.
 *
 ******************************************************************************
 */

package Phwang.Utils.ThreadMgr;

import Phwang.Utils.AbendClass;

public class ThreadClass {
    private String objectName() {return "ThreadClass";}

    private String threadName;
    
    public String ThreadName( ) { return this.threadName; }
    
    public ThreadClass(String thread_name_val) {
        this.debugIt(false, "ThreadClass", "init start");
    	
        this.threadName = thread_name_val;
    }
    
    public void StartThread() {
        this.debugIt(true, "StartThread", "Create thread (" + this.ThreadName() + ")");
    	
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
