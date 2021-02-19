package Phwang.Main;

import Phwang.Engine.EngineRootClass;
import Phwang.Fabric.FabricRootClass;
import Phwang.FrontEnd.FrontEndRootClass;
import Phwang.Models.ModelRootClass;
import Phwang.Theme.ThemeRootClass;
import Phwang.Utils.AbendClass;

public class GlobalVariableClass {
    private String objectName() {return "GlobalVariableClass";}
    
    static public FrontEndRootClass frontEndRootObject;
    static public FabricRootClass fabricRootObject;
    static public ThemeRootClass themeRootObject;
    static public EngineRootClass engineRootObject;
    static public ModelRootClass modelRootObject;

    public GlobalVariableClass() {
        this.debugIt(true, "EngineRootClass", "init start");
	}
    
    public static void Initilization()
    {
        if (frontEndRootObject == null)
        {
            fabricRootObject = new FabricRootClass();
            frontEndRootObject = new FrontEndRootClass();
            themeRootObject = new ThemeRootClass();
            engineRootObject = new EngineRootClass();
            modelRootObject = new ModelRootClass();
        }
    }
    
    public static FrontEndRootClass getGoRoot()
    {
        Initilization();
        return frontEndRootObject;
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

