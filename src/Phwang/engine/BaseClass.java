/*
 ******************************************************************************
 *                                       
 *  Copyright (c) 2018 phwang. All rights reserved.
 *
 ******************************************************************************
 */

package Phwang.engine;

import Phwang.Utils.*;
import Phwang.protocols.ThemeEngineProtocolClass;
import Phwang.go.GoRootClass;

public class BaseClass {
    private String objectName() {return "BaseClass";}

    private ListEntryClass listEntryObject;
    private String roomIdStr;
    private int baseId;
    private String baseIdStr;
    private GoRootClass goRootObject;

    public String RoomIdStr() { return this.roomIdStr; }
    public String BaseIdStr() { return this.baseIdStr; }

    public BaseClass(String room_id_str_val) {
        this.roomIdStr = room_id_str_val;
    }

    public void bindListEntry(ListEntryClass list_entry_objectg_val) {
        this.listEntryObject = list_entry_objectg_val;
        this.baseId = this.listEntryObject.Id();
        this.baseIdStr = EncodeNumberClass.encodeNumber(this.baseId, ThemeEngineProtocolClass.ENGINE_BASE_ID_SIZE);
    }

    public String setupBase(String input_data_val) {
        String input_data = input_data_val.substring(1);

        switch (input_data_val.charAt(0)) {
            case 'G':
                this.goRootObject = new GoRootClass();
                return this.goRootObject.doSetup(input_data);

            default:
                String err_msg = "command " + input_data_val.charAt(0) + " not supported";
                this.abendIt("setupBase", err_msg);
                return err_msg;
        }
    }

    public String processInputData(String input_data_val) {
        String input_data = input_data_val.substring(1);

        switch (input_data_val.charAt(0)) {
            case 'G':
                String output_data = this.goRootObject.processInputData(input_data);
                return output_data;

            default:
                String err_msg = "command " + input_data_val.charAt(0) + " not supported";
                this.abendIt("ProcessInputData", err_msg);
                return err_msg;
        }
    }

    private void debugIt(Boolean on_off_val, String str0_val, String str1_val) { if (on_off_val) this.logitIt(str0_val, str1_val); }
    private void logitIt(String str0_val, String str1_val) { AbendClass.phwangLogit(this.objectName() + "." + str0_val + "()", str1_val); }
    public void abendIt(String str0_val, String str1_val) { AbendClass.phwangAbend(this.objectName() + "." + str0_val + "()", str1_val); }
}
