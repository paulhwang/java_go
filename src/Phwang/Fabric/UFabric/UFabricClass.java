package Phwang.Fabric.UFabric;

import Phwang.Fabric.FabricRootClass;
import Phwang.Utils.AbendClass;

public class UFabricClass {
    private String objectName() {return "UFabricClass";}
    
    public UFabricClass(FabricRootClass fabric_root_class_val)
    {
        this.debugIt(true, "UFabricClass", "init start");
        //this.fabricRootObject = fabric_root_class_val;
        //this.uFabricParserObject = new UFabricParserClass(this);
        //this.binderObject = new PhwangUtils.BinderClass(this.objectName);
        //this.binderObject.BindAsTcpServer(Protocols.FabricThemeProtocolClass.GROUP_ROOM_PROTOCOL_TRANSPORT_PORT_NUMBER);

        //this.receiveThread = new Thread(this.receiveThreadFunc);
        //this.receiveThread.Start();

        this.debugIt(true, "UFabricClass", "init done");
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
