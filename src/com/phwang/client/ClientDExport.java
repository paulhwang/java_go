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

public class ClientDExport implements ClientDExportInt {
    private String objectName() {return "ClientDExport";}
    
    private ClientRoot clientRoot_;
    
    private ClientRoot clientRoot() { return this.clientRoot_; }
    private ClientUBinder clientUBinder() { return this.clientRoot_.clientUBinder(); }
    private Binder uBinder() { return this.clientUBinder().uBinder(); }
    private ClientFabricInfo clientFabricInfo() { return this.clientRoot_.clientFabricInfo(); }
    private ClientGoConfig goConfig() { return this.clientRoot_.goConfig(); }
 
    protected ClientDExport(ClientRoot root_val) {
        this.debug(false, "ClientDExport", "init start");
        
    	this.clientRoot_ = root_val;
    }

    private void transmitToFabric(String data_str_val) {
    	this.debug(true, "transmitToFabric", "data_str_val=" + data_str_val);
       	this.uBinder().transmitStringData(this.clientFabricInfo().jobIdStr() + data_str_val);
    }
    
    public void setupLink() {
    	this.debug(false, "setupLink", "name=" + this.clientFabricInfo().myName());
    	
        StringBuilder command_buf = new StringBuilder();
        command_buf.append(ClientImport.FABRIC_COMMAND_SETUP_LINK); 
        command_buf.append(EncodeNumber.encode(this.clientFabricInfo().myName().length(), ProtocolDefineClass.DATA_LENGTH_SIZE));
        command_buf.append(this.clientFabricInfo().myName());
        command_buf.append(EncodeNumber.encode(this.clientFabricInfo().password().length(), ProtocolDefineClass.DATA_LENGTH_SIZE));
        command_buf.append(this.clientFabricInfo().password());
        String command_str = command_buf.toString();
        
    	this.debug(false, "setupLink", "command_str=" + command_str);
    	
    	this.transmitToFabric(command_str);
    }
    
    public void removeLink() {
    	
    }
    
    public void getLinkData() {
    	this.debug(false, "getLinkData", "link_id=" + this.clientFabricInfo().linkIdStr());
    	
        StringBuilder command_buf = new StringBuilder();
        command_buf.append(ClientImport.FABRIC_COMMAND_GET_LINK_DATA); 
        command_buf.append(this.clientFabricInfo().linkIdStr()); 
        String command_str = command_buf.toString();
        
    	this.debug(false, "getLinkData", "command_str=" + command_str);
    	
    	this.transmitToFabric(command_str);
    }
    
    public void getNameList() {
    	this.debug(false, "getNameList", "link_id=" + this.clientFabricInfo().linkIdStr());
    	
        StringBuilder command_buf = new StringBuilder();
        command_buf.append(ClientImport.FABRIC_COMMAND_GET_LINK_DATA); 
        command_buf.append(this.clientFabricInfo().linkIdStr()); 
        String command_str = command_buf.toString();
        
    	this.debug(false, "getNameList", "command_str=" + command_str);
    	
    	this.transmitToFabric(command_str);
    }
    
    public void setupSession() {
    	this.debug(false, "setupSession", "link_id=" + this.clientFabricInfo().linkIdStr());
    	
        StringBuilder command_buf = new StringBuilder();
        command_buf.append(ClientImport.FABRIC_COMMAND_SETUP_SESSION); 
        command_buf.append(this.clientFabricInfo().linkIdStr()); 
        command_buf.append(EncodeNumber.encode(this.clientFabricInfo().hisName().length(), ProtocolDefineClass.DATA_LENGTH_SIZE));
        command_buf.append(this.clientFabricInfo().hisName());
        command_buf.append(this.goConfig().getGoConfigStr());
        String command_str = command_buf.toString();
        
    	this.debug(false, "setupSession", "command_str=" + command_str);
    	
    	this.transmitToFabric(command_str);
    }
    
    public void setupSession2() {
    	
    }
    
    public void setupSession3() {
    	this.debug(false, "setupSession3", "link_id=" + this.clientFabricInfo().linkIdStr());
    	
        StringBuilder command_buf = new StringBuilder();
        command_buf.append(ClientImport.FABRIC_COMMAND_SETUP_SESSION3); 
        command_buf.append(this.clientFabricInfo().linkIdStr()); 
        command_buf.append(this.clientFabricInfo().sessionIdStr()); 
        String command_str = command_buf.toString();
        
    	this.debug(false, "setupSession3", "command_str=" + command_str);
    	
    	this.transmitToFabric(command_str);
    }
    
    public void removeSession() {
    	
    }
    
    public void putSessionData() {
    	this.debug(false, "putSessionData", "link_id=" + this.clientFabricInfo().linkIdStr());
    	
        StringBuilder command_buf = new StringBuilder();
        command_buf.append(ClientImport.FABRIC_COMMAND_PUT_SESSION_DATA); 
        command_buf.append(this.clientFabricInfo().linkIdStr()); 
        command_buf.append(this.clientFabricInfo().sessionIdStr());
        String command_str = command_buf.toString();
        
    	this.debug(false, "putSessionData", "command_str=" + command_str);
    	
    	this.transmitToFabric(command_str);
    	
    }
    
    public void getSessionData() {
    	
    }
    
    private void debug(Boolean on_off, String s0, String s1) { if (on_off) this.log(s0, s1); }
    private void log(String s0, String s1) { this.clientRoot().logIt(this.objectName() + "." + s0 + "()", s1); }
    protected void abend(String s0, String s1) { this.clientRoot().abendIt(this.objectName() + "." + s0 + "()", s1); }
}
