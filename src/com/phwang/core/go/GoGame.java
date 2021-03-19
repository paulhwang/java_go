/*
 ******************************************************************************
 *                                       
 *  Copyright (c) 2021 phwang. All rights reserved.
 *
 ******************************************************************************
 */

package com.phwang.core.go;

import com.phwang.core.utils.*;

public class GoGame {
    private String objectName() {return "GoGame";}
    private static final int GO_GAME_CLASS_MAX_MOVES_ARRAY_SIZE = 1024;

    private GoRoot goRoot_;
    private int totalMoves_;
    private int maxMove_;
    private int nextColor_;
    private Boolean thePassReceived = false;
    private Boolean theGameIsOver = false;
    private GoMove[] theMovesArray;

    public GoConfig goConfig() { return this.goRoot_.goConfig();  }
    public GoBoard goBoard() { return this.goRoot_.goBoard(); }
    public GoFight goFight() { return this.goRoot_.goFight(); }
    public int totalMoves() { return this.totalMoves_; }
    public int nextColor() { return this.nextColor_; }

    public GoGame(GoRoot go_root_val) {
        this.goRoot_ = go_root_val;
        this.theMovesArray = new GoMove[GO_GAME_CLASS_MAX_MOVES_ARRAY_SIZE];
    }

    private void resetGameObjectPartialData() {
        this.nextColor_ = GoDefine.GO_BLACK_STONE;
        this.thePassReceived = false;
        this.theGameIsOver = false;
    }

    public void addNewMoveAndFight(GoMove move_val) {
        this.debug(false, "AddNewMoveAndFight", "Move = " + move_val.moveInfo());

        if (move_val.TurnIndex() != this.totalMoves_ + 1) {
            this.log("AddNewMoveAndFight", "duplicated move received ***************** index= " + move_val.TurnIndex());
            return;
        }

        if (this.theGameIsOver) {
            this.abend("AddNewMoveAndFight", "theGameIsOver");
            return;
        }

        this.thePassReceived = false;
        this.goBoard().clearLastDeadStone();
        this.insertMoveToMoveList(move_val);
        this.goFight().enterBattle(move_val);
        this.nextColor_ = GoDefine.getOppositeColor(move_val.MyColor());
    }

    private void insertMoveToMoveList(GoMove move_val) {
        this.theMovesArray[this.totalMoves_] = move_val;
        this.totalMoves_++;
        this.maxMove_ = this.totalMoves_;
    }

    public void processBackwardMove() {
        this.debug(true, "ProcessBackwardMove", "");

        this.thePassReceived = false;
        if (this.totalMoves_ <= this.goConfig().handicapPoint()) {
            return;
        }
        this.totalMoves_--;
        this.processTheWholeMoveList();
    }

    public void processDoubleBackwardMove() {
        this.debug(true, "ProcessDoubleBackwardMove", "");

        this.thePassReceived = false;
        if (this.totalMoves_ <= this.goConfig().handicapPoint()) {
            return;
        }
        this.totalMoves_ = this.goConfig().handicapPoint();
        this.processTheWholeMoveList();
    }

    public void processForwardMove() {
        this.debug(true, "ProcessForwardMove", "");

        this.thePassReceived = false;
        if (this.totalMoves_ > this.maxMove_) {
            this.abend("ProcessForwardMove", "totalMoves > maxMove=");
            return;
        }
        if (this.totalMoves_ == this.maxMove_) {
            return;
        }
        this.totalMoves_++;
        this.processTheWholeMoveList();
    }

    public void processDoubleForwardMove() {
        this.debug(true, "ProcessDoubleForwardMove", "");

        this.thePassReceived = false;
        if (this.totalMoves_ > this.maxMove_) {
            this.abend("ProcessDoubleForwardMove", "totalMoves > maxMove=");
            return;
        }
        if (this.totalMoves_ == this.maxMove_) {
            return;
        }
        this.totalMoves_ = this.maxMove_;
        this.processTheWholeMoveList();
    }

    private void processTheWholeMoveList() {
        this.goBoard().resetBoardObjectData();
        this.goFight().resetEngineObjectData();
        this.resetGameObjectPartialData();

        int i = 0;
        while (i < this.totalMoves_) {
        	GoMove move = this.theMovesArray[i];
            this.goFight().enterBattle(move);
            this.nextColor_ = GoDefine.getOppositeColor(move.MyColor());
            i += 1;
        }
    }
    
    private void debug(Boolean on_off, String s0, String s1) { if (on_off) this.log(s0, s1); }
    private void log(String s0, String s1) { Abend.log(this.objectName() + "." + s0 + "()", s1); }
    public void abend(String s0, String s1) { Abend.abend(this.objectName() + "." + s0 + "()", s1); }
}
