/*
 ******************************************************************************
 *                                       
 *  Copyright (c) 2021 phwang. All rights reserved.
 *
 ******************************************************************************
 */

package com.phwang.core.fabric;

import com.phwang.core.utils.Encoders;
import com.phwang.core.utils.ListEntry;
import com.phwang.core.utils.BinderBundle;
import com.phwang.core.utils.Define;

public class FabricUParser {
    private static String objectName() {return "FabricUParser";}

    private String RESPONSE_IS_GET_LINK_DATA_NAME_LIST = FabricExport.WEB_FABRIC_PROTOCOL_RESPOND_IS_GET_LINK_DATA_NAME_LIST;

    private FabricRoot fabricRoot_;

    protected FabricRoot fabricRoot() { return this.fabricRoot_; }
    private FabricDBinder fabricDBinder() { return this.fabricRoot().fabricDBinder(); }
    private FabricUBinder fabricUBinder() { return this.fabricRoot().fabricUBinder(); }
    private FabricLinkMgr linkMgr() { return this.fabricRoot().linkMgr(); }
    private FabricGroupMgr groupMgr() { return this.fabricRoot().groupMgr(); }

    protected FabricUParser(FabricRoot root_val) {
        this.debug(false, "FabricUParser", "init start");

        this.fabricRoot_ = root_val;
    }

    protected void parseInputPacket(BinderBundle bundle_val) {
        String response_data = null;
        String job_id_str = null;
        String input_str = bundle_val.data();
        this.debug(true, "parseInputPacket", "input_str = " + input_str);

        String rest_str = input_str;
        if (rest_str.charAt(0) == FabricExport.FABRIC_COMMAND_HTTP_DATA) {
            rest_str = rest_str.substring(1);
            job_id_str = Encoders.sSubstring2(rest_str);
            rest_str = Encoders.sSubstring2_(rest_str);
        }

        this.debug(false, "parseInputPacket", "data_str = " + rest_str);
        switch (rest_str.charAt(0)) {
            case FabricExport.FABRIC_COMMAND_SETUP_LINK:
                response_data = this.processSetupLinkRequest(rest_str.substring(1));
                break;
            case FabricExport.FABRIC_COMMAND_REMOVE_LINK:
                response_data = this.processRemoveLinkRequest(rest_str.substring(1));
                break;
            case FabricExport.FABRIC_COMMAND_GET_LINK_DATA:
                response_data = this.processGetLinkDataRequest(rest_str.substring(1));
                break;
            case FabricExport.FABRIC_COMMAND_GET_NAME_LIST:
                response_data = this.processGetNameListRequest(rest_str.substring(1));
                break;
            case FabricExport.FABRIC_COMMAND_SETUP_SESSION:
                response_data = this.processSetupSessionRequest(rest_str.substring(1));
                break;
            case FabricExport.FABRIC_COMMAND_SETUP_SESSION2:
                response_data = this.processSetupSession2Request(rest_str.substring(1));
                break;
            case FabricExport.FABRIC_COMMAND_SETUP_SESSION3:
                response_data = this.processSetupSession3Request(rest_str.substring(1));
                break;
            case FabricExport.FABRIC_COMMAND_PUT_SESSION_DATA:
                response_data = this.processPutSessionDataRequest(rest_str.substring(1));
                break;
            case FabricExport.FABRIC_COMMAND_GET_SESSION_DATA:
                response_data = this.processGetSessionDataRequest(rest_str.substring(1));
                break;
            default:
                response_data = "*** Bad command! Fix it!";
        	    this.abend("parseInputPacket", "should not reach here, data=" + input_str);
        	    break;
        }

        if (job_id_str != null) {
            response_data = job_id_str + response_data;
        }
        bundle_val.setData(response_data);
        this.fabricDBinder().transmitBundleData(bundle_val);
    }

    private String processSetupLinkRequest(String input_str_val) {
        this.debug(false, "processSetupLinkRequest", "input_str_val=" + input_str_val);
        
        char client_type = input_str_val.charAt(0);
        String rest_str = input_str_val.substring(1);

        String my_name = Encoders.sDecode2(rest_str);
    	rest_str = Encoders.sDecode2_(rest_str);

        String password = Encoders.sDecode2(rest_str);
        rest_str = Encoders.sDecode2_(rest_str);

        this.debug(false, "processSetupLinkRequest", "my_name = " + my_name);
        this.debug(false, "processSetupLinkRequest", "password = " + password);

        FabricLink link = this.linkMgr().mallocLink(client_type, my_name);
        if (link == null) {
        	this.abend("processSetupLinkRequest", "link is null");
        	return null;
        }
        String response_data = this.generateSetupLinkResponse(link.linkIdStr(), link.myName());
        return response_data;
    }
    
