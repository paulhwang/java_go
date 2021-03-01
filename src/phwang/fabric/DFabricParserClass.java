/*
 ******************************************************************************
 *                                       
 *  Copyright (c) 2018 phwang. All rights reserved.
 *
 ******************************************************************************
 */

package phwang.fabric;

//import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import phwang.utils.*;
import phwang.utils.EncodeNumberClass;
import phwang.browser.BrowserDefine;
import phwang.protocols.FabricFrontEndProtocolClass;
import phwang.protocols.FabricThemeProtocolClass;
import org.json.simple.parser.ParseException;

public class DFabricParserClass {
    private String objectName() {return "DFabricParserClass";}

    private String RESPONSE_IS_GET_LINK_DATA_NAME_LIST = FabricFrontEndProtocolClass.WEB_FABRIC_PROTOCOL_RESPOND_IS_GET_LINK_DATA_NAME_LIST;

    private DFabricClass dFabricObject;
    private DFabricResponseClass dFabricResponseObject;

    public FabricRootClass fabricRootObject() { return this.dFabricObject.fabricRootObject(); }
    private UFabricClass UFabricObject() { return this.fabricRootObject().uFabricObject(); }
    private LinkMgrClass LinkMgrObject() { return this.fabricRootObject().linkMgrObject(); }
    private GroupMgrClass GroupMgrObject() { return this.fabricRootObject().groupMgrObject(); }

    public DFabricParserClass(DFabricClass dfabric_object_val) {
        this.debug(false, "DFabricParserClass", "init start");

        this.dFabricObject = dfabric_object_val;
        this.dFabricResponseObject = new DFabricResponseClass(this);
    }

    public void parseInputPacket(String input_data_val) {
        String job_id_str = input_data_val.substring(0, FabricFrontEndProtocolClass.FRONT_JOB_ID_SIZE);
        String json_str = input_data_val.substring(FabricFrontEndProtocolClass.FRONT_JOB_ID_SIZE);
        String command = null;
        String data = null;
        String response_data = null;
        
        this.debug(true, "parseInputPacket", "*****input_data_val = " + input_data_val);
        
        if (json_str.charAt(0) == 'L') {
            response_data = this.processSetupLinkRequest(json_str.substring(1));
            if (response_data == null) {
            	this.abend("parseInputPacket", "response_data is null, command=" + command);
            }
            this.dFabricObject.transmitData(job_id_str + response_data);
        	return;
        }
        
        
        try {
            JSONParser parser = new JSONParser();
        	JSONObject json = (JSONObject) parser.parse(json_str);

            command = (String) json.get("command");
            data = (String) json.get("data");
        
        }
        catch(ParseException pe) {
        	this.log("parseInputPacket", "position: " + pe.getPosition());
        	this.abend("parseInputPacket", "ParseException: " + pe + " json_str=" + json_str + "   input_data=" + input_data_val);
            this.dFabricObject.transmitData(job_id_str + "***ParseException***");
        	return;
        }
        catch (Exception e) {
        	this.abend("parseInputPacket", "Exception: " + e + " json_str=" + json_str + "   input_data=" + input_data_val);
            this.dFabricObject.transmitData(job_id_str + "***Exception***");
        	return;
        }
        
        this.debug(false, "parseInputPacket", "*********************command = " + command);
        if (command.equals("setup_link")) {
            //response_data = this.processSetupLinkRequest(data);
        }
        else if (command.equals("get_link_data")) {
            response_data = this.processGetLinkDataRequest(data);
        }
        else if (command.equals("get_name_list")) {
            response_data = this.processGetNameListRequest(data);
        }
        else if (command.equals("setup_session")) {
            response_data = this.processSetupSessionRequest(data);
        }
        else if (command.equals("setup_session2")) {
            response_data = this.processSetupSession2Request(data);
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
            response_data = "command " + command + " not supported";
            this.abend("parseInputPacket", response_data);
        }
        
        if (response_data == null) {
        	this.abend("parseInputPacket", "response_data is null, command=" + command);
        }
        this.dFabricObject.transmitData(job_id_str + response_data);
    }

