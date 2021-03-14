/*
 ******************************************************************************
 *                                       
 *  Copyright (c) 2021 phwang. All rights reserved.
 *
 ******************************************************************************
 */

package com.phwang.core.theme;

import com.phwang.core.utils.*;

public class ThemeRoot {
    private String objectName() {return "ThemeRoot";}

    private ThreadMgr threadMgr_;
    private ThemeUBinder themeUBinder_;
    private ThemeDBinder themeDBinder_;
    private ThemeRoomMgr roomMgr_;
    private ThemeDParser themeDParser_ ;
    private ThemeUParser themeUParser_ ;
    
    public ThreadMgr threadMgr() { return this.threadMgr_; }
    public ThemeUBinder themeUBinder() { return this.themeUBinder_; }
    public ThemeDBinder themeDBinder() { return this.themeDBinder_; }
    public ThemeUParser themeUParser() { return this.themeUParser_ ; }
    public ThemeDParser themeDParser() { return this.themeDParser_ ; }
    public ThemeRoomMgr roomMgr() { return this.roomMgr_; }


    public ThemeRoot() {
        this.debug(false, "ThemeRoot", "init start");

        this.threadMgr_ = new ThreadMgr();
        this.themeUBinder_ = new ThemeUBinder(this);
        this.themeDBinder_ = new ThemeDBinder(this);
        this.themeDParser_ = new ThemeDParser(this);
        this.themeUParser_ = new ThemeUParser(this);
        this.roomMgr_ = new ThemeRoomMgr(this);
        
        this.themeUBinder().startThreads();
        this.themeDBinder().startThreads();
    }
    
    private void debug(Boolean on_off, String s0, String s1) { if (on_off) this.log(s0, s1); }
    private void log(String s0, String s1) { this.logIt(this.objectName() + "." + s0 + "()", s1); }
    public void abend(String s0, String s1) { this.abendIt(this.objectName() + "." + s0 + "()", s1); }

    private Boolean debug_on = true;
    public void logIt(String s0, String s1) { if (this.debug_on) Abend.log(s0, s1); }
    public void abendIt(String s0, String s1) { if (this.debug_on) Abend.abend(s0, s1); }
}
