/*
 ******************************************************************************
 *                                       
 *  Copyright (c) 2018 phwang. All rights reserved.
 *
 ******************************************************************************
 */

package Phwang.Theme.DTheme;

import Phwang.Utils.AbendClass;
import Phwang.Theme.ThemeRootClass;
import Phwang.Theme.Utheme.UThemeClass;
import Phwang.Theme.RoomMgr.RoomMgrClass;

public class DThemeParserClass {
    private String objectName() {return "DThemeParserClass";}
    
    private DThemeClass dThemeObject;

    public ThemeRootClass ThemeRootObject() { return this.dThemeObject.ThemeRootObject(); }
    public UThemeClass UThemeObject() { return this.ThemeRootObject().UThemeObject(); }
    public RoomMgrClass RoomMgrObject() { return this.ThemeRootObject().RoomMgrObject(); }

    public DThemeParserClass(DThemeClass d_theme_object_val)
    {
        this.debugIt(true, "DThemeParserClass", "init start");
        this.dThemeObject = d_theme_object_val;
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

    public void abendIt(String str0_val, String str1_val)
    {
        AbendClass.phwangAbend(this.objectName() + "." + str0_val + "()", str1_val);
    }
}
