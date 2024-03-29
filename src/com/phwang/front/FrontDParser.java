/*
 ******************************************************************************
 *                                       
 *  Copyright (c) 2021 phwang. All rights reserved.
 *
 ******************************************************************************
 */

package com.phwang.front;

import org.json.simple.JSONObject;
import com.phwang.core.utils.Encoders;
import com.phwang.core.utils.Define;

public class FrontDParser {
    private String objectName() {return "FrontDParser";}
    
    private FrontRoot frontRoot_;
    
    protected FrontRoot frontRoot() { return this.frontRoot_; }
    
    protected FrontDParser(FrontRoot root_val) {
        this.debug(false, "FrontDParser", "init start");
        this.frontRoot_ = root_val;
    }
    
    protected String parserResponseData(String input_data_val) {
    	this.debug(false, "parserResponseData", "input_data_val=" + input_data_val);
    	String json_response_data;
    	
    	char command = input_data_val.charAt(0);
    	
    	if (command == FrontImport.FABRIC_COMMAND_SETUP_LINK) {
    		json_response_data = parserSetupLinkResponse(input_data_val.substring(1));
       		return buildResponseJson(FrontExport.FRONT_COMMAND_SETUP_LINK, json_response_data);
    	}	
    	else if (command == FrontImport.FABRIC_COMMAND_GET_LINK_DATA) {
    		json_response_data = parserGetLinkDataResponse(input_data_val.substring(1));
       		return buildResponseJson(FrontExport.FRONT_COMMAND_GET_LINK_DATA, json_response_data);
    	}	
    	else if (command == FrontImport.FABRIC_COMMAND_GET_NAME_LIST) {
    		json_response_data = parserGetNameListResponse(input_data_val.substring(1));
       		return buildResponseJson(FrontExport.FRONT_COMMAND_GET_NAME_LIST, json_response_data);
    	}	
    	else if (command == FrontImport.FABRIC_COMMAND_SETUP_SESSION) {
    		json_response_data = parserSetupSessionResponse(input_data_val.substring(1));
       		return buildResponseJson(FrontExport.FRONT_COMMAND_SETUP_SESSION, json_response_data);
    	}	
    	else if (command == FrontImport.FABRIC_COMMAND_SETUP_SESSION2) {
    		json_response_data = parserSetupSession2Response(input_data_val.substring(1));
       		return buildResponseJson(FrontExport.FRONT_COMMAND_SETUP_SESSION2, json_response_data);
    	}	
    	else if (command == FrontImport.FABRIC_COMMAND_SETUP_SESSION3) {
    		json_response_data = parserSetupSession3Response(input_data_val.substring(1));
       		return buildResponseJson(FrontExport.FRONT_COMMAND_SETUP_SESSION3, json_response_data);
    	}	
    	else if (command == FrontImport.FABRIC_COMMAND_PUT_SESSION_DATA) {
    		json_response_data = parserPutSessionDataResponse(input_data_val.substring(1));
       		return buildResponseJson(FrontExport.FRONT_COMMAND_PUT_SESSION_DATA, json_response_data);
    	}	
    	else if (command == FrontImport.FABRIC_COMMAND_GET_SESSION_DATA) {
    		json_response_data = parserGetSessionDataResponse(input_data_val.substring(1));
       		return buildResponseJson(FrontExport.FRONT_COMMAND_GET_SESSION_DATA, json_response_data);
    	}
    	else {
    		json_response_data = null;////////////////for now
    	}
    	
		return json_response_data;
    }
    
    private String buildResponseJson(String command_str_val, String data_str_val) {
   		
    	JSONObject json_response = new JSONObject();
    	json_response.put("command", command_str_val);
    	json_response.put("data", data_str_val);
   		String str_json_response = json_response.toJSONString();

   		return str_json_response;
    }
    
    private String parserSetupLinkResponse(String input_str_val) {
    	this.debug(false, "parserSetupLinkResponse", "input_str_val=" + input_str_val);

        String rest_str = input_str_val;
        String link_id_str = Encoders.sSubstring2(rest_str);
        rest_str = Encoders.sSubstring2_(rest_str);
        
        String my_name = Encoders.sDecode2(rest_str);
    	rest_str = Encoders.sDecode2_(rest_str);
    	
    	JSONObject json_data = new JSONObject();
    	json_data.put("my_name", my_name);
    	json_data.put("link_id", link_id_str);
   		String json_str_data = json_data.toJSONString();
   		
   		return json_str_data;
    }

    private String parserGetLinkDataResponse(String input_str_val) {
    	this.debug(false, "parserGetLinkDataResponse", "input_str_val=" + input_str_val);

        String rest_str = input_str_val;
        String link_id_str = Encoders.sSubstring2(rest_str);
        rest_str = Encoders.sSubstring2_(rest_str);

        String data = Encoders.sDecode2(rest_str);
        rest_str = Encoders.sDecode2_(rest_str);

        String pending_session_setup = Encoders.sDecode2(rest_str);
        //rest_str = Encoders.sDecode2_(rest_str);
    	
    	JSONObject json_data = new JSONObject();
    	json_data.put("link_id", link_id_str);
    	json_data.put("data", data);
    	json_data.put("pending_session_setup", pending_session_setup);
   		String json_str_data = json_data.toJSONString();
   		
   		return json_str_data;
    }

