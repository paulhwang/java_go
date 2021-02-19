/*
 ******************************************************************************
 *                                       
 *  Copyright (c) 2018 phwang. All rights reserved.
 *
 ******************************************************************************
 */

package Phwang.Utils.ArrayMgr;

import Phwang.Utils.AbendClass;

public class ArrayMgrClass {
    private String objectName() {return "ArrayMgrClass";}

    private char arrayType;
    private int arraySize;
    private int maxArraySize;
    //private object[] objectArrayTable;
    private String[] stringArrayTable;
    private int[] intArrayTable;
    private char [] charArrayTable;

    public int ArraySize() { return this.arraySize; }
    //public object[] ObjectArrayTable() { return this.objectArrayTable; }
    public String[] StringArrayTable() { return this.stringArrayTable; }
    public int[] IntArrayTable() { return this.intArrayTable; }
    public char[] CharArrayTable() { return this.charArrayTable; }


    public ArrayMgrClass(String owner_object_name_val, char array_type_val, int max_array_size_val)
    {
        this.arrayType = array_type_val;
        this.maxArraySize = max_array_size_val;
        this.allocateArrayTable();
    }
    private void allocateArrayTable()
    {
        switch (this.arrayType)
        {
            case 'o': // object
                //this.objectArrayTable = new object[this.maxArraySize];
                break;

            case 's': // string
                this.stringArrayTable = new String[this.maxArraySize];
                break;

            case 'i': // integer
                this.intArrayTable = new int[this.maxArraySize];
                break;

            case 'c': // char
                this.charArrayTable = new char[this.maxArraySize];
                break;

            default:
                this.abendIt("allocArrayTable", "bad type");
                break;
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
