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
import phwang.utils.EncodeNumber;
import phwang.protocols.ProtocolDefineClass;

public class FrontUParser {
    private String objectName() {return "FrontUParser";}
    
    private FrontRoot frontRoot_;
    
    public FrontRoot frontRoot() { return this.frontRoot_; }
    public FrontUParser frontUParser() { return this.frontRoot().frontUParser(); }
    
    public FrontUParser(FrontRoot root_val) {
        this.debug(false, "FrontUParser", "init start");

        this.frontRoot_ = root_val;
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
        
        String response_data;
        if (command.equals(FrontExport.FRONT_COMMAND_SETUP_LINK)) {
            response_data = this.processSetupLinkRequest(data);
        }
        else if (command.equals(FrontExport.FRONT_COMMAND_GET_LINK_DATA)) {
            response_data = this.processGetLinkDataRequest(data);
        }
        else if (command.equals(FrontExport.FRONT_COMMAND_GET_NAME_LIST)) {
            response_data = this.processGetNameListRequest(data);
        }
        else if (command.equals(FrontExport.FRONT_COMMAND_SETUP_SESSION)) {
            response_data = this.processSetupSessionRequest(data);
        }
        else if (command.equals(FrontExport.FRONT_COMMAND_SETUP_SESSION2)) {
            response_data = this.processSetupSession2Request(data);
        }
        else if (command.equals(FrontExport.FRONT_COMMAND_SETUP_SESSION3)) {
            response_data = this.processSetupSession3Request(data);
        }
        else if (command.equals(FrontExport.FRONT_COMMAND_PUT_SESSION_DATA)) {
            response_data = this.processPutSessionDataRequest(data);
        }
        else if (command.equals(FrontExport.FRONT_COMMAND_GET_SESSION_DATA)) {
            response_data = this.processGetSessionDataRequest(data);
        }
        else {
            response_data = "command " + command + " not supported";
            this.abend("parseInputPacket", response_data);
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
    	
        this.debug(false, "processSetupLinkRequest", "my_name = " + my_name);
        this.debug(false, "processSetupLinkRequest", "password = " + password);

        StringBuilder response_buf = new StringBuilder();
        response_buf.append(FrontImport.FABRIC_COMMAND_SETUP_LINK); 
        response_buf.append(EncodeNumber.encodeNumber(my_name.length(), ProtocolDefineClass.DATA_LENGTH_SIZE));
        response_buf.append(my_name);
        response_buf.append(EncodeNumber.encodeNumber(password.length(), ProtocolDefineClass.DATA_LENGTH_SIZE));
        response_buf.append(password);
        return response_buf.toString();
    }
    
    private String processGetLinkDataRequest(String json_str_val) {
        this.debug(false, "processGetLinkDataRequest", "json_str_val = " + json_str_val);
    	String link_id_str;

        try {
            JSONParser parser = new JSONParser();
        	JSONObject json = (JSONObject) parser.parse(json_str_val);
        	link_id_str = (String) json.get("link_id");
        }
        catch(ParseException pe) {
        	this.abend("processGetLinkDataRequest", "ParseException: " + pe + " json_str=" + json_str_val);
        	return "ParseException";
        }
        catch (Exception e) {
        	this.abend("processGetLinkDataRequest", "Exception: " + e + " json_str=" + json_str_val);
        	return "Exception";
        }
    	
        this.debug(false, "processGetLinkDataRequest", "link_id = " + link_id_str);

        StringBuilder response_buf = new StringBuilder();
        response_buf.append(FrontImport.FABRIC_COMMAND_GET_LINK_DATA); 
        response_buf.append(link_id_str);
        return response_buf.toString();
    }
    
    private String processGetNameListRequest(String json_str_val) {
        this.debug(false, "processGetNameListRequest", "json_str_val = " + json_str_val);
    	String link_id_str;
    	String name_list_tag_str;

        try {
            JSONParser parser = new JSONParser();
        	JSONObject json = (JSONObject) parser.parse(json_str_val);
        	link_id_str = (String) json.get("link_id");
        	name_list_tag_str = (String) json.get("name_list_tag");
        } 
        catch(ParseException pe) {
        	this.abend("processGetNameListRequest", "ParseException: " + pe + " json_str=" + json_str_val);
        	return "ParseException";
        }
        catch (Exception e) {
        	this.abend("processGetNameListRequest", "Exception: " + e + " json_str=" + json_str_val);
        	return "Exception";
        }

        StringBuilder response_buf = new StringBuilder();
        response_buf.append(FrontImport.FABRIC_COMMAND_GET_NAME_LIST); 
        response_buf.append(link_id_str);
        response_buf.append(name_list_tag_str);
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

        StringBuilder response_buf = new StringBuilder();
        response_buf.append(FrontImport.FABRIC_COMMAND_SETUP_SESSION); 
        response_buf.append(link_id_str);
        response_buf.append(EncodeNumber.encodeNumber(his_name.length(), ProtocolDefineClass.DATA_LENGTH_SIZE));
        response_buf.append(his_name);
        response_buf.append(EncodeNumber.encodeNumber(theme_data_str.length(), ProtocolDefineClass.DATA_LENGTH_SIZE));
        response_buf.append(theme_data_str);
        return response_buf.toString();
    }

    private String processSetupSession2Request(String json_str_val) {
        this.debug(false, "processSetupSession2Request", "json_str_val = " + json_str_val);
    	String link_id_str;
    	String session_id_str;
    	String theme_id_str;
    	String accept_str;
    	String theme_data_str;

        try {
            JSONParser parser = new JSONParser();
        	JSONObject json = (JSONObject) parser.parse(json_str_val);
        	link_id_str = (String) json.get("link_id");
        	session_id_str = (String) json.get("session_id");
        	theme_id_str = (String) json.get("theme_id");
        	accept_str = (String) json.get("accept");
        	theme_data_str = (String) json.get("theme_data");
        } 
        catch(ParseException pe) {
        	this.abend("processSetupSession2Request", "ParseException: " + pe + " json_str=" + json_str_val);
        	return "ParseException";
        }
        catch (Exception e) {
        	this.abend("processSetupSession2Request", "Exception: " + e + " json_str=" + json_str_val);
        	return "Exception";
        }
        
        this.debug(false, "processSetupSession2Request", "link_id = " + link_id_str);
        this.debug(false, "processSetupSession2Request", "theme_id_str = " + theme_id_str);
        this.debug(false, "processSetupSession2Request", "theme_data = " + theme_data_str);

        StringBuilder response_buf = new StringBuilder();
        response_buf.append(FrontImport.FABRIC_COMMAND_SETUP_SESSION2); 
        response_buf.append(link_id_str);
        response_buf.append(session_id_str);
        response_buf.append(theme_id_str);
        response_buf.append(EncodeNumber.encodeNumber(theme_data_str.length(), ProtocolDefineClass.DATA_LENGTH_SIZE));
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
        	this.abend("processSetupSession3Request", "ParseException: " + pe + " json_str=" + json_str_val);
        	return "ParseException";
        }
        catch (Exception e) {
        	this.abend("processSetupSession3Request", "Exception: " + e + " json_str=" + json_str_val);
        	return "Exception";
        }
    	
        this.debug(false, "processSetupSession3Request", "link_id = " + link_id_str);
        this.debug(false, "processSetupSession3Request", "session_id = " + session_id_str);

        StringBuilder response_buf = new StringBuilder();
        response_buf.append(FrontImport.FABRIC_COMMAND_SETUP_SESSION3); 
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

        StringBuilder response_buf = new StringBuilder();
        response_buf.append(FrontImport.FABRIC_COMMAND_PUT_SESSION_DATA); 
        response_buf.append(link_id_str);
        response_buf.append(session_id_str);
        response_buf.append(EncodeNumber.encodeNumber(data.length(), ProtocolDefineClass.DATA_LENGTH_SIZE));
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

        StringBuilder response_buf = new StringBuilder();
        response_buf.append(FrontImport.FABRIC_COMMAND_GET_SESSION_DATA); 
        response_buf.append(link_id_str);
        response_buf.append(session_id_str);
        return response_buf.toString();
    }
    
    private void debug(Boolean on_off, String s0, String s1) { if (on_off) this.log(s0, s1); }
    private void log(String s0, String s1) { this.frontRoot().logIt(this.objectName() + "." + s0 + "()", s1); }
    public void abend(String s0, String s1) { this.frontRoot().abendIt(this.objectName() + "." + s0 + "()", s1); }
}
