/*
 ******************************************************************************
 *                                       
 *  Copyright (c) 2021 phwang. All rights reserved.
 *
 ******************************************************************************
 */

package com.phwang.client;

import com.phwang.core.utils.Encoders;
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
    	
    	switch (input_data_val.charAt(0)) {
            case ClientImport.FABRIC_COMMAND_SETUP_LINK:
    		    parserSetupLinkResponse(input_data_val.substring(1));
    		    break;
            case ClientImport.FABRIC_COMMAND_GET_LINK_DATA:
    		    parserGetLinkDataResponse(input_data_val.substring(1));
    		    break;
            case ClientImport.FABRIC_COMMAND_GET_NAME_LIST:
    		    parserGetNameListResponse(input_data_val.substring(1));
    		    break;
            case ClientImport.FABRIC_COMMAND_SETUP_SESSION:
    		    parserSetupSessionResponse(input_data_val.substring(1));
    		    break;
            case ClientImport.FABRIC_COMMAND_SETUP_SESSION2:
    		    parserSetupSession2Response(input_data_val.substring(1));
    		    break;
            case ClientImport.FABRIC_COMMAND_SETUP_SESSION3:
    		    parserSetupSession3Response(input_data_val.substring(1));
    		    break;
            case ClientImport.FABRIC_COMMAND_PUT_SESSION_DATA:
    		    parserPutSessionDataResponse(input_data_val.substring(1));
    		    break;
            case ClientImport.FABRIC_COMMAND_GET_SESSION_DATA:
    		    parserGetSessionDataResponse(input_data_val.substring(1));
    		    break;
            default:
    		    this.abend("parserResponseData", "input_data_val=" + input_data_val);
    		    break;
    	}
    }
    
    private void parserSetupLinkResponse(String input_str_val) {
    	this.debug(true, "parserSetupLinkResponse", "input_str_val=" + input_str_val);
    	
        String rest_str = input_str_val;
        String link_id_str = Encoders.sSubstring2(rest_str);
        rest_str = Encoders.sSubstring2_(rest_str);
        
        String my_name = Encoders.sDecode2(rest_str);
    	rest_str = Encoders.sDecode2_(rest_str);
    	
    	this.clientFabricInfo().setLinkIdStr(link_id_str);
    	this.importInterface().handleSetupLinkResponse();
    }

    private void parserGetLinkDataResponse(String input_str_val) {
    	this.debug(false, "parserGetLinkDataResponse", "input_str_val=" + input_str_val);
    	
        String rest_str = input_str_val;
        String link_id_str = Encoders.sSubstring2(rest_str);
        rest_str = Encoders.sSubstring2_(rest_str);

        String data = Encoders.sDecode2(rest_str);
        rest_str = Encoders.sDecode2_(rest_str);

        String pending_session_setup = Encoders.sDecode2(rest_str);
        //rest_str = Encoders.sDecode2_(rest_str);

    	this.importInterface().handleGetLinkDataResponse();
    }

    private void parserGetNameListResponse(String input_str_val) {
    	this.debug(false, "parserGetNameListResponse", "input_str_val=" + input_str_val);
    	
        String rest_str = input_str_val;
        String link_id_str = Encoders.sSubstring2(rest_str);
        rest_str = Encoders.sSubstring2_(rest_str);

        String name_list_str = Encoders.sDecode2(rest_str);
        //rest_str = Encoders.sDecode2_(rest_str);

    	this.importInterface().handleGetNameListResponse();
    }

    private void parserSetupSessionResponse(String input_str_val) {
    	this.debug(true, "parserSetupSessionResponse", "input_str_val=" + input_str_val);
    	
        String rest_str = input_str_val;
        String link_id_str = Encoders.sSubstring2(rest_str);
        rest_str = Encoders.sSubstring2_(rest_str);

        this.debug(true, "parserSetupSessionResponse", "rest_str=" + rest_str);

        String session_id_str = Encoders.sSubstring2(rest_str);
        rest_str = Encoders.sSubstring2_(rest_str);

        this.debug(true, "parserSetupSessionResponse", "session_id_str=" + session_id_str);

        this.clientFabricInfo().setSessionIdStr(session_id_str);
    	this.importInterface().handleSetupSessionResponse();
    }

    private void parserSetupSession2Response(String input_str_val) {
    	this.debug(false, "parserSetupSession2Response", "input_str_val=" + input_str_val);
    	
        String rest_str = input_str_val;
        String link_id_str = Encoders.sSubstring2(rest_str);
        rest_str = Encoders.sSubstring2_(rest_str);

        String session_id_str = Encoders.sSubstring2(rest_str);
        rest_str = Encoders.sSubstring2_(rest_str);

        String theme_id_str = Encoders.sSubstring2(rest_str);
        rest_str = Encoders.sSubstring2_(rest_str);

    	this.importInterface().handleSetupSession2Response();
    }

    private void parserSetupSession3Response(String input_str_val) {
    	this.debug(false, "parserSetupSession3Response", "input_str_val=" + input_str_val);
    	
        String rest_str = input_str_val;
        String link_id_str = Encoders.sSubstring2(rest_str);
        rest_str = Encoders.sSubstring2_(rest_str);

        String session_id_str = Encoders.sSubstring2(rest_str);
        rest_str = Encoders.sSubstring2_(rest_str);

        String theme_id_str = Encoders.sSubstring2(rest_str);
        rest_str = Encoders.sSubstring2_(rest_str);

    	this.importInterface().handleSetupSession3Response();
    }

    private void parserPutSessionDataResponse(String input_str_val) {
    	this.debug(false, "parserPutSessionDataResponse", "input_str_val=" + input_str_val);
    	
        String rest_str = input_str_val;
        String link_id_str = Encoders.sSubstring2(rest_str);
        rest_str = Encoders.sSubstring2_(rest_str);

        String session_id_str = Encoders.sSubstring2(rest_str);
        rest_str = Encoders.sSubstring2_(rest_str);

        String c_data = Encoders.sDecode2(rest_str);
        //rest_str = Encoders.sDecode2_(rest_str);

    	this.importInterface().handlePutSessionDataResponse();
    }

    private void parserGetSessionDataResponse(String input_str_val) {
    	this.debug(false, "parserGetSessionDataResponse", "input_str_val=" + input_str_val);
    	
        String rest_str = input_str_val;
        String link_id_str = Encoders.sSubstring2(rest_str);
        rest_str = Encoders.sSubstring2_(rest_str);

        String session_id_str = Encoders.sSubstring2(rest_str);
        rest_str = Encoders.sSubstring2_(rest_str);

        String c_data = Encoders.sDecode5(rest_str);
    	//rest_str = Encoders.sDecode5_(rest_str);
    	
    	this.importInterface().handleGetSessionDataResponse(c_data);
    }

    private void debug(Boolean on_off, String s0, String s1) { if (on_off) this.log(s0, s1); }
    private void log(String s0, String s1) { this.clientRoot().logIt(this.objectName() + "." + s0 + "()", s1); }
    protected void abend(String s0, String s1) { this.clientRoot().abendIt(this.objectName() + "." + s0 + "()", s1); }
}
