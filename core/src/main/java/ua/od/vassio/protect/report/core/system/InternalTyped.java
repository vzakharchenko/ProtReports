package ua.od.vassio.protect.report.core.system;

/**
 * Created with IntelliJ IDEA.
 * User: vassio
 * Date: 18.10.14
 * Time: 18:53
 */
public class InternalTyped {

    public static int SIGN_LENGTH = 0x45;

    public static byte[] UA1_SIGN = new byte[]{0x55, 0x41, 0x31, 0x5F, 0x53, 0x49, 0x47, 0x4E, 00};
    public static byte[] UA1_CRYPT = new byte[]{0x55, 0x41, 0x31, 0x5F, 0x43, 0x52, 0x59, 0x50, 0x54, 0x00};
    public static byte[] END_MESSAGE = new byte[]{(byte) 0x82, 0x07, 0x67, 0x30};
}

