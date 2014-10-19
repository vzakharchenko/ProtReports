package ua.od.vassio.protect.report.core.key;

import ua.od.vassio.protect.report.core.exception.UnProtectIITException;
import ua.od.vassio.protect.report.core.system.UserInfo;

/**
 * Created with IntelliJ IDEA.
 * User: vassio
 * Date: 18.10.14
 * Time: 16:56
 */
public interface Key {
    public UserInfo getUserInfo();

    public byte[] unprotect(byte[] bytes) throws UnProtectIITException;

}
