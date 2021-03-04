/*
 ******************************************************************************
 *                                       
 *  Copyright (c) 2021 phwang. All rights reserved.
 *
 ******************************************************************************
 */

package phwang.fabric;

public class FabricExport {
    public static final short FABRIC_FRONT_PORT = 8006;
	
	public static final int FABRIC_LINK_ID_SIZE = LinkMgrClass.FABRIC_LINK_ID_SIZE;
	public static final int FABRIC_SESSION_ID_SIZE = SessionMgrClass.FABRIC_SESSION_ID_SIZE;
	public static final int FABRIC_GROUP_ID_SIZE = GroupMgrClass.FABRIC_GROUP_ID_SIZE;
    public static final int NAME_LIST_TAG_SIZE = NameListClass.NAME_LIST_TAG_SIZE;
	
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
