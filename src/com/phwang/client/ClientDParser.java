/*
 ******************************************************************************
 *                                       
 *  Copyright (c) 2021 phwang. All rights reserved.
 *
 ******************************************************************************
 */

package com.phwang.client;

import com.phwang.core.utils.EncodeNumber;
import com.phwang.core.utils.Define;

public class ClientDParser {
    private String objectName() {return "ClientDParser";}
    
    private ClientRoot clientRoot_;
    
    protected ClientRoot clientRoot() { return this.clientRoot_; }
    protected ClientDImportInt importInterface() { return this.clientRoot_.importInterface(); }
    private ClientFabricInfo clientFabricInfo() { return this.clientRoot().clientFabricInfo(); }
    
    protected ClientDParser(ClientRoot root_val) {
        this.debug(false, "ClientDParser", "init start");
        this.clientRoot_ = root_val;
    }
    
    protected void parserResponseData(String input_data_val) {
    	this.debug(true, "parserResponseData", "input_data_val=" + input_data_val);
    	
    	char command = input_data_val.charAt(0);
    	
    	if (command == ClientImport.FABRIC_COMMAND_SETUP_LINK) {
    		parserSetupLinkResponse(input_data_val.substring(1));
    	}	
    	else if (command == ClientImport.FABRIC_COMMAND_GET_LINK_DATA) {
    		parserGetLinkDataResponse(input_data_val.substring(1));
    	}	
    	else if (command == ClientImport.FABRIC_COMMAND_GET_NAME_LIST) {
    		parserGetNameListResponse(input_data_val.substring(1));
    	}	
    	else if (command == ClientImport.FABRIC_COMMAND_SETUP_SESSION) {
    		parserSetupSessionResponse(input_data_val.substring(1));
    	}	
    	else if (command == ClientImport.FABRIC_COMMAND_SETUP_SESSION2) {
    		parserSetupSession2Response(input_data_val.substring(1));
    	}	
    	else if (command == ClientImport.FABRIC_COMMAND_SETUP_SESSION3) {
    		parserSetupSession3Response(input_data_val.substring(1));
    	}	
    	else if (command == ClientImport.FABRIC_COMMAND_PUT_SESSION_DATA) {
    		parserPutSessionDataResponse(input_data_val.substring(1));
    	}	
    	else if (command == ClientImport.FABRIC_COMMAND_GET_SESSION_DATA) {
    		parserGetSessionDataResponse(input_data_val.substring(1));
    	}
    	else {
    		this.abend("parserResponseData", "input_data_val=" + input_data_val);
    	}
    }
    
    private void parserSetupLinkResponse(String input_str_val) {
    	this.debug(true, "parserSetupLinkResponse", "input_str_val=" + input_str_val);
    	
        String rest_str = input_str_val;
        String link_id_str = rest_str.substring(0, ClientImport.FABRIC_LINK_ID_SIZE);
        rest_str = rest_str.substring(ClientImport.FABRIC_LINK_ID_SIZE);
        
        int my_name_len = EncodeNumber.decode(rest_str.substring(0, Define.DATA_LENGTH_SIZE));
        rest_str = rest_str.substring(Define.DATA_LENGTH_SIZE);
        String my_name = rest_str.substring(0, my_name_len);
    	rest_str = rest_str.substring(my_name_len);
    	
    	this.clientFabricInfo().setLinkIdStr(link_id_str);
    	this.importInterface().handleSetupLinkResponse();
    }

    private void parserGetLinkDataResponse(String input_str_val) {
    	this.debug(false, "parserGetLinkDataResponse", "input_str_val=" + input_str_val);
    	
        String rest_str = input_str_val;
        String link_id_str = rest_str.substring(0, ClientImport.FABRIC_LINK_ID_SIZE);
        rest_str = rest_str.substring(ClientImport.FABRIC_LINK_ID_SIZE);
        
        int data_len = EncodeNumber.decode(rest_str.substring(0, Define.DATA_LENGTH_SIZE));
        rest_str = rest_str.substring(Define.DATA_LENGTH_SIZE);
        String data = rest_str.substring(0, data_len);
    	rest_str = rest_str.substring(data_len);
        
        int pending_session_setup_len = EncodeNumber.decode(rest_str.substring(0, Define.DATA_LENGTH_SIZE));
        rest_str = rest_str.substring(Define.DATA_LENGTH_SIZE);
        String pending_session_setup = rest_str.substring(0, pending_session_setup_len);
    	rest_str = rest_str.substring(pending_session_setup_len);
    	
    	this.importInterface().handleGetLinkDataResponse();
    }

    private void parserGetNameListResponse(String input_str_val) {
    	this.debug(false, "parserGetNameListResponse", "input_str_val=" + input_str_val);
    	
        String rest_str = input_str_val;
        String link_id_str = rest_str.substring(0, ClientImport.FABRIC_LINK_ID_SIZE);
        rest_str = rest_str.substring(ClientImport.FABRIC_LINK_ID_SIZE);
        
        int name_list_str_len = EncodeNumber.decode(rest_str.substring(0, Define.DATA_LENGTH_SIZE));
        rest_str = rest_str.substring(Define.DATA_LENGTH_SIZE);
        String name_list_str = rest_str.substring(0, name_list_str_len);
    	rest_str = rest_str.substring(name_list_str_len);
    	
    	this.importInterface().handleGetNameListResponse();
    }

