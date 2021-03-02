/*
 ******************************************************************************
 *                                       
 *  Copyright (c) 2021 phwang. All rights reserved.
 *
 ******************************************************************************
 */

package phwang.front;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import phwang.utils.AbendClass;
import phwang.utils.ListMgrClass;
import phwang.utils.EncodeNumberClass;
import phwang.browser.BrowserDefine;
import phwang.fabric.GroupClass;
import phwang.fabric.LinkClass;
import phwang.fabric.SessionClass;
import phwang.protocols.ProtocolDefineClass;
import phwang.protocols.FabricFrontEndProtocolClass;

public class DFrontParserClass {
    private String objectName() {return "DFrontParserClass";}
    
    private FrontRootClass frontRootObject_;
    
    public FrontRootClass frontEndRootObject() { return this.frontRootObject_; }
    public DFrontParserClass dFrontParserObject() { return this.frontEndRootObject().dFrontParserObject(); }
    
    public DFrontParserClass(FrontRootClass front_root_object_val) {
        this.debug(false, "DFrontParserClass", "init start");

        this.frontRootObject_ = front_root_object_val;
    }

    public String parseInputPacket(String input_data_val) {
        String json_str = input_data_val;
        String command = null;
        String data = null;
        
        try {
        	JSONParser parser = new JSONParser();
        	JSONObject json = (JSONObject) parser.parse(json_str);

            command = (String) json.get("command");
            data = (String) json.get("data");
        
        }
        catch(ParseException pe) {
        	this.log("parseInputPacket", "position: " + pe.getPosition());
        	this.abend("parseInputPacket", "ParseException: " + pe + " json_str=" + json_str);
        	return "ParseException";
        }
        catch (Exception e) {
        	this.abend("parseInputPacket", "Exception: " + e + " json_str=" + json_str);
        	return "ParseException";
        }
        
        this.debug(false, "parseInputPacket", "*********************command = " + command);
        String response_data = null;
        if (command.equals("setup_link")) {
            response_data = this.processSetupLinkRequest(data);
        }
        else if (command.equals("get_link_data")) {
            //response_data = this.processGetLinkDataRequest(data);
        }
        else if (command.equals("get_name_list")) {
            //response_data = this.processGetNameListRequest(data);
        }
        else if (command.equals("setup_session")) {
            response_data = this.processSetupSessionRequest(data);
        }
        else if (command.equals("setup_session2")) {
            //response_data = this.processSetupSession2Request(data);
        }
        else if (command.equals("setup_session3")) {
            response_data = this.processSetupSession3Request(data);
        }
        else if (command.equals("put_session_data")) {
            response_data = this.processPutSessionDataRequest(data);
        }
        else if (command.equals("get_session_data")) {
            response_data = this.processGetSessionDataRequest(data);
        }
        else {
            //response_data = "command " + command + " not supported";
            //this.abend("parseInputPacket", response_data);
        }
        
        return(response_data);
    }

    private String processSetupLinkRequest(String json_str_val) {
        this.debug(false, "processSetupLinkRequest", "json_str_val = " + json_str_val);
    	String my_name;
    	String password;

        try {
            JSONParser parser = new JSONParser();
        	JSONObject json = (JSONObject) parser.parse(json_str_val);
        	my_name = (String) json.get("my_name");
        	password = (String) json.get("password");
        }
        catch(ParseException pe) {
        	this.abend("processSetupLinkRequest", "ParseException: " + pe + " json_str=" + json_str_val);
        	return "ParseException";
        }
        catch (Exception e) {
        	this.abend("processSetupLinkRequest", "Exception: " + e + " json_str=" + json_str_val);
        	return "Exception";
        }
    	
        this.debug(true, "processSetupLinkRequest", "my_name = " + my_name);
        this.debug(true, "processSetupLinkRequest", "password = " + password);

        StringBuilder response_buf = new StringBuilder(FabricImportClass.FABRIC_COMMAND_SETUP_LINK); 
        response_buf.append(EncodeNumberClass.encodeNumber(my_name.length(), ProtocolDefineClass.DATA_LENGTH_SIZE));
        response_buf.append(my_name);
        response_buf.append(EncodeNumberClass.encodeNumber(password.length(), ProtocolDefineClass.DATA_LENGTH_SIZE));
        response_buf.append(password);
        return response_buf.toString();
    }

    private String processSetupSessionRequest(String json_str_val) {
        this.debug(false, "processSetupSessionRequest", "json_str_val = " + json_str_val);
    	String link_id_str;
    	String his_name;
    	String theme_data_str;

        try {
            JSONParser parser = new JSONParser();
        	JSONObject json = (JSONObject) parser.parse(json_str_val);
        	link_id_str = (String) json.get("link_id");
        	his_name = (String) json.get("his_name");
        	theme_data_str = (String) json.get("theme_data");
        } 
        catch(ParseException pe) {
        	this.abend("processSetupSessionRequest", "ParseException: " + pe + " json_str=" + json_str_val);
        	return "ParseException";
        }
        catch (Exception e) {
        	this.abend("processSetupSessionRequest", "Exception: " + e + " json_str=" + json_str_val);
        	return "Exception";
        }
    	
        this.debug(false, "processSetupSessionRequest", "link_id = " + link_id_str);
        this.debug(false, "processSetupSessionRequest", "his_name = " + his_name);
        this.debug(false, "processSetupSessionRequest", "theme_data = " + theme_data_str);

        StringBuilder response_buf = new StringBuilder(FabricImportClass.FABRIC_COMMAND_SETUP_SESSION); 
        response_buf.append(link_id_str);
        response_buf.append(EncodeNumberClass.encodeNumber(his_name.length(), ProtocolDefineClass.DATA_LENGTH_SIZE));
        response_buf.append(his_name);
        response_buf.append(EncodeNumberClass.encodeNumber(theme_data_str.length(), ProtocolDefineClass.DATA_LENGTH_SIZE));
        response_buf.append(theme_data_str);
        return response_buf.toString();
    }

