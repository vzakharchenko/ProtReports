package ua.od.vassio.protect.report.cert.own;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

/**
 * Created by vzakharchenko on 22.10.14.
 */
public abstract class AcskiddOwnCertDownloader extends OwnCertDownloader{
    private static URL PAGE_SEARCH;

    static {
        try {
            PAGE_SEARCH=new URL("http://www.acskidd.gov.ua/certificates-search-results");
        } catch (MalformedURLException e) {
            throw new IllegalArgumentException(e);
        }
    }
    public AcskiddOwnCertDownloader() {
        super(PAGE_SEARCH);
    }

    public abstract PostData getPOstData();

    @Override
    protected List<URL> search(String cookie) throws Exception {
        PostData postData=getPOstData();

        return LinkOwnCertFactory.parsePage(PAGE_SEARCH,postData.toString(),cookie);
    }
}
