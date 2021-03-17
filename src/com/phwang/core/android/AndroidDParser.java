/*
 ******************************************************************************
 *                                       
 *  Copyright (c) 2021 phwang. All rights reserved.
 *
 ******************************************************************************
 */

package com.phwang.core.android;

import org.json.simple.JSONObject;
import com.phwang.core.protocols.ProtocolDefineClass;
import com.phwang.core.utils.EncodeNumber;

public class AndroidDParser {
    private String objectName() {return "AndroidDParser";}
    
    private AndroidRoot frontRoot_;
    
    protected AndroidRoot frontRoot() { return this.frontRoot_; }
    
    protected AndroidDParser(AndroidRoot root_val) {
        this.debug(false, "AndroidDParser", "init start");
        this.frontRoot_ = root_val;
    }
    
    protected void parserResponseData(String input_data_val) {
    	this.debug(false, "parserResponseData", "input_data_val=" + input_data_val);
    	
    	char command = input_data_val.charAt(0);
    	
    	if (command == AndroidImport.FABRIC_COMMAND_SETUP_LINK) {
    		parserSetupLinkResponse(input_data_val.substring(1));
    	}	
    	else if (command == AndroidImport.FABRIC_COMMAND_GET_LINK_DATA) {
    		parserGetLinkDataResponse(input_data_val.substring(1));
    	}	
    	else if (command == AndroidImport.FABRIC_COMMAND_GET_NAME_LIST) {
    		parserGetNameListResponse(input_data_val.substring(1));
    	}	
    	else if (command == AndroidImport.FABRIC_COMMAND_SETUP_SESSION) {
    		parserSetupSessionResponse(input_data_val.substring(1));
    	}	
    	else if (command == AndroidImport.FABRIC_COMMAND_SETUP_SESSION2) {
    		parserSetupSession2Response(input_data_val.substring(1));
    	}	
    	else if (command == AndroidImport.FABRIC_COMMAND_SETUP_SESSION3) {
    		parserSetupSession3Response(input_data_val.substring(1));
    	}	
    	else if (command == AndroidImport.FABRIC_COMMAND_PUT_SESSION_DATA) {
    		parserPutSessionDataResponse(input_data_val.substring(1));
    	}	
    	else if (command == AndroidImport.FABRIC_COMMAND_GET_SESSION_DATA) {
    		parserGetSessionDataResponse(input_data_val.substring(1));
    	}
    	else {
    		this.abend("parserResponseData", "input_data_val=" + input_data_val);
    	}
    }
    
    private void parserSetupLinkResponse(String input_str_val) {
    	this.debug(false, "parserSetupLinkResponse", "input_str_val=" + input_str_val);
    	
        String rest_str = input_str_val;
        String link_id_str = rest_str.substring(0, AndroidImport.FABRIC_LINK_ID_SIZE);
        rest_str = rest_str.substring(AndroidImport.FABRIC_LINK_ID_SIZE);
        
        int my_name_len = EncodeNumber.decode(rest_str.substring(0, ProtocolDefineClass.DATA_LENGTH_SIZE));
        rest_str = rest_str.substring(ProtocolDefineClass.DATA_LENGTH_SIZE);
        String my_name = rest_str.substring(0, my_name_len);
    	rest_str = rest_str.substring(my_name_len);
    }

    private void parserGetLinkDataResponse(String input_str_val) {
    	this.debug(false, "parserGetLinkDataResponse", "input_str_val=" + input_str_val);
    	
        String rest_str = input_str_val;
        String link_id_str = rest_str.substring(0, AndroidImport.FABRIC_LINK_ID_SIZE);
        rest_str = rest_str.substring(AndroidImport.FABRIC_LINK_ID_SIZE);
        
        int data_len = EncodeNumber.decode(rest_str.substring(0, ProtocolDefineClass.DATA_LENGTH_SIZE));
        rest_str = rest_str.substring(ProtocolDefineClass.DATA_LENGTH_SIZE);
        String data = rest_str.substring(0, data_len);
    	rest_str = rest_str.substring(data_len);
        
        int pending_session_setup_len = EncodeNumber.decode(rest_str.substring(0, ProtocolDefineClass.DATA_LENGTH_SIZE));
        rest_str = rest_str.substring(ProtocolDefineClass.DATA_LENGTH_SIZE);
        String pending_session_setup = rest_str.substring(0, pending_session_setup_len);
    	rest_str = rest_str.substring(pending_session_setup_len);
    }

    private void parserGetNameListResponse(String input_str_val) {
    	this.debug(false, "parserGetNameListResponse", "input_str_val=" + input_str_val);
    	
        String rest_str = input_str_val;
        String link_id_str = rest_str.substring(0, AndroidImport.FABRIC_LINK_ID_SIZE);
        rest_str = rest_str.substring(AndroidImport.FABRIC_LINK_ID_SIZE);
        
        int name_list_str_len = EncodeNumber.decode(rest_str.substring(0, ProtocolDefineClass.DATA_LENGTH_SIZE));
        rest_str = rest_str.substring(ProtocolDefineClass.DATA_LENGTH_SIZE);
        String name_list_str = rest_str.substring(0, name_list_str_len);
    	rest_str = rest_str.substring(name_list_str_len);
    }

