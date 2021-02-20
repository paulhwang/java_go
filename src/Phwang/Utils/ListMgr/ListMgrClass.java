/*
 ******************************************************************************
 *                                       
 *  Copyright (c) 2018 phwang. All rights reserved.
 *
 ******************************************************************************
 */

package Phwang.Utils.ListMgr;

import Phwang.Utils.AbendClass;

public class ListMgrClass {
    private String objectName() {return "ListMgrClass";}
    
    private static final int LIST_MGR_MAX_GLOBAL_LIST_ID = 9999;
    private static final int LIST_MGR_ID_INDEX_ARRAY_SIZE = 1000;

    private String theCallerName;
    private int IdSize;
    private int IndexSize;
    ///////////////int theIdIndexSize;
    private int globalId;
    int MaxIdIndexTableIndex;
    private int maxIndex;
    int entryCount;
    private ListEntryClass[] entryTableArray;
    private Object theLock;

    public int MaxIndex() { return this.maxIndex; }
    public ListEntryClass[] EntryTableArray() { return this.entryTableArray; }

    public ListMgrClass(String caller_name_val, int first_global_id_val)
    {
        this.debugIt(true, "ListMgrClass", "init start (" + caller_name_val + ")");

        this.theCallerName = caller_name_val;
        this.globalId = first_global_id_val;
        this.entryCount = 0;
        this.MaxIdIndexTableIndex = 0;
        this.maxIndex = -1;
        //this.theLock = new object();


        //this->theMutex = PTHREAD_MUTEX_INITIALIZER;

        //if (pthread_mutex_init(&this->theMutex, NULL) != 0)
        {
            //this->abend("ListMgrClass", "pthread_mutex_init fail");
        }

        this.entryTableArray = new ListEntryClass[LIST_MGR_ID_INDEX_ARRAY_SIZE];
    }

    public ListEntryClass MallocEntry(Object object_val)
    {
        int id;
        int index;
        this.debugIt(true, "mallocEntry", "start");

        ListEntryClass entry = new ListEntryClass();

        this.abendListMgrClass("before MallocEntry");
        //lock (this.theLock)
        {
            id = this.allocId();
            index = this.allocIndex();
            if (index != -1)
            {
                this.entryTableArray[index] = entry;
            }
            else
            {
                this.abendIt("InsertEntry", "TBD");
            }
        }
        this.abendListMgrClass("after MallocEntry");

        //entry.SetData(id, object_val, index);
        return entry;
    }

    private int allocId()
    {
        if (this.globalId >= LIST_MGR_MAX_GLOBAL_LIST_ID)
        {
            this.globalId = 0;
        }
        this.globalId++;
        return this.globalId;
    }

    private int allocIndex()
    {
        for (int i = 0; i < LIST_MGR_ID_INDEX_ARRAY_SIZE; i++)
        {
            if (this.entryTableArray[i] == null)
            {
                if (i > this.maxIndex)
                {
                    this.maxIndex = i;
                }
                this.entryCount++;
                return i;
            }
        }

        this.abendIt("allocEntryIndex", "out of entry_index");
        return -1;
    }

    void FreeEntry(ListEntryClass entry_val)
    {
        this.abendListMgrClass("before FreeEntry");
        //lock (this.theLock)
        {
            this.entryTableArray[entry_val.Index()] = null;
        this.entryCount--;
        }
        this.abendListMgrClass("after FreeEntry");
    }

    public ListEntryClass GetEntryById(int id_val)
    {
        ListEntryClass entry = null;

        this.abendListMgrClass("before GetEntryById");
        //lock (this.theLock)
        {
            for (int i = 0; i <= maxIndex; i++)
            {
                if (entryTableArray[i].Id() == id_val)
                {
                    entry = entryTableArray[i];
                    break;
                }
            }
        }
        this.abendListMgrClass("after GetEntryById");

        return entry;
    }

    /*
    public delegate Boolean CompareStringFunc(object obj_val, string str_val);
    public ListEntryClass GetEntryByCompare(CompareStringFunc compare_func_val, string string_val)
    {
        ListEntryClass entry = null;

        this.abendListMgrClass("before GetEntryById");
        //lock (this.theLock)
        {
            for (int i = 0; i <= maxIndex; i++)
            {
                if (compare_func_val(entryTableArray[i].Data(), string_val))
                {
                    entry = entryTableArray[i];
                    break;
                }
            }
        }
        this.abendListMgrClass("after GetEntryById");

        return entry;
    }
*/

    private void abendListMgrClass(String msg_val)
    {
        //lock (this.theLock)
        { 
            int count = 0;
            for (int i = 0; i < LIST_MGR_ID_INDEX_ARRAY_SIZE; i++)
            {
                if (this.entryTableArray[i] != null)
                {
                    count++;
                }
            }
            if (this.entryCount != count)
            {
                this.abendIt("abendListMgrClass", "count not match");
            }
        }
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
