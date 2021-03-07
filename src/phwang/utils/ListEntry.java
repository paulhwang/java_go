/*
 ******************************************************************************
 *                                       
 *  Copyright (c) 2021 phwang. All rights reserved.
 *
 ******************************************************************************
 */

package phwang.utils;

import java.util.concurrent.locks.*;

public class ListEntry {
    private int id_;
    private String id_str_;
    private Object data_;
    private int index_;
    private int id_size_;
    private String indexStr_;
    
    public int index() { return this.index_; }
    public int id() { return this.id_; }
    public String idStr() { return this.id_str_; }
    public Object data() { return this.data_; }


    public ListEntry(int index_val, int id_size_val) {
        this.index_ = index_val;
    	this.id_size_ = id_size_val;
    	this.indexStr_ = EncodeNumber.encodeNumber(this.index_, this.id_size_);
    }

    public void setData(int id_val, Object data_val) {
        this.id_ = id_val;
        this.data_ = data_val;
        StringBuilder id_str_buf = new StringBuilder(EncodeNumber.encodeNumber(this.id_, this.id_size_));
        id_str_buf.append(this.indexStr_);
        this.id_str_ = id_str_buf.toString();
    }

    public void clearData() {
        this.data_ = null;
    }
}
