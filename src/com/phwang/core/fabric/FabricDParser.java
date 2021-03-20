/*
 ******************************************************************************
 *                                       
 *  Copyright (c) 2021 phwang. All rights reserved.
 *
 ******************************************************************************
 */

package com.phwang.core.fabric;

import com.phwang.core.utils.ListEntry;

public class FabricDParser {
    private String objectName() {return "FabricDParser";}

    private FabricRoot fabricRoot_;

    protected FabricRoot fabricRoot() { return this.fabricRoot_;}
    protected FabricGroupMgr groupMgr() { return this.fabricRoot().groupMgr(); }

    protected FabricDParser(FabricRoot root_val) {
        this.fabricRoot_ = root_val;
    }

    protected void parseInputPacket(String input_data_val) {
        this.debug(false, "parseInputPacket", input_data_val);
        
        char command = input_data_val.charAt(0);
        String input_data = input_data_val.substring(1);

        if (command == FabricExport.FABRIC_THEME_RESPOND_SETUP_ROOM) {
            this.processSetupRoomResponse(input_data);
            return;
        }

        if (command == FabricExport.FABRIC_THEME_RESPOND_PUT_ROOM_DATA) {
            this.processPutRoomDataResponse(input_data);
            return;
        }

        this.abend("exportedParseFunction", input_data_val);
    }
    
    private void processSetupRoomResponse(String input_data_val) {
        this.debug(false, "processSetupRoomResponse", input_data_val);
        
        String group_id_str = input_data_val.substring(0, FabricExport.FABRIC_GROUP_ID_SIZE);
        String room_id_str = input_data_val.substring(FabricExport.FABRIC_GROUP_ID_SIZE);

        FabricGroup group = this.groupMgr().getGroupByIdStr(group_id_str);
        if (group != null) {
            group.setRoomIdStr(room_id_str);
            
            int max_index = group.gSessionMgr().listMgr().maxIndex();
            ListEntry[] list_entry_array = group.gSessionMgr().listMgr().entryArray();
            for (int i = max_index; i >= 0; i--) {
                if (list_entry_array[i] != null) {
                    FabricSession session = (FabricSession) list_entry_array[i].data();
                    session.link().setPendingSessionSetup3(session.browserThemeIdStr(), session.lSessionIdStr(), "");
                }
            }
            
            int session_array_size = group.getSessionArraySize();
            Object[] session_array = group.getSessionArray();
            //group->setSessionTableArray((SessionClass**)phwangArrayMgrGetArrayTable(group->sessionArrayMgr(), &session_array_size));
            //printf("++++++++++++++++++++++++++++++++++++++++++++%d\n", session_array_size);
            for (int i = 0; i < session_array_size; i++) {
            	FabricSession session = (FabricSession) session_array[i];
                session.link().setPendingSessionSetup3(session.browserThemeIdStr(), session.lSessionIdStr(), "");
            }
        }
    }
    
    private void processPutRoomDataResponse(String input_data_val) {
        this.debug(false, "processPutRoomDataResponse", input_data_val);
        
        String group_id_str = input_data_val.substring(0, FabricExport.FABRIC_GROUP_ID_SIZE);
        String input_data = input_data_val.substring(FabricExport.FABRIC_GROUP_ID_SIZE);
        FabricGroup group = this.groupMgr().getGroupByIdStr(group_id_str);
        if (group != null) {
            int max_index = group.gSessionMgr().listMgr().maxIndex();
            ListEntry[] list_entry_array = group.gSessionMgr().listMgr().entryArray();
            for (int i = max_index; i >= 0; i--) {
                if (list_entry_array[i] != null) {
                    FabricSession session = (FabricSession) list_entry_array[i].data();
                    session.enqueuePendingDownLinkData(input_data);
                }
            }
        	
            int session_array_size = group.getSessionArraySize();
            Object[] session_array = group.getSessionArray();
            for (int i = 0; i < session_array_size; i++) {
            	FabricSession session = (FabricSession)session_array[i];
                session.enqueuePendingDownLinkData(input_data);
            }
        }
    }
    
    private void debug(Boolean on_off, String s0, String s1) { if (on_off) this.log(s0, s1); }
    private void log(String s0, String s1) { this.fabricRoot().logIt(this.objectName() + "." + s0 + "()", s1); }
    protected void abend(String s0, String s1) { this.fabricRoot().abendIt(this.objectName() + "." + s0 + "()", s1); }
}
