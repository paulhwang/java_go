package phwang.utils;

public class AbendClass
{
    static public void log(String s0, String s1) {
        System.out.println(s0 + " " + s1);
    }

    static public void abend(String s0, String s1) {
    	System.out.println("abend+++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
    	System.out.println(s0 + " " + s1);
    	System.out.println("abend+++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
        Junk junk = null;
        junk.data = 1;
    }
    
    static public void phwangLogit(String str0_val, String str1_val)
    {
        System.out.println(str0_val + " " + str1_val);
    }

    static public void phwangAbend(String str0_val, String str1_val)
    {
    	System.out.println("Abend+++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
    	System.out.println(str0_val + " " + str1_val);
    	System.out.println("Abend+++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
        Junk junk = null;
        junk.data = 1;
    }
    
    private class Junk
    {
        public int data;
    }
}
