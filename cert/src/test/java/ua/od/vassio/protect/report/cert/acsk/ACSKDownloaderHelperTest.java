package ua.od.vassio.protect.report.cert.acsk;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.DirectoryFileFilter;
import org.apache.commons.io.filefilter.FileFileFilter;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ua.od.vassio.protect.report.cert.excepion.DownloaderException;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Collection;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

/**
 * Created by vzakharchenko on 21.10.14.
 */
public class ACSKDownloaderHelperTest {
    private  File dir=new File("./certDownloads/");
    @BeforeMethod
    public void before() throws IOException {
        if (dir.exists()){
            FileUtils.cleanDirectory(dir);
            FileUtils.deleteDirectory(dir);
        }
        dir.mkdirs();
    }

    @AfterMethod
    public void after() throws IOException {
        if (dir.exists()){
            FileUtils.cleanDirectory(dir);
            FileUtils.deleteDirectory(dir);
        }
    }

    @Test
    public void testACSKDownloaderHelper() throws IOException, DownloaderException {
        ACSKDownloaderHelper.download(new URL("http://www.acskidd.gov.ua/ca-certificates"),dir);
        Collection<File> files=FileUtils.listFiles(dir, FileFileFilter.FILE, DirectoryFileFilter.INSTANCE);
        assertTrue(files.size() >0);

    }
}
