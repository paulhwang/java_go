/*
 ******************************************************************************
 *                                       
 *  Copyright (c) 2018 phwang. All rights reserved.
 *
 ******************************************************************************
 */

package Phwang.Utils;

import java.util.concurrent.locks.*;

public class ListEntryClass {
    private int theId;
    private Object theData;
    private int theIndex;

    public int Id() { return this.theId; }
    public Object Data() { return this.theData; }
    public int Index() { return this.theIndex; }

    public void setData(int id_val, Object data_val, int index_val) {
        this.theId = id_val;
        this.theData = data_val;
        this.theIndex = index_val;
    }

    public void resetData() {
        this.theData = null;
    }
}
