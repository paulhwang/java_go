/*
 ******************************************************************************
 *                                       
 *  Copyright (c) 2018 phwang. All rights reserved.
 *
 ******************************************************************************
 */

package Phwang.engine.Go;

import Phwang.Utils.*;

public class GoConfigClass {
    private String objectName() {return "GoConfigClass";}

    private GoRootClass theRootObject;
    private int boardSize;
    private int handicapPoint;
    private int komiPoint;

    public int BoardSize() { return this.boardSize; }
    public int HandicapPoint() { return this.handicapPoint; }
    public int KomiPoint() { return this.komiPoint; }

    public GoConfigClass(GoRootClass root_object_val) {
        this.theRootObject = root_object_val;
    }

    public void configIt(String input_data_val) {
    	this.debugIt(false, "ConfigIt", "input_data_val=" + input_data_val);
    	
        String len_str = input_data_val.substring(0,3);
        String board_size_str = input_data_val.substring(3, 5);
        String handicap_str = input_data_val.substring(5, 7);
        String komi_str = input_data_val.substring(7, 9);

        this.boardSize = EncodeNumberClass.decodeNumber(board_size_str);
        this.handicapPoint = EncodeNumberClass.decodeNumber(handicap_str);
        this.komiPoint = EncodeNumberClass.decodeNumber(komi_str);
        
    	this.debugIt(false, "ConfigIt", "boardSize=" + boardSize);
    }

    private Boolean isValidCoordinate(int coordinate_val) {
        return (0 <= coordinate_val) && (coordinate_val < this.boardSize);
    }
    
    public Boolean IsValidCoordinates(int x_val, int y_val) {
        return this.isValidCoordinate(x_val) && this.isValidCoordinate(y_val);
    }

    private void debugIt(Boolean on_off_val, String str0_val, String str1_val) { if (on_off_val) this.logitIt(str0_val, str1_val); }
    private void logitIt(String str0_val, String str1_val) { AbendClass.phwangLogit(this.objectName() + "." + str0_val + "()", str1_val); }
    public void abendIt(String str0_val, String str1_val) { AbendClass.phwangAbend(this.objectName() + "." + str0_val + "()", str1_val); }
}
