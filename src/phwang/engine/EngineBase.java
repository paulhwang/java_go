/*
 ******************************************************************************
 *                                       
 *  Copyright (c) 2021 phwang. All rights reserved.
 *
 ******************************************************************************
 */

package phwang.engine;

import phwang.utils.*;
import phwang.go.GoRoot;

public class EngineBase {
    private String objectName() {return "EngineBase";}

    private ListEntryClass listEntryObject_;
    private String roomIdStr_;
    private GoRoot goRootObject;

    private ListEntryClass listEntryObject() { return this.listEntryObject_; }
    public int baseId() { return this.listEntryObject().id(); }
    public String BaseIdStr() { return this.listEntryObject().idStr(); }
    public String roomIdStr() { return this.roomIdStr_; }

    public EngineBase(String room_id_str_val) {
        this.roomIdStr_ = room_id_str_val;
    }

    public void bindListEntry(ListEntryClass list_entry_object_val) {
        this.listEntryObject_ = list_entry_object_val;
    }

    public String setupBase(String input_data_val) {
    	this.debug(false, "setupBase", "input_data_val=" + input_data_val);
        String input_data = input_data_val.substring(1);

        switch (input_data_val.charAt(0)) {
            case 'G':
                this.goRootObject = new GoRoot();
                return this.goRootObject.doSetup(input_data);

            default:
                String err_msg = "command " + input_data_val.charAt(0) + " not supported";
                this.abend("setupBase", err_msg);
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
                this.abend("ProcessInputData", err_msg);
                return err_msg;
        }
    }
    
    private void debug(Boolean on_off, String s0, String s1) { if (on_off) this.log(s0, s1); }
    private void log(String s0, String s1) { Abend.log(this.objectName() + "." + s0 + "()", s1); }
    public void abend(String s0, String s1) { Abend.abend(this.objectName() + "." + s0 + "()", s1); }
}
