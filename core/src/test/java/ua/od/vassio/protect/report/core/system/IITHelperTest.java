package ua.od.vassio.protect.report.core.system;

import com.iit.certificateAuthority.endUser.libraries.signJava.EndUserResourceExtractor;
import org.apache.commons.io.FileUtils;
import org.testng.annotations.Test;

import java.io.File;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

/**
 * Created with IntelliJ IDEA.
 * User: vassio
 * Date: 18.10.14
 * Time: 15:41
 */
public class IITHelperTest {

    @Test(enabled = false)
    public void testInitialize() throws Exception {
        String path= EndUserResourceExtractor.GetInstallPath();
        if (new File(path).exists()){
            FileUtils.deleteDirectory(new File(path));
        }
        assertFalse(new File(path).exists());
        IITHelper.getInstance();

        assertTrue(new File(path).exists());
    }

    @Test(enabled = false)
    public void testReinstall() throws Exception {
        String path="/tmp/testDirecory";
        if (new File(path).exists()){
            FileUtils.deleteDirectory(new File(path));
        }
        IITHelper.getInstance();

        assertTrue(new File(EndUserResourceExtractor.GetInstallPath()).exists());
        IITHelper.getInstance().reInstall(path);
        assertTrue(new File(path).exists());
        FileUtils.deleteDirectory(new File(path));
    }
}
