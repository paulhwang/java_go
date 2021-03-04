/*
 ******************************************************************************
 *                                       
 *  Copyright (c) 2018 phwang. All rights reserved.
 *
 ******************************************************************************
 */

package phwang.theme;

import phwang.utils.*;

public class ThemeRootClass {
    private String objectName() {return "ThemeRootClass";}

    private ThreadMgrClass threadMgrObject;
    private ThemeUBinder uThemeObject;
    private ThemeDBinder dThemeObject;
    private RoomMgrClass roomMgrObject;

    public ThreadMgrClass ThreadMgrObject() { return this.threadMgrObject; }
    public ThemeUBinder UThemeObject() { return this.uThemeObject; }
    public ThemeDBinder DThemeObject() { return this.dThemeObject; }
    public RoomMgrClass RoomMgrObject() { return this.roomMgrObject; }


    public ThemeRootClass() {
        this.debug(false, "ThemeRootClass", "init start");

        this.threadMgrObject = new ThreadMgrClass();
        this.uThemeObject = new ThemeUBinder(this);
        this.dThemeObject = new ThemeDBinder(this);
        this.roomMgrObject = new RoomMgrClass(this);
        this.UThemeObject().startThreads();
        this.DThemeObject().startThreads();
    }
    
    private void debug(Boolean on_off, String s0, String s1) { if (on_off) this.log(s0, s1); }
    private void log(String s0, String s1) { AbendClass.log(this.objectName() + "." + s0 + "()", s1); }
    public void abend(String s0, String s1) { AbendClass.abend(this.objectName() + "." + s0 + "()", s1); }
}
