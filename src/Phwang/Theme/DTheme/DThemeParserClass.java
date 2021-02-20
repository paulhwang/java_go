/*
 ******************************************************************************
 *                                       
 *  Copyright (c) 2018 phwang. All rights reserved.
 *
 ******************************************************************************
 */

package Phwang.Theme.DTheme;

import Phwang.Utils.AbendClass;
import Phwang.Protocols.FabricThemeProtocolClass;
import Phwang.Protocols.ThemeEngineProtocolClass;
import Phwang.Theme.ThemeRootClass;
import Phwang.Theme.Utheme.UThemeClass;
import Phwang.Theme.RoomMgr.RoomMgrClass;
import Phwang.Theme.RoomMgr.RoomClass;

public class DThemeParserClass {
    private String objectName() {return "DThemeParserClass";}
    
    private DThemeClass dThemeObject;

    public ThemeRootClass ThemeRootObject() { return this.dThemeObject.ThemeRootObject(); }
    public UThemeClass UThemeObject() { return this.ThemeRootObject().UThemeObject(); }
    public RoomMgrClass RoomMgrObject() { return this.ThemeRootObject().RoomMgrObject(); }

    public DThemeParserClass(DThemeClass d_theme_object_val) {
        this.debugIt(true, "DThemeParserClass", "init start");
        this.dThemeObject = d_theme_object_val;
    }

    public void ParseInputPacket(String data_val) {
        this.debugIt(true, "ParseInputPacket", data_val);
        String command = data_val.substring(0, 1);
        String data = data_val.substring(1);

        if (command == FabricThemeProtocolClass.FABRIC_THEME_PROTOCOL_COMMAND_IS_SETUP_ROOM)
        {
            this.processSetupRoom(data);
            return;
        }

        if (command == FabricThemeProtocolClass.FABRIC_THEME_PROTOCOL_COMMAND_IS_PUT_ROOM_DATA)
        {
            this.processPutRoomData(data);
            return;
        }

        this.abendIt("ParseInputPacket", data_val);
    }

    private void processSetupRoom(String input_data_val)
    {
        this.debugIt(true, "processSetupRoom", input_data_val);

        String group_id_index = input_data_val.substring(0, FabricThemeProtocolClass.FABRIC_GROUP_ID_SIZE);
        String input_data = input_data_val.substring(FabricThemeProtocolClass.FABRIC_GROUP_ID_SIZE);

        RoomClass room = this.RoomMgrObject().MallocRoom(group_id_index);
        if (room == null)
        {
            //String downlink_data;
            this.abendIt("processSetupRoom", "null room");
            //downlink_data = data_ptr = (char*)phwangMalloc(ROOM_MGR_DATA_BUFFER_SIZE + 4, "DTSr");
            //*data_ptr++ = FABRIC_THEME_PROTOCOL_RESPOND_IS_SETUP_ROOM;
            //strcpy(data_ptr, "null room");
            //this->transmitFunction(downlink_data);
            return;
        }
        /*
        data_val += GROUP_MGR_PROTOCOL_GROUP_ID_INDEX_SIZE;

        uplink_data = data_ptr = (char*)phwangMalloc(ROOM_MGR_DATA_BUFFER_SIZE + 4, "DTSR");
        *data_ptr++ = THEME_ENGINE_PROTOCOL_COMMAND_IS_SETUP_BASE;

        memcpy(data_ptr, room->roomIdIndex(), ROOM_MGR_PROTOCOL_ROOM_ID_INDEX_SIZE);
        data_ptr += ROOM_MGR_PROTOCOL_ROOM_ID_INDEX_SIZE;

        strcpy(data_ptr, data_val);
        */
        String uplink_data = ThemeEngineProtocolClass.THEME_ENGINE_PROTOCOL_COMMAND_IS_SETUP_BASE;
        uplink_data = uplink_data + room.RoomIdStr() + input_data;
        this.UThemeObject().TransmitData(uplink_data);
    }

    private void processPutRoomData(String input_data_val)
    {
        this.debugIt(true, "processPutRoomData", input_data_val);

        String room_id_str = input_data_val.substring(0, ThemeEngineProtocolClass.THEME_ROOM_ID_SIZE);
        String input_data = input_data_val.substring(ThemeEngineProtocolClass.THEME_ROOM_ID_SIZE);
        RoomClass room = this.RoomMgrObject().GetRoomByRoomIdStr(room_id_str);
        if (room == null)
        {
            this.abendIt("processPutRoomData", "null room");
            return;
        }

        String uplink_data = ThemeEngineProtocolClass.THEME_ENGINE_PROTOCOL_COMMAND_IS_PUT_BASE_DATA;
        uplink_data = uplink_data + room.BaseIdStr() + input_data;
        this.UThemeObject().TransmitData(uplink_data);
    }


    private void debugIt(Boolean on_off_val, String str0_val, String str1_val)
    {
        if (on_off_val)
            this.logitIt(str0_val, str1_val);
    }

    private void logitIt(String str0_val, String str1_val)
    {
        AbendClass.phwangLogit(this.objectName() + "." + str0_val + "()", str1_val);
    }

    public void abendIt(String str0_val, String str1_val)
    {
        AbendClass.phwangAbend(this.objectName() + "." + str0_val + "()", str1_val);
    }
}
