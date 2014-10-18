package ua.od.vassio.protect.report.core.exception;

/**
 * Created with IntelliJ IDEA.
 * User: vassio
 * Date: 18.10.14
 * Time: 17:35
 */
public class UnProtectIITException extends IITException {

    public UnProtectIITException(int code, String message) {
        super(code, message);
    }

    public UnProtectIITException(int code, String message, Throwable cause) {
        super(code, message, cause);
    }
}
