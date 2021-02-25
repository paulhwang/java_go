/*
 ******************************************************************************
 *                                       
 *  Copyright (c) 2018 phwang. All rights reserved.
 *
 ******************************************************************************
 */

package Phwang.Fabric.DFabric;

//import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import Phwang.Utils.AbendClass;
import Phwang.Utils.Encode.EncodeNumberClass;
import Phwang.Utils.ListMgr.ListEntryClass;
import Phwang.Protocols.FabricFrontEndProtocolClass;
import Phwang.Protocols.FabricThemeProtocolClass;
import Phwang.Fabric.LinkMgr.LinkMgrClass;
import Phwang.Fabric.SessionMgr.SessionClass;
import Phwang.Fabric.GroupMgr.GroupClass;
import Phwang.Fabric.GroupMgr.GroupMgrClass;
import Phwang.Fabric.FabricRootClass;
import Phwang.Fabric.UFabric.UFabricClass;
import Phwang.Fabric.LinkMgr.LinkClass;

public class DFabricParserClass {
    private String objectName() {return "DFabricParserClass";}

    private String RESPONSE_IS_GET_LINK_DATA_NAME_LIST = FabricFrontEndProtocolClass.WEB_FABRIC_PROTOCOL_RESPOND_IS_GET_LINK_DATA_NAME_LIST;

    private DFabricClass dFabricObject;
    private DFabricResponseClass dFabricResponseObject;
    private JSONParser parserObject;

    public FabricRootClass FabricRootObject() { return this.dFabricObject.FabricRootObject(); }
    private UFabricClass UFabricObject() { return this.FabricRootObject().UFabricObject(); }
    private LinkMgrClass LinkMgrObject() { return this.FabricRootObject().LinkMgrObject(); }
    private GroupMgrClass GroupMgrObject() { return this.FabricRootObject().GroupMgrObject(); }

    public DFabricParserClass(DFabricClass dfabric_object_val) {
        this.debugIt(false, "DFabricParserClass", "init start");

        this.dFabricObject = dfabric_object_val;
        this.dFabricResponseObject = new DFabricResponseClass(this);
        this.parserObject = new JSONParser();
    }

    public class AjaxFabricRequestFormatClass {
        public String command;
        public int packet_id;
        public String data;
    }

    public void parseInputPacket(String input_data_val) {
        String adax_id = input_data_val.substring(0, FabricFrontEndProtocolClass.AJAX_MAPING_ID_SIZE);
        String toDes = input_data_val.substring(FabricFrontEndProtocolClass.AJAX_MAPING_ID_SIZE);

        AjaxFabricRequestFormatClass ajax_fabric_request = new AjaxFabricRequestFormatClass();
        try {
        	JSONObject json = (JSONObject) this.parserObject.parse(toDes);

            ajax_fabric_request.command = (String) json.get("command");
            ajax_fabric_request.data = (String) json.get("data");
        
        } catch (Exception e) {
        	this.abendIt("parseInputPacket", "***Exception***");
        }
        
        this.debugIt(false, "parseInputPacket", "*********************command = " + ajax_fabric_request.command);
        String response_data = null;
        if (ajax_fabric_request.command.equals("setup_link")) {
            response_data = this.processSetupLinkRequest(ajax_fabric_request.data);
        }
        else if (ajax_fabric_request.command.equals("get_link_data")) {
            response_data = this.processGetLinkDataRequest(ajax_fabric_request.data);
        }
        else if (ajax_fabric_request.command.equals("get_name_list")) {
            response_data = this.processGetNameListRequest(ajax_fabric_request.data);
        }
        else if (ajax_fabric_request.command.equals("setup_session")) {
            response_data = this.processSetupSessionRequest(ajax_fabric_request.data);
        }
        else if (ajax_fabric_request.command.equals("setup_session2")) {
            response_data = this.processSetupSession2Request(ajax_fabric_request.data);
        }
        else if (ajax_fabric_request.command.equals("setup_session3")) {
            response_data = this.processSetupSession3Request(ajax_fabric_request.data);
        }
        else if (ajax_fabric_request.command.equals("put_session_data")) {
            response_data = this.processPutSessionDataRequest(ajax_fabric_request.data);
        }
        else if (ajax_fabric_request.command.equals("get_session_data")) {
            response_data = this.processGetSessionDataRequest(ajax_fabric_request.data);
        }
        else {
            response_data = "command " + ajax_fabric_request.command + " not supported";
            this.abendIt("parseInputPacket", response_data);
        }
        
        this.dFabricObject.TransmitData(adax_id + response_data);
    }

