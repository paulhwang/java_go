/*
 ******************************************************************************
 *                                       
 *  Copyright (c) 2018 phwang. All rights reserved.
 *
 ******************************************************************************
 */

package Phwang.Engine.Go;

import Phwang.Utils.AbendClass;

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

    public GoRootClass()
    {
        this.configObject = new GoConfigClass(this);
        this.boardObject = new GoBoardClass(this);
        this.gameObject = new GoGameClass(this);
        this.fightObject = new GoFightClass(this);
        this.parseObject = new GoParseClass(this);
    }

    private void debugIt(Boolean on_off_val, String str0_val, String str1_val)
    {
        if (on_off_val)
            this.logitIt(str0_val, str1_val);
    }

    private void logitIt(String str0_val, String str1_val)
    {
        AbendClass.phwangLogit(this.objectName() + "." + str0_val + "()", str1_val);
    }

    private void abendIt(String str0_val, String str1_val)
    {
        AbendClass.phwangAbend(this.objectName() + "." + str0_val + "()", str1_val);
    }
}
