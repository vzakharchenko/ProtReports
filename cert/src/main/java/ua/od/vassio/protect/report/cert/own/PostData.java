package ua.od.vassio.protect.report.cert.own;

/**
 * Created by vzakharchenko on 22.10.14.
 */
public class PostData {
    //searchtype=1&pid=9&itIssuerCN=&itIssuerO=&itIssuerOU=&itIssuerName=&itIssuerEDRPOU=2081900842&itIssuerDRFO=
    private int searchtype;
    private int pid;
    private String itIssuerCN="";
    private String itIssuerO="";
    private String itIssuerOU="";
    private String itIssuerName="";
    private String itIssuerEDRPOU="";
    private String itIssuerDRFO="";

    public int getSearchtype() {
        return searchtype;
    }

    public void setSearchtype(int searchtype) {
        this.searchtype = searchtype;
    }

    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    public String getItIssuerCN() {
        return itIssuerCN;
    }

    public void setItIssuerCN(String itIssuerCN) {
        this.itIssuerCN = itIssuerCN;
    }

    public String getItIssuerOU() {
        return itIssuerOU;
    }

    public void setItIssuerOU(String itIssuerOU) {
        this.itIssuerOU = itIssuerOU;
    }

    public String getItIssuerName() {
        return itIssuerName;
    }

    public void setItIssuerName(String itIssuerName) {
        this.itIssuerName = itIssuerName;
    }

    public String getItIssuerEDRPOU() {
        return itIssuerEDRPOU;
    }

    public void setItIssuerEDRPOU(String itIssuerEDRPOU) {
        this.itIssuerEDRPOU = itIssuerEDRPOU;
    }

    public String getItIssuerDRFO() {
        return itIssuerDRFO;
    }

    public void setItIssuerDRFO(String itIssuerDRFO) {
        this.itIssuerDRFO = itIssuerDRFO;
    }

    public String getItIssuerO() {
        return itIssuerO;
    }

    public void setItIssuerO(String itIssuerO) {
        this.itIssuerO = itIssuerO;
    }

    @Override
    public String toString() {
        return "searchtype="+searchtype+"&pid="+pid+"&itIssuerCN="+itIssuerCN+"&itIssuerO="+itIssuerO+"&itIssuerOU="+itIssuerOU+"&itIssuerName="+itIssuerName+"&itIssuerEDRPOU="+itIssuerEDRPOU+"&itIssuerDRFO="+itIssuerDRFO;
    }
}
