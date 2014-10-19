package ua.od.vassio.protect.report.core.system;

import com.iit.certificateAuthority.endUser.libraries.signJava.EndUserApplet;
import com.iit.certificateAuthority.endUser.libraries.signJava.EndUserLibrary;

import java.lang.reflect.Field;

/**
 * Created with IntelliJ IDEA.
 * User: vassio
 * Date: 17.10.14
 * Time: 22:54
 */
public class EndUserAppletWrapper {

    public static EndUserLibrary getEndUserLibrary(EndUserApplet endUserApplet) throws Exception {
        Field library = EndUserApplet.class.getDeclaredField("library");
        library.setAccessible(true);
        return (EndUserLibrary) library.get(endUserApplet);
    }

    public static void setEndUserLibrary(EndUserApplet endUserApplet, EndUserLibrary endUserLibrary) throws Exception {
        Field library = EndUserApplet.class.getDeclaredField("library");
        library.setAccessible(true);
        library.set(endUserApplet, endUserLibrary);
    }
}
