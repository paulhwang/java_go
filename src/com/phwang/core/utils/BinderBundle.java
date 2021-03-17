/*
 ******************************************************************************
 *                                       
 *  Copyright (c) 2021 phwang. All rights reserved.
 *
 ******************************************************************************
 */

package com.phwang.core.utils;

public class BinderBundle {
	private String data_;
	private BinderPort binderPort_;
	public BinderBundle next;
	
	public String data() { return this.data_; }
	public BinderPort binderPort() { return this.binderPort_; }
	
	public void setData(String data_val) { this.data_ = data_val; }
	public void setBinderPort(BinderPort port_val) { this.binderPort_ = port_val; }
	
	public void clear() {
		this.data_ = null;
		this.binderPort_ = null;
	}
}
