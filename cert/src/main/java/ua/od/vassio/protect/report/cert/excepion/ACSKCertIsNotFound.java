package ua.od.vassio.protect.report.cert.excepion;

/**
 * Created by vzakharchenko on 22.10.14.
 */
public class ACSKCertIsNotFound extends RuntimeException {
    public ACSKCertIsNotFound(String message) {
        super(message);
    }
}
