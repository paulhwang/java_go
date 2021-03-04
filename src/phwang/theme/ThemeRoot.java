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
    private ThemeDBinder themeDBinder_;
    private ThemeRoomMgr roomMgr_;

    public ThreadMgrClass ThreadMgrObject() { return this.threadMgrObject; }
    public ThemeUBinder UThemeObject() { return this.uThemeObject; }
    public ThemeDBinder themeDBinder() { return this.themeDBinder_; }
    public ThemeRoomMgr roomMgr() { return this.roomMgr_; }


    public ThemeRoot() {
        this.debug(false, "ThemeRoot", "init start");

        this.threadMgrObject = new ThreadMgrClass();
        this.uThemeObject = new ThemeUBinder(this);
        this.themeDBinder_ = new ThemeDBinder(this);
        this.roomMgr_ = new ThemeRoomMgr(this);
        this.UThemeObject().startThreads();
        this.themeDBinder().startThreads();
    }
    
    private void debug(Boolean on_off, String s0, String s1) { if (on_off) this.log(s0, s1); }
    private void log(String s0, String s1) { AbendClass.log(this.objectName() + "." + s0 + "()", s1); }
    public void abend(String s0, String s1) { AbendClass.abend(this.objectName() + "." + s0 + "()", s1); }
}
