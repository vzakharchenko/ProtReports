package ua.od.vassio.protect.report.cert.own;

import ua.od.vassio.protect.report.cert.excepion.DownloaderException;

import java.io.File;

/**
 * Created by vzakharchenko on 22.10.14.
 */
public abstract class AcskiddOwnCertFactory {

    public static  void downloadByEDRPO(String edrpo,File pathToSave) throws DownloaderException {
        EDRPOOwnCertDownloader edrpoOwnCertDownloader=new EDRPOOwnCertDownloader(edrpo);
        edrpoOwnCertDownloader.download(pathToSave);
    }
}