    private void parserSetupSessionResponse(String input_str_val) {
    	this.debug(false, "generateSetupSessionResponse", "input_str_val=" + input_str_val);
    	
        String rest_str = input_str_val;
        String link_id_str = rest_str.substring(0, AndroidImport.FABRIC_LINK_ID_SIZE);
        rest_str = rest_str.substring(AndroidImport.FABRIC_LINK_ID_SIZE);

        String session_id_str = rest_str.substring(0, AndroidImport.FABRIC_SESSION_ID_SIZE);
        rest_str = rest_str.substring(AndroidImport.FABRIC_SESSION_ID_SIZE);
    }

    private void parserSetupSession2Response(String input_str_val) {
    	this.debug(false, "parserSetupSession2Response", "input_str_val=" + input_str_val);
    	
        String rest_str = input_str_val;
        String link_id_str = rest_str.substring(0, AndroidImport.FABRIC_LINK_ID_SIZE);
        rest_str = rest_str.substring(AndroidImport.FABRIC_LINK_ID_SIZE);

        String session_id_str = rest_str.substring(0, AndroidImport.FABRIC_SESSION_ID_SIZE);
        rest_str = rest_str.substring(AndroidImport.FABRIC_SESSION_ID_SIZE);

        String theme_id_str = rest_str.substring(0, AndroidImport.THEME_ROOM_ID_SIZE);
        rest_str = rest_str.substring(AndroidImport.THEME_ROOM_ID_SIZE);
    }

    private void parserSetupSession3Response(String input_str_val) {
    	this.debug(false, "parserSetupSession3Response", "input_str_val=" + input_str_val);
    	
        String rest_str = input_str_val;
        String link_id_str = rest_str.substring(0, AndroidImport.FABRIC_LINK_ID_SIZE);
        rest_str = rest_str.substring(AndroidImport.FABRIC_LINK_ID_SIZE);

        String session_id_str = rest_str.substring(0, AndroidImport.FABRIC_SESSION_ID_SIZE);
        rest_str = rest_str.substring(AndroidImport.FABRIC_SESSION_ID_SIZE);

        String theme_id_str = rest_str.substring(0, AndroidImport.THEME_ROOM_ID_SIZE);
        rest_str = rest_str.substring(AndroidImport.THEME_ROOM_ID_SIZE);
    }

    private void parserPutSessionDataResponse(String input_str_val) {
    	this.debug(false, "parserPutSessionDataResponse", "input_str_val=" + input_str_val);
    	
        String rest_str = input_str_val;
        String link_id_str = rest_str.substring(0, AndroidImport.FABRIC_LINK_ID_SIZE);
        rest_str = rest_str.substring(AndroidImport.FABRIC_LINK_ID_SIZE);

        String session_id_str = rest_str.substring(0, AndroidImport.FABRIC_SESSION_ID_SIZE);
        rest_str = rest_str.substring(AndroidImport.FABRIC_SESSION_ID_SIZE);

        int c_data_len = EncodeNumber.decode(rest_str.substring(0, ProtocolDefineClass.DATA_LENGTH_SIZE));
        rest_str = rest_str.substring(ProtocolDefineClass.DATA_LENGTH_SIZE);
        String c_data = rest_str.substring(0, c_data_len);
    	rest_str = rest_str.substring(c_data_len);
    }

    private void parserGetSessionDataResponse(String input_str_val) {
    	this.debug(false, "parserGetSessionDataResponse", "input_str_val=" + input_str_val);
    	
        String rest_str = input_str_val;
        String link_id_str = rest_str.substring(0, AndroidImport.FABRIC_LINK_ID_SIZE);
        rest_str = rest_str.substring(AndroidImport.FABRIC_LINK_ID_SIZE);

        String session_id_str = rest_str.substring(0, AndroidImport.FABRIC_SESSION_ID_SIZE);
        rest_str = rest_str.substring(AndroidImport.FABRIC_SESSION_ID_SIZE);

        int c_data_len = EncodeNumber.decode(rest_str.substring(0, ProtocolDefineClass.DATA_LENGTH_SIZE));
        rest_str = rest_str.substring(ProtocolDefineClass.DATA_LENGTH_SIZE);
        String c_data = rest_str.substring(0, c_data_len);
    	rest_str = rest_str.substring(c_data_len);
    }

    private void debug(Boolean on_off, String s0, String s1) { if (on_off) this.log(s0, s1); }
    private void log(String s0, String s1) { this.frontRoot().logIt(this.objectName() + "." + s0 + "()", s1); }
    protected void abend(String s0, String s1) { this.frontRoot().abendIt(this.objectName() + "." + s0 + "()", s1); }
}
