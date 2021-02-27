/*
 ******************************************************************************
 *                                       
 *  Copyright (c) 2018 phwang. All rights reserved.
 *
 ******************************************************************************
 */

package phwang.utils;

import java.util.concurrent.locks.*;

public class LockNumberClass {
	private Lock lock;
	private int number;
	
	public LockNumberClass() {
        this.lock= new ReentrantLock();
	}
	
	public void increment() {
		this.lock.lock();
		this.number++;
		this.lock.unlock();
	}
		
	public void decrement() {
		this.lock.lock();
		this.number--;
		this.lock.unlock();
	}
}
