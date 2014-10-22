package ua.od.vassio.protect.report.cert.acsk;

import ua.od.vassio.protect.report.cert.excepion.DownloaderException;

import java.io.File;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by vzakharchenko on 21.10.14.
 */
public class ACSKDownloaderHelper {
    private static Map<URL,ACSKDownloader> cache=new HashMap<>();

    public static void download(URL website,File pathToSave) throws DownloaderException {
        if (!cache.containsKey(website)){
            ACSKDownloader acskDownloader=new ACSKDownloader(website);
            cache.put(website,acskDownloader);
        }
        cache.get(website).download(pathToSave);
    }
}
