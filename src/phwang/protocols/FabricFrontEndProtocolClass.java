/*
 ******************************************************************************
 *                                       
 *  Copyright (c) 2018 phwang. All rights reserved.
 *
 ******************************************************************************
 */

package phwang.protocols;

import phwang.fabric.FabricDefineClass;
import phwang.front.FrontDefineClass;

public class FabricFrontEndProtocolClass {
    public static final String FABRIC_FRONT_PROTOCOL_SERVER_IP_ADDRESS = "127.0.0.1";
    public static final short FABRIC_FRONT_PROTOCOL_TRANSPORT_PORT_NUMBER = 8006;

    public static final String WEB_FABRIC_PROTOCOL_RESPOND_IS_GET_LINK_DATA_PENDING_SESSION = "S";
    public static final String WEB_FABRIC_PROTOCOL_RESPOND_IS_GET_LINK_DATA_PENDING_SESSION3 = "T";
    public static final String WEB_FABRIC_PROTOCOL_RESPOND_IS_GET_LINK_DATA_PENDING_DATA = "D";
    public static final String WEB_FABRIC_PROTOCOL_RESPOND_IS_GET_LINK_DATA_NAME_LIST = "N";

    public static final int WEB_FABRIC_PROTOCOL_NAME_LIST_TAG_SIZE = 4;

    public static final int FRONT_JOB_ID_SIZE = FrontDefineClass.FRONT_JOB_ID_SIZE;
	
	public static final int FABRIC_LINK_ID_SIZE    = FabricDefineClass.FABRIC_LINK_ID_SIZE;
	public static final int FABRIC_SESSION_ID_SIZE = FabricDefineClass.FABRIC_SESSION_ID_SIZE;
	
	public static final String FABRIC_COMMAND_SETUP_LINK       = FabricDefineClass.FABRIC_COMMAND_SETUP_LINK;
	public static final String FABRIC_COMMAND_GET_LINK_DATA    = FabricDefineClass.FABRIC_COMMAND_GET_LINK_DATA;
	public static final String FABRIC_COMMAND_GET_NAME_LIST    = FabricDefineClass.FABRIC_COMMAND_GET_NAME_LIST;
	public static final String FABRIC_COMMAND_SETUP_SESSION    = FabricDefineClass.FABRIC_COMMAND_SETUP_SESSION;
	public static final String FABRIC_COMMAND_SETUP_SESSION2   = FabricDefineClass.FABRIC_COMMAND_SETUP_SESSION2;
	public static final String FABRIC_COMMAND_SETUP_SESSION3   = FabricDefineClass.FABRIC_COMMAND_SETUP_SESSION3;
	public static final String FABRIC_COMMAND_PUT_SESSION_DATA = FabricDefineClass.FABRIC_COMMAND_PUT_SESSION_DATA;
	public static final String FABRIC_COMMAND_GET_SESSION_DATA = FabricDefineClass.FABRIC_COMMAND_GET_SESSION_DATA;
}
