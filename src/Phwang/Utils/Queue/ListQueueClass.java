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
    private Object theLock;
    //private ManualResetEvent theSignal;


    public int GetQueueLength()
    {
        return this.QueueLength;
    }

    public ListQueueClass(Boolean do_suspend_val, int max_length_val)
    {
        this.debugIt(false, "ListQueueClass", "init start");
        
        this.MaxQueueLength = max_length_val;
        this.theLock = new Object();


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

    public void EnqueueData(Object data_val)
    {
        this.debugIt(false, "EnqueueData", (String) data_val);

        /* queue is too big */
        if ((this.MaxQueueLength != 0) && (this.QueueLength > this.MaxQueueLength))
        {
            //phwangFree(data_val, "QueueClass::enqueueData");
            //this->abend("enqueueData", "queue full");
            return;
        }

        QueueEntryClass entry = new QueueEntryClass();
        if (entry == null)
        {
            //this->abend("enqueueData", "fail to create new QueueEntryClass");
            return;
        }
        //entry.data = data_val;

        this.AbendQueue("enqueueData begin");
        //lock (this.theLock)
        {
            this.EnqueueEntry(entry);
            //if (this.theSignal != null)
            {
                //this.theSignal.Set();
            }
        }

        this.AbendQueue("enqueueData end");

        this.debugIt(false, "EnqueueData", "done");
    }
    
    private void EnqueueEntry(QueueEntryClass entry)
    {
        if (this.QueueHead == null)
        {
            entry.next = null;
            entry.prev = null;
            this.QueueHead = entry;
            this.QueueTail = entry;
            this.QueueLength = 1;
        }
        else
        {
            entry.next = null;
            entry.prev = this.QueueTail;
            this.QueueTail.next = entry;
            this.QueueTail = entry;
            this.QueueLength++;
        }
    }

    public Object DequeueData()
    {
        QueueEntryClass entry;

        while (true)
        {
            if (this.QueueHead == null)
            {
                //if (this.theSignal == null)
                {
                    //return null;
                }
                //this.theSignal.WaitOne();
            }
            else
            {
                this.AbendQueue("dequeueData begin");
                //lock (this.theLock)
                {
                    entry = this.dequeueEntry();
                }
                this.AbendQueue("dequeueData end");

                if (entry != null)
                {
                    //Object data = entry.data;
                    entry = null;

                    //this.debugIt(false, "DequeueData", "data = " + (String)data);
                    //return data;
                }
            }
        }

    }

    private QueueEntryClass dequeueEntry()
    {
        QueueEntryClass entry;

        if (this.QueueLength == 0)
        {
            this.abendIt("dequeueEntry", "QueueLength == 0");
            return null;
        }

        if (this.QueueLength == 1)
        {
             entry = this.QueueHead;
            this.QueueHead = this.QueueTail = null;
            this.QueueLength = 0;
            return entry;
        }

        entry = this.QueueHead;
        this.QueueHead = this.QueueHead.next;
        this.QueueHead.prev = null;
        this.QueueLength--;

        return entry;
    }

    private void AbendQueue (String msg_val)
    {
        QueueEntryClass entry;
        int length;

        if (this == null)
        {
           // this->abend("abendQueue", "null this");
            return;
        }

        if (this.QueueLength == 0)
        {
            if ((this.QueueHead != null) || (this.QueueTail != null))
            {
                //this->abend("abendQueue", "theQueueSize == 0");
                return;
            }
        }
        else
        {
            if (this.QueueHead != null)
            {
                //this->abend("abendQueue", "null theQueueHead");
                return;
            }
        }

        //lock (this.theLock)
        {
            length = 0;
            entry = this.QueueHead;
            while (entry != null)
            {
                length++;
                entry = entry.next;
            }

            if (length != this.QueueLength)
            {
                //printf("%s length=%d %d\n", msg_val, length, this->theQueueSize);
                //this->abend("abendQueue", "from head: bad length");
            }

            length = 0;
            entry = this.QueueTail;
            while (entry != null)
            {
                length++;
                entry = entry.prev;
            }

            if (length != this.QueueLength)
            {
                //printf("%s length=%d %d\n", msg_val, length, this->theQueueSize);
                //this->abend("abendQueue", "from tail: bad length");
            }
        }
    }

    private void FlushQueue ()
    {
        QueueEntryClass entry, entry_next;

        //pthread_mutex_lock(&this->theMutex);
        entry = this.QueueHead;
        while (entry != null)
        {
            entry_next = entry.next;
            //phwangFree(entry->data, "QueueClass::flushQueue");
            //delete entry;
            this.QueueLength--;
            entry = entry_next;
        }
        this.QueueHead = this.QueueTail = null;

        if (this.QueueLength != 0)
        {
            //this->abend("flushQueue", "theQueueSize");
        }

        //pthread_mutex_unlock(&this->theMutex);
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
