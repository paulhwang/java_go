package com.phwang.core.android;

public class AndroidUBinder {
    private String objectName() {return "AndroidUBinder";}
    
    private AndroidRoot androidRoot_;
    
    private AndroidRoot androidRoot() { return this.androidRoot_; }
    
    protected AndroidUBinder(AndroidRoot root_val) {
        this.debug(false, "AndroidUBinder", "init start");
        
    	this.androidRoot_ = root_val;
    }

    
    private void debug(Boolean on_off, String s0, String s1) { if (on_off) this.log(s0, s1); }
    private void log(String s0, String s1) { this.androidRoot().logIt(this.objectName() + "." + s0 + "()", s1); }
    protected void abend(String s0, String s1) { this.androidRoot().abendIt(this.objectName() + "." + s0 + "()", s1); }
}
