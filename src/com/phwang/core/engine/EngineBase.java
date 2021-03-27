/*
 ******************************************************************************
 *                                       
 *  Copyright (c) 2021 phwang. All rights reserved.
 *
 ******************************************************************************
 */

package com.phwang.core.engine;

import com.phwang.core.utils.*;
import com.phwang.core.go.GoRoot;

public class EngineBase implements ListEntryInt {
    private String objectName() {return "EngineBase";}

    private ListEntry listEntry_;
    private String roomIdStr_;
    private GoRoot goRoot_;

    public ListEntry listEntry() { return this.listEntry_; }
    public int baseId() { return this.listEntry().id(); }
    public String BaseIdStr() { return this.listEntry().idStr(); }
    public String roomIdStr() { return this.roomIdStr_; }

    public EngineBase(String room_id_str_val) {
        this.roomIdStr_ = room_id_str_val;
    }

    public void bindListEntry(ListEntry list_entry_object_val, String who_val) {
        this.listEntry_ = list_entry_object_val;
    }

    public void unBindListEntry(String who_val) {
        this.listEntry_ = null;
    }

    public String setupBase(String input_data_val) {
    	this.debug(true, "setupBase", "input_data_val=" + input_data_val);
        String input_data = input_data_val.substring(1);

        switch (input_data_val.charAt(0)) {
            case 'G':
                this.goRoot_ = new GoRoot();
                return this.goRoot_.doSetup(input_data);

            default:
                String err_msg = "command " + input_data_val.charAt(0) + " not supported";
                this.abend("setupBase", err_msg);
                return err_msg;
        }
    }

    public String processInputData(String input_data_val) {
    	this.debug(true, "processInputData", "input_data_val=" + input_data_val);
        String input_data = input_data_val.substring(1);

        switch (input_data_val.charAt(0)) {
            case 'G':
                String output_data = this.goRoot_.processInputData(input_data);
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
