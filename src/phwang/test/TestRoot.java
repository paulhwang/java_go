/*
 ******************************************************************************
 *                                       
 *  Copyright (c) 2021 phwang. All rights reserved.
 *
 ******************************************************************************
 */

package phwang.test;

import phwang.utils.AbendClass;

public class TestRoot {
    private String objectName() {return "TestRoot";}

    public TestRoot(Boolean http_on_val, Boolean android_on_val) {
        this.debug(false, "TestRoot", "init start");
        
        if (http_on_val) {
        	
        }

        if (android_on_val) {
        	
        }
	}
    
    private void debug(Boolean on_off, String s0, String s1) { if (on_off) this.log(s0, s1); }
    private void log(String s0, String s1) { this.logIt(this.objectName() + "." + s0 + "()", s1); }
    public void abend(String s0, String s1) { this.abendIt(this.objectName() + "." + s0 + "()", s1); }

    private Boolean debug_on = true;
    public void logIt(String s0, String s1) { if (this.debug_on) AbendClass.log(s0, s1); }
    public void abendIt(String s0, String s1) { if (this.debug_on) AbendClass.abend(s0, s1); }
}
