package ua.od.vassio.protect.report.core.system.storage;

import ua.od.vassio.protect.report.core.exception.IITException;
import ua.od.vassio.protect.report.core.system.IITHelper;

/**
 * Created with IntelliJ IDEA.
 * User: vassio
 * Date: 20.10.14
 * Time: 9:30
 */
public class StorageFactory {

    public static Storage buildEmptyStorage(String pathToCert) {
        FileStorage fileStorage = new FileStorage();
        fileStorage.setPath(pathToCert);
        Storage storage = new Storage(fileStorage, new ProxyServer(), new TSPSettings(), new OCSPSettings(), new CMPSettings(), new LDAPSettings());
        return storage;
    }

    public static Storage buildDefaultStorage(String pathToCert) {
        Storage storage = buildEmptyStorage(pathToCert);

        storage.getFileStorage().setAutoDownloadCRLs(false);
        storage.getFileStorage().setAutoRefresh(true);
        storage.getFileStorage().setAutoDownloadCRLs(false);
        storage.getFileStorage().setFullAndDeltaCRLs(false);
        storage.getFileStorage().setExpireTime(3600);
        storage.getFileStorage().setSaveLoadedCerts(false);

        storage.getProxyServer().setUseProxy(false);

        storage.getTspSettings().setAddress("ivk.org.ua");
        storage.getTspSettings().setGetStamps(false);
        storage.getTspSettings().setPort("80");

        storage.getOcspSettings().setAddress("ivk.org.ua");
        storage.getOcspSettings().setBeforeStore(false);
        storage.getOcspSettings().setUseOCSP(false);
        storage.getOcspSettings().setPort("80");

        storage.getCmpSettings().setAddress("ivk.org.ua");
        storage.getCmpSettings().setCommonName("");
        storage.getCmpSettings().setUseCMP(false);
        storage.getCmpSettings().setPort("80");

        storage.getLdapSettings().setAddress("ivk.org.ua");
        storage.getLdapSettings().setPort("389");
        storage.getLdapSettings().setAnonymous(true);
        storage.getLdapSettings().setUseLDAP(false);
        return storage;
    }

    public static void saveStorage(Storage storage) throws IITException {
        IITHelper.getInstance().saveFileStorage(storage.getFileStorage());
        IITHelper.getInstance().saveCMPSettings(storage.getCmpSettings());
        IITHelper.getInstance().saveLDAPSettings(storage.getLdapSettings());
        IITHelper.getInstance().saveOCSPSettings(storage.getOcspSettings());
        IITHelper.getInstance().saveProxyServer(storage.getProxyServer());
    }

    public static Storage loadStorage() throws IITException {
        return IITHelper.getInstance().loadStorage();
    }


}
