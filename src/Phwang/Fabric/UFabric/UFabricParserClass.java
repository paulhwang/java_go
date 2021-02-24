/*
 ******************************************************************************
 *                                       
 *  Copyright (c) 2018 phwang. All rights reserved.
 *
 ******************************************************************************
 */

package Phwang.Fabric.UFabric;

import Phwang.Utils.AbendClass;
import Phwang.Protocols.FabricThemeProtocolClass;
import Phwang.Fabric.FabricRootClass;
import Phwang.Fabric.GroupMgr.GroupMgrClass;
import Phwang.Fabric.GroupMgr.GroupClass;
import Phwang.Fabric.SessionMgr.SessionClass;

public class UFabricParserClass {
    private String objectName() {return "UFabricParserClass";}

    private UFabricClass uFabricObject;

    public FabricRootClass FabricRootObject() { return this.uFabricObject.FabricRootObject();}
    public GroupMgrClass GroupMgrObject() { return this.FabricRootObject().GroupMgrObject(); }

    public UFabricParserClass(UFabricClass ufabric_object_val) {
        this.uFabricObject = ufabric_object_val;
    }

    public void parseInputPacket(String input_data_val) {
        this.debugIt(true, "parseInputPacket", input_data_val);
        
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

        this.abendIt("exportedParseFunction", input_data_val);
    }
    
    private void processSetupRoomResponse(String input_data_val) {
        this.debugIt(true, "processSetupRoomResponse", input_data_val);
        
        String group_id_str = input_data_val.substring(0, FabricThemeProtocolClass.FABRIC_GROUP_ID_SIZE);
        String room_id_str = input_data_val.substring(FabricThemeProtocolClass.FABRIC_GROUP_ID_SIZE);

        GroupClass group = this.GroupMgrObject().GetGroupByGroupIdStr(group_id_str);
        if (group != null) {
            group.SetRoomIdStr(room_id_str);
            int session_array_size = group.GetSessionArraySize();
            Object[] session_array = group.GetSessionArray();
            //group->setSessionTableArray((SessionClass**)phwangArrayMgrGetArrayTable(group->sessionArrayMgr(), &session_array_size));
            //printf("++++++++++++++++++++++++++++++++++++++++++++%d\n", session_array_size);
            for (int i = 0; i < session_array_size; i++) {
                SessionClass session = (SessionClass) session_array[i];
                session.LinkObject().SetPendingSessionSetup3(session.BrowserThemeIdStr(), session.SessionIdStr(), "");
            }
        }
    }
    
    private void processPutRoomDataResponse(String input_data_val) {
        String group_id_str = input_data_val.substring(0, FabricThemeProtocolClass.FABRIC_GROUP_ID_SIZE);
        String input_data = input_data_val.substring(FabricThemeProtocolClass.FABRIC_GROUP_ID_SIZE);
        GroupClass group = this.GroupMgrObject().GetGroupByGroupIdStr(group_id_str);
        if (group != null) {
            int session_array_size = group.GetSessionArraySize();
            Object[] session_array = group.GetSessionArray();
            for (int i = 0; i < session_array_size; i++) {
                SessionClass session = (SessionClass)session_array[i];
                session.EnqueuePendingDownLinkData(input_data);
            }
        }
    }

    private void debugIt(Boolean on_off_val, String str0_val, String str1_val) { if (on_off_val) this.logitIt(str0_val, str1_val); }
    private void logitIt(String str0_val, String str1_val) { AbendClass.phwangLogit(this.objectName() + "." + str0_val + "()", str1_val); }
    public void abendIt(String str0_val, String str1_val) { AbendClass.phwangAbend(this.objectName() + "." + str0_val + "()", str1_val); }
}
