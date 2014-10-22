package ua.od.vassio.protect.report.cert.acsk;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import ua.od.vassio.protect.report.cert.Downloader;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by vzakharchenko on 21.10.14.
 */
public class LinkACSKFactory {

    public static List<URL> parsePage(URL page) throws Exception{
        List<URL> list=new ArrayList<>();
        Document doc = Jsoup.parse(Downloader.downloadFileToMemory(page), "UTF-8",page.toString());
        for (Element element:doc.select("[href$=cer]")){
           String docUrl=element.attr("href");
            list.add(new URL(page.getProtocol(), page.getHost(), docUrl));
        }
        return list;
    }
}
