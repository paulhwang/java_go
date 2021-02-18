package Phwang.Main;

import Phwang.Engine.EngineRootClass;
import Phwang.Fabric.FabricRootClass;
import Phwang.FrontEnd.FrontEndRootClass;
import Phwang.Models.ModelRootClass;
import Phwang.Theme.ThemeRootClass;
import Phwang.Utils.AbendClass;

public class GlobalVariableClass {
    static public FrontEndRootClass frontEndRootObject;
    static public FabricRootClass fabricRootObject;
    static public ThemeRootClass themeRootObject;
    static public EngineRootClass engineRootObject;
    static public ModelRootClass modelRootObject;

    public GlobalVariableClass () {
		AbendClass.phwangLogit("GlobalVariableClass", "init");
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

}