    private String generateSetupLinkResponse(String link_id_str_val, String my_name_val) {
        StringBuilder response_buf = new StringBuilder();
        response_buf.append(FabricExport.FABRIC_COMMAND_SETUP_LINK); 
        response_buf.append(link_id_str_val);
        response_buf.append(Encoders.sEncode2(my_name_val));
        return response_buf.toString();
    }
    
    private String processRemoveLinkRequest(String input_str_val) {
        this.debug(false, "processRemoveLinkRequest", "input_str_val = " + input_str_val);
        
        String rest_str = input_str_val;
        String link_id_str = Encoders.sSubstring2(rest_str);
        rest_str = Encoders.sSubstring2_(rest_str);
    	
        this.debug(false, "processRemoveLinkRequest", "link_id = " + link_id_str);

        FabricLink link = this.linkMgr().getLinkByIdStr(link_id_str);
        if (link == null) {
            return this.errorProcessRemoveLink(link_id_str, "*************null link");
        }

        String response_data = this.generateRemoveLinkResponse(link.linkIdStr(), "Succeed");
        return response_data;
    }

    private String errorProcessRemoveLink(String link_id_val, String error_msg_val) {
        return error_msg_val;
    }

    protected String generateRemoveLinkResponse(String link_id_str_val, String result_val) {
        StringBuilder response_buf = new StringBuilder();
        response_buf.append(FabricExport.FABRIC_COMMAND_GET_LINK_DATA); 
        response_buf.append(link_id_str_val);
        response_buf.append(Encoders.sEncode2(result_val));
        return response_buf.toString();
    }
    
    
    private String processGetLinkDataRequest(String input_str_val) {
        this.debug(false, "processGetLinkDataRequest", "input_str_val = " + input_str_val);
        
        String rest_str = input_str_val;
        String link_id_str = Encoders.sSubstring2(rest_str);
        rest_str = Encoders.sSubstring2_(rest_str);
    	
        this.debug(false, "processGetLinkDataRequest", "link_id = " + link_id_str);

        FabricLink link = this.linkMgr().getLinkByIdStr(link_id_str);
        if (link == null) {
            return this.errorProcessGetLinkData(link_id_str, "*************null link");
        }

        String downlink_data = RESPONSE_IS_GET_LINK_DATA_NAME_LIST + this.fabricRoot().nameList().nameListTagStr();

        int max_session_table_array_index = link.GetSessionArrayMaxIndex();
        ListEntry[] session_table_array = link.GetSessionArrayEntryTable();
        String pending_session_data = "";
        for (int i = 0; i <= max_session_table_array_index; i++) {
        	ListEntry list_entry = session_table_array[i];
        	FabricSession session = (FabricSession)list_entry.data();
            if (session != null) {
               if (session.getPendingDownLinkDataCount() > 0) {
                    downlink_data = downlink_data + FabricExport.WEB_FABRIC_PROTOCOL_RESPOND_IS_GET_LINK_DATA_PENDING_DATA + link.linkIdStr() + session.lSessionIdStr();
                }
            }
        }
       
        String pending_session_setup = "";
        String pending_session_str = link.getPendingSessionSetup();
        if (pending_session_str != null) {
            pending_session_setup = pending_session_setup + FabricExport.WEB_FABRIC_PROTOCOL_RESPOND_IS_GET_LINK_DATA_PENDING_SESSION;
            pending_session_setup = pending_session_setup + pending_session_str;
        }

        String pending_session_str3 = link.getPendingSessionSetup3();
        if (pending_session_str3 != null) {
            pending_session_setup = pending_session_setup + FabricExport.WEB_FABRIC_PROTOCOL_RESPOND_IS_GET_LINK_DATA_PENDING_SESSION3;
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
        StringBuilder response_buf = new StringBuilder();
        response_buf.append(FabricExport.FABRIC_COMMAND_GET_LINK_DATA); 
        response_buf.append(link_id_str_val);
        response_buf.append(Encoders.sEncode2(data_val));
        response_buf.append(Encoders.sEncode2(pending_session_setup_val));
        return response_buf.toString();
    }
    
    private String processGetNameListRequest(String input_str_val) {
        this.debug(false, "processGetNameListRequest", "input_str_val = " + input_str_val);
        
        String rest_str = input_str_val;
        String link_id_str = Encoders.sSubstring2(rest_str);
        rest_str = Encoders.sSubstring2_(rest_str);

        String name_list_tag_str = rest_str.substring(0, FabricExport.NAME_LIST_TAG_SIZE);
        rest_str = rest_str.substring(FabricExport.NAME_LIST_TAG_SIZE);
    	
        FabricLink link = this.linkMgr().getLinkByIdStr(link_id_str);
        if (link == null) {
            return this.errorProcessGetNameList(link_id_str, "*************null link");
        }

        int name_list_tag = Encoders.iDecode111(name_list_tag_str);
        String name_list = this.fabricRoot().nameList().getNameList(name_list_tag);

        String response_data = this.generateGetNameListResponse(link.linkIdStr(), name_list);
        return response_data;
    }

    private String errorProcessGetNameList(String link_id_val, String error_msg_val) {
        return error_msg_val;
    }

    protected String generateGetNameListResponse(String link_id_str_val, String name_list_str_val) {
        StringBuilder response_buf = new StringBuilder();
        response_buf.append(FabricExport.FABRIC_COMMAND_GET_NAME_LIST); 
        response_buf.append(link_id_str_val);
        response_buf.append(Encoders.sEncode2(name_list_str_val));
        return response_buf.toString();
    }

    private String processSetupSessionRequest(String input_str_val) {
        this.debug(false, "processSetupSessionRequest", "input_str_val=" + input_str_val);
        
        String rest_str = input_str_val;
        String link_id_str = Encoders.sSubstring2(rest_str);
        rest_str = Encoders.sSubstring2_(rest_str);

        String his_name = Encoders.sDecode2(rest_str);
        rest_str = Encoders.sDecode2_(rest_str);

        String theme_data_str = Encoders.sDecode2(rest_str);
        //rest_str = Encoders.sDecode2_(rest_str);

        this.debug(false, "processSetupSessionRequest", "link_id = " + link_id_str);
        this.debug(false, "processSetupSessionRequest", "his_name = " + his_name);
        this.debug(false, "processSetupSessionRequest", "theme_data = " + theme_data_str);

        String rest_str1 = input_str_val;
        String theme_id_str = Encoders.sSubstring2(rest_str1);
        rest_str1 = Encoders.sSubstring2_(rest_str1);

        String theme_data = rest_str1;
        theme_data = theme_data_str;

        FabricLink link = this.linkMgr().getLinkByIdStr(link_id_str);
        if (link == null) {
            return this.errorProcessSetupSession(link_id_str, "*************null link");
        }
        
        FabricSession session = link.mallocSession();
        session.setBrowserThemeIdStr(theme_id_str);
        FabricGroup group = this.groupMgr().mallocGroup(theme_data);
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
        	FabricLink his_link = this.linkMgr().GetLinkByMyName(his_name);
            if (his_link == null) {
                return this.errorProcessSetupSession(link_id_str, "his_link does not exist");
            }
            FabricSession his_session = his_link.mallocSession();
            if (his_session == null) {
                return this.errorProcessSetupSession(link_id_str, "null his_session");
            }

            group.insertSession(his_session);
            his_session.bindGroup(group);

            his_link.setPendingSessionSetup(his_link.linkIdStr() + his_session.lSessionIdStr(), theme_data);
        }

        String response_data = this.generateSetupSessionResponse(link.linkIdStr(), session.lSessionIdStr());
        return response_data;
    }