    private String processSetupLinkRequest(String input_str_val) {
        this.debug(false, "processSetupLinkRequest", "input_str_val = " + input_str_val);
        
        int my_name_len = EncodeNumberClass.decodeNumber(input_str_val.substring(0, 2));
    	String my_name = input_str_val.substring(2, 2 + my_name_len);
    	String rest_str = input_str_val.substring(2 + my_name_len);
    	
        int password_len = EncodeNumberClass.decodeNumber(rest_str.substring(0, 2));
    	String password = rest_str.substring(2, 2 + password_len);
    	
        this.debug(true, "processSetupLinkRequest", "my_name = " + my_name);
        this.debug(true, "processSetupLinkRequest", "password = " + password);

        LinkClass link = this.LinkMgrObject().mallocLink(my_name);
        if (link == null) {
        	this.abend("processSetupLinkRequest", "link is null");
        	return null;
        }
        String response_data = this.generateSetupLinkResponse(link.linkIdStr(), link.myName());
        return response_data;
    }

    private String processSetupLinkRequest00000(String json_str_val) {
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

        LinkClass link = this.LinkMgrObject().mallocLink(my_name);
        if (link == null) {
        	this.abend("processSetupLinkRequest", "link is null");
        	return null;
        }
        String response_data = this.generateSetupLinkResponse(link.linkIdStr(), link.myName());
        return response_data;
    }
    
    private String generateSetupLinkResponse(String link_id_val, String my_name_val) {
    	JSONObject json_data = new JSONObject();
    	json_data.put("my_name", my_name_val);
    	json_data.put("link_id", link_id_val);
   		String json_str_data = json_data.toJSONString();
   		return json_str_data;
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

        LinkClass link = this.LinkMgrObject().getLinkByIdStr(link_id_str);
        if (link == null) {
            return this.errorProcessGetLinkData(link_id_str, "*************null link");
        }

        String downlink_data = RESPONSE_IS_GET_LINK_DATA_NAME_LIST + this.fabricRootObject().nameListObject().NameListTagStr();

        int max_session_table_array_index = link.GetSessionArrayMaxIndex();
        ListEntryClass[] session_table_array = link.GetSessionArrayEntryTable();
        String pending_session_data = "";
        for (int i = 0; i <= max_session_table_array_index; i++) {
            ListEntryClass list_entry = session_table_array[i];
            SessionClass session = (SessionClass)list_entry.data();
            if (session != null) {
               if (session.getPendingDownLinkDataCount() > 0) {
                    downlink_data = downlink_data + FabricFrontEndProtocolClass.WEB_FABRIC_PROTOCOL_RESPOND_IS_GET_LINK_DATA_PENDING_DATA + link.linkIdStr() + session.SessionIdStr();
                }
            }
        }
       
        String pending_session_setup = "";
        String pending_session_str = link.getPendingSessionSetup();
        if (pending_session_str != null) {
            pending_session_setup = pending_session_setup + FabricFrontEndProtocolClass.WEB_FABRIC_PROTOCOL_RESPOND_IS_GET_LINK_DATA_PENDING_SESSION;
            pending_session_setup = pending_session_setup + pending_session_str;
        }

        String pending_session_str3 = link.getPendingSessionSetup3();
        if (pending_session_str3 != null) {
            pending_session_setup = pending_session_setup + FabricFrontEndProtocolClass.WEB_FABRIC_PROTOCOL_RESPOND_IS_GET_LINK_DATA_PENDING_SESSION3;
            pending_session_setup = pending_session_setup + pending_session_str3;
        }

        downlink_data = downlink_data + pending_session_setup;

        String response_data = this.generateGetLinkDataResponse(link.linkIdStr(), downlink_data, pending_session_setup);
        return response_data;
    }

    private String errorProcessGetLinkData(String link_id_val, String error_msg_val) {
        return error_msg_val;
    }

    public String generateGetLinkDataResponse(String link_id_str_val, String data_val, String pending_session_setup_val) {
    	JSONObject json_data = new JSONObject();
    	json_data.put("link_id", link_id_str_val);
    	json_data.put("data", data_val);
    	json_data.put("pending_session_setup", pending_session_setup_val);
   		String json_str_data = json_data.toJSONString();
   		return json_str_data;
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
    	
        LinkClass link = this.LinkMgrObject().getLinkByIdStr(link_id_str);
        if (link == null) {
            return this.errorProcessGetNameList(link_id_str, "*************null link");
        }

        int name_list_tag = EncodeNumberClass.decodeNumber(name_list_tag_str);
        String name_list = this.fabricRootObject().nameListObject().getNameList(name_list_tag);

        String response_data = this.generateGetNameListResponse(link.linkIdStr(), name_list);
        return response_data;
    }

    private String errorProcessGetNameList(String link_id_val, String error_msg_val) {
        return error_msg_val;
    }

