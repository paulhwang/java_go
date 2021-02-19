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
    //private ListEntryClass[] entryTableArray;
    //private object theLock;

    public int MaxIndex() { return this.maxIndex; }
    //public ListEntryClass[] EntryTableArray() { return this.entryTableArray; }

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

        //this.entryTableArray = new ListEntryClass[LIST_MGR_ID_INDEX_ARRAY_SIZE];
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
