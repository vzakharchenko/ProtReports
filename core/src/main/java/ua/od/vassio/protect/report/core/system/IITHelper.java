package ua.od.vassio.protect.report.core.system;

import com.iit.certificateAuthority.endUser.libraries.signJava.*;
import org.apache.commons.lang3.text.WordUtils;
import ua.od.vassio.protect.report.core.exception.*;

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

    private static IITHelper iitHelper=null;
    protected static EndUserLibrary library;
    protected static EndUser endUser =new EndUser();
    private static boolean isInstalled;
    private static String installPath=EndUserResourceExtractor.GetInstallPath();

    private IITHelper() {
    }

    private static void initializeIITHelper() throws InitializeIITException {
        iitHelper=new IITHelper();
        try {
            EndUserResourceExtractor.codebaseURL = IITHelper.class.getClassLoader().getResource("");
        } catch (Exception ex){
            if (ex instanceof InitializeIITException){
               throw (InitializeIITException)ex;
            }
            if (library==null){
                throw new InitializeIITException(IITErrorList.ERROR_INITIALIZE_FAILED,"InitializationFail",ex);
            }
             throw new InitializeIITException(endUser.GetLastErrorCode(),endUser.GetLastError(),ex);
        }

    }
    public static synchronized IITHelper getInstance() throws InitializeIITException, InstallIITException {
        if (iitHelper==null){
            initializeIITHelper();

        }
        if (library==null){
            initialize(installPath);
        }
        if (!library.IsInitialized()){
            initialize(installPath);
        }
        return iitHelper;
    }

    private static boolean initialize(String installDirectory) throws InstallIITException {
        if (isInstalled){
            return false;
        }

        File installDir=new File(installDirectory);
        if (!installDir.exists()){
            installDir.mkdirs();
        }
        if (!installDir.exists()){
            throw new InstallIITException(IITErrorList.ERROR_INSTALL_FAILED," Directory is not exists: "+installDirectory);
        }
        endUser.SetCharset("windows-1251");
        endUser.SetUIMode(false);
        EndUserResourceExtractor.SetPath(installDirectory);
        library=new EndUserLibrary();
        try {
            EndUserAppletWrapper.setEndUserLibrary(endUser, library);
            endUser.Initialize();
        } catch (Exception e) {
            throw  new InstallIITException(IITErrorList.ERROR_INSTALL_FAILED,e.getMessage(),e);
        }
        validateToError();
        isInstalled=true;
        installPath=installDirectory;
        return true;
    }

    public void reInstall(String installDirectory) throws InstallIITException {
        if (library.IsInitialized()){
            finalizeLibrary();
        }
        initialize(installDirectory);
    }

    private static void validateToError() throws InternalIITException {
        if (endUser.GetLastErrorCode()!=0){
            throw new InternalIITException(endUser.GetLastErrorCode(),endUser.GetLastError());
        }
    }

    public void finalizeLibrary(){
        endUser.Finalize();
        isInstalled=false;
    }

    public boolean isInitialized(){
        return endUser.IsInitialized()&&isInstalled;
    }

    public UserInfo readPrivateKey(String fileName,String password) throws KeyReadExceptionIITException {
       if (!isInitialized()){
           throw new KeyReadExceptionIITException(EndUserError.ERROR_NOT_INITIALIZED,"Library is not Initialized");
       }
        EndUserOwnerInfo endUserOwnerInfo;
        try {
            endUser.ReadPrivateKeyFile(fileName, password);
            endUserOwnerInfo= endUser.GetPrivateKeyOwnerInfo();

        } catch (Exception e) {
            throw new KeyReadExceptionIITException(endUser.GetLastErrorCode(),endUser.GetLastError(),e);
        }
        validateToError();
        return populateFromIIT(endUserOwnerInfo);
    }

    public byte[] unprotect(byte[] data) throws UnProtectIITException {
        if (!isInitialized()||!endUser.IsPrivateKeyReaded()){
            throw new UnProtectIITException(EndUserError.ERROR_NOT_INITIALIZED,"Library is not Initialized");
        }
        String base64Data=endUser.BASE64Encode(data);

        try {
            EndUserSenderInfo endUserSenderInfo= endUser.Develop(base64Data);
           byte[] decodedData= endUserSenderInfo.GetData();
           validateToError();
           return  decodedData;
        } catch (Exception e) {
            throw new UnProtectIITException(endUser.GetLastErrorCode(),endUser.GetLastError(),e);
        }
    }

    private UserInfo populateFromIIT(EndUserOwnerInfo endUserOwnerInfo){
        UserInfo userInfo=new UserInfo();

        try {
            Field[] fields= EndUserOwnerInfo.class.getDeclaredFields();
            for (Field field: fields){
                Field userInfoField=UserInfo.class.getDeclaredField(field.getName());
                if  (userInfoField==null){
                    throw new IllegalArgumentException("Field "+field.getName() + " is not found in "+UserInfo.class);
                }
                String methodFromName="Get"+ WordUtils.capitalize(field.getName());
                Method methodFrom=EndUserOwnerInfo.class.getDeclaredMethod(methodFromName);
                Method methodTo= UserInfo.class.getDeclaredMethod("set"+ WordUtils.capitalize(field.getName()),field.getType());
                Object value= methodFrom.invoke(endUserOwnerInfo);
                methodTo.invoke(userInfo,value);
            }
        } catch (Exception ex){
            throw new RuntimeException(ex);
        }
        return userInfo;
    }



}
