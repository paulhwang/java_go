/*
 ******************************************************************************
 *                                       
 *  Copyright (c) 2021 phwang. All rights reserved.
 *
 ******************************************************************************
 */

package com.phwang.core.theme;

import com.phwang.core.utils.*;
import com.phwang.core.protocols.FabricThemeProtocolClass;
import com.phwang.core.protocols.ThemeEngineProtocolClass;

public class ThemeUParser {
    private String objectName() {return "ThemeUParser";}
    
    ThemeRoot themeRoot_;

    public ThemeRoot themeRoot() { return this.themeRoot_; }
    public ThemeUBinder themeUBinder() { return this.themeRoot().themeUBinder(); }
    public ThemeRoomMgr roomMgr() { return this.themeRoot().roomMgr(); }

    public ThemeUParser(ThemeRoot root_val) {
        this.debug(false, "ThemeUParser", "init start");
        this.themeRoot_ = root_val;
    }

    public void parseInputPacket(String data_val) {
        this.debug(false, "parseInputPacket", data_val);
        String command = data_val.substring(0, 1);
        String data = data_val.substring(1);

        if (command.equals(FabricThemeProtocolClass.FABRIC_THEME_PROTOCOL_COMMAND_IS_SETUP_ROOM)) {
            this.processSetupRoom(data);
            return;
        }

        if (command.equals(FabricThemeProtocolClass.FABRIC_THEME_PROTOCOL_COMMAND_IS_PUT_ROOM_DATA)) {
            this.processPutRoomData(data);
            return;
        }

        this.abend("parseInputPacket", data_val);
    }

    private void processSetupRoom(String input_data_val) {
        this.debug(false, "processSetupRoom", input_data_val);

        String group_id_index = input_data_val.substring(0, FabricThemeProtocolClass.FABRIC_GROUP_ID_SIZE);
        String input_data = input_data_val.substring(FabricThemeProtocolClass.FABRIC_GROUP_ID_SIZE);

        ThemeRoom room = this.roomMgr().mallocRoom(group_id_index);
        if (room == null) {
            //String downlink_data;
            this.abend("processSetupRoom", "null room");
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
        uplink_data = uplink_data + room.roomIdStr() + input_data;
        this.themeUBinder().transmitData(uplink_data);
    }

    private void processPutRoomData(String input_data_val) {
        this.debug(false, "processPutRoomData", input_data_val);

        String room_id_str = input_data_val.substring(0, ThemeExport.THEME_ROOM_ID_SIZE);
        String input_data = input_data_val.substring(ThemeExport.THEME_ROOM_ID_SIZE);
        ThemeRoom room = this.roomMgr().getRoomByIdStr(room_id_str);
        if (room == null) {
            this.abend("processPutRoomData", "null room");
            return;
        }

        String uplink_data = ThemeEngineProtocolClass.THEME_ENGINE_PROTOCOL_COMMAND_IS_PUT_BASE_DATA;
        uplink_data = uplink_data + room.baseIdStr() + input_data;
        this.themeUBinder().transmitData(uplink_data);
    }
    
    private void debug(Boolean on_off, String s0, String s1) { if (on_off) this.log(s0, s1); }
    private void log(String s0, String s1) { this.themeRoot().logIt(this.objectName() + "." + s0 + "()", s1); }
    public void abend(String s0, String s1) { this.themeRoot().abendIt(this.objectName() + "." + s0 + "()", s1); }
}
