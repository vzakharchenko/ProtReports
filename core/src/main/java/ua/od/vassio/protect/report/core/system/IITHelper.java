package ua.od.vassio.protect.report.core.system;

import com.iit.certificateAuthority.endUser.libraries.signJava.*;
import org.apache.commons.lang3.text.WordUtils;
import ua.od.vassio.protect.report.core.exception.*;
import ua.od.vassio.protect.report.core.system.storage.*;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * Created with IntelliJ IDEA.
 * User: vassio
 * Date: 18.10.14
 * Time: 14:58
 */
public class IITHelper {

    protected static EndUserLibrary library;
    protected static EndUser endUser = new EndUser();
    private static IITHelper iitHelper = null;
    private static boolean isInstalled;
    private static String installPath = EndUserResourceExtractor.GetInstallPath();

    private IITHelper() {
    }

    private static void initializeIITHelper() throws InitializeIITException {
        iitHelper = new IITHelper();
        try {
            EndUserResourceExtractor.codebaseURL = IITHelper.class.getClassLoader().getResource("EUSignJavaLibs.Mac.64.zip");
        } catch (Exception ex) {
            if (ex instanceof InitializeIITException) {
                throw (InitializeIITException) ex;
            }
            if (library == null) {
                throw new InitializeIITException(IITErrorList.ERROR_INITIALIZE_FAILED, "InitializationFail", ex);
            }
            throw new InitializeIITException(endUser.GetLastErrorCode(), endUser.GetLastError(), ex);
        }

    }

    public static synchronized IITHelper getInstance() throws InitializeIITException, InstallIITException {
        if (iitHelper == null) {
            initializeIITHelper();

        }
        if (library == null) {
            initialize(installPath);
        }
        if (!library.IsInitialized()) {
            initialize(installPath);
        }
        return iitHelper;
    }

    private static boolean initialize(String installDirectory) throws InstallIITException {
        if (isInstalled) {
            return false;
        }

        File installDir = new File(installDirectory);
        if (!installDir.exists()) {
            installDir.mkdirs();
        }
        if (!installDir.exists()) {
            throw new InstallIITException(IITErrorList.ERROR_INSTALL_FAILED, " Directory is not exists: " + installDirectory);
        }
        endUser.SetUIMode(false);
        EndUserResourceExtractor.SetPath(installDirectory);
        library = new EndUserLibrary();
        try {
            EndUserAppletWrapper.setEndUserLibrary(endUser, library);
            endUser.Initialize();
        } catch (Exception e) {
            throw new InstallIITException(IITErrorList.ERROR_INSTALL_FAILED, e.getMessage(), e);
        }
        validateToError();
        isInstalled = true;
        installPath = installDirectory;
        return true;
    }

    private static void validateToError() throws InternalIITException {
        if (endUser.GetLastErrorCode() != 0) {
            throw new InternalIITException(endUser.GetLastErrorCode(), endUser.GetLastError());
        }
    }

    public void reInstall(String installDirectory) throws InstallIITException {
        if (library.IsInitialized()) {
            finalizeLibrary();
        }
        initialize(installDirectory);
    }

    public void finalizeLibrary() {
        endUser.Finalize();
        isInstalled = false;
    }

    public boolean isInitialized() {
        return endUser.IsInitialized() && isInstalled;
    }

    public UserInfo readPrivateKey(String fileName, String password) throws KeyReadExceptionIITException {
        if (!isInitialized()) {
            throw new KeyReadExceptionIITException(EndUserError.ERROR_NOT_INITIALIZED, "Library is not Initialized");
        }
        EndUserOwnerInfo endUserOwnerInfo;
        try {
            endUser.ReadPrivateKeyFile(fileName, password);
            endUserOwnerInfo = endUser.GetPrivateKeyOwnerInfo();

        } catch (Exception e) {
            throw new KeyReadExceptionIITException(endUser.GetLastErrorCode(), endUser.GetLastError(), e);
        }
        validateToError();
        return populateFromIIT(endUserOwnerInfo);
    }

    public byte[] unprotect(byte[] data) throws UnProtectIITException {
        if (!isInitialized() || !endUser.IsPrivateKeyReaded()) {
            throw new UnProtectIITException(EndUserError.ERROR_NOT_INITIALIZED, "Library is not Initialized");
        }
        String base64Data = endUser.BASE64Encode(data);

        try {
            EndUserSenderInfo endUserSenderInfo = endUser.Develop(base64Data);
            byte[] decodedData = endUserSenderInfo.GetData();
            validateToError();
            return decodedData;
        } catch (Exception e) {
            throw new UnProtectIITException(endUser.GetLastErrorCode(), endUser.GetLastError(), e);
        }
    }

