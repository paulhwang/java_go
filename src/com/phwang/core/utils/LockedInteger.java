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
	private Lock lock;
	private int it;
	
	public LockedInteger(int val) {
        this.lock= new ReentrantLock();
        this.it = val;
	}
	
	public int get() { return this.it; }
	
	public void increment() {
		this.lock.lock();
		this.it++;
		this.lock.unlock();
	}
		
	public void decrement() {
		this.lock.lock();
		this.it--;
		this.lock.unlock();
	}
}
