/*
 ******************************************************************************
 *                                       
 *  Copyright (c) 2021 phwang. All rights reserved.
 *
 ******************************************************************************
 */

package com.phwang.core.theme;

//import com.phwang.core.utils.*;

import com.phwang.core.utils.Encoders;

public class ThemeUParser {
    private static String objectName() {return "ThemeUParser";}
    
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
        char command = data_val.charAt(0);
        String data = data_val.substring(1);

        if (command == ThemeImport.FABRIC_THEME_COMMAND_SETUP_ROOM) {
            this.processSetupRoom(data);
            return;
        }

        if (command == ThemeImport.FABRIC_THEME_COMMAND_PUT_ROOM_DATA) {
            this.processPutRoomData(data);
            return;
        }

        this.abend("parseInputPacket", data_val);
    }

    private void processSetupRoom(String input_str_val) {
        this.debug(false, "processSetupRoom", input_str_val);

        String rest_str = input_str_val;
        String group_id_str = Encoders.sSubstring2(rest_str);
        rest_str = Encoders.sSubstring2_(rest_str);

        String input_data = rest_str;

        ThemeRoom room = this.roomMgr().mallocRoom(group_id_str);
        if (room == null) {
            this.abend("processSetupRoom", "null room");
            return;
        }
        
        StringBuilder buf = new StringBuilder();
        buf.append(ThemeExport.THEME_ENGINE_COMMAND_SETUP_BASE);
        buf.append(room.roomIdStr());
        buf.append(input_data);
        this.themeUBinder().transmitData(buf.toString());
    }

    private void processPutRoomData(String input_str_val) {
        this.debug(false, "processPutRoomData", input_str_val);

        String rest_str = input_str_val;
        String room_id_str = Encoders.sSubstring2(rest_str);
        rest_str = Encoders.sSubstring2_(rest_str);

        String input_data = rest_str;

        ThemeRoom room = this.roomMgr().getRoomByIdStr(room_id_str);
        if (room == null) {
            this.abend("processPutRoomData", "null room");
            return;
        }

        StringBuilder buf = new StringBuilder();
        buf.append(ThemeExport.THEME_ENGINE_COMMAND_PUT_BASE_DATA);
        buf.append(room.baseIdStr());
        buf.append(input_data);
        this.themeUBinder().transmitData(buf.toString());
    }
    
    private void debug(Boolean on_off, String s0, String s1) { if (on_off) this.log(s0, s1); }
    private void log(String s0, String s1) { this.themeRoot().logIt(this.objectName() + "." + s0 + "()", s1); }
    public void abend(String s0, String s1) { this.themeRoot().abendIt(this.objectName() + "." + s0 + "()", s1); }
}