    private void mallocRoom(FabricGroup group_val, String theme_info_val) {
        this.debug(false, "mallocRoom", "theme_info_val=" + theme_info_val);
    	
        StringBuilder data_buf = new StringBuilder();
        data_buf.append(FabricExport.FABRIC_THEME_COMMAND_SETUP_ROOM);
        data_buf.append(group_val.groupIdStr());
        data_buf.append(theme_info_val);
        String uplink_data = data_buf.toString();
        this.fabricRoot().fabricUBinder().transmitData(uplink_data);
    }

    private String errorProcessSetupSession(String link_id_val, String error_msg_val) {
        return error_msg_val;
    }

    protected String generateSetupSessionResponse(String link_id_str_val, String session_id_str_val) {
        StringBuilder response_buf = new StringBuilder();
        response_buf.append(FabricExport.FABRIC_COMMAND_SETUP_SESSION); 
        response_buf.append(link_id_str_val);
        response_buf.append(session_id_str_val);
        return response_buf.toString();
    }

    private String processSetupSession2Request(String input_str_val) {
        this.debug(false, "processSetupSession2Request", "input_str_val = " + input_str_val);
    	/////String accept_str;
        
        String rest_str = input_str_val;
        String link_id_str = Encoders.sSubstring2(rest_str);
        rest_str = Encoders.sSubstring2_(rest_str);

        String session_id_str = Encoders.sSubstring2(rest_str);
        rest_str = Encoders.sSubstring2_(rest_str);

        String theme_id_str = Encoders.sSubstring2(rest_str);
        rest_str = Encoders.sSubstring2_(rest_str);

        String theme_data_str = Encoders.sDecode2(rest_str);
        //rest_str = Encoders.sDecode2_(rest_str);

        this.debug(false, "processSetupSession2Request", "link_id = " + link_id_str);
        this.debug(false, "processSetupSession2Request", "session_id = " + session_id_str);

        FabricLink link = this.linkMgr().getLinkByIdStr(link_id_str);
        if (link == null) {
            return this.errorProcessSetupSession2(link_id_str, "null link");
        }
        
        FabricSession session = link.sessionMgr().getSessionByIdStr(session_id_str);
        if (session == null) {
            return errorProcessSetupSession2(link_id_str, "null session");
        }

        session.setBrowserThemeIdStr(theme_id_str);
        FabricGroup group = session.group();
        if (group == null) {
            return errorProcessSetupSession2(link_id_str, "null group");
        }
        this.mallocRoom(group, theme_data_str);

        String response_data = this.generateSetupSession2Response(link.linkIdStr(), session.lSessionIdStr(), session.browserThemeIdStr());
        return response_data;
    }

