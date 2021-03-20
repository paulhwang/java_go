/*
 ******************************************************************************
 *                                       
 *  Copyright (c) 2021 phwang. All rights reserved.
 *
 ******************************************************************************
 */

package com.phwang.core.engine;

import com.phwang.core.utils.*;
import com.phwang.core.protocols.ThemeEngineProtocolClass;

public class EngineUParser {
    private String objectName() {return "EngineUParser";}

    private EngineRoot engineRoot_;

    public EngineRoot engineRoot() { return this.engineRoot_; }
    public EngineDBinder engineDBinder() { return this.engineRoot().engineDBinder(); }
    public EngineBaseMgr baseMgr() { return this.engineRoot().baseMgr(); }

    public EngineUParser(EngineRoot root_val) {
        this.engineRoot_ = root_val;
    }

    public void ParseInputPacket(String input_data_val) {
        this.debug(false, "ParseInputPacket", "data=" + input_data_val);

        String command = input_data_val.substring(0, 1);
        String input_data = input_data_val.substring(1);

        if (command.equals(EngineImport.THEME_ENGINE_COMMAND_SETUP_BASE)) {
            this.processSetupBase(input_data);
            return;
        }

        if (command.equals(EngineImport.THEME_ENGINE_COMMAND_PUT_BASE_DATA)) {
            this.processPutBaseData(input_data);
            return;
        }

        this.abend("ParseInputPacket", input_data_val);
    }

    private void processSetupBase(String input_data_val) {
        this.debug(false, "processSetupBase", "data=" + input_data_val);

        String room_id_str = input_data_val.substring(0, EngineImport.THEME_ROOM_ID_SIZE);
        String input_data = input_data_val.substring(EngineImport.THEME_ROOM_ID_SIZE);

        EngineBase go_base_object = this.baseMgr().MallocGoBase(room_id_str);
        if (go_base_object == null) {
            this.abend("processSetupBase", "null go_base");
            return;
        }

        String output_data = go_base_object.setupBase(input_data);

        String downlink_data = EngineImport.THEME_ENGINE_RESPOND_SETUP_BASE;
        downlink_data = downlink_data + go_base_object.roomIdStr() + go_base_object.BaseIdStr() + output_data;
        this.engineDBinder().TransmitData(downlink_data);
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

        String downlink_data = EngineImport.THEME_ENGINE_RESPOND_PUT_BASE_DATA;
        downlink_data = downlink_data + go_base_object.roomIdStr() + output_data;
        this.engineDBinder().TransmitData(downlink_data);
    }
    
    private void debug(Boolean on_off, String s0, String s1) { if (on_off) this.log(s0, s1); }
    private void log(String s0, String s1) { this.engineRoot().logIt(this.objectName() + "." + s0 + "()", s1); }
    public void abend(String s0, String s1) { this.engineRoot().abendIt(this.objectName() + "." + s0 + "()", s1); }
}
