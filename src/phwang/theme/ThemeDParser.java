/*
 ******************************************************************************
 *                                       
 *  Copyright (c) 2021 phwang. All rights reserved.
 *
 ******************************************************************************
 */

package phwang.theme;

import phwang.utils.*;
import phwang.protocols.ThemeEngineProtocolClass;
import phwang.protocols.FabricThemeProtocolClass;

public class ThemeDParser {
    private String objectName() {return "ThemeDParser";}
    
    private ThemeUBinder uThemeObject;

    public ThemeRootClass ThemeRootObject() { return this.uThemeObject.ThemeRootObject(); }
    public ThemeDBinder DThemeObject() { return this.ThemeRootObject().DThemeObject(); }
    public ThemeRoomMgr RoomMgrObject() { return this.ThemeRootObject().RoomMgrObject(); }

    public ThemeDParser(ThemeUBinder u_theme_object_val) {
        this.debug(false, "ThemeDParser", "init start");
        this.uThemeObject = u_theme_object_val;
    }
    
    public void parseInputPacket(String input_data_val) {
        this.debug(false, "parseInputPacket", input_data_val);
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

        this.abend("parseInputPacket", command);
    }

    private void processSetupBaseResponse(String input_data_val) {
        this.debug(false, "processSetupBaseResponse", input_data_val);

        String room_id_str = input_data_val.substring(0, ThemeExport.THEME_ROOM_ID_SIZE);
        String base_id_str = input_data_val.substring(ThemeExport.THEME_ROOM_ID_SIZE);
        
        this.debug(false, "processSetupBaseResponse", "room_id_str=" + room_id_str);
        this.debug(false, "processSetupBaseResponse", "base_id_str=" + base_id_str);

        ThemeRoom room_object = this.RoomMgrObject().getRoomByIdStr(room_id_str);
        room_object.setBaseIdStr(base_id_str);
        String downlink_data = FabricThemeProtocolClass.FABRIC_THEME_PROTOCOL_RESPOND_IS_SETUP_ROOM;
        downlink_data = downlink_data + room_object.groupIdStr() + room_object.RoomIdStr();
        this.DThemeObject().transmitData(downlink_data);

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
        this.debug(false, "processPutBaseDataResponse", input_data_val);
        String room_id_str = input_data_val.substring(0, ThemeExport.THEME_ROOM_ID_SIZE);
        String data = input_data_val.substring(ThemeExport.THEME_ROOM_ID_SIZE);

        ThemeRoom room_object = this.RoomMgrObject().getRoomByIdStr(room_id_str);
        if (room_object == null) {
            this.abend("processPutBaseDataResponse", "null room");
            return;
        }

        String downlink_data = FabricThemeProtocolClass.FABRIC_THEME_PROTOCOL_RESPOND_IS_PUT_ROOM_DATA;
        downlink_data = downlink_data + room_object.groupIdStr() + data;
        this.DThemeObject().transmitData(downlink_data);

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
    
    private void debug(Boolean on_off, String s0, String s1) { if (on_off) this.log(s0, s1); }
    private void log(String s0, String s1) { AbendClass.log(this.objectName() + "." + s0 + "()", s1); }
    public void abend(String s0, String s1) { AbendClass.abend(this.objectName() + "." + s0 + "()", s1); }
}
