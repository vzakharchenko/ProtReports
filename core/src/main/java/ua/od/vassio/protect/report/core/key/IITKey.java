package ua.od.vassio.protect.report.core.key;

import ua.od.vassio.protect.report.core.exception.IITException;
import ua.od.vassio.protect.report.core.exception.UnProtectIITException;
import ua.od.vassio.protect.report.core.system.IITHelper;
import ua.od.vassio.protect.report.core.system.UserInfo;

/**
 * Created with IntelliJ IDEA.
 * User: vassio
 * Date: 18.10.14
 * Time: 16:57
 */
public class IITKey implements Key {
    private static IITHelper iitHelper;
    private UserInfo userInfo;

    public IITKey(UserInfo userInfo) throws IITException {
        this.userInfo = userInfo;
        iitHelper = IITHelper.getInstance();
    }

    protected static void setIitHelper(IITHelper iitHelper) {
        iitHelper = iitHelper;
    }

    @Override
    public UserInfo getUserInfo() {
        return userInfo;
    }

    @Override
    public byte[] unprotect(byte[] bytes) throws UnProtectIITException {
        return iitHelper.unprotect(bytes);
    }
}