    private UserInfo populateFromIIT(EndUserOwnerInfo endUserOwnerInfo) {
        UserInfo userInfo = new UserInfo();

        try {
            Field[] fields = EndUserOwnerInfo.class.getDeclaredFields();
            for (Field field : fields) {
                Field userInfoField = UserInfo.class.getDeclaredField(field.getName());
                if (userInfoField == null) {
                    throw new IllegalArgumentException("Field " + field.getName() + " is not found in " + UserInfo.class);
                }
                String methodFromName = "Get" + WordUtils.capitalize(field.getName());
                Method methodFrom = EndUserOwnerInfo.class.getDeclaredMethod(methodFromName);
                Method methodTo = UserInfo.class.getDeclaredMethod("set" + WordUtils.capitalize(field.getName()), field.getType());
                Object value = methodFrom.invoke(endUserOwnerInfo);
                methodTo.invoke(userInfo, value);
            }
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
        return userInfo;
    }

    public static void saveFileStorage(FileStorage fileStorage) {
        try {
            endUser.SetFileStoreSettings(populateFromrFileStoreSettings(fileStorage));
        } catch (Exception e) {
            throw new InternalIITException(IITErrorList.ERROR_STORAGE_FAILED, e.getMessage(), e);
        }
    }

    public static void saveProxyServer(ProxyServer proxyServer) {
        try {
            endUser.SetProxySettings(populateFromProxySettings(proxyServer));
        } catch (Exception e) {
            throw new InternalIITException(IITErrorList.ERROR_STORAGE_FAILED, e.getMessage(), e);
        }
    }

    public static void saveOCSPSettings(OCSPSettings ocspSettings) {
        try {
            endUser.SetOCSPSettings(populateFromOCSPSettings(ocspSettings));
        } catch (Exception e) {
            throw new InternalIITException(IITErrorList.ERROR_STORAGE_FAILED, e.getMessage(), e);
        }
    }

    public static void saveCMPSettings(CMPSettings cmpSettings) {
        try {
            endUser.SetCMPSettings(populateFromCMPSettings(cmpSettings));
        } catch (Exception e) {
            throw new InternalIITException(IITErrorList.ERROR_STORAGE_FAILED, e.getMessage(), e);
        }
    }

    public static void saveLDAPSettings(LDAPSettings ldapSettings) {
        try {
            endUser.SetLDAPSettings(populateFromLDAPSettings(ldapSettings));
        } catch (Exception e) {
            throw new InternalIITException(IITErrorList.ERROR_STORAGE_FAILED, e.getMessage(), e);
        }
    }

    private static EndUserLDAPSettings populateFromLDAPSettings(LDAPSettings ldapSettings) {
        return new EndUserLDAPSettings(ldapSettings.isUseLDAP(), ldapSettings.getAddress(), ldapSettings.getPort(), ldapSettings.isAnonymous(), ldapSettings.getUser(), ldapSettings.getPassword());
    }

    private static EndUserCMPSettings populateFromCMPSettings(CMPSettings cmpSettings) {
        EndUserCMPSettings endUserCMPSettings = endUser.CreateCMPSettings();
        endUserCMPSettings.SetUseCMP(cmpSettings.isUseCMP());
        endUserCMPSettings.SetAddress(cmpSettings.getAddress());
        endUserCMPSettings.SetPort(cmpSettings.getPort());
        endUserCMPSettings.SetCommonName(cmpSettings.getCommonName());
        return endUserCMPSettings;
    }

    private static EndUserOCSPSettings populateFromOCSPSettings(OCSPSettings ocspSettings) {
        EndUserOCSPSettings endUserOCSPSettings = endUser.CreateOCSPSettings();
        endUserOCSPSettings.SetAddress(ocspSettings.getAddress());
        endUserOCSPSettings.SetPort(ocspSettings.getPort());
        endUserOCSPSettings.SetBeforeStore(ocspSettings.isBeforeStore());
        endUserOCSPSettings.SetUseOCSP(ocspSettings.isUseOCSP());
        endUserOCSPSettings.SetPort(ocspSettings.getPort());
        return endUserOCSPSettings;
    }

    private static EndUserProxySettings populateFromProxySettings(ProxyServer proxyServer) {
        return new EndUserProxySettings(proxyServer.isUseProxy(), proxyServer.isAnonymous(), proxyServer.getAddress(), proxyServer.getPort(), proxyServer.getUser(), proxyServer.getPassword(), proxyServer.isSavePassword());
    }

    private static EndUserFileStoreSettings populateFromrFileStoreSettings(FileStorage fileStorage) {
        return new EndUserFileStoreSettings(fileStorage.getPath(), fileStorage.isCheckCRLs(), fileStorage.isAutoRefresh(), fileStorage.isOwnCRLsOnly(), fileStorage.isFullAndDeltaCRLs(), fileStorage.isAutoDownloadCRLs(), fileStorage.isSaveLoadedCerts(), fileStorage.getExpireTime());
    }


    private static void populateFromEndUserLDAPSettings(LDAPSettings ldapSettings, EndUserLDAPSettings endUserLDAPSettings) {
        ldapSettings.setAnonymous(endUserLDAPSettings.GetAnonymous());
        ldapSettings.setUseLDAP(endUserLDAPSettings.GetUseLDAP());
        ldapSettings.setPort(endUserLDAPSettings.GetPort());
        ldapSettings.setPassword(endUserLDAPSettings.GetPassword());
        ldapSettings.setUser(endUserLDAPSettings.GetUser());
        ldapSettings.setAddress(endUserLDAPSettings.GetAddress());
    }

    private static void populateFromEndUserCMPSettings(CMPSettings cmpSettings, EndUserCMPSettings endUserCMPSettings) {
        cmpSettings.setAddress(endUserCMPSettings.GetAddress());
        cmpSettings.setPort(endUserCMPSettings.GetPort());
        cmpSettings.setUseCMP(endUserCMPSettings.GetUseCMP());
        cmpSettings.setCommonName(endUserCMPSettings.GetCommonName());
    }

    private static void populateFromEndUserOCSPSettings(OCSPSettings ocspSettings, EndUserOCSPSettings endUserOCSPSettings) {
        ocspSettings.setBeforeStore(endUserOCSPSettings.GetBeforeStore());
        ocspSettings.setUseOCSP(endUserOCSPSettings.GetUseOCSP());
        ocspSettings.setPort(endUserOCSPSettings.GetPort());
        ocspSettings.setAddress(endUserOCSPSettings.GetAddress());
    }

    private static void populateFromEndUserProxySettings(ProxyServer proxyServer, EndUserProxySettings endUserProxySettings) {
        proxyServer.setAddress(endUserProxySettings.GetAddress());
        proxyServer.setAnonymous(endUserProxySettings.GetAnonymous());
        proxyServer.setUseProxy(endUserProxySettings.GetUseProxy());
        proxyServer.setPort(endUserProxySettings.GetPort());
        proxyServer.setSavePassword(endUserProxySettings.GetSavePassword());
        proxyServer.setUser(endUserProxySettings.GetUser());
    }

    private static void populateFromEndUserFileStoreSettings(FileStorage fileStorage, EndUserFileStoreSettings endUserFileStoreSettings) {
        fileStorage.setPath(endUserFileStoreSettings.GetPath());
        fileStorage.setExpireTime(endUserFileStoreSettings.GetExpireTime());
        fileStorage.setFullAndDeltaCRLs(endUserFileStoreSettings.GetFullAndDeltaCRLs());
        fileStorage.setSaveLoadedCerts(endUserFileStoreSettings.GetSaveLoadedCerts());
        fileStorage.setAutoDownloadCRLs(endUserFileStoreSettings.GetAutoDownloadCRLs());
        fileStorage.setAutoRefresh(endUserFileStoreSettings.GetAutoRefresh());
        fileStorage.setCheckCRLs(endUserFileStoreSettings.GetCheckCRLs());
        fileStorage.setOwnCRLsOnly(endUserFileStoreSettings.GetOwnCRLsOnly());
    }

    public static Storage loadStorage() {
        Storage storage = StorageFactory.buildEmptyStorage("");
        try {
            populateFromEndUserFileStoreSettings(storage.getFileStorage(), endUser.GetFileStoreSettings());
            populateFromEndUserProxySettings(storage.getProxyServer(), endUser.GetProxySettings());
            populateFromEndUserOCSPSettings(storage.getOcspSettings(), endUser.GetOCSPSettings());
            populateFromEndUserCMPSettings(storage.getCmpSettings(), endUser.GetCMPSettings());
            populateFromEndUserLDAPSettings(storage.getLdapSettings(), endUser.GetLDAPSettings());
            return storage;
        } catch (Exception e) {
            throw new InternalIITException(IITErrorList.ERROR_STORAGE_FAILED, e.getMessage(), e);
        }

    }
}
