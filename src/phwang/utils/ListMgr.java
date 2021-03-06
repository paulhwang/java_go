/*
 ******************************************************************************
 *                                       
 *  Copyright (c) 2021 phwang. All rights reserved.
 *
 ******************************************************************************
 */

package phwang.utils;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import phwang.fabric.FabricLink;
import phwang.utils.*;

public class ListMgr {
    private String objectName() {return "ListMgr";}
    
    private Boolean abendListMgrClassIsOn = true;
    private int idSize_;
    private String callerName;
    private int globalId;
    int MaxIdIndexTableIndex;
    private int maxIndex;
    private int maxGlobalId;
    int entryCount;
    private ListEntry[] entryArray;
    private int arraySize;
    private Lock theLock;

    public int idSize() { return this.idSize_; }
    public int MaxIndex() { return this.maxIndex; }
    public ListEntry[] EntryTableArray() { return this.entryArray; }

    public ListMgr(int id_size_val, int array_size_val, String caller_name_val, int first_global_id_val) {
        this.debug(false, "ListMgr", "init start (" + caller_name_val + ")");

        this.idSize_ = id_size_val;
        this.callerName = caller_name_val;
        this.globalId = first_global_id_val;
        this.entryCount = 0;
        this.MaxIdIndexTableIndex = 0;
        this.maxIndex = -1;
        this.theLock = new ReentrantLock();
        this.arraySize = array_size_val;
        
        this.maxGlobalId = 1;
        for (int i = 0; i < this.idSize_; i++) {
        	this.maxGlobalId *= 10;
        }
        this.maxGlobalId -= 1;
        
        this.entryArray = new ListEntry[this.arraySize];
    }

    private int allocId() {
        if (this.globalId >= this.maxGlobalId) {
            this.globalId = -1;
        }
        this.globalId++;
        return this.globalId;
    }

    public ListEntry malloc(Object object_val) {
        this.debug(false, "malloc", "start");
    	
        this.abendListMgr("before malloc");
        this.theLock.lock();
        ListEntry entry = this.malloc_(object_val);
    	this.theLock.unlock();
        this.abendListMgr("after malloc");
        
        return entry;
    }

    private ListEntry malloc_(Object object_val) {
        int id = this.allocId();
        for (int i = 0; i < this.arraySize; i++) {
            if (this.entryArray[i] == null) {
            	this.entryArray[i] = new ListEntry(i, this.idSize());
                if (i > this.maxIndex) {
                    this.maxIndex = i;
                }
                else {
                    this.abend("allocIndex", "maxIndex");
                }
                this.entryCount++;
                this.entryArray[i].setData(id, object_val);
                return this.entryArray[i];
            }
            
            if (this.entryArray[i].data() == null) {
                this.entryArray[i].setData(id, object_val);
                return this.entryArray[i];
            }
        }
        
        //this.abend("allocIndex", "run out");
        
        ListEntry[] new_array = new ListEntry[this.arraySize * 2];
        for (int i = 0; i < this.arraySize; i++) {
        	new_array[i] = this.entryArray[i];
        	this.entryArray[i] = null;;
        }
        this.entryArray = new_array;
        this.maxIndex = this.arraySize;
        this.entryCount = this.arraySize + 1;
        this.arraySize = this.arraySize * 2;
    	this.entryArray[this.maxIndex] = new ListEntry(this.maxIndex, this.idSize());
        this.entryArray[this.maxIndex].setData(id, object_val);
        return this.entryArray[this.maxIndex];
    }

    public void free(ListEntry entry_val) {
        this.abendListMgr("before free");
        
        this.theLock.lock();
        this.free_(entry_val);
        this.theLock.unlock();
        
        this.abendListMgr("after free");
    }

    private void free_(ListEntry entry_val) {
        this.entryArray[entry_val.Index()].resetData();
        this.entryCount--;
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
    
    public ListEntry getEntryByIdStr(String id_str_val) {
    	String id_str = id_str_val.substring(0, idSize_);
    	String index_str = id_str_val.substring(idSize_);
        int id = EncodeNumber.decodeNumber(id_str);
        int index = EncodeNumber.decodeNumber(index_str);
    	
        ListEntry entry = this.entryArray[index];
        if (entry.data() == null) {
        	this.abend("ListEntryClass", "null data");
        	return null;
        }
        if (entry.id() != id) {
        	this.abend("ListEntryClass", "id not match");
        	return null;
        }
        return entry;
    }
    
    public ListEntry getEntryByIdStrOld(String id_str_val) {
    	String id_str = id_str_val.substring(0, idSize_);
        int id = EncodeNumber.decodeNumber(id_str);

        return this.getEntryById(id);
    }
    
    public ListEntry getEntryById(int id_val) {
        this.abendListMgr("before getEntryById");
        
        this.theLock.lock();
        ListEntry entry = getEntryById_(id_val);
    	this.theLock.unlock();
    	
    	this.abendListMgr("after getEntryById");
    	return entry;
    }

    private ListEntry getEntryById_(int id_val) {
    	ListEntry entry;

        for (int i = 0; i <= this.maxIndex; i++) {
            entry = this.entryArray[i];
            if ((entry.data() != null) && entry.id() == id_val) {
                return entry;
            }
        }
        return null;
    }

    public ListEntry getEntryByCompare(ListMgrInterface calling_object_val, String string_val) {
        this.abendListMgr("before getEntryByCompare");
        
        this.theLock.lock();
        ListEntry entry = getEntryByCompare_(calling_object_val, string_val);
        this.theLock.unlock();
        
        this.abendListMgr("after getEntryByCompare");
    	return entry;
    }
    
    private ListEntry getEntryByCompare_(ListMgrInterface calling_object_val, String string_val) {
    	ListEntry entry;

        for (int i = 0; i <= maxIndex; i++) {
            entry = this.entryArray[i];
            if ((entry.data() != null) && calling_object_val.compareObjectFunc(entry.data(), string_val)) {
                return entry;
            }
        }
        return null;
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
            if ((this.entryArray[i] != null) && (this.entryArray[i].data() != null)) {
                count++;
            }
        }
        if (this.entryCount != count) {
            this.abend("abendListMgr_", "count not match");
        }
    }
    
    private void debug(Boolean on_off, String s0, String s1) { if (on_off) this.log(s0, s1); }
    private void log(String s0, String s1) { Abend.log(this.objectName() + "." + s0 + "()", s1); }
    public void abend(String s0, String s1) { Abend.abend(this.objectName() + "." + s0 + "()", s1); }
}
