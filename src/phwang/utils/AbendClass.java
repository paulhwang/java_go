package phwang.utils;

import java.util.concurrent.locks.*;

public class AbendClass {
	static boolean logStopped = false;
    static Lock abendLock = new ReentrantLock();
	
    static public void log(String s0, String s1) {
    	if (logStopped) {
    		return;
    	}
    	
        System.out.println(s0 + " " + s1);
    }

    static public void abend(String s0, String s1) {
    	if (logStopped) {
    		return;
    	}
    	logStopped = true;
   	
    	abendLock.lock();
    	System.out.println("***********************ABEND*************************");
    	System.out.println(s0 + " " + s1);
    	System.out.println("***********************ABEND*************************");
    	forceCrash();
    	abendLock.unlock();
    }
    
	static Boolean crashHere;
    static private void forceCrash() {
    	if (crashHere) return;
    }
}
