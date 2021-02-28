/*
 ******************************************************************************
 *                                       
 *  Copyright (c) 2018 phwang. All rights reserved.
 *
 ******************************************************************************
 */

package phwang.utils;

import java.util.concurrent.locks.*;

public class ListEntryClass {
    private int id_;
    private Object data_;
    private int index_;

    public int id() { return this.id_; }
    public Object data() { return this.data_; }
    public int Index() { return this.index_; }

    public void setData(int id_val, Object data_val, int index_val) {
        this.id_ = id_val;
        this.data_ = data_val;
        this.index_ = index_val;
    }

    public void resetData() {
        this.data_ = null;
    }
}
