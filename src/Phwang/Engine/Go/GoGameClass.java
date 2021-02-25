/*
 ******************************************************************************
 *                                       
 *  Copyright (c) 2018 phwang. All rights reserved.
 *
 ******************************************************************************
 */

package Phwang.Engine.Go;

import Phwang.Utils.AbendClass;

public class GoGameClass {
    private String objectName() {return "GoGameClass";}
    private static final int GO_GAME_CLASS_MAX_MOVES_ARRAY_SIZE = 1024;

    private GoRootClass theRootObject;
    private int theTotalMoves;
    private int theMaxMove;
    private int theNextColor;
    private Boolean thePassReceived = false;
    private Boolean theGameIsOver = false;
    private GoMoveClass[] theMovesArray;

    public GoConfigClass ConfigObject() { return this.theRootObject.ConfigObject();  }
    public GoBoardClass BoardObject() { return this.theRootObject.BoardObject(); }
    public GoFightClass FightObject() { return this.theRootObject.FightObject(); }
    public int TotalMoves() { return this.theTotalMoves; }
    public int NextColor() { return this.theNextColor; }

    public GoGameClass(GoRootClass go_root_object_val) {
        this.theRootObject = go_root_object_val;
        this.theMovesArray = new GoMoveClass[GO_GAME_CLASS_MAX_MOVES_ARRAY_SIZE];
    }

    private void resetGameObjectPartialData() {
        this.theNextColor = GoDefineClass.GO_BLACK_STONE;
        this.thePassReceived = false;
        this.theGameIsOver = false;
    }

    public void AddNewMoveAndFight(GoMoveClass move_val) {
        this.debugIt(true, "AddNewMoveAndFight", "Move = " + move_val.MoveInfo());

        if (move_val.TurnIndex() != this.theTotalMoves + 1) {
            this.logitIt("AddNewMoveAndFight", "duplicated move received *****************");
            return;
        }

        if (this.theGameIsOver) {
            this.abendIt("AddNewMoveAndFight", "theGameIsOver");
            return;
        }

        this.thePassReceived = false;
        this.BoardObject().ClearLastDeadStone();
        this.insertMoveToMoveList(move_val);
        this.FightObject().EnterBattle(move_val);
        this.theNextColor = GoDefineClass.GetOppositeColor(move_val.MyColor());
    }

    private void insertMoveToMoveList(GoMoveClass move_val) {
        this.theMovesArray[this.theTotalMoves] = move_val;
        this.theTotalMoves++;
        this.theMaxMove = this.theTotalMoves;
    }

    public void ProcessBackwardMove() {
        this.debugIt(true, "ProcessBackwardMove", "");

        this.thePassReceived = false;
        if (this.theTotalMoves <= this.ConfigObject().HandicapPoint()) {
            return;
        }
        this.theTotalMoves--;
        this.processTheWholeMoveList();
    }

    public void ProcessDoubleBackwardMove() {
        this.debugIt(true, "ProcessDoubleBackwardMove", "");

        this.thePassReceived = false;
        if (this.theTotalMoves <= this.ConfigObject().HandicapPoint()) {
            return;
        }
        this.theTotalMoves = this.ConfigObject().HandicapPoint();
        this.processTheWholeMoveList();
    }

    public void ProcessForwardMove() {
        this.debugIt(true, "ProcessForwardMove", "");

        this.thePassReceived = false;
        if (this.theTotalMoves > this.theMaxMove) {
            this.abendIt("ProcessForwardMove", "totalMoves > maxMove=");
            return;
        }
        if (this.theTotalMoves == this.theMaxMove) {
            return;
        }
        this.theTotalMoves++;
        this.processTheWholeMoveList();
    }

    public void ProcessDoubleForwardMove() {
        this.debugIt(true, "ProcessDoubleForwardMove", "");

        this.thePassReceived = false;
        if (this.theTotalMoves > this.theMaxMove) {
            this.abendIt("ProcessDoubleForwardMove", "totalMoves > maxMove=");
            return;
        }
        if (this.theTotalMoves == this.theMaxMove) {
            return;
        }
        this.theTotalMoves = this.theMaxMove;
        this.processTheWholeMoveList();
    }

    private void processTheWholeMoveList() {
        this.BoardObject().ResetBoardObjectData();
        this.FightObject().ResetEngineObjectData();
        this.resetGameObjectPartialData();

        int i = 0;
        while (i < this.theTotalMoves) {
            GoMoveClass move = this.theMovesArray[i];
            this.FightObject().EnterBattle(move);
            this.theNextColor = GoDefineClass.GetOppositeColor(move.MyColor());
            i += 1;
        }
    }

    private void debugIt(Boolean on_off_val, String str0_val, String str1_val) { if (on_off_val) this.logitIt(str0_val, str1_val); }
    private void logitIt(String str0_val, String str1_val) { AbendClass.phwangLogit(this.objectName() + "." + str0_val + "()", str1_val); }
    public void abendIt(String str0_val, String str1_val) { AbendClass.phwangAbend(this.objectName() + "." + str0_val + "()", str1_val); }
}
