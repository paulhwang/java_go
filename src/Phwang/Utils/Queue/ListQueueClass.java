/*
 ******************************************************************************
 *                                       
 *  Copyright (c) 2018 phwang. All rights reserved.
 *
 ******************************************************************************
 */

package Phwang.Utils.Queue;

import Phwang.Utils.AbendClass;

public class ListQueueClass {
    private String objectName() {return "ListQueueClass";}

    private int QUEUE_CLASS_DEFAULT_MAX_QUEUE_SIZE = 1000;
    private int QueueLength;
    private QueueEntryClass QueueHead;
    private QueueEntryClass QueueTail;
    private int MaxQueueLength;
    //private object theLock;
    //private ManualResetEvent theSignal;


    public int GetQueueLength()
    {
        return this.QueueLength;
    }

    public ListQueueClass(Boolean do_suspend_val, int max_length_val)
    {
        this.debugIt(true, "ListQueueClass", "init start");
        
        this.MaxQueueLength = max_length_val;
        //this.theLock = new object();


        if (do_suspend_val) {
            //this.theSignal = new ManualResetEvent(false);
        }
        else {
            //this.theSignal = null;
        }

        if (this.MaxQueueLength == 0) {
            this.MaxQueueLength = QUEUE_CLASS_DEFAULT_MAX_QUEUE_SIZE;
        }

        ///////////////////////this.TheMutex = new Mutex(true, "queue by phwang");
        ////////////////if (pthread_mutex_init(&this->theMutex, NULL) != 0) {
            ///////////////////this->abend("QueueClass", "pthread_mutex_init fail");
        ////////////////////}
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
