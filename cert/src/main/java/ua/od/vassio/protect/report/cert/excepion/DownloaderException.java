package ua.od.vassio.protect.report.cert.excepion;

/**
 * Created by vzakharchenko on 21.10.14.
 */
public class DownloaderException extends Exception {
    public DownloaderException() {
    }

    public DownloaderException(String message) {
        super(message);
    }

    public DownloaderException(String message, Throwable cause) {
        super(message, cause);
    }

    public DownloaderException(Throwable cause) {
        super(cause);
    }
}
