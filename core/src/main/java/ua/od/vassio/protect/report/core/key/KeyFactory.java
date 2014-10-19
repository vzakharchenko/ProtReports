package ua.od.vassio.protect.report.core.key;

import ua.od.vassio.protect.report.core.exception.IITException;
import ua.od.vassio.protect.report.core.system.IITHelper;
import ua.od.vassio.protect.report.core.system.UserInfo;

import java.io.File;

/**
 * Created with IntelliJ IDEA.
 * User: vassio
 * Date: 18.10.14
 * Time: 18:28
 */
public class KeyFactory {
    public static Key openPrivateKey(String homeDir, File keyFile, String password) throws IITException {
        IITHelper iitHelper = IITHelper.getInstance();
        iitHelper.reInstall(homeDir);
        UserInfo userInfo = iitHelper.readPrivateKey(keyFile.getAbsolutePath(), password);
        return new IITKey(userInfo);
    }
}
