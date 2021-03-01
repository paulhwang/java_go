/*
 ******************************************************************************
 *                                       
 *  Copyright (c) 2018 phwang. All rights reserved.
 *
 ******************************************************************************
 */

package phwang.go;

import phwang.utils.*;

public class GoParseClass {
    private String objectName() {return "GoParseClass";}

    private GoRootClass theRootObject;

    public GoGameClass GameObject() { return this.theRootObject.GameObject(); }


    public GoParseClass(GoRootClass go_root_object_val) {
        this.theRootObject = go_root_object_val;
    }

    public void parseInputData(String input_data_val)
    {
        int len = input_data_val.length();//to be deleted
        switch (input_data_val.charAt(0))
        {
            case GoProtocolClass.GO_PROTOCOL_MOVE_COMMAND:
                GoMoveClass move = new GoMoveClass(input_data_val.substring(1, 9));
                this.GameObject().addNewMoveAndFight(move);
                return;

            case GoProtocolClass.GO_PROTOCOL_BACKWARD_COMMAND:
                this.GameObject().processBackwardMove();
                return;

            case GoProtocolClass.GO_PROTOCOL_DOUBLE_BACKWARD_COMMAND:
                this.GameObject().processDoubleBackwardMove();
                return;

            case GoProtocolClass.GO_PROTOCOL_FORWARD_COMMAND:
                this.GameObject().processForwardMove();
                return;

            case GoProtocolClass.GO_PROTOCOL_DOUBLE_FORWARD_COMMAND:
                this.GameObject().processDoubleForwardMove();
                return;

            case GoProtocolClass.GO_PROTOCOL_PASS_COMMAND:
                return;

            default:
                String err_msg = "command " + input_data_val.charAt(1) + " not supported";
                this.abend("parseInputData", err_msg);
                return;
        }
    }
    
    private void debug(Boolean on_off, String s0, String s1) { if (on_off) this.log(s0, s1); }
    private void log(String s0, String s1) { AbendClass.log(this.objectName() + "." + s0 + "()", s1); }
    public void abend(String s0, String s1) { AbendClass.abend(this.objectName() + "." + s0 + "()", s1); }
}
