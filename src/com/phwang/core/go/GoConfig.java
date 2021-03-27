/*
 ******************************************************************************
 *                                       
 *  Copyright (c) 2021 phwang. All rights reserved.
 *
 ******************************************************************************
 */

package com.phwang.core.go;

import com.phwang.core.utils.*;

public class GoConfig {
    private String objectName() {return "GoConfig";}

    private GoRoot goRoot_;
    private int boardSize_;
    private int handicapPoint_;
    private int komiPoint_;

    public int boardSize() { return this.boardSize_; }
    public int handicapPoint() { return this.handicapPoint_; }
    public int komiPoint() { return this.komiPoint_; }

    public GoConfig(GoRoot root_val) {
        this.goRoot_ = root_val;
    }

    public void configIt(String input_data_val) {
    	this.debug(false, "ConfigIt", "input_data_val=" + input_data_val);
    	
        String len_str = input_data_val.substring(0,3);
        String board_size_str = input_data_val.substring(3, 5);
        String handicap_str = input_data_val.substring(5, 7);
        String komi_str = input_data_val.substring(7, 9);

        this.boardSize_ = Encoders.iDecodeRaw(board_size_str);
        this.handicapPoint_ = Encoders.iDecodeRaw(handicap_str);
        this.komiPoint_ = Encoders.iDecodeRaw(komi_str);
        
    	this.debug(false, "ConfigIt", "boardSize=" + boardSize());
    }

    private Boolean isValidCoordinate_(int coordinate_val) {
        return (0 <= coordinate_val) && (coordinate_val < this.boardSize());
    }
    
    public Boolean isValidCoordinates(int x_val, int y_val) {
        return this.isValidCoordinate_(x_val) && this.isValidCoordinate_(y_val);
    }
    
    private void debug(Boolean on_off, String s0, String s1) { if (on_off) this.log(s0, s1); }
    private void log(String s0, String s1) { Abend.log(this.objectName() + "." + s0 + "()", s1); }
    public void abend(String s0, String s1) { Abend.abend(this.objectName() + "." + s0 + "()", s1); }
}
