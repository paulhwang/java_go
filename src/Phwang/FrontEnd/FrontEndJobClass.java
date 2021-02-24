/*
 ******************************************************************************
 *                                       
 *  Copyright (c) 2018 phwang. All rights reserved.
 *
 ******************************************************************************
 */

package Phwang.FrontEnd;

import Phwang.Utils.AbendClass;
import Phwang.Utils.UtilsClass;

public class FrontEndJobClass {
    private String objectName() {return "FrontEndJobClass";}

    public String ajaxIdStr;
    private String theData;
    private Thread pendingThread;

    public FrontEndJobClass(String ajax_id_str_val) {
        this.ajaxIdStr = ajax_id_str_val;
    }

    public String ReadData() {
        while (this.theData == null) {
        	try {
                this.debugIt(true, "ReceiveData", "***sleep");
                this.pendingThread = Thread.currentThread();
        		Thread.sleep(10000);
        	}
        	catch (InterruptedException e) {
        	}
            continue;
        }
        this.debugIt(false, "ReceiveData", "theData=" + this.theData);
        return this.theData;
    }

    public void WriteData(String data_val) {
        this.theData = data_val;
        if (this.pendingThread != null) {
        	this.pendingThread.interrupt();
        }
    }

    private void debugIt(Boolean on_off_val, String str0_val, String str1_val) { if (on_off_val) this.logitIt(str0_val, str1_val); }
    private void logitIt(String str0_val, String str1_val) { AbendClass.phwangLogit(this.objectName() + "." + str0_val + "()", str1_val); }
    public void abendIt(String str0_val, String str1_val) { AbendClass.phwangAbend(this.objectName() + "." + str0_val + "()", str1_val); }
}
