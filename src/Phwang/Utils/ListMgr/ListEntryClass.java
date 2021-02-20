/*
 ******************************************************************************
 *                                       
 *  Copyright (c) 2018 phwang. All rights reserved.
 *
 ******************************************************************************
 */

package Phwang.Utils.ListMgr;

public class ListEntryClass {
    private int theId;
    private Object theData;
    private int theIndex;

    public int Id() { return this.theId; }
    public Object Data() { return this.theData; }
    public int Index() { return this.theIndex; }

    public void SetData(int id_val, Object data_val, int index_val)
    {
        this.theId = id_val;
        this.theData = data_val;
        this.theIndex = index_val;
    }
}
