/*
 ******************************************************************************
 *                                       
 *  Copyright (c) 2021 phwang. All rights reserved.
 *
 ******************************************************************************
 */

package phwang.front;

import phwang.fabric.FabricDefineClass;
import phwang.theme.ThemeDefineClass;

public class FabricImportClass {
    public static final String FABRIC_FRONT_SERVER_IP_ADDRESS = "127.0.0.1";
    public static final short FABRIC_FRONT_PORT = FabricDefineClass.FABRIC_FRONT_PORT;
    
	public static final int THEME_ROOM_ID_SIZE = ThemeDefineClass.THEME_ROOM_ID_SIZE;
	
	public static final int FABRIC_LINK_ID_SIZE    = FabricDefineClass.FABRIC_LINK_ID_SIZE;
	public static final int FABRIC_SESSION_ID_SIZE = FabricDefineClass.FABRIC_SESSION_ID_SIZE;
	
	
	public static final String FABRIC_COMMAND_SETUP_LINK       = FabricDefineClass.FABRIC_COMMAND_SETUP_LINK;
	public static final String FABRIC_COMMAND_REMOVE_LINK       = FabricDefineClass.FABRIC_COMMAND_REMOVE_LINK;
	public static final String FABRIC_COMMAND_GET_LINK_DATA    = FabricDefineClass.FABRIC_COMMAND_GET_LINK_DATA;
	public static final String FABRIC_COMMAND_GET_NAME_LIST    = FabricDefineClass.FABRIC_COMMAND_GET_NAME_LIST;
	public static final String FABRIC_COMMAND_SETUP_SESSION    = FabricDefineClass.FABRIC_COMMAND_SETUP_SESSION;
	public static final String FABRIC_COMMAND_SETUP_SESSION2   = FabricDefineClass.FABRIC_COMMAND_SETUP_SESSION2;
	public static final String FABRIC_COMMAND_SETUP_SESSION3   = FabricDefineClass.FABRIC_COMMAND_SETUP_SESSION3;
	public static final String FABRIC_COMMAND_PUT_SESSION_DATA = FabricDefineClass.FABRIC_COMMAND_PUT_SESSION_DATA;
	public static final String FABRIC_COMMAND_GET_SESSION_DATA = FabricDefineClass.FABRIC_COMMAND_GET_SESSION_DATA;
}
