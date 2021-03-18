/*
 ******************************************************************************
 *                                       
 *  Copyright (c) 2021 phwang. All rights reserved.
 *
 ******************************************************************************
 */

package com.phwang.client;

import com.phwang.core.protocols.ProtocolDefineClass;
import com.phwang.core.utils.Binder;
import com.phwang.core.utils.EncodeNumber;
import com.phwang.front.FrontJob;
import com.phwang.front.FrontJobMgr;

public class ClientDExport implements ClientDExportInt {
    private String objectName() {return "ClientDExport";}
    
    private ClientRoot androidRoot_;
    
    private ClientRoot androidRoot() { return this.androidRoot_; }
    private ClientUBinder androidUBinder() { return this.androidRoot().androidUBinder(); }
    private Binder uBinder() { return this.androidUBinder().uBinder(); }
    private ClientFabricInfo androidFabricInfo() { return this.androidRoot_.androidFabricInfo(); }
 
    protected ClientDExport(ClientRoot root_val) {
        this.debug(false, "ClientDExport", "init start");
        
    	this.androidRoot_ = root_val;
    }

    private void transmitToFabric(String data_str_val) {
    	this.debug(true, "transmitToFabric", "data_str_val=" + data_str_val);
       	this.uBinder().transmitStringData(this.androidFabricInfo().jobIdStr() + data_str_val);
    }
    
    public void setupLink(String my_name_val, String password_val) {
    	this.debug(false, "setupLink", "name=" + my_name_val);
    	
        StringBuilder command_buf = new StringBuilder();
        command_buf.append(ClientImport.FABRIC_COMMAND_SETUP_LINK); 
        command_buf.append(EncodeNumber.encode(my_name_val.length(), ProtocolDefineClass.DATA_LENGTH_SIZE));
        command_buf.append(my_name_val);
        command_buf.append(EncodeNumber.encode(password_val.length(), ProtocolDefineClass.DATA_LENGTH_SIZE));
        command_buf.append(password_val);
        String command_str = command_buf.toString();
        
    	this.debug(false, "setupLink", "command_str=" + command_str);
    	
    	this.transmitToFabric(command_str);
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