    private String errorProcessSetupSession2(String link_id_val, String error_msg_val) {
        return error_msg_val;
    }

    protected String generateSetupSession2Response(String link_id_str_val, String session_id_str_val, String theme_id_str_val) {
        StringBuilder response_buf = new StringBuilder();
        response_buf.append(FabricExport.FABRIC_COMMAND_SETUP_SESSION3); 
        response_buf.append(link_id_str_val);
        response_buf.append(session_id_str_val);
        response_buf.append(theme_id_str_val);
        return response_buf.toString();
    }

    private String processSetupSession3Request(String input_str_val) {
        this.debug(true, "processSetupSession3Request", "input_str_val = " + input_str_val);
        
        String rest_str = input_str_val;
        String link_id_str = Encoders.sSubstring2(rest_str);
        rest_str = Encoders.sSubstring2_(rest_str);

        String session_id_str = Encoders.sSubstring2(rest_str);
        rest_str = Encoders.sSubstring2_(rest_str);
    	
        this.debug(false, "processSetupSession3Request", "link_id = " + link_id_str);
        this.debug(false, "processSetupSession3Request", "session_id = " + session_id_str);

        FabricLink link = this.linkMgr().getLinkByIdStr(link_id_str);
        if (link == null) {
            return this.errorProcessSetupSession3(link_id_str, "null link");
        }
        
        FabricSession session = link.sessionMgr().getSessionByIdStr(session_id_str);
        if (session == null) {
            return errorProcessSetupSession3(link_id_str, "null session");
        }

        String response_data = this.generateSetupSession3Response(link_id_str, session_id_str, session.browserThemeIdStr());
        return response_data;
    }

    private String errorProcessSetupSession3(String link_id_val, String error_msg_val) {
        return error_msg_val;
    }

    protected String generateSetupSession3Response(String link_id_str_val, String session_id_str_val, String theme_id_str_val) {
        StringBuilder response_buf = new StringBuilder();
        response_buf.append(FabricExport.FABRIC_COMMAND_SETUP_SESSION3); 
        response_buf.append(link_id_str_val);
        response_buf.append(session_id_str_val);
        response_buf.append(theme_id_str_val);
        return response_buf.toString();
    }

