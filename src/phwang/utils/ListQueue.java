/*
 ******************************************************************************
 *                                       
 *  Copyright (c) 2021 phwang. All rights reserved.
 *
 ******************************************************************************
 */

package phwang.utils;

import java.util.concurrent.locks.*;
import phwang.utils.*;

public class ListQueue {
    private String objectName() {return "ListQueue";}

    private int DEFAULT_MAX_QUEUE_LENGTH = 1000;
    private int MAX_FREE_LIST_LENGTH = 100;
    private Boolean abendQueueIsOn = true;
    private Thread pendingThread;
    private int length_;
    private QueueEntryClass head;
    private QueueEntryClass tail;
    private int maxLength;
    private Lock queueLock_;
    private Lock pendingThreadLock_;
    private Lock theMallocLock;
    private QueueEntryClass freeEntryList;
    private int freeListLength;
    
    public int length() { return this.length_; }

    public ListQueue(Boolean do_suspend_val, int max_length_val) {
        this.debug(false, "ListQueue", "init start");
        
        this.maxLength = max_length_val;
        this.queueLock_ = new ReentrantLock();
        this.pendingThreadLock_ = new ReentrantLock();
        this.theMallocLock = new ReentrantLock();

        if (this.maxLength == 0) {
            this.maxLength = DEFAULT_MAX_QUEUE_LENGTH;
        }
    }

    public void enqueue(Object data_val) {
        this.debug(false, "enqueue", (String) data_val);

        QueueEntryClass entry = this.malloc();
        entry.data = data_val;
        
        this.abendQueue("enqueue begin");
    	this.queueLock_.lock();
        this.enqueue_(entry);
    	this.queueLock_.unlock();
    	
    	this.interruptPendingThread();
        this.abendQueue("enqueue end");
    }
    
    private void enqueue_(QueueEntryClass entry_val) {
        if (this.head == null) {
        	entry_val.next = null;
        	entry_val.prev = null;
            this.head = entry_val;
            this.tail = entry_val;
            this.length_ = 1;
        }
        else {
        	entry_val.next = null;
        	entry_val.prev = this.tail;
            this.tail.next = entry_val;
            this.tail = entry_val;
            this.length_++;
        }
    }

    public Object dequeue() {
        QueueEntryClass entry;

        this.abendQueue("dequeue start");
    	this.queueLock_.lock();
    	entry = this.dequeue_();
    	this.queueLock_.unlock();
        this.abendQueue("dequeue end");
    	
        if (entry == null) {
        	return null;
        }
        else {
            Object data = entry.data;
            this.free(entry);

            this.debug(false, "dequeue", "data=" + (String)data);
            return data;
        }
    }

    private QueueEntryClass dequeue_() {
        QueueEntryClass entry;

        if (this.length_ == 0) {
            return null;
        }

        if (this.length_ == 1) {
            entry = this.head;
            this.head = this.tail = null;
            this.length_ = 0;
            return entry;
        }

        entry = this.head;
        this.head = this.head.next;
        this.head.prev = null;
        this.length_--;

        return entry;
    }

    private void flush() {
        this.abendQueue("flush start");
    	this.queueLock_.lock();
    	this.flush_();
    	this.queueLock_.unlock();
        this.abendQueue("flush end");
    }

    private void flush_() {
        QueueEntryClass entry, entry_next;

        entry = this.head;
        while (entry != null) {
            entry_next = entry.next;
            entry.clear();
            this.length_--;
            entry = entry_next;
        }
        this.head = this.tail = null;

        if (this.length_ != 0) {
            this.abend("flush_", "length is not 0");
        }
    }

    private QueueEntryClass malloc() {
        QueueEntryClass entry;
        
    	this.theMallocLock.lock();
    	if (this.freeEntryList != null) {
    		entry = this.freeEntryList;
    		this.freeEntryList = this.freeEntryList.next;
    		this.freeListLength--;
    	}
    	else {
    		entry = null;
    	}
    	this.theMallocLock.unlock();
    	
    	if (entry != null) {
    		return entry;
    	}
    	else {
    		return new QueueEntryClass();
    	}
    }
    
    private void free(QueueEntryClass entry) {
    	if (this.freeListLength > MAX_FREE_LIST_LENGTH) {
    		entry.clear();
    		return;
    	}
    	
    	this.theMallocLock.lock();
    	entry.next = this.freeEntryList;
    	this.freeEntryList = entry;
    	this.freeListLength++;
    	this.theMallocLock.unlock();
    }
    
    private void releaseFreeEntryList() {
    	while (this.freeEntryList != null) {
    		QueueEntryClass entry = this.freeEntryList;
    		this.freeEntryList = this.freeEntryList.next;
    		entry.clear();
    	}
    }
    
    public void destructQueue() {
    	this.releaseFreeEntryList();
    }
    
    private void abendQueue (String msg_val) {
    	if (!this.abendQueueIsOn)
    		return;
    	
    	this.queueLock_.lock();
    	this.abendQueue_(msg_val);
    	this.queueLock_.unlock();
    }

    private void abendQueue_(String msg_val) {
        QueueEntryClass entry;
        int length;

        if (this.length_ == 0) {
            if ((this.head != null) || (this.tail != null)) {
                this.abend("abendQueue_", msg_val + " length_ == 0");
            }
            return;
        }

        length = 0;
        entry = this.head;
        while (entry != null) {
            length++;
            entry = entry.next;
        }

        if (length != this.length_) {
            this.abend("abendQueue_", msg_val + " from head: bad length");
        }

        length = 0;
        entry = this.tail;
        while (entry != null) {
            length++;
            entry = entry.prev;
        }
        if (length != this.length_) {
           this.abend("abendQueue_", msg_val + " from tail: bad length");
        }
    }

    public void setPendingThread(Thread thread_val) {
    	this.pendingThreadLock_.lock();
    	this.pendingThread = thread_val;
    	this.pendingThreadLock_.unlock();
    }
    
    private void interruptPendingThread() {
    	this.pendingThreadLock_.lock();
    	
    	if (this.pendingThread != null) {
    		pendingThread.interrupt();
    		this.pendingThread = null;
    	}
    	
    	this.pendingThreadLock_.unlock();
    }
    
    private void debug(Boolean on_off, String s0, String s1) { if (on_off) this.log(s0, s1); }
    private void log(String s0, String s1) { AbendClass.log(this.objectName() + "." + s0 + "()", s1); }
    public void abend(String s0, String s1) { AbendClass.abend(this.objectName() + "." + s0 + "()", s1); }
}

class QueueEntryClass {
    public QueueEntryClass next;
    public QueueEntryClass prev;
    public Object data;
    
    public void clear() {
    	this.next = null;
    	this.prev = null;
    	this.data = null;
    }
}
