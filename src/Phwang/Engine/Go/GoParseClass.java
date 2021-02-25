/*
 ******************************************************************************
 *                                       
 *  Copyright (c) 2018 phwang. All rights reserved.
 *
 ******************************************************************************
 */

package Phwang.Engine.Go;

import Phwang.Utils.AbendClass;

public class GoParseClass {
    private String objectName() {return "GoParseClass";}

    private GoRootClass theRootObject;

    public GoGameClass GameObject() { return this.theRootObject.GameObject(); }


    public GoParseClass(GoRootClass go_root_object_val) {
        this.theRootObject = go_root_object_val;
    }

    public void ParseInputData(String input_data_val)
    {
        int len = input_data_val.length();//to be deleted
        switch (input_data_val.charAt(0))
        {
            case GoProtocolClass.GO_PROTOCOL_MOVE_COMMAND:
                GoMoveClass move = new GoMoveClass(input_data_val.substring(1, 9));
                this.GameObject().AddNewMoveAndFight(move);
                return;

            case GoProtocolClass.GO_PROTOCOL_BACKWARD_COMMAND:
                this.GameObject().ProcessBackwardMove();
                return;

            case GoProtocolClass.GO_PROTOCOL_DOUBLE_BACKWARD_COMMAND:
                this.GameObject().ProcessDoubleBackwardMove();
                return;

            case GoProtocolClass.GO_PROTOCOL_FORWARD_COMMAND:
                this.GameObject().ProcessForwardMove();
                return;

            case GoProtocolClass.GO_PROTOCOL_DOUBLE_FORWARD_COMMAND:
                this.GameObject().ProcessDoubleForwardMove();
                return;

            case GoProtocolClass.GO_PROTOCOL_PASS_COMMAND:
                return;

            default:
                String err_msg = "command " + input_data_val.charAt(1) + " not supported";
                this.abendIt("ParseInputData", err_msg);
                return;
        }
    }

    private void debugIt(Boolean on_off_val, String str0_val, String str1_val) { if (on_off_val) this.logitIt(str0_val, str1_val); }
    private void logitIt(String str0_val, String str1_val) { AbendClass.phwangLogit(this.objectName() + "." + str0_val + "()", str1_val); }
    public void abendIt(String str0_val, String str1_val) { AbendClass.phwangAbend(this.objectName() + "." + str0_val + "()", str1_val); }
}
