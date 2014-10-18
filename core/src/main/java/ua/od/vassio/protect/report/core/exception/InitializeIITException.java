package ua.od.vassio.protect.report.core.exception;

/**
 * Created with IntelliJ IDEA.
 * User: vassio
 * Date: 18.10.14
 * Time: 15:10
 */
public class InitializeIITException extends IITException {
    public InitializeIITException(int code, String message) {
        super(code, message);
    }

    public InitializeIITException(int code, String message, Throwable cause) {
        super(code, message, cause);
    }
}
