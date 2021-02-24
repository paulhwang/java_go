/*
 ******************************************************************************
 *                                       
 *  Copyright (c) 2018 phwang. All rights reserved.
 *
 ******************************************************************************
 */

package Phwang.Theme.Utheme;

import Phwang.Utils.AbendClass;
import Phwang.Protocols.ThemeEngineProtocolClass;
import Phwang.Protocols.FabricThemeProtocolClass;
import Phwang.Theme.ThemeRootClass;
import Phwang.Theme.DTheme.DThemeClass;
import Phwang.Theme.RoomMgr.RoomMgrClass;
import Phwang.Theme.RoomMgr.RoomClass;

public class UThemeParserClass {
    private String objectName() {return "UThemeParserClass";}
    
    private UThemeClass uThemeObject;

    public ThemeRootClass ThemeRootObject() { return this.uThemeObject.ThemeRootObject(); }
    public DThemeClass DThemeObject() { return this.ThemeRootObject().DThemeObject(); }
    public RoomMgrClass RoomMgrObject() { return this.ThemeRootObject().RoomMgrObject(); }

    public UThemeParserClass(UThemeClass u_theme_object_val) {
        this.debugIt(false, "UThemeParserClass", "init start");
        this.uThemeObject = u_theme_object_val;
    }
    
    public void ParseInputPacket(String input_data_val) {
        this.debugIt(true, "ParseInputPacket", input_data_val);
        String command = input_data_val.substring(0, 1);
        String input_data = input_data_val.substring(1);

        if (command.equals(ThemeEngineProtocolClass.THEME_ENGINE_PROTOCOL_RESPOND_IS_SETUP_BASE)) {
                this.processSetupBaseResponse(input_data);
                return;
        }

        if (command.equals(ThemeEngineProtocolClass.THEME_ENGINE_PROTOCOL_RESPOND_IS_PUT_BASE_DATA)) {
            this.processPutBaseDataResponse(input_data);
            return;
        }

        this.abendIt("exportedparseFunction", command);
    }

    private void processSetupBaseResponse(String input_data_val) {
        this.debugIt(true, "processSetupBaseResponse", input_data_val);

        String room_id_str = input_data_val.substring(0, 4);
        String base_id_str = input_data_val.substring(4, 8);
        
        this.debugIt(true, "processSetupBaseResponse", "room_id_str=" + room_id_str);
        this.debugIt(true, "processSetupBaseResponse", "base_id_str=" + base_id_str);

        RoomClass room_object = this.RoomMgrObject().GetRoomByRoomIdStr(room_id_str);
        this.debugIt(true, "00000processSetupBaseResponse", "room_id_str=" + room_id_str);
        room_object.PutBaseIdStr(base_id_str);
        String downlink_data = FabricThemeProtocolClass.FABRIC_THEME_PROTOCOL_RESPOND_IS_SETUP_ROOM;
        downlink_data = downlink_data + room_object.GroupIdStr() + room_object.RoomIdStr();
        this.DThemeObject().TransmitData(downlink_data);
        this.debugIt(true, "processSetupBaseResponse", "11111room_id_str=" + room_id_str);

        /*
        char* room_id_index_val = data_val;

        char* downlink_data;
        char* data_ptr;
        int group_array_size;

        RoomClass* room = this->theThemeObject->searchRoom(room_id_index_val);
        if (!room)
        {
            this->abend("processSetupBaseResponse", "null room");
            return;
        }

        data_val += ROOM_MGR_PROTOCOL_ROOM_ID_INDEX_SIZE;
        room->setBaseIdIndex(data_val);

        downlink_data = data_ptr = (char*)phwangMalloc(ROOM_MGR_DATA_BUFFER_SIZE + 4, "UTSB");
        *data_ptr++ = FABRIC_THEME_PROTOCOL_RESPOND_IS_SETUP_ROOM;

        room->setGroupTableArray((char**)phwangArrayMgrGetArrayTable(room->groupArrayMgr(), &group_array_size));
        memcpy(data_ptr, room->groupTableArray(0), GROUP_MGR_PROTOCOL_GROUP_ID_INDEX_SIZE);
        data_ptr += GROUP_MGR_PROTOCOL_GROUP_ID_INDEX_SIZE;

        memcpy(data_ptr, room->roomIdIndex(), ROOM_MGR_PROTOCOL_ROOM_ID_INDEX_SIZE);
        data_ptr += ROOM_MGR_PROTOCOL_ROOM_ID_INDEX_SIZE;
        *data_ptr = 0;
        this->theThemeObject->dThemeObject()->transmitFunction(downlink_data);
        */
    }

    private void processPutBaseDataResponse(String input_data_val) {
        this.debugIt(true, "processPutBaseDataResponse", input_data_val);
        String room_id_str = input_data_val.substring(0, ThemeEngineProtocolClass.THEME_ROOM_ID_SIZE);
        String data = input_data_val.substring(ThemeEngineProtocolClass.THEME_ROOM_ID_SIZE);

        RoomClass room_object = this.RoomMgrObject().GetRoomByRoomIdStr(room_id_str);
        if (room_object == null) {
            this.abendIt("processPutBaseDataResponse", "null room");
            return;
        }

        String downlink_data = FabricThemeProtocolClass.FABRIC_THEME_PROTOCOL_RESPOND_IS_PUT_ROOM_DATA;
        downlink_data = downlink_data + room_object.GroupIdStr() + data;
        this.DThemeObject().TransmitData(downlink_data);

        /*
        char* downlink_data;
        char* data_ptr;
        int group_array_size;


        RoomClass* room = this->theThemeObject->searchRoom(data_val);
        if (!room)
        {
            this->abend("processPutBaseDataResponse", "null room");
            return;
        }
        data_val += ROOM_MGR_PROTOCOL_ROOM_ID_INDEX_SIZE;

        room->setGroupTableArray((char**)phwangArrayMgrGetArrayTable(room->groupArrayMgr(), &group_array_size));
        for (int i = 0; i < group_array_size; i++)
        {
            if (room->groupTableArray(i))
            {
                downlink_data = data_ptr = (char*)phwangMalloc(ROOM_MGR_DATA_BUFFER_SIZE + 4, "UTPB");
                *data_ptr++ = FABRIC_THEME_PROTOCOL_RESPOND_IS_PUT_ROOM_DATA;
                memcpy(data_ptr, room->groupTableArray(i), GROUP_MGR_PROTOCOL_GROUP_ID_INDEX_SIZE);
                data_ptr += GROUP_MGR_PROTOCOL_GROUP_ID_INDEX_SIZE;
                strcpy(data_ptr, data_val);
                this->theThemeObject->dThemeObject()->transmitFunction(downlink_data);
            }
        }
        */
    }

    private void debugIt(Boolean on_off_val, String str0_val, String str1_val) { if (on_off_val) this.logitIt(str0_val, str1_val); }
    private void logitIt(String str0_val, String str1_val) { AbendClass.phwangLogit(this.objectName() + "." + str0_val + "()", str1_val); }
    public void abendIt(String str0_val, String str1_val) { AbendClass.phwangAbend(this.objectName() + "." + str0_val + "()", str1_val); }
}
