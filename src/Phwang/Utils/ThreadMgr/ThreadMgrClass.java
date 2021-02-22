/*
 ******************************************************************************
 *                                       
 *  Copyright (c) 2018 phwang. All rights reserved.
 *
 ******************************************************************************
 */

package Phwang.Utils.ThreadMgr;

import Phwang.Utils.AbendClass;

public class ThreadMgrClass {
    private String objectName() {return "ThreadMgrClass";}

    public ThreadMgrClass() {
        this.debugIt(false, "ThreadMgrClass", "init start");
    	
    }

    public ThreadClass CreateThreadObject(String thread_name_val) {
    	ThreadClass thread_object = new ThreadClass(thread_name_val);
    	thread_object.StartThread();
    	return thread_object;
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
