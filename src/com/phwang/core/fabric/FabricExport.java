/*
 ******************************************************************************
 *                                       
 *  Copyright (c) 2021 phwang. All rights reserved.
 *
 ******************************************************************************
 */

package com.phwang.core.fabric;

public class FabricExport {
    public static final short FABRIC_ANDROID_PORT = 8001;
    public static final short FABRIC_FRONT_PORT = 8001;

    public static final int FRONT_JOB_ID_SIZE_ = 4;
    public static final int FRONT_JOB_ID_SIZE = FRONT_JOB_ID_SIZE_ * 2;
	
	public static final int FABRIC_LINK_ID_SIZE = FabricLinkMgr.FABRIC_LINK_ID_SIZE;
	public static final int FABRIC_SESSION_ID_SIZE = FabricLSessionMgr.FABRIC_SESSION_ID_SIZE;
	public static final int FABRIC_GROUP_ID_SIZE = FabricGroupMgr.FABRIC_GROUP_ID_SIZE;
    public static final int NAME_LIST_TAG_SIZE = FabricNameList.NAME_LIST_TAG_SIZE;
	
	public static final char FABRIC_COMMAND_SETUP_LINK = 'L';
	public static final char FABRIC_COMMAND_REMOVE_LINK = 'l';
	public static final char FABRIC_COMMAND_GET_LINK_DATA = 'D';
	public static final char FABRIC_COMMAND_GET_NAME_LIST = 'N';
	public static final char FABRIC_COMMAND_SETUP_SESSION = 'S';
	public static final char FABRIC_COMMAND_SETUP_SESSION2 = 's';
	public static final char FABRIC_COMMAND_SETUP_SESSION3 = 'T';
	public static final char FABRIC_COMMAND_PUT_SESSION_DATA = 'P';
	public static final char FABRIC_COMMAND_GET_SESSION_DATA = 'G';
}
