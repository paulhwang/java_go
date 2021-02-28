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
    
    private static final int LIST_MGR_MAX_GLOBAL_LIST_ID = 9999;
    private static final int LIST_MGR_ID_INDEX_ARRAY_SIZE = 1000;

    private Boolean abendListMgrClassIsOn = true;
    private int idSize_;
    private String theCallerName;
    private int IdSize;
    private int IndexSize;
    ///////////////int theIdIndexSize;
    private int globalId;
    int MaxIdIndexTableIndex;
    private int maxIndex;
    int entryCount;
    private ListEntryClass[] entryTableArray;
    private Lock theLock;

    public int idSize() { return this.idSize_; }
    public int MaxIndex() { return this.maxIndex; }
    public ListEntryClass[] EntryTableArray() { return this.entryTableArray; }

    public ListMgrClass(int id_size_val, String caller_name_val, int first_global_id_val) {
        this.debug(false, "ListMgrClass", "init start (" + caller_name_val + ")");

        this.idSize_ = id_size_val;
        this.theCallerName = caller_name_val;
        this.globalId = first_global_id_val;
        this.entryCount = 0;
        this.MaxIdIndexTableIndex = 0;
        this.maxIndex = -1;
        this.theLock = new ReentrantLock();

        this.entryTableArray = new ListEntryClass[LIST_MGR_ID_INDEX_ARRAY_SIZE];
    }

    public ListEntryClass mallocEntry(Object object_val) {
        this.debug(false, "MallocEntry", "start");
    	
        this.abendListMgrClass("before MallocEntry");
        this.theLock.lock();
        ListEntryClass entry = this.mallocEntry_(object_val);
    	this.theLock.unlock();
        this.abendListMgrClass("after MallocEntry");
        
        return entry;
    }

    private ListEntryClass mallocEntry_(Object object_val) {
        int id;
        int index;

        ListEntryClass entry = new ListEntryClass(this.idSize());
        id = this.allocId();
        index = this.allocIndex();
        if (index != -1) {
            this.entryTableArray[index] = entry;
        }
        else {
        	this.abend("mallocEntry_", "index too small ");
        }

        entry.setData(id, object_val, index);
        return entry;
    }

    private int allocId() {
        if (this.globalId >= LIST_MGR_MAX_GLOBAL_LIST_ID) {
            this.globalId = 0;
        }
        this.globalId++;
        return this.globalId;
    }

    private int allocIndex() {
        for (int i = 0; i < LIST_MGR_ID_INDEX_ARRAY_SIZE; i++) {
            if (this.entryTableArray[i] == null) {
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

    public void freeEntry(ListEntryClass entry_val) {
        this.abendListMgrClass("before FreeEntry");
        
        this.theLock.lock();
        this.doFreeEntry(entry_val);
        this.theLock.unlock();
        
        this.abendListMgrClass("after FreeEntry");
    }

    private void doFreeEntry(ListEntryClass entry_val) {
        this.entryTableArray[entry_val.Index()] = null;
        this.entryCount--;
    }

    public ListEntryClass getEntryById(int id_val) {
        this.abendListMgrClass("before GetEntryById");
        
        this.theLock.lock();
    	ListEntryClass entry = doGetEntryById(id_val);
    	this.theLock.unlock();
    	
    	this.abendListMgrClass("after GetEntryById");
    	return entry;
    }

    private ListEntryClass doGetEntryById(int id_val) {
        ListEntryClass entry = null;

        for (int i = 0; i <= maxIndex; i++) {
            if (entryTableArray[i].id() == id_val) {
                entry = entryTableArray[i];
                break;
            }
        }
        return entry;
    }

    public ListEntryClass getEntryByCompare(ListMgrInterface calling_object_val, String string_val) {
        this.abendListMgrClass("before GetEntryByCompare");
        
        this.theLock.lock();
        ListEntryClass entry = doGetEntryByCompare(calling_object_val, string_val);
        this.theLock.unlock();
        
        this.abendListMgrClass("after GetEntryByCompare");
    	return entry;
    }
    
    private ListEntryClass doGetEntryByCompare(ListMgrInterface calling_object_val, String string_val) {
        ListEntryClass entry = null;

        for (int i = 0; i <= maxIndex; i++) {
            if (calling_object_val.compareObjectFunc(entryTableArray[i].data(), string_val)) {
                entry = entryTableArray[i];
            }
        }
        return entry;
    }

    private void abendListMgrClass(String msg_val) {
    	if (!this.abendListMgrClassIsOn)
    		return;
    	
    	this.theLock.lock();
    	this.doAbendListMgrClass(msg_val);
    	this.theLock.unlock();
    }
    
    private void doAbendListMgrClass(String msg_val) {
        int count = 0;
        
        for (int i = 0; i < LIST_MGR_ID_INDEX_ARRAY_SIZE; i++) {
            if (this.entryTableArray[i] != null) {
                count++;
            }
        }
        if (this.entryCount != count) {
            this.abend("DoAbendListMgrClass", "count not match");
        }
    }
    
    private void debug(Boolean on_off, String s0, String s1) { if (on_off) this.log(s0, s1); }
    private void log(String s0, String s1) { AbendClass.log(this.objectName() + "." + s0 + "()", s1); }
    public void abend(String s0, String s1) { AbendClass.abend(this.objectName() + "." + s0 + "()", s1); }
}
