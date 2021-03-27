/*
 ******************************************************************************
 *                                       
 *  Copyright (c) 2021 phwang. All rights reserved.
 *
 ******************************************************************************
 */

package com.phwang.core.go;

import com.phwang.core.utils.*;

public class GoBoard {
    private String objectName() {return "GoBoard";}
    
    private int[][] theBoardArray;
    private int[][] theMarkedBoardArray;
    private String theBoardOutputBuffer;
    private int theBlackCapturedStones;
    private int theWhiteCapturedStones;
    private int theLastDeadX;
    private int theLastDeadY;
    private GoRoot goRoot_;

    public GoConfig goConfig() { return this.goRoot_.goConfig(); }
    public GoGame goGame() { return this.goRoot_.goGame(); }
    public String boardOutputBuffer() { return this.theBoardOutputBuffer; }
    public int boardArray(int x_val, int y_val) { return this.theBoardArray[x_val][y_val]; }
    public void addBlackCapturedStones(int val) { this.theBlackCapturedStones += val; }
    public void addWhiteCapturedStones(int val) { this.theWhiteCapturedStones += val; }
    public void setBoardArray(int x_val, int y_val, int data_val) { this.theBoardArray[x_val][y_val] = data_val; }
    public void setLastDeadStone(int x_val, int y_val) { this.theLastDeadX = x_val; this.theLastDeadY = y_val; }

    public GoBoard(GoRoot root_val) {
        this.goRoot_ = root_val;
        this.theBoardArray = new int[GoDefine.MAX_BOARD_SIZE] [GoDefine.MAX_BOARD_SIZE];
        this.theMarkedBoardArray = new int[GoDefine.MAX_BOARD_SIZE] [GoDefine.MAX_BOARD_SIZE];
        this.resetBoardObjectData();
    }

    private final char GO_PROTOCOL_GAME_INFO = 'G';

    public void encodeBoard() {
        this.theBoardOutputBuffer = "";
        this.theBoardOutputBuffer = this.theBoardOutputBuffer + GO_PROTOCOL_GAME_INFO;
        this.theBoardOutputBuffer = this.theBoardOutputBuffer + Encoders.iEncodeRaw(this.goGame().totalMoves(), GoDefine.TOTAL_MOVE_SIZE);
        this.theBoardOutputBuffer = this.theBoardOutputBuffer + Encoders.iEncodeRaw1(this.goGame().nextColor());

        int board_size = this.goConfig().boardSize();
        for (int i = 0; i < board_size; i++) {
            for (int j = 0; j < board_size; j++) {
                char c = '0';
                switch (this.theBoardArray[i][j]) {
                    case 1: c = '1'; break;
                    case 2: c = '2'; break;
                }
                this.theBoardOutputBuffer = this.theBoardOutputBuffer + c;
            }
        }

        this.theBoardOutputBuffer = this.theBoardOutputBuffer + Encoders.iEncodeRaw3(this.theBlackCapturedStones);
        this.theBoardOutputBuffer = this.theBoardOutputBuffer + Encoders.iEncodeRaw3(this.theWhiteCapturedStones);

        this.theBoardOutputBuffer = this.theBoardOutputBuffer + Encoders.iEncodeRaw2(this.theLastDeadX);
        this.theBoardOutputBuffer = this.theBoardOutputBuffer + Encoders.iEncodeRaw2(this.theLastDeadY);

        this.debug(false, "encodeBoard", this.theBoardOutputBuffer);
    }

    public void addStoneToBoard(int x_val, int y_val, int color_val) {
        if (!this.goConfig().isValidCoordinates(x_val, y_val)) {
            this.log("addStoneToBoard", "bad coordinate: " + x_val + " " + y_val);
            this.abend("addStoneToBoard", "bad coordinate: " + x_val + " " + y_val);
            return;
        }

        this.theBoardArray[x_val][y_val] = color_val;
    }

    private Boolean isEmptySpace(int x_val, int y_val) {
        if (!this.goRoot_.goConfig().isValidCoordinates(x_val, y_val)) {
            return false;
        }
        if (this.theBoardArray[x_val][y_val] != GoDefine.GO_EMPTY_STONE) {
            return false;
        }
        return true;
    }

    public Boolean stoneHasAir(int x_val, int y_val) {
        if (this.isEmptySpace(x_val, y_val - 1)) {
            return true;
        }
        if (this.isEmptySpace(x_val, y_val + 1)) {
            return true;
        }
        if (this.isEmptySpace(x_val - 1, y_val)) {
            return true;
        }
        if (this.isEmptySpace(x_val + 1, y_val)) {
            return true;
        }
        return false;
    }

    public void resetBoardObjectData() {
        int board_size = this.goConfig().boardSize();
        for (int i = 0; i < board_size; i++) {
            for (int j = 0; j < board_size; j++) {
                this.theBoardArray[i][j] = GoDefine.GO_EMPTY_STONE;
                this.theMarkedBoardArray[i][j] = GoDefine.GO_EMPTY_STONE;
            }
        }
        this.theBlackCapturedStones = 0;
        this.theWhiteCapturedStones = 0;
        this.clearLastDeadStone();
    }

    public void resetMarkedBoardObjectData() {

    }

    public void clearLastDeadStone() {
        this.theLastDeadX = 19;
        this.theLastDeadY = 19;
    }
    
    private void debug(Boolean on_off, String s0, String s1) { if (on_off) this.log(s0, s1); }
    private void log(String s0, String s1) { Abend.log(this.objectName() + "." + s0 + "()", s1); }
    public void abend(String s0, String s1) { Abend.abend(this.objectName() + "." + s0 + "()", s1); }
}