    public String generateGetNameListResponse(String link_id_str_val, String name_list_str_val) {
    	JSONObject json_data = new JSONObject();
    	json_data.put("link_id", link_id_str_val);
    	json_data.put("c_name_list", name_list_str_val);
   		String json_str_data = json_data.toJSONString();
   		return json_str_data;
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

        String theme_id_str = theme_data_str.substring(0, BrowserDefine.BROWSER_THEME_ID_SIZE);
        String theme_data = theme_data_str.substring(BrowserDefine.BROWSER_THEME_ID_SIZE);

        LinkClass link = this.LinkMgrObject().getLinkByIdStr(link_id_str);
        if (link == null) {
            return this.errorProcessSetupSession(link_id_str, "*************null link");
        }
        
        SessionClass session = link.mallocSession();
        session.setBrowserThemeIdStr(theme_id_str);
        GroupClass group = this.GroupMgrObject().mallocGroup(theme_data);
        if (group == null) {
        	this.abend("processSetupSessionRequest", "null group");
            return this.errorProcessSetupSession(link_id_str, "null group");
        }
        group.insertSession(session);
        session.bindGroup(group);
        
        if (his_name.equals(link.myName())) {
            this.mallocRoom(group, theme_data);
        }
        else {
            LinkClass his_link = this.LinkMgrObject().GetLinkByMyName(his_name);
            if (his_link == null) {
                return this.errorProcessSetupSession(link_id_str, "his_link does not exist");
            }
            SessionClass his_session = his_link.mallocSession();
            if (his_session == null) {
                return this.errorProcessSetupSession(link_id_str, "null his_session");
            }

            group.insertSession(his_session);
            his_session.bindGroup(group);

            his_link.setPendingSessionSetup(his_link.linkIdStr() + his_session.SessionIdStr(), theme_data);
        }

        String response_data = this.generateSetupSessionResponse(link.linkIdStr(), session.SessionIdStr());
        return response_data;
    }

    private void mallocRoom(GroupClass group_val, String theme_info_val) {
        String uplink_data = FabricThemeProtocolClass.FABRIC_THEME_PROTOCOL_COMMAND_IS_SETUP_ROOM;
        uplink_data = uplink_data + group_val.GroupIdStr();
        uplink_data = uplink_data + theme_info_val;
        this.fabricRootObject().uFabricObject().transmitData(uplink_data);
    }

    private String errorProcessSetupSession(String link_id_val, String error_msg_val) {
        return error_msg_val;
    }

    public String generateSetupSessionResponse(String link_id_str_val, String session_id_str_val) {
    	JSONObject json_data = new JSONObject();
    	json_data.put("link_id", link_id_str_val);
    	json_data.put("session_id", session_id_str_val);
   		String json_str_data = json_data.toJSONString();
   		return json_str_data;
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
        	this.abend("processSetupSessionRequest", "ParseException: " + pe + " json_str=" + json_str_val);
        	return "ParseException";
        }
        catch (Exception e) {
        	this.abend("processSetupSessionRequest", "Exception: " + e + " json_str=" + json_str_val);
        	return "Exception";
        }
    	
        this.debug(false, "processSetupSession2Request", "link_id = " + link_id_str);
        this.debug(false, "processSetupSession2Request", "session_id = " + session_id_str);

        LinkClass link = this.LinkMgrObject().getLinkByIdStr(link_id_str);
        if (link == null) {
            return this.errorProcessSetupSession3(link_id_str, "null link");
        }
        
        SessionClass session = link.sessionMgrObject().getSessionBySessionIdStr(session_id_str);
        if (session == null) {
            return errorProcessSetupSession3(link_id_str, "null session");
        }

        session.setBrowserThemeIdStr(theme_id_str);
        GroupClass group = session.GroupObject();
        if (group == null) {
            return errorProcessSetupSession2(link_id_str, "null group");
        }
        this.mallocRoom(group, theme_data_str);

