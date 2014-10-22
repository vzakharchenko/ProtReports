package ua.od.vassio.protect.report.cert.excepion;

/**
 * Created by vzakharchenko on 22.10.14.
 */
public class OwnCertIsNotFound extends RuntimeException {
    public OwnCertIsNotFound(String message) {
        super(message);
    }
}
