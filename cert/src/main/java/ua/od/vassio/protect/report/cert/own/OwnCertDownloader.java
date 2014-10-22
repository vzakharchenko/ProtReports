package ua.od.vassio.protect.report.cert.own;

import org.apache.commons.collections4.CollectionUtils;
import ua.od.vassio.protect.report.cert.CertDownloader;
import ua.od.vassio.protect.report.cert.Downloader;
import ua.od.vassio.protect.report.cert.excepion.DownloaderException;
import ua.od.vassio.protect.report.cert.excepion.OwnCertIsNotFound;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;

/**
 * Created by vzakharchenko on 22.10.14.
 */
public abstract class OwnCertDownloader implements CertDownloader {

    protected URL pageSearch;

    public OwnCertDownloader(URL pageSearch) {
        this.pageSearch = pageSearch;
    }

    protected String getCookie() throws IOException {
        return Downloader.getCookie(pageSearch);
    }

    protected abstract List<URL> search(String cookie) throws Exception;

    @Override
    public void download(File pathToSave) throws DownloaderException {
       try {
           String cookie=getCookie();
           List<URL> foundCert =search(cookie);
           if (CollectionUtils.isNotEmpty(foundCert)){
               for (URL url:foundCert){
                 String fileName=Downloader.getName(url,cookie);
                 Downloader.downloadFileToDisk(url,fileName,pathToSave);
               }
           } else {
               throw new OwnCertIsNotFound("Own certificate is Not Found ");
           }

       } catch (Exception ex){
          throw new DownloaderException(ex);
       }

    }
}
