/*
 ******************************************************************************
 *                                       
 *  Copyright (c) 2021 phwang. All rights reserved.
 *
 ******************************************************************************
 */

package com.phwang.core.utils;

import java.util.concurrent.locks.*;
import com.phwang.core.utils.Abend;

public class ListQueue {
    private String objectName() {return "ListQueue";}

    private static QueueEntryClass freeEntryList;
    private static int freeListLength;
    private static Lock freeListLock = new ReentrantLock();
    private static int MAX_FREE_LIST_LENGTH = 1000;

    private int DEFAULT_MAX_QUEUE_LENGTH = 1000;
    private Boolean abendQueueIsOn_ = true;
    private int maxPendingThreadCount_ = 5;
    
    private Thread[] pendingThreadArray_;
    private int length_;
    private QueueEntryClass head_;
    private QueueEntryClass tail_;
    private int maxLength_;
    private Lock queueLock_;
    private Lock pendingThreadLock_;
    
    public int length() { return this.length_; }

    public ListQueue(Boolean do_suspend_val, int max_length_val) {
        this.debug(false, "ListQueue", "init start");
        
        this.maxLength_ = max_length_val;
        this.pendingThreadArray_ = new Thread[this.maxPendingThreadCount_];
        this.queueLock_ = new ReentrantLock();
        this.pendingThreadLock_ = new ReentrantLock();

        if (this.maxLength_ == 0) {
            this.maxLength_ = DEFAULT_MAX_QUEUE_LENGTH;
        }
    }
    
    public void destructor() {
    	this.releaseFreeEntryList();
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
        if (this.head_ == null) {
        	entry_val.next = null;
        	entry_val.prev = null;
            this.head_ = entry_val;
            this.tail_ = entry_val;
            this.length_ = 1;
        }
        else {
        	entry_val.next = null;
        	entry_val.prev = this.tail_;
            this.tail_.next = entry_val;
            this.tail_ = entry_val;
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
            entry = this.head_;
            this.head_ = this.tail_ = null;
            this.length_ = 0;
            return entry;
        }

        entry = this.head_;
        this.head_ = this.head_.next;
        this.head_.prev = null;
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

        entry = this.head_;
        while (entry != null) {
            entry_next = entry.next;
            entry.clear();
            this.length_--;
            entry = entry_next;
        }
        this.head_ = this.tail_ = null;

        if (this.length_ != 0) {
            this.abend("flush_", "length is not 0");
        }
    }
    
    private void abendQueue (String msg_val) {
    	if (!this.abendQueueIsOn_)
    		return;
    	
    	this.queueLock_.lock();
    	this.abendQueue_(msg_val);
    	this.queueLock_.unlock();
    }

    private void abendQueue_(String msg_val) {
        QueueEntryClass entry;
        int length;

        if (this.length_ == 0) {
            if ((this.head_ != null) || (this.tail_ != null)) {
                this.abend("abendQueue_", msg_val + " length_ == 0");
            }
            return;
        }

        length = 0;
        entry = this.head_;
        while (entry != null) {
            length++;
            entry = entry.next;
        }

        if (length != this.length_) {
            this.abend("abendQueue_", msg_val + " from head: bad length");
        }

        length = 0;
        entry = this.tail_;
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
    	this.setPendingThread_(thread_val);
    	this.pendingThreadLock_.unlock();
    }

    public void setPendingThread_(Thread thread_val) {
    	for (int i = 0; i < this.maxPendingThreadCount_; i++) {
    		if (this.pendingThreadArray_[i] == null) {
    			this.pendingThreadArray_[i] = thread_val;
    			return;
    		}
    	}
    }
    
    private void interruptPendingThread() {
    	this.pendingThreadLock_.lock();
    	this.interruptPendingThread_();
    	this.pendingThreadLock_.unlock();
    }
    
    private void interruptPendingThread_() {
    	for (int i = 0; i < this.maxPendingThreadCount_; i++) {
    		if (this.pendingThreadArray_[i] != null) {
    			pendingThreadArray_[i].interrupt();
    			this.pendingThreadArray_[i] = null;
    			return;
    		}
    	}
    }

    public static QueueEntryClass malloc() {
        QueueEntryClass entry;
        
        freeListLock.lock();
    	if (freeEntryList != null) {
    		entry = freeEntryList;
    		freeEntryList = freeEntryList.next;
    		freeListLength--;
    	}
    	else {
    		entry = null;
    	}
    	freeListLock.unlock();
    	
    	if (entry != null) {
    		return entry;
    	}
    	else {
    		return new QueueEntryClass();
    	}
    }
    
    public static void free(QueueEntryClass entry) {
    	if (freeListLength > MAX_FREE_LIST_LENGTH) {
    		entry.clear();
    		return;
    	}
    	
    	freeListLock.lock();
    	entry.next = freeEntryList;
    	freeEntryList = entry;
    	freeListLength++;
    	freeListLock.unlock();
    }
    
    public static void releaseFreeEntryList() {
    	freeListLock.lock();
    	while (freeEntryList != null) {
    		QueueEntryClass entry = freeEntryList;
    		freeEntryList = freeEntryList.next;
    		entry.clear();
    	}
    	freeListLock.unlock();
    }
    
    private void debug(Boolean on_off, String s0, String s1) { if (on_off) this.log(s0, s1); }
    private void log(String s0, String s1) { Abend.log(this.objectName() + "." + s0 + "()", s1); }
    public void abend(String s0, String s1) { Abend.abend(this.objectName() + "." + s0 + "()", s1); }
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
