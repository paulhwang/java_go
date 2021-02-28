/*
 ******************************************************************************
 *                                       
 *  Copyright (c) 2018 phwang. All rights reserved.
 *
 ******************************************************************************
 */

package phwang.utils;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import phwang.utils.*;

public class ListMgrClass {
    private String objectName() {return "ListMgrClass";}
    
    private static final int MAX_GLOBAL_ID = 99999;
    private static final int ENTRY_TABLE_ARRAY_SIZE = 100;

    private Boolean abendListMgrClassIsOn = true;
    private int idSize_;
    private String callerName;
    private int globalId;
    int MaxIdIndexTableIndex;
    private int maxIndex;
    int entryCount;
    private ListEntryClass[] entryArray;
    private int arraySize;
    private Lock theLock;

    public int idSize() { return this.idSize_; }
    public int MaxIndex() { return this.maxIndex; }
    public ListEntryClass[] EntryTableArray() { return this.entryArray; }

    public ListMgrClass(int id_size_val, String caller_name_val, int first_global_id_val) {
        this.debug(false, "ListMgrClass", "init start (" + caller_name_val + ")");

        this.idSize_ = id_size_val;
        this.callerName = caller_name_val;
        this.globalId = first_global_id_val;
        this.entryCount = 0;
        this.MaxIdIndexTableIndex = 0;
        this.maxIndex = -1;
        this.theLock = new ReentrantLock();
        this.arraySize = ENTRY_TABLE_ARRAY_SIZE;

        this.entryArray = new ListEntryClass[this.arraySize];
    }

    private int allocId() {
        if (this.globalId >= MAX_GLOBAL_ID) {
            this.globalId = 0;
        }
        this.globalId++;
        return this.globalId;
    }

    public ListEntryClass malloc(Object object_val) {
        this.debug(false, "malloc", "start");
    	
        this.abendListMgr("before malloc");
        this.theLock.lock();
        ListEntryClass entry = this.malloc_(object_val);
    	this.theLock.unlock();
        this.abendListMgr("after malloc");
        
        return entry;
    }

    private ListEntryClass malloc_(Object object_val) {
        int id;
        int index;

        ListEntryClass entry = new ListEntryClass(this.idSize());
        id = this.allocId();
        index = this.allocIndex();
        if (index != -1) {
            this.entryArray[index] = entry;
        }
        else {
        	this.abend("malloc_", "index too small ");
        }

        entry.setData(id, object_val, index);
        return entry;
    }

    private int allocIndex() {
        for (int i = 0; i < this.arraySize; i++) {
            if (this.entryArray[i] == null) {
                if (i > this.maxIndex) {
                    this.maxIndex = i;
                }
                this.entryCount++;
                return i;
            }
        }
        this.abend("allocIndex", "run out");
        return -1;
    }

    public void free(ListEntryClass entry_val) {
        this.abendListMgr("before free");
        
        this.theLock.lock();
        this.free_(entry_val);
        this.theLock.unlock();
        
        this.abendListMgr("after free");
    }

    private void free_(ListEntryClass entry_val) {
        this.entryArray[entry_val.Index()] = null;
        this.entryCount--;
        entry_val.resetData();
    }

    public void flush() {
        this.abendListMgr("before flush");
        
        this.theLock.lock();
        this.flush_();
        this.theLock.unlock();
        
        this.abendListMgr("after flush");
    }

    public void flush_() {
        for (int i = 0; i <= this.maxIndex; i++) {
            this.entryArray[i].resetData();
            this.entryArray[i] = null;
        }
        this.entryCount = 0;
    }
    
    public ListEntryClass getEntryById(int id_val) {
        this.abendListMgr("before getEntryById");
        
        this.theLock.lock();
    	ListEntryClass entry = getEntryById_(id_val);
    	this.theLock.unlock();
    	
    	this.abendListMgr("after getEntryById");
    	return entry;
    }

    private ListEntryClass getEntryById_(int id_val) {
        ListEntryClass entry = null;

        for (int i = 0; i <= this.maxIndex; i++) {
            if (this.entryArray[i].id() == id_val) {
                entry = this.entryArray[i];
                break;
            }
        }
        return entry;
    }

    public ListEntryClass getEntryByCompare(ListMgrInterface calling_object_val, String string_val) {
        this.abendListMgr("before getEntryByCompare");
        
        this.theLock.lock();
        ListEntryClass entry = getEntryByCompare_(calling_object_val, string_val);
        this.theLock.unlock();
        
        this.abendListMgr("after getEntryByCompare");
    	return entry;
    }
    
    private ListEntryClass getEntryByCompare_(ListMgrInterface calling_object_val, String string_val) {
        ListEntryClass entry = null;

        for (int i = 0; i <= maxIndex; i++) {
            if (calling_object_val.compareObjectFunc(this.entryArray[i].data(), string_val)) {
                entry = this.entryArray[i];
                break;
            }
        }
        return entry;
    }

    private void abendListMgr(String msg_val) {
    	if (!this.abendListMgrClassIsOn)
    		return;
    	
    	this.theLock.lock();
    	this.abendListMgr_(msg_val);
    	this.theLock.unlock();
    }
    
    private void abendListMgr_(String msg_val) {
        int count = 0;
        
        for (int i = 0; i < this.arraySize; i++) {
            if (this.entryArray[i] != null) {
                count++;
            }
        }
        if (this.entryCount != count) {
            this.abend("abendListMgr_", "count not match");
        }
    }
    
    private void debug(Boolean on_off, String s0, String s1) { if (on_off) this.log(s0, s1); }
    private void log(String s0, String s1) { AbendClass.log(this.objectName() + "." + s0 + "()", s1); }
    public void abend(String s0, String s1) { AbendClass.abend(this.objectName() + "." + s0 + "()", s1); }
}
