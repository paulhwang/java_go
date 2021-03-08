/*
 ******************************************************************************
 *                                       
 *  Copyright (c) 2021 phwang. All rights reserved.
 *
 ******************************************************************************
 */

package phwang.android;

import phwang.fabric.FabricExport;
//import phwang.theme.ThemeExport;

public class AndroidImport {
    public static final String FABRIC_ANDROID_SERVER_IP_ADDRESS = "127.0.0.1";
    public static final short FABRIC_ANDROID_PORT = FabricExport.FABRIC_ANDROID_PORT;
    
	//public static final int THEME_ROOM_ID_SIZE = ThemeExport.THEME_ROOM_ID_SIZE;
	
	public static final int FABRIC_LINK_ID_SIZE    = FabricExport.FABRIC_LINK_ID_SIZE;
	public static final int FABRIC_SESSION_ID_SIZE = FabricExport.FABRIC_SESSION_ID_SIZE;
	
	
	public static final char FABRIC_COMMAND_SETUP_LINK       = FabricExport.FABRIC_COMMAND_SETUP_LINK;
	public static final char FABRIC_COMMAND_REMOVE_LINK      = FabricExport.FABRIC_COMMAND_REMOVE_LINK;
	public static final char FABRIC_COMMAND_GET_LINK_DATA    = FabricExport.FABRIC_COMMAND_GET_LINK_DATA;
	public static final char FABRIC_COMMAND_GET_NAME_LIST    = FabricExport.FABRIC_COMMAND_GET_NAME_LIST;
	public static final char FABRIC_COMMAND_SETUP_SESSION    = FabricExport.FABRIC_COMMAND_SETUP_SESSION;
	public static final char FABRIC_COMMAND_SETUP_SESSION2   = FabricExport.FABRIC_COMMAND_SETUP_SESSION2;
	public static final char FABRIC_COMMAND_SETUP_SESSION3   = FabricExport.FABRIC_COMMAND_SETUP_SESSION3;
	public static final char FABRIC_COMMAND_PUT_SESSION_DATA = FabricExport.FABRIC_COMMAND_PUT_SESSION_DATA;
	public static final char FABRIC_COMMAND_GET_SESSION_DATA = FabricExport.FABRIC_COMMAND_GET_SESSION_DATA;
}
