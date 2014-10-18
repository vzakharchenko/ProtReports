package ua.od.vassio.protect.report.core.exception;

/**
 * Created with IntelliJ IDEA.
 * User: vassio
 * Date: 18.10.14
 * Time: 16:18
 */
public class KeyReadExceptionIITException extends IITException {
    public KeyReadExceptionIITException(int code, String message) {
        super(code, message);
    }

    public KeyReadExceptionIITException(int code, String message, Throwable cause) {
        super(code, message, cause);
    }
}
