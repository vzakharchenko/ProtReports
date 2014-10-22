package ua.od.vassio.protect.report.cert.own;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import ua.od.vassio.protect.report.cert.Downloader;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by vzakharchenko on 22.10.14.
 */
public class LinkOwnCertFactory {
    public static List<URL> parsePage(URL postUrl,String postData,String cookie) throws Exception{
        List<URL> list=new ArrayList<>();
        Document doc = Jsoup.parse(Downloader.downloadFileToMemory(postUrl,postData,cookie), "UTF-8", postUrl.toString());
        for (Element element:doc.select("a[href^=index.php?page=ddownload&]")){
            String docUrl=element.attr("href");
            list.add(new URL(postUrl.getProtocol(), postUrl.getHost(), StringUtils.startsWith(docUrl,"/")?docUrl:"/"+docUrl));
        }
        return list;
    }
}
