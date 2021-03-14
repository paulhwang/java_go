/*
 ******************************************************************************
 *                                       
 *  Copyright (c) 2021 phwang. All rights reserved.
 *
 ******************************************************************************
 */

package com.phwang.core.utils;

import java.util.concurrent.locks.*;

public class LockedBoolean {
	private Lock lock;
	private Boolean it = false;

	public Boolean get() { return this.it; }
	
	public LockedBoolean() {
        this.lock= new ReentrantLock();
	}

	public void set(Boolean val) {
		this.lock.lock();
		this.it = val;
		this.lock.unlock();
	}
}
