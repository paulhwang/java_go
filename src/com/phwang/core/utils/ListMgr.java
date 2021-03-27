/*
 ******************************************************************************
 *                                       
 *  Copyright (c) 2021 phwang. All rights reserved.
 *
 ******************************************************************************
 */

package com.phwang.core.utils;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ListMgr {
    private String objectName() {return "ListMgr";}
    
    private Boolean abendListMgrClassIsOn = true;
    private int idSize_;
    private String callerName_;
    private int globalId_;
    int MaxIdIndexTableIndex_;
    private int maxIndex_;
    private int maxGlobalId_;
    private int entryCount_;
    private ListEntry[] oldEntryArray_;//needed for postponing garbage collection
    private ListEntry[] entryArray_;
    private int arraySize_;
    private Lock lock_;

    public int entryCount() { return this.entryCount_; }
    public int idSize() { return this.idSize_; }
    public int maxIndex() { return this.maxIndex_; }
    public ListEntry[] entryArray() { return this.entryArray_; }

    public ListMgr(int id_size_val, int array_size_val, String caller_name_val, int first_global_id_val) {
        this.debug(false, "ListMgr", "init start (" + caller_name_val + ")");

        this.idSize_ = id_size_val;
        this.callerName_ = caller_name_val;
        this.globalId_ = first_global_id_val - 1;
        this.entryCount_ = 0;
        this.MaxIdIndexTableIndex_ = 0;
        this.maxIndex_ = -1;
        this.lock_ = new ReentrantLock();
        this.arraySize_ = array_size_val;
        
        this.maxGlobalId_ = 1;
        for (int i = 0; i < this.idSize_; i++) {
        	this.maxGlobalId_ *= 10;
        }
        this.maxGlobalId_ -= 1;
        
        this.entryArray_ = new ListEntry[this.arraySize_];
    }

    private int allocId() {
        if (this.globalId_ >= this.maxGlobalId_) {
            this.globalId_ = -1;
        }
        this.globalId_++;
        return this.globalId_;
    }

    public ListEntry malloc(ListEntryInt entity_int_val) {
        this.debug(false, "malloc", "start");
    	
        this.abendListMgr("before malloc");
        this.lock_.lock();
        
        ListEntry entry = this.malloc_();
        
        int id = this.allocId();
        this.entryCount_++;
        entry.setData(id, entity_int_val);
        entity_int_val.bindListEntry(entry, this.callerName_);
        
    	this.lock_.unlock();
        this.abendListMgr("after malloc");
        
        if (this.entryCount_ > this.maxGlobalId_) {
            this.abend("malloc", "entryCount_=" + this.entryCount_ + " > maxGlobalId_=" + this.maxGlobalId_);
        }
        
        if (entry.index() > this.maxGlobalId_) {
            this.abend("malloc", "index=" + entry.index() + " > maxGlobalId_=" + this.maxGlobalId_);
        }
        
        this.debug(false, "malloc", "id=" + entry.id() + " index=" + entry.index());
        return entry;
    }

    private ListEntry malloc_() {
        for (int i = 0; i < this.arraySize_; i++) {
            if (this.entryArray_[i] == null) {
            	this.entryArray_[i] = new ListEntry(i, this.idSize());
                if (i > this.maxIndex_) {
                    this.maxIndex_ = i;
                }
                else {
                    this.abend("malloc_", "maxIndex");
                }
                return this.entryArray_[i];
            }
            else {
            	if (this.entryArray_[i].data() == null) {
            		return this.entryArray_[i];
            	}
            }
        }
        
        this.oldEntryArray_ = this.entryArray_;
        ListEntry[] new_array = new ListEntry[this.arraySize_ * 2];
        for (int i = 0; i < this.arraySize_; i++) {
        	new_array[i] = this.oldEntryArray_[i];
        	//*** to remove the lock from reading array //this.entryArray_[i] = null;;
        }
        this.entryArray_ = new_array;
        this.maxIndex_ = this.arraySize_;
        this.arraySize_ = this.arraySize_ * 2;
    	this.entryArray_[this.maxIndex_] = new ListEntry(this.maxIndex_, this.idSize());
        return this.entryArray_[this.maxIndex_];
    }

    public void free(ListEntry entry_val) {
        this.abendListMgr("before free");
        
        this.lock_.lock();
        this.free_(entry_val);
        this.lock_.unlock();
        
        this.abendListMgr("after free");
    }

    private void free_(ListEntry entry_val) {
        this.entryArray_[entry_val.index()].data().unBindListEntry(this.callerName_);
        this.entryArray_[entry_val.index()].clearData();
        this.entryCount_--;
    }

    public void flush() {
        this.abendListMgr("before flush");
        
        this.lock_.lock();
        this.flush_();
        this.lock_.unlock();
        
        this.abendListMgr("after flush");
    }

    public void flush_() {
        for (int i = 0; i <= this.maxIndex_; i++) {
            this.entryArray_[i].data().unBindListEntry(this.callerName_);
            this.entryArray_[i].clearData();
            this.entryArray_[i] = null;
        }
        this.entryCount_ = 0;
    }
    
    public ListEntry getEntryByIdStr(String id_str_val) {
    	String id_str = id_str_val.substring(2, 2 + idSize_);
        int id = Encoders.iDecodeRaw(id_str);

    	String index_str = id_str_val.substring(2 + idSize_);
        int index = Encoders.iDecodeRaw(index_str);
        
    	ListEntry entry;
        //this.theLock.lock();
    	entry = this.getEntryByIdStr_(id, index);
        //this.theLock.unlock();
    	return entry;
    }
    
    private ListEntry getEntryByIdStr_(int id_val, int index_val) {
        ListEntry entry = this.entryArray_[index_val];
        if (entry == null) {
        	this.abend("getEntryByIdStr_", "null entry");
        	return null;
        }
        
        if (entry.data() == null) {
        	this.abend("getEntryByIdStr_", "null data");
        	return null;
        }
        
        if (entry.id() != id_val) {
        	this.abend("getEntryByIdStr_", "id not match");
        	return null;
        }
        return entry;
    }

    public ListEntry getEntryById(int id_val) {
        this.abendListMgr("before getEntryById");
        
        this.lock_.lock();
        ListEntry entry = getEntryById_(id_val);
    	this.lock_.unlock();
    	
    	this.abendListMgr("after getEntryById");
    	return entry;
    }

    private ListEntry getEntryById_(int id_val) {
    	ListEntry entry;

        for (int i = 0; i <= this.maxIndex_; i++) {
            entry = this.entryArray_[i];
            if ((entry.data() != null) && entry.id() == id_val) {
                return entry;
            }
        }
        return null;
    }

    public ListEntry getEntryByCompare(ListMgrInt calling_object_val, String string_val) {
        this.abendListMgr("before getEntryByCompare");
        
        this.lock_.lock();
        ListEntry entry = getEntryByCompare_(calling_object_val, string_val);
        this.lock_.unlock();
        
        this.abendListMgr("after getEntryByCompare");
    	return entry;
    }
    
    private ListEntry getEntryByCompare_(ListMgrInt calling_object_val, String string_val) {
    	ListEntry entry;

        for (int i = 0; i <= maxIndex_; i++) {
            entry = this.entryArray_[i];
            if ((entry.data() != null) && calling_object_val.compareObjectFunc(entry.data(), string_val)) {
                return entry;
            }
        }
        return null;
    }

    private void abendListMgr(String msg_val) {
    	if (!this.abendListMgrClassIsOn)
    		return;
    	
    	this.lock_.lock();
    	this.abendListMgr_(msg_val);
    	this.lock_.unlock();
    }
    
    private void abendListMgr_(String msg_val) {
        int count = 0;
        
        for (int i = 0; i < this.arraySize_; i++) {
            if ((this.entryArray_[i] != null) && (this.entryArray_[i].data() != null)) {
                count++;
            }
        }
        if (this.entryCount_ != count) {
            this.abend("abendListMgr_", msg_val + " entryCount_=" + this.entryCount_ + " != count=" + count);
        }
    }
    
    private void debug(Boolean on_off, String s0, String s1) { if (on_off) this.log(s0, s1); }
    private void log(String s0, String s1) { Abend.log(this.objectName() + "." + s0 + "()", s1); }
    public void abend(String s0, String s1) { Abend.abend(this.objectName() + "." + s0 + "()", s1); }
}
