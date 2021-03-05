/*
 ******************************************************************************
 *                                       
 *  Copyright (c) 2021 phwang. All rights reserved.
 *
 ******************************************************************************
 */

package phwang.go;

import phwang.utils.*;

public class GoGame {
    private String objectName() {return "GoGame";}
    private static final int GO_GAME_CLASS_MAX_MOVES_ARRAY_SIZE = 1024;

    private GoRoot goRoot_;
    private int theTotalMoves;
    private int theMaxMove;
    private int theNextColor;
    private Boolean thePassReceived = false;
    private Boolean theGameIsOver = false;
    private GoMove[] theMovesArray;

    public GoConfig goConfig() { return this.goRoot_.goConfig();  }
    public GoBoard goBoard() { return this.goRoot_.goBoard(); }
    public GoFight goFight() { return this.goRoot_.goFight(); }
    public int TotalMoves() { return this.theTotalMoves; }
    public int NextColor() { return this.theNextColor; }

    public GoGame(GoRoot go_root_val) {
        this.goRoot_ = go_root_val;
        this.theMovesArray = new GoMove[GO_GAME_CLASS_MAX_MOVES_ARRAY_SIZE];
    }

    private void resetGameObjectPartialData() {
        this.theNextColor = GoDefine.GO_BLACK_STONE;
        this.thePassReceived = false;
        this.theGameIsOver = false;
    }

    public void addNewMoveAndFight(GoMove move_val) {
        this.debug(false, "AddNewMoveAndFight", "Move = " + move_val.MoveInfo());

        if (move_val.TurnIndex() != this.theTotalMoves + 1) {
            this.log("AddNewMoveAndFight", "duplicated move received *****************");
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
        this.theNextColor = GoDefine.getOppositeColor(move_val.MyColor());
    }

    private void insertMoveToMoveList(GoMove move_val) {
        this.theMovesArray[this.theTotalMoves] = move_val;
        this.theTotalMoves++;
        this.theMaxMove = this.theTotalMoves;
    }

    public void processBackwardMove() {
        this.debug(true, "ProcessBackwardMove", "");

        this.thePassReceived = false;
        if (this.theTotalMoves <= this.goConfig().handicapPoint()) {
            return;
        }
        this.theTotalMoves--;
        this.processTheWholeMoveList();
    }

    public void processDoubleBackwardMove() {
        this.debug(true, "ProcessDoubleBackwardMove", "");

        this.thePassReceived = false;
        if (this.theTotalMoves <= this.goConfig().handicapPoint()) {
            return;
        }
        this.theTotalMoves = this.goConfig().handicapPoint();
        this.processTheWholeMoveList();
    }

    public void processForwardMove() {
        this.debug(true, "ProcessForwardMove", "");

        this.thePassReceived = false;
        if (this.theTotalMoves > this.theMaxMove) {
            this.abend("ProcessForwardMove", "totalMoves > maxMove=");
            return;
        }
        if (this.theTotalMoves == this.theMaxMove) {
            return;
        }
        this.theTotalMoves++;
        this.processTheWholeMoveList();
    }

    public void processDoubleForwardMove() {
        this.debug(true, "ProcessDoubleForwardMove", "");

        this.thePassReceived = false;
        if (this.theTotalMoves > this.theMaxMove) {
            this.abend("ProcessDoubleForwardMove", "totalMoves > maxMove=");
            return;
        }
        if (this.theTotalMoves == this.theMaxMove) {
            return;
        }
        this.theTotalMoves = this.theMaxMove;
        this.processTheWholeMoveList();
    }

    private void processTheWholeMoveList() {
        this.goBoard().resetBoardObjectData();
        this.goFight().resetEngineObjectData();
        this.resetGameObjectPartialData();

        int i = 0;
        while (i < this.theTotalMoves) {
        	GoMove move = this.theMovesArray[i];
            this.goFight().enterBattle(move);
            this.theNextColor = GoDefine.getOppositeColor(move.MyColor());
            i += 1;
        }
    }
    
    private void debug(Boolean on_off, String s0, String s1) { if (on_off) this.log(s0, s1); }
    private void log(String s0, String s1) { AbendClass.log(this.objectName() + "." + s0 + "()", s1); }
    public void abend(String s0, String s1) { AbendClass.abend(this.objectName() + "." + s0 + "()", s1); }
}
