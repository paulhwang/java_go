/*
 ******************************************************************************
 *                                       
 *  Copyright (c) 2021 phwang. All rights reserved.
 *
 ******************************************************************************
 */

package com.phwang.core.utils;

public class ListEntry {
    private int id_;
    private String id_str_;
    private ListEntryInt data_;
    private int index_;
    private int id_size_;
    private String indexStr_;
    
    public int index() { return this.index_; }
    public int id() { return this.id_; }
    public String idStr() { return this.id_str_; }
    public ListEntryInt data() { return this.data_; }

    public ListEntry(int index_val, int id_size_val) {
        this.index_ = index_val;
    	this.id_size_ = id_size_val;
    	this.indexStr_ = Encoders.iEncodeRaw(this.index_, this.id_size_);
    }

    public void setData(int id_val, ListEntryInt data_val) {
        this.id_ = id_val;
        this.data_ = data_val;
        StringBuilder id_str_buf = new StringBuilder(Encoders.iEncodeRaw(this.id_, this.id_size_));
        id_str_buf.append(this.indexStr_);
        this.id_str_ = Encoders.sEncode2(id_str_buf.toString());
    }

    public void clearData() {
        this.data_ = null;
    }
}
