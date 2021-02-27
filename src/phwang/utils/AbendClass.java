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
    
    static public void phwangLogit(String str0_val, String str1_val)
    {
    	if (logStopped.get())
    		return;
        System.out.println(str0_val + " " + str1_val);
    }

    static public void phwangAbend(String str0_val, String str1_val)
    {
    	System.out.println("Abend+++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
    	System.out.println(str0_val + " " + str1_val);
    	System.out.println("Abend+++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
    	forceCrash();
    }
    
    static private void forceCrash() {
    	logStopped.set(true);
    	
    	if (crashHere)
    		return;
    }
}
