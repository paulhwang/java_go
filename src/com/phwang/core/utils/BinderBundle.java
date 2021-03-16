/*
 ******************************************************************************
 *                                       
 *  Copyright (c) 2021 phwang. All rights reserved.
 *
 ******************************************************************************
 */

package com.phwang.core.utils;

public class BinderBundle {
	private String id_;
	private String data_;
	
	public BinderBundle(String id_val, String data_val) {
		this.id_ = id_val;
		this.data_ = data_val;
	}
	
	public String id() { return this.id_; };
	public String data() { return this.data_; }
	
	public void setId(String val) { this.id_ = val; };
	public void setData(String val) { this.data_ = val; }
}
