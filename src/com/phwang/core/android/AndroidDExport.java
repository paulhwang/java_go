/*
 ******************************************************************************
 *                                       
 *  Copyright (c) 2021 phwang. All rights reserved.
 *
 ******************************************************************************
 */

package com.phwang.core.android;

import com.phwang.core.protocols.ProtocolDefineClass;
import com.phwang.core.utils.EncodeNumber;

public class AndroidDExport implements AndroidDExportInt {
    private String objectName() {return "AndroidDExport";}
    
    private AndroidRoot androidRoot_;
    
    private AndroidRoot androidRoot() { return this.androidRoot_; }
    
    protected AndroidDExport(AndroidRoot root_val) {
        this.debug(false, "AndroidDExport", "init start");
        
    	this.androidRoot_ = root_val;
    }

    
    public void setupLink(String my_name_val, String password_val) {
    	this.debug(true, "setupLink", "name=" + my_name_val);
    	
        StringBuilder command_buf = new StringBuilder();
        command_buf.append(AndroidImport.FABRIC_COMMAND_SETUP_LINK); 
        command_buf.append(EncodeNumber.encode(my_name_val.length(), ProtocolDefineClass.DATA_LENGTH_SIZE));
        command_buf.append(my_name_val);
        command_buf.append(EncodeNumber.encode(password_val.length(), ProtocolDefineClass.DATA_LENGTH_SIZE));
        command_buf.append(password_val);
        String command_str = command_buf.toString();
        
    	this.debug(true, "setupLink", "command_str=" + command_str);
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
    protected void abend(String s0, String s1) { this.androidRoot().abendIt(this.objectName() + "." + s0 + "()", s1); }
}