    private void parserSetupSessionResponse(String input_str_val) {
    	this.debug(false, "parserSetupSessionResponse", "input_str_val=" + input_str_val);
    	
        String rest_str = input_str_val;
        String link_id_str = rest_str.substring(0, ClientImport.FABRIC_LINK_ID_SIZE);
        rest_str = rest_str.substring(ClientImport.FABRIC_LINK_ID_SIZE);

        String session_id_str = rest_str.substring(0, ClientImport.FABRIC_SESSION_ID_SIZE);
        rest_str = rest_str.substring(ClientImport.FABRIC_SESSION_ID_SIZE);
    	
    	this.clientFabricInfo().setSessionIdStr(session_id_str);
    	this.importInterface().handleSetupSessionResponse();
    }

    private void parserSetupSession2Response(String input_str_val) {
    	this.debug(false, "parserSetupSession2Response", "input_str_val=" + input_str_val);
    	
        String rest_str = input_str_val;
        String link_id_str = rest_str.substring(0, ClientImport.FABRIC_LINK_ID_SIZE);
        rest_str = rest_str.substring(ClientImport.FABRIC_LINK_ID_SIZE);

        String session_id_str = rest_str.substring(0, ClientImport.FABRIC_SESSION_ID_SIZE);
        rest_str = rest_str.substring(ClientImport.FABRIC_SESSION_ID_SIZE);

        String theme_id_str = rest_str.substring(0, ClientImport.THEME_ROOM_ID_SIZE);
        rest_str = rest_str.substring(ClientImport.THEME_ROOM_ID_SIZE);
    	
    	this.importInterface().handleSetupSession2Response();
    }

    private void parserSetupSession3Response(String input_str_val) {
    	this.debug(false, "parserSetupSession3Response", "input_str_val=" + input_str_val);
    	
        String rest_str = input_str_val;
        String link_id_str = rest_str.substring(0, ClientImport.FABRIC_LINK_ID_SIZE);
        rest_str = rest_str.substring(ClientImport.FABRIC_LINK_ID_SIZE);

        String session_id_str = rest_str.substring(0, ClientImport.FABRIC_SESSION_ID_SIZE);
        rest_str = rest_str.substring(ClientImport.FABRIC_SESSION_ID_SIZE);

        String theme_id_str = rest_str.substring(0, ClientImport.THEME_ROOM_ID_SIZE);
        rest_str = rest_str.substring(ClientImport.THEME_ROOM_ID_SIZE);
    	
    	this.importInterface().handleSetupSession3Response();
    }

    private void parserPutSessionDataResponse(String input_str_val) {
    	this.debug(false, "parserPutSessionDataResponse", "input_str_val=" + input_str_val);
    	
        String rest_str = input_str_val;
        String link_id_str = rest_str.substring(0, ClientImport.FABRIC_LINK_ID_SIZE);
        rest_str = rest_str.substring(ClientImport.FABRIC_LINK_ID_SIZE);

        String session_id_str = rest_str.substring(0, ClientImport.FABRIC_SESSION_ID_SIZE);
        rest_str = rest_str.substring(ClientImport.FABRIC_SESSION_ID_SIZE);

        int c_data_len = EncodeNumber.decode(rest_str.substring(0, Define.DATA_LENGTH_SIZE));
        rest_str = rest_str.substring(Define.DATA_LENGTH_SIZE);
        String c_data = rest_str.substring(0, c_data_len);
    	rest_str = rest_str.substring(c_data_len);
    	
    	this.importInterface().handlePutSessionDataResponse();
    }

    private void parserGetSessionDataResponse(String input_str_val) {
    	this.debug(false, "parserGetSessionDataResponse", "input_str_val=" + input_str_val);
    	
        String rest_str = input_str_val;
        String link_id_str = rest_str.substring(0, ClientImport.FABRIC_LINK_ID_SIZE);
        rest_str = rest_str.substring(ClientImport.FABRIC_LINK_ID_SIZE);

        String session_id_str = rest_str.substring(0, ClientImport.FABRIC_SESSION_ID_SIZE);
        rest_str = rest_str.substring(ClientImport.FABRIC_SESSION_ID_SIZE);

        int c_data_len = EncodeNumber.decode(rest_str.substring(0, Define.BIG_DATA_LENGTH_SIZE));
        rest_str = rest_str.substring(Define.BIG_DATA_LENGTH_SIZE);
        String c_data = rest_str.substring(0, c_data_len);
    	rest_str = rest_str.substring(c_data_len);
    	
    	this.importInterface().handleGetSessionDataResponse(c_data);
    }

    private void debug(Boolean on_off, String s0, String s1) { if (on_off) this.log(s0, s1); }
    private void log(String s0, String s1) { this.clientRoot().logIt(this.objectName() + "." + s0 + "()", s1); }
    protected void abend(String s0, String s1) { this.clientRoot().abendIt(this.objectName() + "." + s0 + "()", s1); }
}
