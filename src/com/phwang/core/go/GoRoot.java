/*
 ******************************************************************************
 *                                       
 *  Copyright (c) 2021 phwang. All rights reserved.
 *
 ******************************************************************************
 */

package com.phwang.core.go;

import com.phwang.core.utils.Abend;

public class GoRoot {
    private String objectName() {return "GoRoot";}

    private GoConfig goConfig_;
    private GoBoard goBoard_;
    private GoGame goGame_;
    private GoParse goParse_;
    private GoFight goFight_;

    public GoConfig goConfig() { return this.goConfig_; }
    public GoBoard goBoard() { return this.goBoard_; }
    public GoGame goGame() { return this.goGame_; }
    public GoParse goParse() { return this.goParse_; }
    public GoFight goFight() { return this.goFight_; }

    public GoRoot() {
        this.goConfig_ = new GoConfig(this);
        this.goBoard_ = new GoBoard(this);
        this.goGame_ = new GoGame(this);
        this.goFight_ = new GoFight(this);
        this.goParse_ = new GoParse(this);
    }

    public String doSetup(String input_data_val) {
        this.goConfig().configIt(input_data_val);
        return "";
    }

    public String processInputData(String input_data_val) {
        this.goParse().parseInputData(input_data_val);
        this.goBoard().encodeBoard();
        this.debug(false, "transmitBoardData", this.goBoard().boardOutputBuffer());
        return this.goBoard().boardOutputBuffer();
    }
    
    private void debug(Boolean on_off, String s0, String s1) { if (on_off) this.log(s0, s1); }
    private void log(String s0, String s1) { this.logIt(this.objectName() + "." + s0 + "()", s1); }
    public void abend(String s0, String s1) { this.abendIt(this.objectName() + "." + s0 + "()", s1); }

    private Boolean debug_on = true;
    public void logIt(String s0, String s1) { if (this.debug_on) Abend.log(s0, s1); }
    public void abendIt(String s0, String s1) { if (this.debug_on) Abend.abend(s0, s1); }
}
