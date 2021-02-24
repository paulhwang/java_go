/*
 ******************************************************************************
 *                                       
 *  Copyright (c) 2018 phwang. All rights reserved.
 *
 ******************************************************************************
 */

package Phwang.Engine.DEngine;

import Phwang.Utils.AbendClass;
import Phwang.Protocols.ThemeEngineProtocolClass;
import Phwang.Engine.EngineRootClass;
import Phwang.Engine.BaseMgrClass;
import Phwang.Engine.BaseClass;

public class DEngineParserClass {
    private String objectName() {return "DEngineParserClass";}

    private DEngineClass dEngineObject;

    public EngineRootClass EngineRootObject() { return this.dEngineObject.EngineRootObject(); }
    public BaseMgrClass BaseMgrObject() { return this.EngineRootObject().BaseMgrObject(); }

    public DEngineParserClass(DEngineClass d_engine_object_val) {
        this.dEngineObject = d_engine_object_val;
    }

    public void ParseInputPacket(String input_data_val) {
        this.debugIt(true, "ParseInputPacket", "data=" + input_data_val);

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

        this.abendIt("ParseInputPacket", input_data_val);
    }

    private void processSetupBase(String input_data_val) {
        this.debugIt(true, "processSetupBase", "data=" + input_data_val);

        String room_id_str = input_data_val.substring(0, ThemeEngineProtocolClass.THEME_ROOM_ID_SIZE);
        String input_data = input_data_val.substring(ThemeEngineProtocolClass.THEME_ROOM_ID_SIZE);

        BaseClass go_base_object = this.BaseMgrObject().MallocGoBase(room_id_str);
        if (go_base_object == null) {
            this.abendIt("processSetupBase", "null go_base");
            return;
        }

        String output_data = go_base_object.SetupBase(input_data);

        String downlink_data = ThemeEngineProtocolClass.THEME_ENGINE_PROTOCOL_RESPOND_IS_SETUP_BASE;
        downlink_data = downlink_data + go_base_object.RoomIdStr() + go_base_object.BaseIdStr() + output_data;
        this.dEngineObject.TransmitData(downlink_data);
    }

    private void processPutBaseData(String input_data_val) {
        this.debugIt(true, "processPutBaseData", "data=" + input_data_val);
        String base_id_str = input_data_val.substring(0, ThemeEngineProtocolClass.ENGINE_BASE_ID_SIZE);
        String input_data = input_data_val.substring(ThemeEngineProtocolClass.ENGINE_BASE_ID_SIZE);

        BaseClass go_base_object = this.BaseMgrObject().GetBaseByIdStr(base_id_str);
        if (go_base_object == null) {
            this.abendIt("processPutBaseData", "null go_base");
            return;
        }

        String output_data = go_base_object.ProcessInputData(input_data);

        String downlink_data = ThemeEngineProtocolClass.THEME_ENGINE_PROTOCOL_RESPOND_IS_PUT_BASE_DATA;
        downlink_data = downlink_data + go_base_object.RoomIdStr() + output_data;
        this.dEngineObject.TransmitData(downlink_data);
    }

    private void debugIt(Boolean on_off_val, String str0_val, String str1_val) { if (on_off_val) this.logitIt(str0_val, str1_val); }
    private void logitIt(String str0_val, String str1_val) { AbendClass.phwangLogit(this.objectName() + "." + str0_val + "()", str1_val); }
    public void abendIt(String str0_val, String str1_val) { AbendClass.phwangAbend(this.objectName() + "." + str0_val + "()", str1_val); }
}
