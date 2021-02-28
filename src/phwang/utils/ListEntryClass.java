/*
 ******************************************************************************
 *                                       
 *  Copyright (c) 2018 phwang. All rights reserved.
 *
 ******************************************************************************
 */

package phwang.utils;

import java.util.concurrent.locks.*;

import phwang.fabric.FabricDefineClass;

public class ListEntryClass {
    private int id_;
    private String id_str_;
    private Object data_;
    private int index_;
    private int id_size;

    public ListEntryClass(int index_val) {
        this.index_ = index_val;
    }
    
    public int Index() { return this.index_; }
    public int id() { return this.id_; }
    public String idStr() { return this.id_str_; }
    public Object data() { return this.data_; }

    public void setData(int id_val, Object data_val, int id_size_val) {
        this.id_ = id_val;
        this.data_ = data_val;
    	this.id_size = id_size_val;
        this.id_str_ = EncodeNumberClass.encodeNumber(this.id(), this.id_size);
    }

    public void resetData() {
        this.data_ = null;
    }
}
