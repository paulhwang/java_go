/*
 ******************************************************************************
 *                                       
 *  Copyright (c) 2018 phwang. All rights reserved.
 *
 ******************************************************************************
 */

package Phwang.Fabric;

import Phwang.Utils.AbendClass;
import Phwang.Utils.ThreadMgr.ThreadMgrClass;
import Phwang.Fabric.DFabric.DFabricClass;
import Phwang.Fabric.GroupMgr.GroupMgrClass;
import Phwang.Fabric.LinkMgr.LinkMgrClass;
import Phwang.Fabric.NameList.NameListClass;
import Phwang.Fabric.UFabric.UFabricClass;

public class FabricRootClass {
    private String objectName() {return "FabricRootClass";}
    
    private ThreadMgrClass threadMgrObject;
    private UFabricClass uFabricObject;
    private DFabricClass dFabricObject;
    private LinkMgrClass linkMgrObject;
    private GroupMgrClass groupMgrObject;
    private NameListClass nameListObject;

    public ThreadMgrClass ThreadMgrObject() { return this.threadMgrObject; }
    public UFabricClass UFabricObject() { return this.uFabricObject; }
    public DFabricClass DFabricObject() { return this.dFabricObject; }
    public LinkMgrClass LinkMgrObject() { return this.linkMgrObject; }
    public GroupMgrClass GroupMgrObject() { return this.groupMgrObject; }
    public NameListClass NameListObject() { return this.nameListObject; }

    public FabricRootClass () {
        this.debugIt(false, "FabricRootClass", "init start");
        
        this.threadMgrObject = new ThreadMgrClass();
        this.uFabricObject = new UFabricClass(this);
        this.dFabricObject = new DFabricClass(this);
        this.linkMgrObject = new LinkMgrClass(this);
        this.groupMgrObject = new GroupMgrClass(this);
        this.nameListObject = new NameListClass(this);
        
        this.UFabricObject().startThreads();
        this.DFabricObject().startThreads();

        this.StartWatchDogThread();
	}

    private void StartWatchDogThread() {

    }

    private void debugIt(Boolean on_off_val, String str0_val, String str1_val) { if (on_off_val) this.logitIt(str0_val, str1_val); }
    private void logitIt(String str0_val, String str1_val) { AbendClass.phwangLogit(this.objectName() + "." + str0_val + "()", str1_val); }
    public void abendIt(String str0_val, String str1_val) { AbendClass.phwangAbend(this.objectName() + "." + str0_val + "()", str1_val); }
}
