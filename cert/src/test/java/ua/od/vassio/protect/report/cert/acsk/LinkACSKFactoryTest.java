package ua.od.vassio.protect.report.cert.acsk;

import org.testng.annotations.Test;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import static org.testng.Assert.assertTrue;

/**
 * Created by vzakharchenko on 21.10.14.
 */
public class LinkACSKFactoryTest {
    @Test
    public void testLinkACSKFactory() throws Exception {
        List<URL> files=LinkACSKFactory.parsePage(new URL("http://www.acskidd.gov.ua/ca-certificates"));
        assertTrue(files.size()>0);
    }
}
