package ua.od.vassio.protect.report.cert.acsk;

import org.apache.commons.collections4.CollectionUtils;
import ua.od.vassio.protect.report.cert.CertDownloader;
import ua.od.vassio.protect.report.cert.Downloader;
import ua.od.vassio.protect.report.cert.excepion.ACSKCertIsNotFound;
import ua.od.vassio.protect.report.cert.excepion.DownloaderException;

import java.io.File;
import java.net.URL;
import java.util.List;

/**
 * Created by vzakharchenko on 21.10.14.
 */
public class ACSKDownloader implements CertDownloader {
    private URL website;

    public ACSKDownloader(URL website) {
        this.website = website;
    }

    @Override
    public void download(File pathToSave) throws DownloaderException {
        try{

            List<URL> files=LinkACSKFactory.parsePage(website);

            if (CollectionUtils.isEmpty(files)){
                throw new ACSKCertIsNotFound("ACSK Certificate Is Not Found");
            }

            for (URL file:files){
                Downloader.downloadFileToDisk(file,pathToSave);
            }

        } catch (Exception ex){
            throw new DownloaderException(ex);
        }

    }
}
