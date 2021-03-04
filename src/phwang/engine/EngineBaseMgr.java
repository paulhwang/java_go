/*
 ******************************************************************************
 *                                       
 *  Copyright (c) 2021 phwang. All rights reserved.
 *
 ******************************************************************************
 */

package phwang.engine;

import phwang.utils.*;

public class EngineBaseMgr {
    private String objectName() {return "EngineBaseMgr";}
    
    public static final int ENGINE_BASE_ID_SIZE_ = 4;
    public static final int ENGINE_BASE_ID_SIZE = ENGINE_BASE_ID_SIZE_ * 2;
    
    private static final int FIRST_BASE_ID = 9000;
    private static final int LIST_MGR_ARRAY_SIZE = 128;

    private EngineRootClass engineRootObject;
    private ListMgrClass listMgr;

    public ListMgrClass ListMgr() { return this.listMgr; }
  
    public EngineBaseMgr(EngineRootClass engine_root_object_val) {
        this.debug(false, "EngineBaseMgr", "init start");

        this.engineRootObject = engine_root_object_val;
        this.listMgr = new ListMgrClass(ENGINE_BASE_ID_SIZE_, LIST_MGR_ARRAY_SIZE, this.objectName(), FIRST_BASE_ID);
    }

    public EngineBase MallocGoBase(String room_id_val) {
    	EngineBase go_base = new EngineBase(room_id_val);
        ListEntryClass list_entry = this.listMgr.malloc(go_base);
        go_base.bindListEntry(list_entry);
        return go_base;
    }

    public void FreeGoBase(EngineBase link_val) {

    }
    
    public EngineBase GetBaseByIdStr(String base_id_str_val) {
        ListEntryClass list_entry = this.listMgr.getEntryByIdStr(base_id_str_val);
        if (list_entry == null) {
            return null;
        }
        return (EngineBase)list_entry.data();
    }
    
    private void debug(Boolean on_off, String s0, String s1) { if (on_off) this.log(s0, s1); }
    private void log(String s0, String s1) { AbendClass.log(this.objectName() + "." + s0 + "()", s1); }
    public void abend(String s0, String s1) { AbendClass.abend(this.objectName() + "." + s0 + "()", s1); }
}