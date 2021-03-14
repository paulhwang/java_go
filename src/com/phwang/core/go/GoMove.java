/*
 ******************************************************************************
 *                                       
 *  Copyright (c) 2021 phwang. All rights reserved.
 *
 ******************************************************************************
 */

package com.phwang.core.go;

public class GoMove {
    private int x_;
    private int y_;
    private int myColor_;
    private int turnIndex_;
    private String moveInfo_;

    public int X() { return this.x_; }
    public int Y() { return this.y_; }
    public int MyColor() { return this.myColor_; }
    public int TurnIndex() { return this.turnIndex_; }
    public String moveInfo() { return this.moveInfo_; }

    public GoMove(String encoded_move_val) {
        this.decodeMove(encoded_move_val);
    }

    private void decodeMove(String encoded_move_val) {
        this.x_ = (encoded_move_val.charAt(0) - '0') * 10 + (encoded_move_val.charAt(1) - '0');
        this.y_ = (encoded_move_val.charAt(2) - '0') * 10 + (encoded_move_val.charAt(3) - '0');
        this.myColor_ = encoded_move_val.charAt(4) - '0';
        this.turnIndex_ = (encoded_move_val.charAt(5) - '0') * 100 + (encoded_move_val.charAt(6) - '0') * 10 + (encoded_move_val.charAt(7) - '0');
        this.moveInfo_ = "(" + this.x_ + ", " + this.y_ + ") " + this.myColor_ + ", " + this.turnIndex_;
    }
}
