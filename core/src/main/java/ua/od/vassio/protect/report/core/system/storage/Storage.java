package ua.od.vassio.protect.report.core.system.storage;

/**
 * Created with IntelliJ IDEA.
 * User: vassio
 * Date: 20.10.14
 * Time: 9:26
 */
public class Storage {
    private ProxyServer proxyServer;
    private FileStorage fileStorage;
    private TSPSettings tspSettings;
    private OCSPSettings ocspSettings;
    private LDAPSettings ldapSettings;
    private CMPSettings cmpSettings;

    protected Storage(FileStorage fileStorage, ProxyServer proxyServer, TSPSettings tspSettings, OCSPSettings ocspSettings, CMPSettings cmpSettings, LDAPSettings ldapSettings) {
        this.fileStorage = fileStorage;
        this.proxyServer = proxyServer;
        this.tspSettings = tspSettings;
        this.ocspSettings = ocspSettings;
        this.cmpSettings = cmpSettings;
        this.ldapSettings = ldapSettings;
    }

    public ProxyServer getProxyServer() {
        return proxyServer;
    }

    public FileStorage getFileStorage() {
        return fileStorage;
    }

    public TSPSettings getTspSettings() {
        return tspSettings;
    }

    public OCSPSettings getOcspSettings() {
        return ocspSettings;
    }

    public LDAPSettings getLdapSettings() {
        return ldapSettings;
    }

    public CMPSettings getCmpSettings() {
        return cmpSettings;
    }
}