    private String processSetupSession3Request(String json_str_val) {
        this.debug(false, "processSetupSession3Request", "json_str_val = " + json_str_val);
    	String link_id_str;
    	String session_id_str;
        
        try {
            JSONParser parser = new JSONParser();
        	JSONObject json = (JSONObject) parser.parse(json_str_val);
        	link_id_str = (String) json.get("link_id");
        	session_id_str = (String) json.get("session_id");
        } 
        catch(ParseException pe) {
        	this.abend("processSetupSessionRequest", "ParseException: " + pe + " json_str=" + json_str_val);
        	return "ParseException";
        }
        catch (Exception e) {
        	this.abend("processSetupSessionRequest", "Exception: " + e + " json_str=" + json_str_val);
        	return "Exception";
        }
    	
        this.debug(false, "processSetupSession3Request", "link_id = " + link_id_str);
        this.debug(false, "processSetupSession3Request", "session_id = " + session_id_str);

        StringBuilder response_buf = new StringBuilder(FabricImportClass.FABRIC_COMMAND_SETUP_SESSION3); 
        response_buf.append(link_id_str);
        response_buf.append(session_id_str);
        return response_buf.toString();
    }
    
    private String processPutSessionDataRequest(String json_str_val) {
        this.debug(false, "processPutSessionDataRequest", "json_str_val = " + json_str_val);
    	String link_id_str;
    	String session_id_str;
    	String data;
    	//String xmt_seq_str;
       
        try {
            JSONParser parser = new JSONParser();
        	JSONObject json = (JSONObject) parser.parse(json_str_val);
        	link_id_str = (String) json.get("link_id");
        	session_id_str = (String) json.get("session_id");
        	data = (String) json.get("data");
        	//xmt_seq_str = (String) json.get("xmt_seq");
        } 
        catch(ParseException pe) {
        	this.abend("processPutSessionDataRequest", "ParseException: " + pe + " json_str=" + json_str_val);
        	return "ParseException";
        }
        catch (Exception e) {
        	this.abend("processPutSessionDataRequest", "Exception: " + e + " json_str=" + json_str_val);
        	return "Exception";
        }
    	
        this.debug(false, "processPutSessionDataRequest", "link_id = " + link_id_str);
        this.debug(false, "processPutSessionDataRequest", "session_id = " + session_id_str);
        //this.debug(false, "processPutSessionDataRequest", "xmt_seq = " + xmt_seq_str);
        this.debug(false, "processPutSessionDataRequest", "data = " + data);

        StringBuilder response_buf = new StringBuilder(FabricImportClass.FABRIC_COMMAND_PUT_SESSION_DATA); 
        response_buf.append(link_id_str);
        response_buf.append(session_id_str);
        response_buf.append(EncodeNumberClass.encodeNumber(data.length(), ProtocolDefineClass.DATA_LENGTH_SIZE));
        response_buf.append(data);
        return response_buf.toString();
    }
    
    private String processGetSessionDataRequest(String json_str_val) {
        this.debug(false, "processGetSessionDataRequest", "json_str_val = " + json_str_val);
    	String link_id_str;
    	String session_id_str;
        try {
            JSONParser parser = new JSONParser();
        	JSONObject json = (JSONObject) parser.parse(json_str_val);
        	link_id_str = (String) json.get("link_id");
        	session_id_str = (String) json.get("session_id");
        } 
        catch(ParseException pe) {
        	this.abend("processGetSessionDataRequest", "ParseException: " + pe + " json_str=" + json_str_val);
        	return "ParseException";
        }
        catch (Exception e) {
        	this.abend("processGetSessionDataRequest", "Exception: " + e + " json_str=" + json_str_val);
        	return "Exception";
        }
    	
        this.debug(false, "processPutSessionDataRequest", "link_id = " + link_id_str);
        this.debug(false, "processPutSessionDataRequest", "session_id = " + session_id_str);

        StringBuilder response_buf = new StringBuilder(FabricImportClass.FABRIC_COMMAND_GET_SESSION_DATA); 
        response_buf.append(link_id_str);
        response_buf.append(session_id_str);
        return response_buf.toString();
    }
    
    private void debug(Boolean on_off, String s0, String s1) { if (on_off) this.log(s0, s1); }
    private void log(String s0, String s1) { AbendClass.log(this.objectName() + "." + s0 + "()", s1); }
    public void abend(String s0, String s1) { AbendClass.abend(this.objectName() + "." + s0 + "()", s1); }
}
