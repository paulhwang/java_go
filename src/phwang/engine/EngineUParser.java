/*
 ******************************************************************************
 *                                       
 *  Copyright (c) 2021 phwang. All rights reserved.
 *
 ******************************************************************************
 */

package phwang.engine;

import phwang.utils.*;
import phwang.protocols.ThemeEngineProtocolClass;

public class EngineUParser {
    private String objectName() {return "EngineUParser";}

    private EngineDBinder engineDBinder_;

    public EngineRoot engineRoot() { return this.engineDBinder_.engineRoot(); }
    public EngineBaseMgr baseMgr() { return this.engineRoot().baseMgr(); }

    public EngineUParser(EngineDBinder d_engine_object_val) {
        this.engineDBinder_ = d_engine_object_val;
    }

    public void ParseInputPacket(String input_data_val) {
        this.debug(false, "ParseInputPacket", "data=" + input_data_val);

        String command = input_data_val.substring(0, 1);
        String input_data = input_data_val.substring(1);

        if (command.equals(ThemeEngineProtocolClass.THEME_ENGINE_PROTOCOL_COMMAND_IS_SETUP_BASE)) {
            this.processSetupBase(input_data);
            return;
        }

        if (command.equals(ThemeEngineProtocolClass.THEME_ENGINE_PROTOCOL_COMMAND_IS_PUT_BASE_DATA)) {
            this.processPutBaseData(input_data);
            return;
        }

        this.abend("ParseInputPacket", input_data_val);
    }

    private void processSetupBase(String input_data_val) {
        this.debug(false, "processSetupBase", "data=" + input_data_val);

        String room_id_str = input_data_val.substring(0, ThemeEngineProtocolClass.THEME_ROOM_ID_SIZE);
        String input_data = input_data_val.substring(ThemeEngineProtocolClass.THEME_ROOM_ID_SIZE);

        EngineBase go_base_object = this.baseMgr().MallocGoBase(room_id_str);
        if (go_base_object == null) {
            this.abend("processSetupBase", "null go_base");
            return;
        }

        String output_data = go_base_object.setupBase(input_data);

        String downlink_data = ThemeEngineProtocolClass.THEME_ENGINE_PROTOCOL_RESPOND_IS_SETUP_BASE;
        downlink_data = downlink_data + go_base_object.roomIdStr() + go_base_object.BaseIdStr() + output_data;
        this.engineDBinder_.TransmitData(downlink_data);
    }

    private void processPutBaseData(String input_data_val) {
        this.debug(false, "processPutBaseData", "data=" + input_data_val);
        String base_id_str = input_data_val.substring(0, EngineExport.ENGINE_BASE_ID_SIZE);
        String input_data = input_data_val.substring(EngineExport.ENGINE_BASE_ID_SIZE);

        EngineBase go_base_object = this.baseMgr().GetBaseByIdStr(base_id_str);
        if (go_base_object == null) {
            this.abend("processPutBaseData", "null go_base");
            return;
        }

        String output_data = go_base_object.processInputData(input_data);

        String downlink_data = ThemeEngineProtocolClass.THEME_ENGINE_PROTOCOL_RESPOND_IS_PUT_BASE_DATA;
        downlink_data = downlink_data + go_base_object.roomIdStr() + output_data;
        this.engineDBinder_.TransmitData(downlink_data);
    }
    
    private void debug(Boolean on_off, String s0, String s1) { if (on_off) this.log(s0, s1); }
    private void log(String s0, String s1) { AbendClass.log(this.objectName() + "." + s0 + "()", s1); }
    public void abend(String s0, String s1) { AbendClass.abend(this.objectName() + "." + s0 + "()", s1); }
}
