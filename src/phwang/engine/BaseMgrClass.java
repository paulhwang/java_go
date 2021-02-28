/*
 ******************************************************************************
 *                                       
 *  Copyright (c) 2018 phwang. All rights reserved.
 *
 ******************************************************************************
 */

package phwang.engine;

import phwang.utils.*;

public class BaseMgrClass {
    private String objectName() {return "BaseMgrClass";}
    
    private static final int FIRST_BASE_ID = 9000;
    private static final int LIST_MGR_ARRAY_SIZE = 128;

    private EngineRootClass engineRootObject;
    private ListMgrClass listMgr;

    public ListMgrClass ListMgr() { return this.listMgr; }
  
    public BaseMgrClass(EngineRootClass engine_root_object_val) {
        this.debug(false, "BaseMgrClass", "init start");

        this.engineRootObject = engine_root_object_val;
        this.listMgr = new ListMgrClass(EngineDefineClass.ENGINE_BASE_ID_SIZE, LIST_MGR_ARRAY_SIZE, this.objectName(), FIRST_BASE_ID);
    }

    public BaseClass MallocGoBase(String room_id_val) {
        BaseClass go_base = new BaseClass(room_id_val);
        ListEntryClass list_entry = this.listMgr.malloc(go_base);
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
        BaseClass base_object = (BaseClass)list_entry.data();

        return base_object;
    }
    
    private void debug(Boolean on_off, String s0, String s1) { if (on_off) this.log(s0, s1); }
    private void log(String s0, String s1) { AbendClass.log(this.objectName() + "." + s0 + "()", s1); }
    public void abend(String s0, String s1) { AbendClass.abend(this.objectName() + "." + s0 + "()", s1); }
}
