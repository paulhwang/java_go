/*
 ******************************************************************************
 *                                       
 *  Copyright (c) 2018 phwang. All rights reserved.
 *
 ******************************************************************************
 */

package phwang.utils;

import java.util.concurrent.locks.*;
import phwang.utils.*;

public class ListQueueClass {
    private String objectName() {return "ListQueueClass";}

    private int QUEUE_CLASS_DEFAULT_MAX_QUEUE_SIZE = 1000;
    private Boolean abendQueueIsOn = true;
    private Thread pendingThread;
    private int length_;
    private QueueEntryClass QueueHead;
    private QueueEntryClass QueueTail;
    private int maxLength;
    private Lock theLock;

    public void setPendingThread(Thread thread_val) { this.pendingThread = thread_val; }
    public int length() { return this.length_; }

    public ListQueueClass(Boolean do_suspend_val, int max_length_val) {
        this.debugIt(false, "ListQueueClass", "init start");
        
        this.maxLength = max_length_val;
        this.theLock = new ReentrantLock();

        if (this.maxLength == 0) {
            this.maxLength = QUEUE_CLASS_DEFAULT_MAX_QUEUE_SIZE;
        }
    }

    public void enqueueData(Object data_val) {
        this.debugIt(false, "enqueueData", (String) data_val);

        QueueEntryClass entry = new QueueEntryClass();
        entry.data = data_val;
        
        this.abendQueue("enqueueData begin");
    	this.theLock.lock();
        this.enqueue_(entry);
    	this.theLock.unlock();
    	if (this.pendingThread != null) {
    		pendingThread.interrupt();
    		this.pendingThread = null;
    	}
        this.abendQueue("enqueueData end");
    }
    
    private void enqueue_(QueueEntryClass entry_val) {
        if (this.QueueHead == null) {
        	entry_val.next = null;
        	entry_val.prev = null;
            this.QueueHead = entry_val;
            this.QueueTail = entry_val;
            this.length_ = 1;
        }
        else {
        	entry_val.next = null;
        	entry_val.prev = this.QueueTail;
            this.QueueTail.next = entry_val;
            this.QueueTail = entry_val;
            this.length_++;
        }
    }

    public Object dequeueData() {
        QueueEntryClass entry;

        this.abendQueue("dequeueData start");
    	this.theLock.lock();
    	entry = this.dequeue_();
    	this.theLock.unlock();
        this.abendQueue("dequeueData end");
    	
        if (entry == null) {
        	return null;
        }
        else {
            Object data = entry.data;
            entry.resetQueueEntry();

            this.debugIt(false, "dequeueData", "data = " + (String)data);
            return data;
        }
    }

    private QueueEntryClass dequeue_() {
        QueueEntryClass entry;

        if (this.length_ == 0) {
            return null;
        }

        if (this.length_ == 1) {
            entry = this.QueueHead;
            this.QueueHead = this.QueueTail = null;
            this.length_ = 0;
            return entry;
        }

        entry = this.QueueHead;
        this.QueueHead = this.QueueHead.next;
        this.QueueHead.prev = null;
        this.length_--;

        return entry;
    }

    private void flushQueue() {
        this.abendQueue("flushQueue start");
    	this.theLock.lock();
    	this.flush_();
    	this.theLock.unlock();
        this.abendQueue("flushQueue end");
    }

    private void flush_() {
        QueueEntryClass entry, entry_next;

        entry = this.QueueHead;
        while (entry != null) {
            entry_next = entry.next;
            entry.resetQueueEntry();
            this.length_--;
            entry = entry_next;
        }
        this.QueueHead = this.QueueTail = null;

        if (this.length_ != 0) {
            this.abendIt("doFlushQueue", "theQueueSize");
        }
    }

    private void abendQueue (String msg_val) {
    	if (!this.abendQueueIsOn)
    		return;
    	
    	this.theLock.lock();
    	this.doAbendQueue();
    	this.theLock.unlock();
    }

    private void doAbendQueue() {
        QueueEntryClass entry;
        int length;

        if (this.length_ == 0) {
            if ((this.QueueHead != null) || (this.QueueTail != null)) {
                this.abendIt("doAbendQueue", "theQueueSize == 0");
            }
            return;
        }

        length = 0;
        entry = this.QueueHead;
        while (entry != null) {
            length++;
            entry = entry.next;
        }

        if (length != this.length_) {
            this.abendIt("doAbendQueue", "from head: bad length");
        }

        length = 0;
        entry = this.QueueTail;
        while (entry != null) {
            length++;
            entry = entry.prev;
        }
        if (length != this.length_) {
           this.abendIt("doAbendQueue", "from tail: bad length");
        }
    }

    private void debugIt(Boolean on_off_val, String str0_val, String str1_val) { if (on_off_val) this.logitIt(str0_val, str1_val); }
    private void logitIt(String str0_val, String str1_val) { AbendClass.phwangLogit(this.objectName() + "." + str0_val + "()", str1_val); }
    public void abendIt(String str0_val, String str1_val) { AbendClass.phwangAbend(this.objectName() + "." + str0_val + "()", str1_val); }
}

class QueueEntryClass {
    public QueueEntryClass next;
    public QueueEntryClass prev;
    public Object data;
    
    public void resetQueueEntry() {
    	this.next = null;
    	this.prev = null;
    	this.data = null;
    }
}
