/*
 ******************************************************************************
 *                                       
 *  Copyright (c) 2018 phwang. All rights reserved.
 *
 ******************************************************************************
 */

package Phwang.Fabric.DFabric;

import Phwang.Utils.AbendClass;
import Phwang.Protocols.FabricFrontEndProtocolClass;
import Phwang.Fabric.LinkMgr.LinkMgrClass;
import Phwang.Fabric.GroupMgr.GroupMgrClass;
import Phwang.Fabric.FabricRootClass;
import Phwang.Fabric.UFabric.UFabricClass;

public class DFabricParserClass {
    private String objectName() {return "DFabricParserClass";}

    private String RESPONSE_IS_GET_LINK_DATA_NAME_LIST = FabricFrontEndProtocolClass.WEB_FABRIC_PROTOCOL_RESPOND_IS_GET_LINK_DATA_NAME_LIST;

    private DFabricClass dFabricObject;
    private DFabricResponseClass dFabricResponseObject;

    public FabricRootClass FabricRootObject() { return this.dFabricObject.FabricRootObject(); }
    private UFabricClass UFabricObject() { return this.FabricRootObject().UFabricObject(); }
    private LinkMgrClass LinkMgrObject() { return this.FabricRootObject().LinkMgrObject(); }
    private GroupMgrClass GroupMgrObject() { return this.FabricRootObject().GroupMgrObject(); }

    public DFabricParserClass(DFabricClass dfabric_object_val) {
        this.debugIt(false, "DFabricParserClass", "init start");

        this.dFabricObject = dfabric_object_val;
        this.dFabricResponseObject = new DFabricResponseClass(this);
    }

    public void parseInputPacket(String input_data_val)
    {
        String adax_id = input_data_val.substring(0, FabricFrontEndProtocolClass.AJAX_MAPING_ID_SIZE);
        String toDes = input_data_val.substring(FabricFrontEndProtocolClass.AJAX_MAPING_ID_SIZE);
        
        
        return;/////////////////////////////////////////////////////
        /***********************************************
        AjaxFabricRequestFormatClass ajax_fabric_request;
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

    private void debugIt(Boolean on_off_val, String str0_val, String str1_val) { if (on_off_val) this.logitIt(str0_val, str1_val); }
    private void logitIt(String str0_val, String str1_val) { AbendClass.phwangLogit(this.objectName() + "." + str0_val + "()", str1_val); }
    public void abendIt(String str0_val, String str1_val) { AbendClass.phwangAbend(this.objectName() + "." + str0_val + "()", str1_val); }
}
