/*
 ******************************************************************************
 *                                       
 *  Copyright (c) 2018 phwang. All rights reserved.
 *
 ******************************************************************************
 */

package Phwang.Engine;

import Phwang.Utils.AbendClass;
import Phwang.Utils.ListMgr.ListMgrClass;
import Phwang.Utils.ListMgr.ListEntryClass;
import Phwang.Utils.*;

public class BaseMgrClass {
    private String objectName() {return "BaseMgrClass";}
    
    private static final int FIRST_BASE_ID = 9000;

    private EngineRootClass engineRootObject;
    private ListMgrClass listMgr;

    public ListMgrClass ListMgr() { return this.listMgr; }
  
    public BaseMgrClass(EngineRootClass engine_root_object_val) {
        this.debugIt(false, "BaseMgrClass", "init start");

        this.engineRootObject = engine_root_object_val;
        this.listMgr = new ListMgrClass(this.objectName(), FIRST_BASE_ID);
    }

    public BaseClass MallocGoBase(String room_id_val) {
        BaseClass go_base = new BaseClass(room_id_val);
        ListEntryClass list_entry = this.listMgr.mallocEntry(go_base);
        go_base.bindListEntry(list_entry);
        return go_base;
    }

    public void FreeGoBase(BaseClass link_val) {

    }
    
    public BaseClass GetBaseByIdStr(String base_id_str_val) {
        int base_id = EncodeNumberClass.decodeNumber(base_id_str_val);

        return this.GetBaseById(base_id);
    }

    public BaseClass GetBaseById(int id_val) {
        ListEntryClass list_entry = this.listMgr.getEntryById(id_val);
        if (list_entry == null) {
            return null;
        }
        BaseClass base_object = (BaseClass)list_entry.Data();

        return base_object;
    }

    private void debugIt(Boolean on_off_val, String str0_val, String str1_val) { if (on_off_val) this.logitIt(str0_val, str1_val); }
    private void logitIt(String str0_val, String str1_val) { AbendClass.phwangLogit(this.objectName() + "." + str0_val + "()", str1_val); }
    public void abendIt(String str0_val, String str1_val) { AbendClass.phwangAbend(this.objectName() + "." + str0_val + "()", str1_val); }
}
