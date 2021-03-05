/*
 ******************************************************************************
 *                                       
 *  Copyright (c) 2021 phwang. All rights reserved.
 *
 ******************************************************************************
 */

package phwang.go;

import phwang.utils.*;

public class GoRoot {
    private String objectName() {return "GoRoot";}

    private GoConfig goConfig_;
    private GoBoard goBoard_;
    private GoGame goGame_;
    private GoParse parseObject;
    private GoFight fightObject;

    public GoConfig goConfig() { return this.goConfig_; }
    public GoBoard goBoard() { return this.goBoard_; }
    public GoGame GameObject() { return this.goGame_; }
    public GoParse ParseObject() { return this.parseObject; }
    public GoFight FightObject() { return this.fightObject; }

    public GoRoot() {
        this.goConfig_ = new GoConfig(this);
        this.goBoard_ = new GoBoard(this);
        this.goGame_ = new GoGame(this);
        this.fightObject = new GoFight(this);
        this.parseObject = new GoParse(this);
    }

    public String doSetup(String input_data_val) {
        this.goConfig().configIt(input_data_val);
        return "";
    }

    public String processInputData(String input_data_val) {
        this.parseObject.parseInputData(input_data_val);
        this.goBoard().encodeBoard();
        this.debug(false, "transmitBoardData", this.goBoard().BoardOutputBuffer());
        return this.goBoard().BoardOutputBuffer();
    }
    
    private void debug(Boolean on_off, String s0, String s1) { if (on_off) this.log(s0, s1); }
    private void log(String s0, String s1) { AbendClass.log(this.objectName() + "." + s0 + "()", s1); }
    public void abend(String s0, String s1) { AbendClass.abend(this.objectName() + "." + s0 + "()", s1); }
}
