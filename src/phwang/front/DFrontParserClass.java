/*
 ******************************************************************************
 *                                       
 *  Copyright (c) 2018 phwang. All rights reserved.
 *
 ******************************************************************************
 */

package phwang.front;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import phwang.protocols.FabricFrontEndProtocolClass;
import phwang.utils.AbendClass;
import phwang.utils.ListMgrClass;

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
        	this.abend("parseInputPacket", "ParseException: " + pe + " json_str=" + json_str + "   input_data=" + input_data_val);
        	return "ParseException";
        }
        catch (Exception e) {
        	this.abend("parseInputPacket", "Exception: " + e + " json_str=" + json_str + "   input_data=" + input_data_val);
        	return "ParseException";
        }
        
        this.debug(false, "parseInputPacket", "*********************command = " + command);
        String response_data = null;
        if (command.equals("setup_link")) {
            //response_data = this.processSetupLinkRequest(data);
        }
        else if (command.equals("get_link_data")) {
            //response_data = this.processGetLinkDataRequest(data);
        }
        else if (command.equals("get_name_list")) {
            //response_data = this.processGetNameListRequest(data);
        }
        else if (command.equals("setup_session")) {
            //response_data = this.processSetupSessionRequest(data);
        }
        else if (command.equals("setup_session2")) {
            //response_data = this.processSetupSession2Request(data);
        }
        else if (command.equals("setup_session3")) {
            //response_data = this.processSetupSession3Request(data);
        }
        else if (command.equals("put_session_data")) {
            //response_data = this.processPutSessionDataRequest(data);
        }
        else if (command.equals("get_session_data")) {
            //response_data = this.processGetSessionDataRequest(data);
        }
        else {
            //response_data = "command " + command + " not supported";
            //this.abend("parseInputPacket", response_data);
        }
        
        return(response_data);
    }
    
    private void debug(Boolean on_off, String s0, String s1) { if (on_off) this.log(s0, s1); }
    private void log(String s0, String s1) { AbendClass.log(this.objectName() + "." + s0 + "()", s1); }
    public void abend(String s0, String s1) { AbendClass.abend(this.objectName() + "." + s0 + "()", s1); }
}
