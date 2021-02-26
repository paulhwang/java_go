/*
 ******************************************************************************
 *                                       
 *  Copyright (c) 2018 phwang. All rights reserved.
 *
 ******************************************************************************
 */

package Phwang.engine.Go;

public class GoMoveClass {
    private int theX;
    private int theY;
    private int myColor;
    private int turnIndex;
    private String moveInfo;

    public int X() { return this.theX; }
    public int Y() { return this.theY; }
    public int MyColor() { return this.myColor; }
    public int TurnIndex() { return this.turnIndex; }
    public String MoveInfo() { return this.moveInfo; }

    public GoMoveClass(String encoded_move_val) {
        this.decodeMove(encoded_move_val);
    }

    private void decodeMove(String encoded_move_val) {
        this.theX = (encoded_move_val.charAt(0) - '0') * 10 + (encoded_move_val.charAt(1) - '0');
        this.theY = (encoded_move_val.charAt(2) - '0') * 10 + (encoded_move_val.charAt(3) - '0');
        this.myColor = encoded_move_val.charAt(4) - '0';
        this.turnIndex = (encoded_move_val.charAt(5) - '0') * 100 + (encoded_move_val.charAt(6) - '0') * 10 + (encoded_move_val.charAt(7) - '0');
        this.moveInfo = "(" + this.theX + ", " + this.theY + ") " + this.myColor + ", " + this.turnIndex;
    }
}
