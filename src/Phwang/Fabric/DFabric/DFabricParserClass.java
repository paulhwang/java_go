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
import Phwang.Protocols.FabricFrontEndProtocolClass;
import Phwang.Fabric.LinkMgr.LinkMgrClass;
import Phwang.Fabric.SessionMgr.SessionClass;
import Phwang.Fabric.GroupMgr.GroupClass;
import Phwang.Fabric.GroupMgr.GroupMgrClass;
import Phwang.Fabric.GroupMgr.GroupClass;
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
        
        this.logitIt("parseInputPacket", "command = " + ajax_fabric_request.command);
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
            response_data = this.processSetupSessionRequest(ajax_fabric_request.data);
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
        }
        
        this.dFabricObject.TransmitData(adax_id + response_data);
    }
    
    public class SetupLinkRequestFormatClass {
        public String my_name;
        public String password;
    }

    private String processSetupLinkRequest(String input_data_val) {
        this.debugIt(false, "processSetupLinkRequest", "input_data_val = " + input_data_val);

        SetupLinkRequestFormatClass format_data = new SetupLinkRequestFormatClass();
        try {
        	JSONParser parser = new JSONParser();
        	JSONObject json = (JSONObject) parser.parse(input_data_val);
        	format_data.my_name = (String) json.get("my_name");
        	format_data.password = (String) json.get("password");
        	
            this.debugIt(false, "processSetupLinkRequest", "my_name = " + format_data.my_name);
            this.debugIt(false, "processSetupLinkRequest", "password = " + format_data.password);

            LinkClass link = this.LinkMgrObject().MallocLink(format_data.my_name);
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

        return "junk";
    }

    private String processGetNameListRequest(String input_data_val) {
        this.debugIt(true, "processGetNameListRequest", "input_data_val = " + input_data_val);

        return "junk";
    }
    
    public class SetupSessionRequestFormat {
        public String link_id;
        public String his_name;
        public String theme_data;
    }

    private String processSetupSessionRequest(String input_data_val) {
        this.debugIt(true, "processSetupSessionRequest", "input_data_val = " + input_data_val);

        SetupSessionRequestFormat format_data = new SetupSessionRequestFormat();
        try {
        	JSONParser parser = new JSONParser();
        	JSONObject json = (JSONObject) parser.parse(input_data_val);
        	format_data.link_id = (String) json.get("link_id");
        	format_data.his_name = (String) json.get("his_name");
        	format_data.theme_data = (String) json.get("theme_data");
        	
            this.debugIt(true, "processSetupSessionRequest", "link_id = " + format_data.link_id);
            this.debugIt(true, "processSetupSessionRequest", "his_name = " + format_data.his_name);
            this.debugIt(true, "processSetupSessionRequest", "theme_data = " + format_data.theme_data);

            String theme_id_str = format_data.theme_data.substring(0, FabricFrontEndProtocolClass.BROWSER_THEME_ID_SIZE);
            String theme_data = format_data.theme_data.substring(FabricFrontEndProtocolClass.BROWSER_THEME_ID_SIZE);

            LinkClass link = this.LinkMgrObject().GetLinkByIdStr(format_data.link_id);
            if (link == null) {
                return this.errorProcessSetupSession(format_data.link_id, "null group");
            }
            
            this.debugIt(true, "processSetupSessionRequest", "link found");
            
            SessionClass session = link.MallocSession();
            session.SetBrowserThemeIdStr(theme_id_str);
            GroupClass group = this.GroupMgrObject().MallocGroup(theme_data);
            if (group == null) {
            	this.abendIt("processSetupSessionRequest", "null group");
                return this.errorProcessSetupSession(format_data.link_id, "null group");
            }
            this.debugIt(true, "processSetupSessionRequest111", "********111group created");
            group.InsertSession(session);
            this.debugIt(true, "processSetupSessionRequest222", "********222group created");
            session.BindGroup(group);
            
            this.debugIt(true, "processSetupSessionRequest333", "********333group created");

            if (format_data.his_name.equals(link.MyName())) {
                //this.mallocRoom(group, theme_data);
            }
            else {
                LinkClass his_link = this.LinkMgrObject().GetLinkByMyName(format_data.his_name);
                if (his_link == null)
                {
                    //return this.errorProcessSetupSession(format_data.link_id, "his_link does not exist");
                }
                SessionClass his_session = his_link.MallocSession();
                if (his_session == null)
                {
                    //return this.errorProcessSetupSession(format_data.link_id, "null his_session");
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

    private String errorProcessSetupSession(String link_id_val, String error_msg_val) {
        return error_msg_val;
    }

    public String GenerateSetupSessionResponse(String link_id_str_val, String session_id_str_val) {
    	return "junk";
    }

    private String processSetupSession2Request(String input_data_val) {
        this.debugIt(true, "processSetupSessionRequest", "input_data_val = " + input_data_val);

        return "junk";
    }

    private String processSetupSession3Request(String input_data_val) {
        this.debugIt(true, "processSetupSessionRequest", "input_data_val = " + input_data_val);

        return "junk";
    }

    private String processPutSessionDataRequest(String input_data_val) {
        this.debugIt(true, "processPutSessionDataRequest", "input_data_val = " + input_data_val);

        return "junk";
    }

    private String processGetSessionDataRequest(String input_data_val) {
        this.debugIt(true, "processGetSessionDataRequest", "input_data_val = " + input_data_val);

        return "junk";
    }

   /*
   [DataContract]
   private class SetupLinkResponseFormatClass
   {
       [DataMember]
       public string my_name { get; set; }

       [DataMember]
       public string link_id { get; set; }
   }

   public string GenerateSetupLinkResponse(string link_id_var, string my_name_var)
   {
       SetupLinkResponseFormatClass raw_data = new SetupLinkResponseFormatClass { my_name = my_name_var, link_id = link_id_var };

       this.debugIt(true, "GenerateSetupLinkResponse", "");
       DataContractJsonSerializer js = new DataContractJsonSerializer(typeof(SetupLinkResponseFormatClass));
       MemoryStream msObj = new MemoryStream();

       js.WriteObject(msObj, raw_data);
       msObj.Position = 0;

       StreamReader sr = new StreamReader(msObj, Encoding.UTF8);
       string data = sr.ReadToEnd();
       sr.Close();
       msObj.Close();

       this.debugIt(true, "GenerateSetupLinkResponse", "data = " + data);
       string response_data = this.EncodeResponse("setup_link", data);
       return response_data;
   }
*/
	   
    private void debugIt(Boolean on_off_val, String str0_val, String str1_val) { if (on_off_val) this.logitIt(str0_val, str1_val); }
    private void logitIt(String str0_val, String str1_val) { AbendClass.phwangLogit(this.objectName() + "." + str0_val + "()", str1_val); }
    public void abendIt(String str0_val, String str1_val) { AbendClass.phwangAbend(this.objectName() + "." + str0_val + "()", str1_val); }
}
