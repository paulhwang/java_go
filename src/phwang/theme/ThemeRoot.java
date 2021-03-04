/*
 ******************************************************************************
 *                                       
 *  Copyright (c) 2021 phwang. All rights reserved.
 *
 ******************************************************************************
 */

package phwang.theme;

import phwang.utils.*;

public class ThemeRoot {
    private String objectName() {return "ThemeRoot";}

    private ThreadMgrClass threadMgrObject;
    private ThemeUBinder uThemeObject;
    private ThemeDBinder dThemeObject;
    private ThemeRoomMgr roomMgrObject;

    public ThreadMgrClass ThreadMgrObject() { return this.threadMgrObject; }
    public ThemeUBinder UThemeObject() { return this.uThemeObject; }
    public ThemeDBinder DThemeObject() { return this.dThemeObject; }
    public ThemeRoomMgr RoomMgrObject() { return this.roomMgrObject; }


    public ThemeRoot() {
        this.debug(false, "ThemeRoot", "init start");

        this.threadMgrObject = new ThreadMgrClass();
        this.uThemeObject = new ThemeUBinder(this);
        this.dThemeObject = new ThemeDBinder(this);
        this.roomMgrObject = new ThemeRoomMgr(this);
        this.UThemeObject().startThreads();
        this.DThemeObject().startThreads();
    }
    
    private void debug(Boolean on_off, String s0, String s1) { if (on_off) this.log(s0, s1); }
    private void log(String s0, String s1) { AbendClass.log(this.objectName() + "." + s0 + "()", s1); }
    public void abend(String s0, String s1) { AbendClass.abend(this.objectName() + "." + s0 + "()", s1); }
}