    private String processSetupLinkRequest(String input_data_val) {
        this.debugIt(false, "processSetupLinkRequest", "input_data_val = " + input_data_val);

        try {
        	JSONParser parser = new JSONParser();
        	JSONObject json = (JSONObject) parser.parse(input_data_val);
        	String my_name = (String) json.get("my_name");
        	String password = (String) json.get("password");
        	
            this.debugIt(false, "processSetupLinkRequest", "my_name = " + my_name);
            this.debugIt(false, "processSetupLinkRequest", "password = " + password);

            LinkClass link = this.LinkMgrObject().MallocLink(my_name);
            String response_data = this.generateSetupLinkResponse(link.LinkIdStr(), link.MyName());
            return response_data;
        } catch (Exception e) {
            return null;
        }
    }
    private String generateSetupLinkResponse(String link_id_val, String my_name_val) {
    	JSONObject json_data = new JSONObject();
    	json_data.put("my_name", my_name_val);
    	json_data.put("link_id", link_id_val);
   		String json_str_data = json_data.toJSONString();
   		return json_str_data;
    }
    
    private String processGetLinkDataRequest(String input_data_val) {
        this.debugIt(true, "processGetLinkDataRequest", "input_data_val = " + input_data_val);

        try {
        	JSONParser parser = new JSONParser();
        	JSONObject json = (JSONObject) parser.parse(input_data_val);
        	String link_id_str = (String) json.get("link_id");
        	
            this.debugIt(false, "processGetLinkDataRequest", "link_id = " + link_id_str);

            LinkClass link = this.LinkMgrObject().GetLinkByIdStr(link_id_str);
            if (link == null) {
                return this.errorProcessGetLinkData(link_id_str, "*************null link");
            }

            String downlink_data = RESPONSE_IS_GET_LINK_DATA_NAME_LIST + this.FabricRootObject().NameListObject().NameListTagStr();

            int max_session_table_array_index = link.GetSessionArrayMaxIndex();
            ListEntryClass[] session_table_array = link.GetSessionArrayEntryTable();
            String pending_session_data = "";
            for (int i = 0; i <= max_session_table_array_index; i++) {
                ListEntryClass list_entry = session_table_array[i];
                SessionClass session = (SessionClass)list_entry.Data();
                if (session != null) {
                   if (session.GetPendingDownLinkDataCount() > 0) {
                        downlink_data = downlink_data + FabricFrontEndProtocolClass.WEB_FABRIC_PROTOCOL_RESPOND_IS_GET_LINK_DATA_PENDING_DATA + link.LinkIdStr() + session.SessionIdStr();
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

            String response_data = this.generateGetLinkDataResponse(link.LinkIdStr(), downlink_data, pending_session_setup);
            return response_data;
        } catch (Exception e) {
            return null;
        }
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
    
    private String processGetNameListRequest(String input_data_val) {
        this.debugIt(true, "processGetNameListRequest", "input_data_val = " + input_data_val);

        try {
        	JSONParser parser = new JSONParser();
        	JSONObject json = (JSONObject) parser.parse(input_data_val);
        	String link_id_str = (String) json.get("link_id");
        	String name_list_tag_str = (String) json.get("name_list_tag");
        	
            this.debugIt(true, "processGetNameListRequest", "link_id = " + link_id_str);

            LinkClass link = this.LinkMgrObject().GetLinkByIdStr(link_id_str);
            if (link == null) {
                return this.errorProcessGetNameList(link_id_str, "*************null link");
            }

            int name_list_tag = EncodeNumberClass.DecodeNumber(name_list_tag_str);
            String name_list = this.FabricRootObject().NameListObject().GetNameList(name_list_tag);

            String response_data = this.generateGetNameListResponse(link.LinkIdStr(), name_list);
            return response_data;
        } catch (Exception e) {
            return null;
        }
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

    private String processSetupSessionRequest(String input_data_val) {
        this.debugIt(false, "processSetupSessionRequest", "input_data_val = " + input_data_val);

        try {
        	JSONParser parser = new JSONParser();
        	JSONObject json = (JSONObject) parser.parse(input_data_val);
        	String link_id_str = (String) json.get("link_id");
        	String his_name = (String) json.get("his_name");
        	String theme_data_str = (String) json.get("theme_data");
        	
            this.debugIt(false, "processSetupSessionRequest", "link_id = " + link_id_str);
            this.debugIt(false, "processSetupSessionRequest", "his_name = " + his_name);
            this.debugIt(false, "processSetupSessionRequest", "theme_data = " + theme_data_str);

            String theme_id_str = theme_data_str.substring(0, FabricFrontEndProtocolClass.BROWSER_THEME_ID_SIZE);
            String theme_data = theme_data_str.substring(FabricFrontEndProtocolClass.BROWSER_THEME_ID_SIZE);

            LinkClass link = this.LinkMgrObject().GetLinkByIdStr(link_id_str);
            if (link == null) {
                return this.errorProcessSetupSession(link_id_str, "*************null link");
            }
            
            SessionClass session = link.MallocSession();
            session.SetBrowserThemeIdStr(theme_id_str);
            GroupClass group = this.GroupMgrObject().MallocGroup(theme_data);
            if (group == null) {
            	this.abendIt("processSetupSessionRequest", "null group");
                return this.errorProcessSetupSession(link_id_str, "null group");
            }
            group.InsertSession(session);
            session.BindGroup(group);
            
            if (his_name.equals(link.MyName())) {
                this.mallocRoom(group, theme_data);
            }
            else {
                LinkClass his_link = this.LinkMgrObject().GetLinkByMyName(his_name);
                if (his_link == null) {
                    return this.errorProcessSetupSession(link_id_str, "his_link does not exist");
                }
                SessionClass his_session = his_link.MallocSession();
                if (his_session == null) {
                    return this.errorProcessSetupSession(link_id_str, "null his_session");
                }

                group.InsertSession(his_session);
                his_session.BindGroup(group);

                his_link.SetPendingSessionSetup(his_link.LinkIdStr() + his_session.SessionIdStr(), theme_data);
            }

            String response_data = this.GenerateSetupSessionResponse(link.LinkIdStr(), session.SessionIdStr());
            return response_data;
        } catch (Exception e) {
            return null;
        }
    }

    private void mallocRoom(GroupClass group_val, String theme_info_val) {
        String uplink_data = FabricThemeProtocolClass.FABRIC_THEME_PROTOCOL_COMMAND_IS_SETUP_ROOM;
        uplink_data = uplink_data + group_val.GroupIdStr();
        uplink_data = uplink_data + theme_info_val;
        this.FabricRootObject().UFabricObject().TransmitData(uplink_data);
    }

    private String errorProcessSetupSession(String link_id_val, String error_msg_val) {
        return error_msg_val;
    }

    public String GenerateSetupSessionResponse(String link_id_str_val, String session_id_str_val) {
    	JSONObject json_data = new JSONObject();
    	json_data.put("link_id", link_id_str_val);
    	json_data.put("session_id", session_id_str_val);
   		String json_str_data = json_data.toJSONString();
   		return json_str_data;
    }

    private String processSetupSession2Request(String input_data_val) {
        this.debugIt(false, "processSetupSession2Request", "input_data_val = " + input_data_val);

        
        try {
        	JSONParser parser = new JSONParser();
        	JSONObject json = (JSONObject) parser.parse(input_data_val);
        	String link_id_str = (String) json.get("link_id");
        	String session_id_str = (String) json.get("session_id");
        	String theme_id_str = (String) json.get("theme_id");
        	String accept_str = (String) json.get("accept");
        	String theme_data_str = (String) json.get("theme_data");
        	
            this.debugIt(false, "processSetupSession2Request", "link_id = " + link_id_str);
            this.debugIt(false, "processSetupSession2Request", "session_id = " + session_id_str);

            LinkClass link = this.LinkMgrObject().GetLinkByIdStr(link_id_str);
            if (link == null) {
                return this.errorProcessSetupSession3(link_id_str, "null link");
            }
            
            SessionClass session = link.SessionMgrObject().getSessionBySessionIdStr(session_id_str);
            if (session == null) {
                return errorProcessSetupSession3(link_id_str, "null session");
            }

            session.SetBrowserThemeIdStr(theme_id_str);
            GroupClass group = session.GroupObject();
            if (group == null) {
                return errorProcessSetupSession2(link_id_str, "null group");
            }
            this.mallocRoom(group, theme_data_str);

            String response_data = this.generateSetupSession2Response(link.LinkIdStr(), session.SessionIdStr(), session.BrowserThemeIdStr());
            return response_data;
        } catch (Exception e) {
            return null;
        }
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

    private String processSetupSession3Request(String input_data_val) {
        this.debugIt(false, "processSetupSession3Request", "input_data_val = " + input_data_val);
        
        try {
        	JSONParser parser = new JSONParser();
        	JSONObject json = (JSONObject) parser.parse(input_data_val);
        	String link_id_str = (String) json.get("link_id");
        	String session_id_str = (String) json.get("session_id");
        	
            this.debugIt(false, "processSetupSession3Request", "link_id = " + link_id_str);
            this.debugIt(false, "processSetupSession3Request", "session_id = " + session_id_str);

            LinkClass link = this.LinkMgrObject().GetLinkByIdStr(link_id_str);
            if (link == null) {
                return this.errorProcessSetupSession3(link_id_str, "null link");
            }
            
            SessionClass session = link.SessionMgrObject().getSessionBySessionIdStr(session_id_str);
            if (session == null) {
                return errorProcessSetupSession3(link_id_str, "null session");
            }

            String response_data = this.generateSetupSession3Response(link_id_str, session_id_str, session.BrowserThemeIdStr());
            return response_data;
            
        } catch (Exception e) {
            return null;
        }
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
        
        
    private String processPutSessionDataRequest(String input_data_val) {
        this.debugIt(false, "processPutSessionDataRequest", "input_data_val = " + input_data_val);

       
        try {
        	JSONParser parser = new JSONParser();
        	JSONObject json = (JSONObject) parser.parse(input_data_val);
        	String link_id_str = (String) json.get("link_id");
        	String session_id_str = (String) json.get("session_id");
        	String data = (String) json.get("data");
        	String xmt_seq_str = (String) json.get("xmt_seq");
        	
            this.debugIt(false, "processPutSessionDataRequest", "link_id = " + link_id_str);
            this.debugIt(false, "processPutSessionDataRequest", "session_id = " + session_id_str);
            this.debugIt(false, "processPutSessionDataRequest", "xmt_seq = " + xmt_seq_str);
            this.debugIt(false, "processPutSessionDataRequest", "data = " + data);

            LinkClass link = this.LinkMgrObject().GetLinkByIdStr(link_id_str);
            if (link == null) {
                return this.errorProcessSetupSession3(link_id_str, "null link");
            }
            
            SessionClass session = link.SessionMgrObject().getSessionBySessionIdStr(session_id_str);
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
            this.UFabricObject().TransmitData(uplink_data);

            /* send the response down */
            String response_data = this.generatePutSessionDataResponse(link.LinkIdStr(), session.SessionIdStr(), "job is done");
            return response_data;
        } catch (Exception e) {
            return null;
        }
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

    private String processGetSessionDataRequest(String input_data_val) {
        this.debugIt(false, "processGetSessionDataRequest", "input_data_val = " + input_data_val);

        
        try {
        	JSONParser parser = new JSONParser();
        	JSONObject json = (JSONObject) parser.parse(input_data_val);
        	String link_id_str = (String) json.get("link_id");
        	String session_id_str = (String) json.get("session_id");
        	
            this.debugIt(false, "processPutSessionDataRequest", "link_id = " + link_id_str);
            this.debugIt(false, "processPutSessionDataRequest", "session_id = " + session_id_str);

            LinkClass link = this.LinkMgrObject().GetLinkByIdStr(link_id_str);
            if (link == null) {
                return this.errorProcessSetupSession3(link_id_str, "null link");
            }
            
            SessionClass session = link.SessionMgrObject().getSessionBySessionIdStr(session_id_str);
            if (session == null) {
                return errorProcessSetupSession3(link_id_str, "null session");
            }
            
            String data = session.GetPendingDownLinkData();

            /* send the response down */
            String response_data = this.generatePutSessionDataResponse(link.LinkIdStr(), session.SessionIdStr(), data);
            return response_data;
        } catch (Exception e) {
            return null;
        }
    }

    private String errorProcessGetSessionData(String link_id_val, String error_msg_val) {
        return error_msg_val;
    }

    public String GenerateGetSessionDataResponse(String link_id_str_val, String session_id_str_val, String c_data_val) {
    	JSONObject json_data = new JSONObject();
    	json_data.put("link_id", link_id_str_val);
    	json_data.put("session_id", session_id_str_val);
    	json_data.put("c_data", c_data_val);
   		String json_str_data = json_data.toJSONString();
   		return json_str_data;
    }
	   
    private void debugIt(Boolean on_off_val, String str0_val, String str1_val) { if (on_off_val) this.logitIt(str0_val, str1_val); }
    private void logitIt(String str0_val, String str1_val) { AbendClass.phwangLogit(this.objectName() + "." + str0_val + "()", str1_val); }
    public void abendIt(String str0_val, String str1_val) { AbendClass.phwangAbend(this.objectName() + "." + str0_val + "()", str1_val); }
}
