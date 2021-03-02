/*
 ******************************************************************************
 *                                       
 *  Copyright (c) 2018 phwang. All rights reserved.
 *
 ******************************************************************************
 */

package phwang.protocols;

public class FabricFrontEndProtocolClass {
    public static final String FABRIC_FRONT_PROTOCOL_SERVER_IP_ADDRESS = "127.0.0.1";
    public static final short FABRIC_FRONT_PROTOCOL_TRANSPORT_PORT_NUMBER = 8006;

    public static final String WEB_FABRIC_PROTOCOL_RESPOND_IS_GET_LINK_DATA_PENDING_SESSION = "S";
    public static final String WEB_FABRIC_PROTOCOL_RESPOND_IS_GET_LINK_DATA_PENDING_SESSION3 = "T";
    public static final String WEB_FABRIC_PROTOCOL_RESPOND_IS_GET_LINK_DATA_PENDING_DATA = "D";
    public static final String WEB_FABRIC_PROTOCOL_RESPOND_IS_GET_LINK_DATA_NAME_LIST = "N";

    public static final int WEB_FABRIC_PROTOCOL_NAME_LIST_TAG_SIZE = 4;
}
