/*
 ******************************************************************************
 *                                       
 *  Copyright (c) 2021 phwang. All rights reserved.
 *
 ******************************************************************************
 */

package com.phwang.core.front;

import com.phwang.core.fabric.FabricExport;
import com.phwang.core.theme.ThemeExport;

public class FrontImport {
    public static final String FABRIC_FRONT_SERVER_IP_ADDRESS = "127.0.0.1";
    public static final short FABRIC_FRONT_PORT = FabricExport.FABRIC_FRONT_PORT;
    
	public static final int THEME_ROOM_ID_SIZE = ThemeExport.THEME_ROOM_ID_SIZE;
	
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
