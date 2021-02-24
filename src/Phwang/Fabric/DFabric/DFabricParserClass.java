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
        
        this.logitIt("parseInputPacket", "command = " + ajax_fabric_request.command);
        String response_data = null;
        if (ajax_fabric_request.command.equals("setup_link")) {
            response_data = this.processSetupLinkRequest(ajax_fabric_request.data);
        }
        /*
        else if (ajax_fabric_request.command == "get_link_data")
        {
            response_data = this.processGetLinkDataRequest(ajax_fabric_request.data);
        }
        else if (ajax_fabric_request.command == "get_name_list")
        {
            response_data = this.processGetNameListRequest(ajax_fabric_request.data);
        }
        else if (ajax_fabric_request.command == "setup_session")
        {
            response_data = this.processSetupSessionRequest(ajax_fabric_request.data);
        }
        else if (ajax_fabric_request.command == "setup_session2")
        {
            response_data = this.processSetupSession2Request(ajax_fabric_request.data);
        }
        else if (ajax_fabric_request.command == "setup_session3")
        {
            response_data = this.processSetupSession3Request(ajax_fabric_request.data);
        }
        else if (ajax_fabric_request.command == "put_session_data")
        {
            response_data = this.processPutSessionDataRequest(ajax_fabric_request.data);
        }
        else if (ajax_fabric_request.command == "get_session_data")
        {
            response_data = this.processGetSessionDataRequest(ajax_fabric_request.data);
        }
        else
        {
            response_data = "command " + ajax_fabric_request.command + " not supported";
        }
        */
        
        this.dFabricObject.TransmitData(adax_id + response_data);
    }
    
    public class SetupLinkRequestFormatClass {
        public String my_name;
        public String password;
    }

    private String processSetupLinkRequest(String input_data_val) {
        this.debugIt(true, "processSetupLinkRequest", "input_data_val = " + input_data_val);

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
   
    public String generateSetupLinkResponse(String link_id_val, String my_name_val) {
    	JSONObject json_data = new JSONObject();
    	json_data.put("my_name", my_name_val);
    	json_data.put("link_id", link_id_val);
   		String json_str_data = json_data.toJSONString();
   		return json_str_data;
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
