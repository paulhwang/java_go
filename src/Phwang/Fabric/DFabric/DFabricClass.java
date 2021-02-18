package Phwang.Fabric.DFabric;

import Phwang.Fabric.FabricRootClass;
import Phwang.Utils.AbendClass;

public class DFabricClass {
    private String objectName = "DFabricClass";
    
    public DFabricClass(FabricRootClass fabric_root_class_val)
    {
        this.debugIt(true, "DFabricClass", "init start");
        //this.fabricRootObject = fabric_root_class_val;
        //this.dFabricParserObject = new DFabricParserClass(this);
        //this.binderObject = new PhwangUtils.BinderClass(this.objectName);
        //this.binderObject.BindAsTcpServer(Protocols.FabricFrontEndProtocolClass.LINK_MGR_PROTOCOL_TRANSPORT_PORT_NUMBER);

        //this.receiveThread = new Thread(this.receiveThreadFunc);
        //this.receiveThread.Start();

        this.debugIt(true, "DFabricClass", "init done");
    }

    private void debugIt(Boolean on_off_val, String str0_val, String str1_val)
    {
        if (on_off_val)
            this.logitIt(str0_val, str1_val);
    }

    private void logitIt(String str0_val, String str1_val)
    {
        AbendClass.phwangLogit(this.objectName + "." + str0_val + "()", str1_val);
    }

    private void abendIt(String str0_val, String str1_val)
    {
        AbendClass.phwangAbend(this.objectName + "." + str0_val + "()", str1_val);
    }

}
