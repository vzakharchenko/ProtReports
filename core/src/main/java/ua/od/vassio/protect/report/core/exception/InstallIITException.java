package ua.od.vassio.protect.report.core.exception;

/**
 * Created with IntelliJ IDEA.
 * User: vassio
 * Date: 18.10.14
 * Time: 15:23
 */
public class InstallIITException extends IITException {
    public InstallIITException(int code, String message) {
        super(code, message);
    }

    public InstallIITException(int code, String message, Throwable cause) {
        super(code, message, cause);
    }
}