        String response_data = this.generateSetupSession2Response(link.linkIdStr(), session.SessionIdStr(), session.BrowserThemeIdStr());
        return response_data;
    }

    private String errorProcessSetupSession2(String link_id_val, String error_msg_val) {
        return error_msg_val;
    }

    public String generateSetupSession2Response(String link_id_str_val, String session_id_str_val, String theme_id_str_val) {
    	JSONObject json_data = new JSONObject();
    	json_data.put("link_id", link_id_str_val);
    	json_data.put("session_id", session_id_str_val);
    	json_data.put("theme_id", theme_id_str_val);
   		String json_str_data = json_data.toJSONString();
   		return json_str_data;
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

        LinkClass link = this.LinkMgrObject().getLinkByIdStr(link_id_str);
        if (link == null) {
            return this.errorProcessSetupSession3(link_id_str, "null link");
        }
        
        SessionClass session = link.sessionMgrObject().getSessionBySessionIdStr(session_id_str);
        if (session == null) {
            return errorProcessSetupSession3(link_id_str, "null session");
        }

        String response_data = this.generateSetupSession3Response(link_id_str, session_id_str, session.BrowserThemeIdStr());
        return response_data;
    }


    private String errorProcessSetupSession3(String link_id_val, String error_msg_val) {
        return error_msg_val;
    }

    public String generateSetupSession3Response(String link_id_str_val, String session_id_str_val, String theme_id_str_val) {
    	JSONObject json_data = new JSONObject();
    	json_data.put("link_id", link_id_str_val);
    	json_data.put("session_id", session_id_str_val);
    	json_data.put("theme_id", theme_id_str_val);
   		String json_str_data = json_data.toJSONString();
   		return json_str_data;
    }
        
    private String processPutSessionDataRequest(String json_str_val) {
        this.debug(false, "processPutSessionDataRequest", "json_str_val = " + json_str_val);
    	String link_id_str;
    	String session_id_str;
    	String data;
    	String xmt_seq_str;
       
        try {
            JSONParser parser = new JSONParser();
        	JSONObject json = (JSONObject) parser.parse(json_str_val);
        	link_id_str = (String) json.get("link_id");
        	session_id_str = (String) json.get("session_id");
        	data = (String) json.get("data");
        	xmt_seq_str = (String) json.get("xmt_seq");
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
        this.debug(false, "processPutSessionDataRequest", "xmt_seq = " + xmt_seq_str);
        this.debug(false, "processPutSessionDataRequest", "data = " + data);

        LinkClass link = this.LinkMgrObject().getLinkByIdStr(link_id_str);
        if (link == null) {
            return this.errorProcessSetupSession3(link_id_str, "null link");
        }
        
        SessionClass session = link.sessionMgrObject().getSessionBySessionIdStr(session_id_str);
        if (session == null) {
            return errorProcessSetupSession3(link_id_str, "null session");
        }

        String room_id_str = session.GroupObject().RoomIdStr();
        if (room_id_str == null) {
            return this.errorProcessPutSessionData(link_id_str, "null room");
        }

        /* transfer data up */
        String uplink_data = FabricThemeProtocolClass.FABRIC_THEME_PROTOCOL_COMMAND_IS_PUT_ROOM_DATA;
        uplink_data = uplink_data + room_id_str + data;
        this.UFabricObject().transmitData(uplink_data);

        /* send the response down */
        String response_data = this.generatePutSessionDataResponse(link.linkIdStr(), session.SessionIdStr(), "job is done");
        return response_data;
    }

    private String errorProcessPutSessionData(String link_id_val, String error_msg_val) {
        return error_msg_val;
    }

    public String generatePutSessionDataResponse(String link_id_str_val, String session_id_str_val, String c_data_val) {
    	JSONObject json_data = new JSONObject();
    	json_data.put("link_id", link_id_str_val);
    	json_data.put("session_id", session_id_str_val);
    	json_data.put("c_data", c_data_val);
   		String json_str_data = json_data.toJSONString();
   		return json_str_data;
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

        LinkClass link = this.LinkMgrObject().getLinkByIdStr(link_id_str);
        if (link == null) {
            return this.errorProcessSetupSession3(link_id_str, "null link");
        }
        
        SessionClass session = link.sessionMgrObject().getSessionBySessionIdStr(session_id_str);
        if (session == null) {
            return errorProcessSetupSession3(link_id_str, "null session");
        }
        
        String data = session.getPendingDownLinkData();

        /* send the response down */
        String response_data = this.generateGetSessionDataResponse(link.linkIdStr(), session.SessionIdStr(), data);
        return response_data;
    }

    private String errorProcessGetSessionData(String link_id_val, String error_msg_val) {
        return error_msg_val;
    }

    public String generateGetSessionDataResponse(String link_id_str_val, String session_id_str_val, String c_data_val) {
    	JSONObject json_data = new JSONObject();
    	json_data.put("link_id", link_id_str_val);
    	json_data.put("session_id", session_id_str_val);
    	json_data.put("c_data", c_data_val);
   		String json_str_data = json_data.toJSONString();
   		return json_str_data;
    }
    
    private void debug(Boolean on_off, String s0, String s1) { if (on_off) this.log(s0, s1); }
    private void log(String s0, String s1) { AbendClass.log(this.objectName() + "." + s0 + "()", s1); }
    public void abend(String s0, String s1) { AbendClass.abend(this.objectName() + "." + s0 + "()", s1); }
}
