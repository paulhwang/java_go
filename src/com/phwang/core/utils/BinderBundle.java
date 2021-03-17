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
	public BinderBundle next;
	
	public String id() { return this.id_; };
	public String data() { return this.data_; }
	
	public void setId(String val) { this.id_ = val; };
	public void setData(String val) { this.data_ = val; }
	
	public void clear() {
		this.id_ = null;
		this.data_ = null;
	}
}
