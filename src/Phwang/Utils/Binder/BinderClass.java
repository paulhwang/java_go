/*
 ******************************************************************************
 *                                       
 *  Copyright (c) 2018 phwang. All rights reserved.
 *
 ******************************************************************************
 */

package Phwang.Utils.Binder;

import Phwang.Utils.AbendClass;
import Phwang.Utils.Queue.ListQueueClass;

public class BinderClass {
    private String objectName() {return "BinderClass";}

    private ListQueueClass receiveQueue;
    private String ownerObject;
    //private NetworkStream networkStream;
    //private Thread receiveThread { get; set; }
    //private Thread transmitThread { get; set; }


    public BinderClass(String owner_object_var)
    {
        this.ownerObject = owner_object_var;
        this.receiveQueue = new ListQueueClass(true, 0);
    }


    private void debugIt(Boolean on_off_val, String str0_val, String str1_val)
    {
        if (on_off_val)
            this.logitIt(str0_val, str1_val);
    }

    private void logitIt(String str0_val, String str1_val)
    {
        AbendClass.phwangLogit(this.objectName() + "." + str0_val + "()", str1_val);
    }

    private void abendIt(String str0_val, String str1_val)
    {
        AbendClass.phwangAbend(this.objectName() + "." + str0_val + "()", str1_val);
    }
}
