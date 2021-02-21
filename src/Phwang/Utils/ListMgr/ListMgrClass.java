/*
 ******************************************************************************
 *                                       
 *  Copyright (c) 2018 phwang. All rights reserved.
 *
 ******************************************************************************
 */

package Phwang.Utils.ListMgr;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import Phwang.Utils.AbendClass;

public class ListMgrClass {
    private String objectName() {return "ListMgrClass";}
    
    private static final int LIST_MGR_MAX_GLOBAL_LIST_ID = 9999;
    private static final int LIST_MGR_ID_INDEX_ARRAY_SIZE = 1000;

    private Boolean abendListMgrClassIsOn = true;
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

    public int MaxIndex() { return this.maxIndex; }
    public ListEntryClass[] EntryTableArray() { return this.entryTableArray; }

    public ListMgrClass(String caller_name_val, int first_global_id_val)
    {
        this.debugIt(false, "ListMgrClass", "init start (" + caller_name_val + ")");

        this.theCallerName = caller_name_val;
        this.globalId = first_global_id_val;
        this.entryCount = 0;
        this.MaxIdIndexTableIndex = 0;
        this.maxIndex = -1;
        this.theLock = new ReentrantLock();

        this.entryTableArray = new ListEntryClass[LIST_MGR_ID_INDEX_ARRAY_SIZE];
    }

    public ListEntryClass MallocEntry(Object object_val) {
        this.debugIt(true, "MallocEntry", "start");
    	
        this.abendListMgrClass("before MallocEntry");
        this.theLock.lock();
        ListEntryClass entry = this.DoMallocEntry(object_val);
    	this.theLock.unlock();
        this.abendListMgrClass("after MallocEntry");
        
        return entry;
    }

    private ListEntryClass DoMallocEntry(Object object_val) {
        int id;
        int index;

        ListEntryClass entry = new ListEntryClass();
        id = this.allocId();
        index = this.allocIndex();
        if (index != -1) {
            this.entryTableArray[index] = entry;
        }
        else {
            this.abendIt("DoMallocEntry", "TBD");
        }

        entry.SetData(id, object_val, index);
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

        this.abendIt("allocEntryIndex", "out of entry_index");
        return -1;
    }

    public void FreeEntry(ListEntryClass entry_val)
    {
        this.abendListMgrClass("before FreeEntry");
        this.theLock.lock();
        this.DoFreeEntry(entry_val);
        this.theLock.unlock();
        this.abendListMgrClass("after FreeEntry");
    }

    private void DoFreeEntry(ListEntryClass entry_val) {
        this.entryTableArray[entry_val.Index()] = null;
        this.entryCount--;
    }

    public ListEntryClass GetEntryById(int id_val) {
        this.abendListMgrClass("before GetEntryById");
        this.theLock.lock();
    	ListEntryClass entry = DoGetEntryById(id_val);
    	this.theLock.unlock();
    	this.abendListMgrClass("after GetEntryById");
    	return entry;
    }

    public ListEntryClass DoGetEntryById(int id_val) {
        ListEntryClass entry = null;

        for (int i = 0; i <= maxIndex; i++) {
            if (entryTableArray[i].Id() == id_val) {
                entry = entryTableArray[i];
                break;
            }
        }
        return entry;
    }

    //public delegate Boolean CompareStringFunc(object obj_val, String str_val);
    @FunctionalInterface
    public interface CompareStringFunc {
        Boolean run(Object obj_val, String str_val);
    }
    
    public ListEntryClass GetEntryByCompare(CompareStringFunc compare_func_val, String string_val) {
        this.abendListMgrClass("before GetEntryByCompare");
        this.theLock.lock();
        ListEntryClass entry = DoGetEntryByCompare(compare_func_val, string_val);
        this.theLock.unlock();
        this.abendListMgrClass("after GetEntryByCompare");
    	return entry;
    }
    
    public ListEntryClass DoGetEntryByCompare(CompareStringFunc compare_func_val, String string_val) {
        ListEntryClass entry = null;

        for (int i = 0; i <= maxIndex; i++) {
            if (compare_func_val.run(entryTableArray[i].Data(), string_val)) {
                entry = entryTableArray[i];
            }
        }

        return entry;
    }

    private void abendListMgrClass(String msg_val) {
    	if (!this.abendListMgrClassIsOn)
    		return;
    	
    	this.theLock.lock();
    	this.DoAbendListMgrClass(msg_val);
    	this.theLock.lock();
    }
    
    private void DoAbendListMgrClass(String msg_val) {
        int count = 0;
        
        for (int i = 0; i < LIST_MGR_ID_INDEX_ARRAY_SIZE; i++) {
            if (this.entryTableArray[i] != null) {
                count++;
            }
        }
        if (this.entryCount != count) {
            this.abendIt("DoAbendListMgrClass", "count not match");
        }
    }

    private void debugIt(Boolean on_off_val, String str0_val, String str1_val) {
        if (on_off_val)
            this.logitIt(str0_val, str1_val);
    }

    private void logitIt(String str0_val, String str1_val) {
        AbendClass.phwangLogit(this.objectName() + "." + str0_val + "()", str1_val);
    }

    public void abendIt(String str0_val, String str1_val) {
        AbendClass.phwangAbend(this.objectName() + "." + str0_val + "()", str1_val);
    }

}
