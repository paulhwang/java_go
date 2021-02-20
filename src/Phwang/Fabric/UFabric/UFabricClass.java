/*
 ******************************************************************************
 *                                       
 *  Copyright (c) 2018 phwang. All rights reserved.
 *
 ******************************************************************************
 */

package Phwang.Fabric.UFabric;

import Phwang.Utils.AbendClass;
import Phwang.Utils.Binder.BinderClass;
import Phwang.Fabric.FabricRootClass;
import Phwang.Theme.Utheme.UThemeClass;

public class UFabricClass {
    private String objectName() {return "UFabricClass";}
    
    private static final String FABRIC_THEME_PROTOCOL_COMMAND_IS_SETUP_ROOM = "R";
    
    ///////////////#define FABRIC_THEME_PROTOCOL_RESPOND_IS_SETUP_ROOM 'r'
    //////////////////#define FABRIC_THEME_PROTOCOL_COMMAND_IS_PUT_ROOM_DATA 'D'
    ///////////////#define FABRIC_THEME_PROTOCOL_RESPOND_IS_PUT_ROOM_DATA 'd'

    private FabricRootClass fabricRootObject;
    private UFabricParserClass uFabricParserObject;
    public BinderClass binderObject;
    private Thread receiveThread;
    private UFabricReceiveRunnable receiveRunable;

    public FabricRootClass FabricRootObject() { return this.fabricRootObject; }

    public UFabricClass(FabricRootClass fabric_root_class_val) {
        this.debugIt(false, "UFabricClass", "init start");
        this.fabricRootObject = fabric_root_class_val;
        this.uFabricParserObject = new UFabricParserClass(this);
        this.binderObject = new BinderClass(this.objectName());
        //this.binderObject.BindAsTcpServer(Protocols.FabricThemeProtocolClass.GROUP_ROOM_PROTOCOL_TRANSPORT_PORT_NUMBER);
    }

    public void startThreads() {
        this.receiveRunable = new UFabricReceiveRunnable(this);
        this.receiveThread = new Thread(this.receiveRunable);
        this.receiveThread.start();
     }

    public void uFabricRreceiveThreadFunc()
    {
        this.debugIt(true, "uFabricRreceiveThreadFunc", "start thread");

        String data;
        while (true)
        {
            data = this.binderObject.ReceiveData();
            if (data == null)
            {
                this.abendIt("receiveThreadFunc", "null data");
                continue;
            }
            this.debugIt(true, "receiveThreadFunc", "data = " + data);
            this.uFabricParserObject.parseInputPacket(data);
        }
    }

    public void TransmitData(String data_val) {
        this.binderObject.TransmitData(data_val);
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

class UFabricReceiveRunnable implements Runnable
{
	UFabricClass theUFabricObject;
	
	public UFabricReceiveRunnable(UFabricClass u_fabric_object_val) {
		this.theUFabricObject = u_fabric_object_val;
	}
	
	public void run() {
		this.theUFabricObject.uFabricRreceiveThreadFunc();
	}
}

