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

    private GoConfig configObject;
    private GoBoard boardObject;
    private GoGame gameObject;
    private GoParse parseObject;
    private GoFight fightObject;

    public GoConfig ConfigObject() { return this.configObject; }
    public GoBoard BoardObject() { return this.boardObject; }
    public GoGame GameObject() { return this.gameObject; }
    public GoParse ParseObject() { return this.parseObject; }
    public GoFight FightObject() { return this.fightObject; }

    public GoRoot() {
        this.configObject = new GoConfig(this);
        this.boardObject = new GoBoard(this);
        this.gameObject = new GoGame(this);
        this.fightObject = new GoFight(this);
        this.parseObject = new GoParse(this);
    }

    public String doSetup(String input_data_val) {
        this.configObject.configIt(input_data_val);
        return "";
    }

    public String processInputData(String input_data_val) {
        this.parseObject.parseInputData(input_data_val);
        this.boardObject.encodeBoard();
        this.debug(false, "transmitBoardData", this.boardObject.BoardOutputBuffer());
        return this.boardObject.BoardOutputBuffer();
    }
    
    private void debug(Boolean on_off, String s0, String s1) { if (on_off) this.log(s0, s1); }
    private void log(String s0, String s1) { AbendClass.log(this.objectName() + "." + s0 + "()", s1); }
    public void abend(String s0, String s1) { AbendClass.abend(this.objectName() + "." + s0 + "()", s1); }
}
