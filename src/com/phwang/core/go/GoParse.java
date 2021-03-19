/*
 ******************************************************************************
 *                                       
 *  Copyright (c) 2021 phwang. All rights reserved.
 *
 ******************************************************************************
 */

package com.phwang.core.go;

import com.phwang.core.utils.*;

public class GoParse {
    private String objectName() {return "GoParse";}

    private GoRoot goRoot_;

    public GoGame goGame() { return this.goRoot_.goGame(); }


    public GoParse(GoRoot go_root_val) {
        this.goRoot_ = go_root_val;
    }

    public void parseInputData(String input_data_val) {
    	this.debug(false, "parseInputData", "input_data_val=" + input_data_val);
    	
        int len = input_data_val.length();//to be deleted
        switch (input_data_val.charAt(0)) {
            case GoExport.GO_PROTOCOL_MOVE_COMMAND:
            	GoMove move = new GoMove(input_data_val.substring(1, 9));
                this.goGame().addNewMoveAndFight(move);
                return;

            case GoExport.GO_PROTOCOL_BACKWARD_COMMAND:
                this.goGame().processBackwardMove();
                return;

            case GoExport.GO_PROTOCOL_DOUBLE_BACKWARD_COMMAND:
                this.goGame().processDoubleBackwardMove();
                return;

            case GoExport.GO_PROTOCOL_FORWARD_COMMAND:
                this.goGame().processForwardMove();
                return;

            case GoExport.GO_PROTOCOL_DOUBLE_FORWARD_COMMAND:
                this.goGame().processDoubleForwardMove();
                return;

            case GoExport.GO_PROTOCOL_PASS_COMMAND:
                return;

            default:
                String err_msg = "command " + input_data_val.charAt(1) + " not supported";
                this.abend("parseInputData", err_msg);
                return;
        }
    }
    
    private void debug(Boolean on_off, String s0, String s1) { if (on_off) this.log(s0, s1); }
    private void log(String s0, String s1) { Abend.log(this.objectName() + "." + s0 + "()", s1); }
    public void abend(String s0, String s1) { Abend.abend(this.objectName() + "." + s0 + "()", s1); }
}
