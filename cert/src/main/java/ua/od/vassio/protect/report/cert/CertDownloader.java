package ua.od.vassio.protect.report.cert;

import ua.od.vassio.protect.report.cert.excepion.DownloaderException;

import java.io.File;

/**
 * Created by vzakharchenko on 21.10.14.
 */
public interface CertDownloader {
    public void download(File pathToSave) throws DownloaderException;
}
