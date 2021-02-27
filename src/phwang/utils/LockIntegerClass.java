/*
 ******************************************************************************
 *                                       
 *  Copyright (c) 2018 phwang. All rights reserved.
 *
 ******************************************************************************
 */

package phwang.utils;

import java.util.concurrent.locks.*;

public class LockIntegerClass {
	private Lock lock;
	private int it;
	
	public LockIntegerClass(int val) {
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
