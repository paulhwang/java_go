/*
 ******************************************************************************
 *                                       
 *  Copyright (c) 2021 phwang. All rights reserved.
 *
 ******************************************************************************
 */

package phwang.android;

public class AndroidDExport implements AndroidDExportInt {
    private String objectName() {return "AndroidDExport";}
    
    private AndroidRoot androidRoot_;
    
    public AndroidRoot androidRoot() { return this.androidRoot_; }
    
    public AndroidDExport(AndroidRoot root_val) {
        this.debug(false, "AndroidDExport", "init start");
        
    	this.androidRoot_ = root_val;
    }

    
    public void setupLink(String name_val, String password_val) {
    	
    }
    
    public void removeLink(String link_id_val) {
    	
    }
    
    public void getLinkData(String link_id_val) {
    	
    }
    
    public void getNameList(String link_id_val) {
    	
    }
    
    public void setupSession(String link_id_val) {
    	
    }
    
    public void setupSession2(String link_id_val) {
    	
    }
    
    public void setupSession3(String link_id_val) {
    	
    }
    
    public void removeSession(String link_id_val, String session_id_val) {
    	
    }
    
    public void putSessionData(String link_id_val, String session_id_val) {
    	
    }
    
    public void getSessionData(String link_id_val, String session_id_val) {
    	
    }
    
    private void debug(Boolean on_off, String s0, String s1) { if (on_off) this.log(s0, s1); }
    private void log(String s0, String s1) { this.androidRoot().logIt(this.objectName() + "." + s0 + "()", s1); }
    public void abend(String s0, String s1) { this.androidRoot().abendIt(this.objectName() + "." + s0 + "()", s1); }
}
