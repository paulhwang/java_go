/*
 ******************************************************************************
 *                                       
 *  Copyright (c) 2018 phwang. All rights reserved.
 *
 ******************************************************************************
 */

package Phwang.Engine.Go;

import Phwang.Utils.AbendClass;
import Phwang.Utils.Encode.EncodeNumberClass;

public class GoConfigClass {
    private String objectName() {return "GoConfigClass";}

    private GoRootClass theRootObject;
    private int boardSize;
    private int handicapPoint;
    private int komiPoint;

    public int BoardSize() { return this.boardSize; }
    public int HandicapPoint() { return this.handicapPoint; }
    public int KomiPoint() { return this.komiPoint; }

    public GoConfigClass(GoRootClass root_object_val)
    {
        this.theRootObject = root_object_val;
    }

    public void ConfigIt(String input_data_val)
    {
    	/*
        String len_str = input_data_val.Substring(0,3);
        String board_size_str = input_data_val.Substring(3, 2);
        String handicap_str = input_data_val.Substring(5, 2);
        String komi_str = input_data_val.Substring(7, 2);

        this.boardSize = EncodeNumberClass.DecodeNumber(board_size_str);
        this.handicapPoint = EncodeNumberClass.DecodeNumber(handicap_str);
        this.komiPoint = EncodeNumberClass.DecodeNumber(komi_str);

        int len = input_data_val.Length;//to be deleted
        String name = input_data_val.Substring(10);//to be deleted
        */
    }

    private Boolean isValidCoordinate(int coordinate_val)
    {
        return (0 <= coordinate_val) && (coordinate_val < this.boardSize);
    }
    public Boolean IsValidCoordinates(int x_val, int y_val)
    {
        return this.isValidCoordinate(x_val) && this.isValidCoordinate(y_val);
    }

    private void debugIt(Boolean on_off_val, String str0_val, String str1_val)
    {
        if (on_off_val)
            this.logitIt(str0_val, str1_val);
    }

    private void logitIt(String str0_val, String str1_val)
    {
        AbendClass.phwangLogit(this.objectName() + "." + str0_val + "()", str1_val);
    }

    private void abendIt(String str0_val, String str1_val)
    {
        AbendClass.phwangAbend(this.objectName() + "." + str0_val + "()", str1_val);
    }
}
