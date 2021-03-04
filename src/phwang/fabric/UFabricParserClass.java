/*
 ******************************************************************************
 *                                       
 *  Copyright (c) 2018 phwang. All rights reserved.
 *
 ******************************************************************************
 */

package phwang.fabric;

import phwang.utils.*;
import phwang.protocols.FabricThemeProtocolClass;
import phwang.fabric.FabricRootClass;

public class UFabricParserClass {
    private String objectName() {return "UFabricParserClass";}

    private UFabricClass uFabricObject_;

    private UFabricClass uFabricObject() { return this.uFabricObject_; }
    public FabricRootClass fabricRootObject() { return this.uFabricObject().fabricRootObject();}
    public GroupMgrClass groupMgrObject() { return this.fabricRootObject().groupMgrObject(); }

    public UFabricParserClass(UFabricClass ufabric_object_val) {
        this.uFabricObject_ = ufabric_object_val;
    }

    public void parseInputPacket(String input_data_val) {
        this.debug(false, "parseInputPacket", input_data_val);
        
        String command = input_data_val.substring(0, 1);
        String input_data = input_data_val.substring(1);

        if (command.equals(FabricThemeProtocolClass.FABRIC_THEME_PROTOCOL_RESPOND_IS_SETUP_ROOM)) {
            this.processSetupRoomResponse(input_data);
            return;
        }

        if (command.equals(FabricThemeProtocolClass.FABRIC_THEME_PROTOCOL_RESPOND_IS_PUT_ROOM_DATA)) {
            this.processPutRoomDataResponse(input_data);
            return;
        }

        this.abend("exportedParseFunction", input_data_val);
    }
    
    private void processSetupRoomResponse(String input_data_val) {
        this.debug(false, "processSetupRoomResponse", input_data_val);
        
        String group_id_str = input_data_val.substring(0, FabricExport.FABRIC_GROUP_ID_SIZE);
        String room_id_str = input_data_val.substring(FabricExport.FABRIC_GROUP_ID_SIZE);

        GroupClass group = this.groupMgrObject().getGroupByGroupIdStr(group_id_str);
        if (group != null) {
            group.setRoomIdStr(room_id_str);
            int session_array_size = group.getSessionArraySize();
            Object[] session_array = group.getSessionArray();
            //group->setSessionTableArray((SessionClass**)phwangArrayMgrGetArrayTable(group->sessionArrayMgr(), &session_array_size));
            //printf("++++++++++++++++++++++++++++++++++++++++++++%d\n", session_array_size);
            for (int i = 0; i < session_array_size; i++) {
                SessionClass session = (SessionClass) session_array[i];
                session.linkObject().setPendingSessionSetup3(session.BrowserThemeIdStr(), session.SessionIdStr(), "");
            }
        }
    }
    
    private void processPutRoomDataResponse(String input_data_val) {
        this.debug(false, "processPutRoomDataResponse", input_data_val);
        
        String group_id_str = input_data_val.substring(0, FabricExport.FABRIC_GROUP_ID_SIZE);
        String input_data = input_data_val.substring(FabricExport.FABRIC_GROUP_ID_SIZE);
        GroupClass group = this.groupMgrObject().getGroupByGroupIdStr(group_id_str);
        if (group != null) {
            int session_array_size = group.getSessionArraySize();
            Object[] session_array = group.getSessionArray();
            for (int i = 0; i < session_array_size; i++) {
                SessionClass session = (SessionClass)session_array[i];
                session.enqueuePendingDownLinkData(input_data);
            }
        }
    }
    
    private void debug(Boolean on_off, String s0, String s1) { if (on_off) this.log(s0, s1); }
    private void log(String s0, String s1) { AbendClass.log(this.objectName() + "." + s0 + "()", s1); }
    public void abend(String s0, String s1) { AbendClass.abend(this.objectName() + "." + s0 + "()", s1); }
}
