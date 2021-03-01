package phwang.utils;

public class AbendClass {
	static LockBooleanClass logStopped = new LockBooleanClass();
	static Boolean crashHere;
	
    static public void log(String s0, String s1) {
    	if (logStopped.get())
    		return;
        System.out.println(s0 + " " + s1);
    }

    static public void abend(String s0, String s1) {
    	System.out.println("abend+++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
    	System.out.println(s0 + " " + s1);
    	System.out.println("abend+++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
    	forceCrash();
    }
    
    static private void forceCrash() {
    	logStopped.set(true);
    	
    	if (crashHere)
    		return;
    }
}
