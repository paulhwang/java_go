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

    public void parseInputPacket(String input_data_val)    {
        String adax_id = input_data_val.substring(0, FabricFrontEndProtocolClass.AJAX_MAPING_ID_SIZE);
        String toDes = input_data_val.substring(FabricFrontEndProtocolClass.AJAX_MAPING_ID_SIZE);

        AjaxFabricRequestFormatClass ajax_fabric_request = new AjaxFabricRequestFormatClass();
        try {
        	JSONObject json = (JSONObject) this.parserObject.parse(toDes);

            ajax_fabric_request.command = (String) json.get("command");
            ajax_fabric_request.data = (String) json.get("data");
        
        } catch (Exception EX) {
        	
        }
        
        String response_data;
        if (ajax_fabric_request.command.equals("setup_link")) {
            this.logitIt("parseInputPacket", "command = " + ajax_fabric_request.command);
            response_data = this.processSetupLinkRequest(ajax_fabric_request.data);
        }

        return;/////////////////////////////////////////////////////
        /***********************************************
        using (var ms = new MemoryStream(Encoding.Unicode.GetBytes(toDes)))
        {
            DataContractJsonSerializer deseralizer = new DataContractJsonSerializer(typeof(AjaxFabricRequestFormatClass));
            ajax_fabric_request = (AjaxFabricRequestFormatClass)deseralizer.ReadObject(ms);
            this.debugIt(true, "ParseAjaxPacket", "input_data_var = " + input_data_val);
            this.debugIt(true, "ParseAjaxPacket", "command = " + ajax_fabric_request.command);
            this.debugIt(true, "ParseAjaxPacket", "data = " + ajax_fabric_request.data);
        }
        
        String response_data;
        if (ajax_fabric_request.command == "setup_link")
        {
            response_data = this.processSetupLinkRequest(ajax_fabric_request.data);
        }
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
        this.dFabricObject.TransmitData(adax_id + response_data);
        */
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
        	
            this.debugIt(true, "processSetupLinkRequest", "my_name = " + format_data.my_name);
            this.debugIt(true, "processSetupLinkRequest", "password = " + format_data.password);

            LinkClass link = this.LinkMgrObject().MallocLink(format_data.my_name);
            String response_data = this.dFabricResponseObject.GenerateSetupLinkResponse(link.LinkIdStr(), link.MyName());
            return response_data;
        } catch (Exception EX) {
            return null;
        }
    }
    
    private void debugIt(Boolean on_off_val, String str0_val, String str1_val) { if (on_off_val) this.logitIt(str0_val, str1_val); }
    private void logitIt(String str0_val, String str1_val) { AbendClass.phwangLogit(this.objectName() + "." + str0_val + "()", str1_val); }
    public void abendIt(String str0_val, String str1_val) { AbendClass.phwangAbend(this.objectName() + "." + str0_val + "()", str1_val); }
}
