/*
 ******************************************************************************
 *                                       
 *  Copyright (c) 2018 phwang. All rights reserved.
 *
 ******************************************************************************
 */

package phwang.utils;

import java.util.concurrent.locks.*;

public class LockBooleanClass {
	private Lock lock;
	private Boolean it = false;

	public Boolean get() { return this.it; }
	
	public LockBooleanClass() {
        this.lock= new ReentrantLock();
	}

	public void set(Boolean val) {
		this.lock.lock();
		this.it = val;
		this.lock.unlock();
	}
}
