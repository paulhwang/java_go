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
    //private ManualResetEvent theSignal;
    private String theData;

    public FrontEndJobClass(String ajax_id_str_val)
    {
        this.ajaxIdStr = ajax_id_str_val;
        //this.theSignal = new ManualResetEvent(false);
    }

    public String ReadData() {
        //this.theSignal.WaitOne();
        String data = this.theData;
        while (data == null) {
            //this.abendIt("ReceiveData", "null data");
            UtilsClass.sleep(1000);
            continue;
        }
        return data;
    }

    public void WriteData(String data_val) {
        this.theData = data_val;
        //this.theSignal.Set();
    }

    private void debugIt(Boolean on_off_val, String str0_val, String str1_val) { if (on_off_val) this.logitIt(str0_val, str1_val); }
    private void logitIt(String str0_val, String str1_val) { AbendClass.phwangLogit(this.objectName() + "." + str0_val + "()", str1_val); }
    public void abendIt(String str0_val, String str1_val) { AbendClass.phwangAbend(this.objectName() + "." + str0_val + "()", str1_val); }
}
