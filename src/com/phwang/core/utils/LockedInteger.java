/*
 ******************************************************************************
 *                                       
 *  Copyright (c) 2021 phwang. All rights reserved.
 *
 ******************************************************************************
 */

package com.phwang.core.utils;

import java.util.concurrent.locks.*;

public class LockedInteger {
	private Lock lock_;
	private int it_;
	
	public LockedInteger(int val) {
        this.lock_= new ReentrantLock();
        this.it_ = val;
	}
	
	public int get() { return this.it_; }
	
	public void increment() {
		this.lock_.lock();
		this.it_++;
		this.lock_.unlock();
	}
		
	public void decrement() {
		this.lock_.lock();
		this.it_--;
		this.lock_.unlock();
	}
}
