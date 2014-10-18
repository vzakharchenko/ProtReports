package ua.od.vassio.protect.report.core.exception;

import com.iit.certificateAuthority.endUser.libraries.signJava.EndUserError;

/**
 * Created with IntelliJ IDEA.
 * User: vassio
 * Date: 18.10.14
 * Time: 15:24
 */
public class IITErrorList extends EndUserError {
    public static final int ERROR_INITIALIZE_FAILED = -1;
    public static final int ERROR_INSTALL_FAILED = -2;
}
