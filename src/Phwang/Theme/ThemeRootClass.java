/*
 ******************************************************************************
 *                                       
 *  Copyright (c) 2018 phwang. All rights reserved.
 *
 ******************************************************************************
 */

package Phwang.Theme;

import Phwang.Utils.AbendClass;
import Phwang.Utils.ThreadMgr.ThreadMgrClass;
import Phwang.Theme.DTheme.DThemeClass;
import Phwang.Theme.RoomMgr.RoomMgrClass;
import Phwang.Theme.Utheme.UThemeClass;

public class ThemeRootClass {
    private String objectName() {return "ThemeRootClass";}

    private ThreadMgrClass threadMgrObject;
    private UThemeClass uThemeObject;
    private DThemeClass dThemeObject;
    private RoomMgrClass roomMgrObject;

    public ThreadMgrClass ThreadMgrObject() { return this.threadMgrObject; }
    public UThemeClass UThemeObject() { return this.uThemeObject; }
    public DThemeClass DThemeObject() { return this.dThemeObject; }
    public RoomMgrClass RoomMgrObject() { return this.roomMgrObject; }


    public ThemeRootClass() {
        this.debugIt(false, "ThemeRootClass", "init start");

        this.threadMgrObject = new ThreadMgrClass();
        this.uThemeObject = new UThemeClass(this);
        this.dThemeObject = new DThemeClass(this);
        this.roomMgrObject = new RoomMgrClass(this);
        this.UThemeObject().startThreads();
        this.DThemeObject().startThreads();
    }

    private void debugIt(Boolean on_off_val, String str0_val, String str1_val) {
        if (on_off_val)
            this.logitIt(str0_val, str1_val);
    }

    private void logitIt(String str0_val, String str1_val) {
        AbendClass.phwangLogit(this.objectName() + "." + str0_val + "()", str1_val);
    }

    public void abendIt(String str0_val, String str1_val) {
        AbendClass.phwangAbend(this.objectName() + "." + str0_val + "()", str1_val);
    }
}