    private String processPutSessionDataRequest(String input_str_val) {
        this.debug(false, "processPutSessionDataRequest", "input_str_val = " + input_str_val);
    	//String xmt_seq_str = null;
        
        String rest_str = input_str_val;
        String link_id_str = Encoders.sSubstring2(rest_str);
        rest_str = Encoders.sSubstring2_(rest_str);

        String session_id_str = Encoders.sSubstring2(rest_str);
        rest_str = Encoders.sSubstring2_(rest_str);

        String data = Encoders.sDecode2(rest_str);
        //rest_str = Encoders.sDecode2_(rest_str);

        this.debug(false, "processPutSessionDataRequest", "link_id=" + link_id_str);
        this.debug(false, "processPutSessionDataRequest", "session_id=" + session_id_str);
        //this.debug(false, "processPutSessionDataRequest", "xmt_seq = " + xmt_seq_str);
        this.debug(false, "processPutSessionDataRequest", "data=" + data);

        FabricLink link = this.linkMgr().getLinkByIdStr(link_id_str);
        if (link == null) {
            return this.errorProcessPutSessionData(link_id_str, "null link");
        }
        
        FabricSession session = link.sessionMgr().getSessionByIdStr(session_id_str);
        if (session == null) {
            return errorProcessPutSessionData(link_id_str, "null session");
        }

        String room_id_str = session.group().roomIdStr();
        if (room_id_str == null) {
            return this.errorProcessPutSessionData(link_id_str, "null room");
        }

        /* transfer data up */
        StringBuilder buf = new StringBuilder();
        buf.append(FabricExport.FABRIC_THEME_COMMAND_PUT_ROOM_DATA);
        buf.append(room_id_str);
        buf.append(data);
        this.fabricUBinder().transmitData(buf.toString());

        /* send the response down */
        String response_data = this.generatePutSessionDataResponse(link.linkIdStr(), session.lSessionIdStr(), "job is done");
        return response_data;
    }

    private String errorProcessPutSessionData(String link_id_val, String error_msg_val) {
        return error_msg_val;
    }

    protected String generatePutSessionDataResponse(String link_id_str_val, String session_id_str_val, String c_data_val) {
        StringBuilder response_buf = new StringBuilder();
        response_buf.append(FabricExport.FABRIC_COMMAND_PUT_SESSION_DATA); 
        response_buf.append(link_id_str_val);
        response_buf.append(session_id_str_val);
        response_buf.append(Encoders.sEncode2(c_data_val));
        return response_buf.toString();
    }

    private String processGetSessionDataRequest(String input_str_val) {
        this.debug(false, "processGetSessionDataRequest", "input_str_val = " + input_str_val);
        
        String rest_str = input_str_val;
        String link_id_str = Encoders.sSubstring2(rest_str);
        rest_str = Encoders.sSubstring2_(rest_str);

        String session_id_str = Encoders.sSubstring2(rest_str);
        rest_str = Encoders.sSubstring2_(rest_str);
    	
        this.debug(false, "processGetSessionDataRequest", "link_id = " + link_id_str);
        this.debug(false, "processGetSessionDataRequest", "session_id = " + session_id_str);

        FabricLink link = this.linkMgr().getLinkByIdStr(link_id_str);
        if (link == null) {
            return this.errorProcessSetupSession3(link_id_str, "null link");
        }
        
        FabricSession session = link.sessionMgr().getSessionByIdStr(session_id_str);
        if (session == null) {
            return errorProcessSetupSession3(link_id_str, "null session");
        }
        
        String data = session.getPendingDownLinkData();

        /* send the response down */
        String response_data = this.generateGetSessionDataResponse(link.linkIdStr(), session.lSessionIdStr(), data);
        return response_data;
    }

    private String errorProcessGetSessionData(String link_id_val, String error_msg_val) {
        return error_msg_val;
    }

    protected String generateGetSessionDataResponse(String link_id_str_val, String session_id_str_val, String c_data_val) {
        StringBuilder response_buf = new StringBuilder();
        response_buf.append(FabricExport.FABRIC_COMMAND_GET_SESSION_DATA); 
        response_buf.append(link_id_str_val);
        response_buf.append(session_id_str_val);
        if (c_data_val == null) {//////////////////////////////////for now
        	c_data_val = "";
        }
        response_buf.append(Encoders.sEncode5(c_data_val));

        return response_buf.toString();
    }

    private void debug(Boolean on_off, String s0, String s1) { if (on_off) this.log(s0, s1); }
    private void log(String s0, String s1) { this.fabricRoot().logIt(this.objectName() + "." + s0 + "()", s1); }
    protected void abend(String s0, String s1) { this.fabricRoot().abendIt(this.objectName() + "." + s0 + "()", s1); }
}