    private String parserGetNameListResponse(String input_str_val) {
    	this.debug(false, "parserGetNameListResponse", "input_str_val=" + input_str_val);

        String rest_str = input_str_val;
        String link_id_str = Encoders.sSubstring2(rest_str);
        rest_str = Encoders.sSubstring2_(rest_str);

        String name_list_str = Encoders.sDecode2(rest_str);
        //rest_str = Encoders.sDecode2_(rest_str);
        
    	JSONObject json_data = new JSONObject();
    	json_data.put("link_id", link_id_str);
    	json_data.put("c_name_list", name_list_str);
   		String json_str_data = json_data.toJSONString();
   		
   		return json_str_data;
    }

    private String parserSetupSessionResponse(String input_str_val) {
    	this.debug(false, "generateSetupSessionResponse", "input_str_val=" + input_str_val);

        String rest_str = input_str_val;
        String link_id_str = Encoders.sSubstring2(rest_str);
        rest_str = Encoders.sSubstring2_(rest_str);

        String session_id_str = Encoders.sSubstring2(rest_str);
        rest_str = Encoders.sSubstring2_(rest_str);
    	
    	JSONObject json_data = new JSONObject();
    	json_data.put("link_id", link_id_str);
    	json_data.put("session_id", session_id_str);
   		String json_str_data = json_data.toJSONString();
   		
   		return json_str_data;
    }

    private String parserSetupSession2Response(String input_str_val) {
    	this.debug(false, "parserSetupSession2Response", "input_str_val=" + input_str_val);

        String rest_str = input_str_val;
        String link_id_str = Encoders.sSubstring2(rest_str);
        rest_str = Encoders.sSubstring2_(rest_str);

        String session_id_str = Encoders.sSubstring2(rest_str);
        rest_str = Encoders.sSubstring2_(rest_str);

        String theme_id_str = Encoders.sSubstring2(rest_str);
        rest_str = Encoders.sSubstring2_(rest_str);
    	
    	JSONObject json_data = new JSONObject();
    	json_data.put("link_id", link_id_str);
    	json_data.put("session_id", session_id_str);
    	json_data.put("theme_id", theme_id_str);
   		String json_str_data = json_data.toJSONString();
   		
   		return json_str_data;
    }

    private String parserSetupSession3Response(String input_str_val) {
    	this.debug(false, "parserSetupSession3Response", "input_str_val=" + input_str_val);

        String rest_str = input_str_val;
        String link_id_str = Encoders.sSubstring2(rest_str);
        rest_str = Encoders.sSubstring2_(rest_str);

        String session_id_str = Encoders.sSubstring2(rest_str);
        rest_str = Encoders.sSubstring2_(rest_str);

        String theme_id_str = Encoders.sSubstring2(rest_str);
        rest_str = Encoders.sSubstring2_(rest_str);
    	
    	JSONObject json_data = new JSONObject();
    	json_data.put("link_id", link_id_str);
    	json_data.put("session_id", session_id_str);
    	json_data.put("theme_id", theme_id_str);
   		String json_str_data = json_data.toJSONString();
   		
   		return json_str_data;
    }

    private String parserPutSessionDataResponse(String input_str_val) {
    	this.debug(false, "parserPutSessionDataResponse", "input_str_val=" + input_str_val);

        String rest_str = input_str_val;
        String link_id_str = Encoders.sSubstring2(rest_str);
        rest_str = Encoders.sSubstring2_(rest_str);

        String session_id_str = Encoders.sSubstring2(rest_str);
        rest_str = Encoders.sSubstring2_(rest_str);

        String c_data = Encoders.sDecode2(rest_str);
        //rest_str = Encoders.sDecode2_(rest_str);

    	JSONObject json_data = new JSONObject();
    	json_data.put("link_id", link_id_str);
    	json_data.put("session_id", session_id_str);
    	json_data.put("c_data", c_data);
   		String json_str_data = json_data.toJSONString();
   		
   		return json_str_data;
    }

    private String parserGetSessionDataResponse(String input_str_val) {
    	this.debug(false, "parserGetSessionDataResponse", "input_str_val=" + input_str_val);

        String rest_str = input_str_val;
        String link_id_str = Encoders.sSubstring2(rest_str);
        rest_str = Encoders.sSubstring2_(rest_str);

        String session_id_str = Encoders.sSubstring2(rest_str);
        rest_str = Encoders.sSubstring2_(rest_str);

        String c_data = Encoders.sDecode5(rest_str);
    	//rest_str = Encoders.sDecode5_(rest_str);
    	
    	JSONObject json_data = new JSONObject();
    	json_data.put("link_id", link_id_str);
    	json_data.put("session_id", session_id_str);
    	json_data.put("c_data", c_data);
   		String json_str_data = json_data.toJSONString();

   		return json_str_data;
    }

    private void debug(Boolean on_off, String s0, String s1) { if (on_off) this.log(s0, s1); }
    private void log(String s0, String s1) { this.frontRoot().logIt(this.objectName() + "." + s0 + "()", s1); }
    protected void abend(String s0, String s1) { this.frontRoot().abendIt(this.objectName() + "." + s0 + "()", s1); }
}
