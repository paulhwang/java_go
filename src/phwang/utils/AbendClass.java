package phwang.utils;

public class AbendClass
{
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
