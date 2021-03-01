/*
 ******************************************************************************
 *                                       
 *  Copyright (c) 2018 phwang. All rights reserved.
 *
 ******************************************************************************
 */

package phwang.go;

import phwang.utils.*;

public class GoRootClass {
    private String objectName() {return "GoRootClass";}

    private GoConfigClass configObject;
    private GoBoardClass boardObject;
    private GoGameClass gameObject;
    private GoParseClass parseObject;
    private GoFightClass fightObject;

    public GoConfigClass ConfigObject() { return this.configObject; }
    public GoBoardClass BoardObject() { return this.boardObject; }
    public GoGameClass GameObject() { return this.gameObject; }
    public GoParseClass ParseObject() { return this.parseObject; }
    public GoFightClass FightObject() { return this.fightObject; }

    public GoRootClass() {
        this.configObject = new GoConfigClass(this);
        this.boardObject = new GoBoardClass(this);
        this.gameObject = new GoGameClass(this);
        this.fightObject = new GoFightClass(this);
        this.parseObject = new GoParseClass(this);
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
