/*
 ******************************************************************************
 *                                       
 *  Copyright (c) 2018 phwang. All rights reserved.
 *
 ******************************************************************************
 */

package Phwang.Utils.Queue;

import java.util.concurrent.locks.*;
import Phwang.Utils.AbendClass;

public class ListQueueClass {
    private String objectName() {return "ListQueueClass";}

    private int QUEUE_CLASS_DEFAULT_MAX_QUEUE_SIZE = 1000;
    private Boolean abendQueueIsOn = true;
    private int theQueueLength;
    private QueueEntryClass QueueHead;
    private QueueEntryClass QueueTail;
    private int MaxQueueLength;
    private Lock theLock;

    public int QueueLength() { return this.theQueueLength; }

    public ListQueueClass(Boolean do_suspend_val, int max_length_val)
    {
        this.debugIt(false, "ListQueueClass", "init start");
        
        this.MaxQueueLength = max_length_val;
        this.theLock = new ReentrantLock();

        if (this.MaxQueueLength == 0) {
            this.MaxQueueLength = QUEUE_CLASS_DEFAULT_MAX_QUEUE_SIZE;
        }
    }

    public void EnqueueData(Object data_val) {
        this.debugIt(false, "EnqueueData", (String) data_val);

        QueueEntryClass entry = new QueueEntryClass();
        entry.data = data_val;
        
        this.AbendQueue("enqueueData begin");
    	this.theLock.lock();
        this.EnqueueEntry(entry);
    	this.theLock.unlock();
        this.AbendQueue("enqueueData end");
    }
    
    private void EnqueueEntry(QueueEntryClass entry_val) {
        if (this.QueueHead == null) {
        	entry_val.next = null;
        	entry_val.prev = null;
            this.QueueHead = entry_val;
            this.QueueTail = entry_val;
            this.theQueueLength = 1;
        }
        else {
        	entry_val.next = null;
        	entry_val.prev = this.QueueTail;
            this.QueueTail.next = entry_val;
            this.QueueTail = entry_val;
            this.theQueueLength++;
        }
    }

    public Object DequeueData() {
        QueueEntryClass entry;

        this.AbendQueue("DequeueData start");
    	this.theLock.lock();
    	entry = this.dequeueEntry();
    	this.theLock.unlock();
        this.AbendQueue("DequeueData end");
    	
        if (entry == null) {
        	return null;
        }
        else {
            Object data = entry.data;
            entry.ResetQueueEntry();

            this.debugIt(false, "DequeueData", "data = " + (String)data);
            return data;
        }
    }

    private QueueEntryClass dequeueEntry() {
        QueueEntryClass entry;

        if (this.theQueueLength == 0) {
            return null;
        }

        if (this.theQueueLength == 1) {
            entry = this.QueueHead;
            this.QueueHead = this.QueueTail = null;
            this.theQueueLength = 0;
            return entry;
        }

        entry = this.QueueHead;
        this.QueueHead = this.QueueHead.next;
        this.QueueHead.prev = null;
        this.theQueueLength--;

        return entry;
    }

    private void FlushQueue() {
        this.AbendQueue("FlushQueue start");
    	this.theLock.lock();
    	this.DoFlushQueue();
    	this.theLock.unlock();
        this.AbendQueue("FlushQueue end");
    }

    private void DoFlushQueue() {
        QueueEntryClass entry, entry_next;

        entry = this.QueueHead;
        while (entry != null) {
            entry_next = entry.next;
            entry.ResetQueueEntry();
            this.theQueueLength--;
            entry = entry_next;
        }
        this.QueueHead = this.QueueTail = null;

        if (this.theQueueLength != 0) {
            this.abendIt("DoFlushQueue", "theQueueSize");
        }
    }

    private void AbendQueue (String msg_val) {
    	if (!this.abendQueueIsOn)
    		return;
    	
    	this.theLock.lock();
    	this.DoAbendQueue();
    	this.theLock.unlock();
    }

    private void DoAbendQueue() {
        QueueEntryClass entry;
        int length;

        if (this.theQueueLength == 0) {
            if ((this.QueueHead != null) || (this.QueueTail != null)) {
                this.abendIt("DoAbendQueue", "theQueueSize == 0");
            }
            return;
        }

        length = 0;
        entry = this.QueueHead;
        while (entry != null) {
            length++;
            entry = entry.next;
        }

        if (length != this.theQueueLength) {
            this.abendIt("DoAbendQueue", "from head: bad length");
        }

        length = 0;
        entry = this.QueueTail;
        while (entry != null) {
            length++;
            entry = entry.prev;
        }
        if (length != this.theQueueLength) {
           this.abendIt("DoAbendQueue", "from tail: bad length");
        }
    }

    private void debugIt(Boolean on_off_val, String str0_val, String str1_val) { if (on_off_val) this.logitIt(str0_val, str1_val); }
    private void logitIt(String str0_val, String str1_val) { AbendClass.phwangLogit(this.objectName() + "." + str0_val + "()", str1_val); }
    public void abendIt(String str0_val, String str1_val) { AbendClass.phwangAbend(this.objectName() + "." + str0_val + "()", str1_val); }
}
