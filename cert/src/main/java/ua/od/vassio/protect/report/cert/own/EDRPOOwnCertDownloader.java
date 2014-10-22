package ua.od.vassio.protect.report.cert.own;

import org.apache.commons.lang3.StringUtils;

import java.net.URL;
import java.util.List;

/**
 * Created by vzakharchenko on 22.10.14.
 */
public class EDRPOOwnCertDownloader extends AcskiddOwnCertDownloader {
    private String edrpo;

    public EDRPOOwnCertDownloader(String edrpo) {
        this.edrpo = edrpo;
        if (StringUtils.length(edrpo)<6){
            throw new IllegalArgumentException("EDRPO size should be bigger than 6");
        }
    }

    @Override
    public PostData getPOstData() {
        PostData postData=new PostData();
        postData.setPid(9);
        postData.setSearchtype(1);
        postData.setItIssuerEDRPOU(edrpo);
        return postData;
    }
}
