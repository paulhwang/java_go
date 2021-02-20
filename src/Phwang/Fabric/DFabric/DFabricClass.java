/*
 ******************************************************************************
 *                                       
 *  Copyright (c) 2018 phwang. All rights reserved.
 *
 ******************************************************************************
 */

package Phwang.Fabric.DFabric;

import Phwang.Utils.AbendClass;
import Phwang.Utils.Binder.BinderClass;
import Phwang.Protocols.FabricFrontEndProtocolClass;
import Phwang.Fabric.FabricRootClass;
import Phwang.Fabric.UFabric.UFabricClass;

public class DFabricClass {
    private String objectName() {return "DFabricClass";}
    
    private FabricRootClass fabricRootObject;
    private DFabricParserClass dFabricParserObject;
    public BinderClass binderObject;
    private Thread receiveThread;
    private DFabricReceiveRunnable receiveRunable;

    public FabricRootClass FabricRootObject() { return this.fabricRootObject; }
    
    public DFabricClass(FabricRootClass fabric_root_class_val) {
        this.debugIt(false, "DFabricClass", "init start");
        
        this.fabricRootObject = fabric_root_class_val;
        this.dFabricParserObject = new DFabricParserClass(this);
        this.binderObject = new BinderClass(this.objectName());
        
        //this.binderObject.BindAsTcpServer(FabricFrontEndProtocolClass.LINK_MGR_PROTOCOL_TRANSPORT_PORT_NUMBER);
    }

    public void startThreads() {
        this.receiveRunable = new DFabricReceiveRunnable(this);
        this.receiveThread = new Thread(this.receiveRunable);
        this.receiveThread.start();
    }

    public void dFabricRreceiveThreadFunc() {
        this.debugIt(true, "dFabricRreceiveThreadFunc", "start thread");

        String data;
        while (true) {
            data = this.binderObject.ReceiveData();
            if (data == null) {
                this.abendIt("receiveThreadFunc", "null data");
                continue;
            }
            this.debugIt(true, "receiveThreadFunc", "data = " + data);
            this.dFabricParserObject.parseInputPacket(data);
        }
    }

    public void TransmitData(String data_val) {
        this.binderObject.TransmitData(data_val);
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

class DFabricReceiveRunnable implements Runnable
{
	DFabricClass theDFabricObject;
	
	public DFabricReceiveRunnable(DFabricClass d_fabric_object_val) {
		this.theDFabricObject = d_fabric_object_val;
	}
	
	public void run() {
		this.theDFabricObject.dFabricRreceiveThreadFunc();
	}
}
