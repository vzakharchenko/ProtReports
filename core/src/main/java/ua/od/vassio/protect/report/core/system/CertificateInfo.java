package ua.od.vassio.protect.report.core.system;

import java.util.Date;

/**
 * Created by vzakharchenko on 22.10.14.
 */
public class CertificateInfo {
    private String issuer;
    private String issuerCN;
    private String serial;
    private String subject;
    private String subjCN;
    private String subjOrg;
    private String subjOrgUnit;
    private String subjTitle;
    private String subjState;
    private String subjLocality;
    private String subjFullName;
    private String subjAddress;
    private String subjPhone;
    private String subjEMail;
    private String subjDNS;
    private String subjEDRPOUCode;
    private String subjDRFOCode;
    private String subjNBUCode;
    private String subjSPFMCode;
    private String subjOCode;
    private String subjOUCode;
    private String subjUserCode;
    private Date certBeginTime;
    private Date certEndTime;
    private boolean privKeyTimesAvail;
    private Date privKeyBeginTime;
    private Date privKeyEndTime;
    private int publicKeyBits;
    private String publicKey;
    private String publicKeyID;
    private boolean ecdhPublicKeyAvail;
    private int ecdhPublicKeyBits;
    private String ecdhPublicKey;
    private String ecdhPublicKeyID;
    private String issuerPublicKeyID;
    private String keyUsage;
    private String extKeyUsages;
    private String polices;
    private String crlDistribPoint1;
    private String crlDistribPoint2;
    private boolean powerCert;
    private boolean subjTypeAvail;
    private boolean subjCA;

    public String getIssuer() {
        return issuer;
    }

    public void setIssuer(String issuer) {
        this.issuer = issuer;
    }

    public String getIssuerCN() {
        return issuerCN;
    }

    public void setIssuerCN(String issuerCN) {
        this.issuerCN = issuerCN;
    }

    public String getSerial() {
        return serial;
    }

    public void setSerial(String serial) {
        this.serial = serial;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getSubjCN() {
        return subjCN;
    }

    public void setSubjCN(String subjCN) {
        this.subjCN = subjCN;
    }

    public String getSubjOrg() {
        return subjOrg;
    }

    public void setSubjOrg(String subjOrg) {
        this.subjOrg = subjOrg;
    }

    public String getSubjOrgUnit() {
        return subjOrgUnit;
    }

    public void setSubjOrgUnit(String subjOrgUnit) {
        this.subjOrgUnit = subjOrgUnit;
    }

    public String getSubjTitle() {
        return subjTitle;
    }

    public void setSubjTitle(String subjTitle) {
        this.subjTitle = subjTitle;
    }

    public String getSubjState() {
        return subjState;
    }

    public void setSubjState(String subjState) {
        this.subjState = subjState;
    }

    public String getSubjLocality() {
        return subjLocality;
    }

    public void setSubjLocality(String subjLocality) {
        this.subjLocality = subjLocality;
    }

    public String getSubjFullName() {
        return subjFullName;
    }

    public void setSubjFullName(String subjFullName) {
        this.subjFullName = subjFullName;
    }

    public String getSubjAddress() {
        return subjAddress;
    }

    public void setSubjAddress(String subjAddress) {
        this.subjAddress = subjAddress;
    }

    public String getSubjPhone() {
        return subjPhone;
    }

    public void setSubjPhone(String subjPhone) {
        this.subjPhone = subjPhone;
    }

    public String getSubjEMail() {
        return subjEMail;
    }

    public void setSubjEMail(String subjEMail) {
        this.subjEMail = subjEMail;
    }

    public String getSubjDNS() {
        return subjDNS;
    }

    public void setSubjDNS(String subjDNS) {
        this.subjDNS = subjDNS;
    }

    public String getSubjEDRPOUCode() {
        return subjEDRPOUCode;
    }

    public void setSubjEDRPOUCode(String subjEDRPOUCode) {
        this.subjEDRPOUCode = subjEDRPOUCode;
    }

    public String getSubjDRFOCode() {
        return subjDRFOCode;
    }

    public void setSubjDRFOCode(String subjDRFOCode) {
        this.subjDRFOCode = subjDRFOCode;
    }

    public String getSubjNBUCode() {
        return subjNBUCode;
    }

    public void setSubjNBUCode(String subjNBUCode) {
        this.subjNBUCode = subjNBUCode;
    }

    public String getSubjSPFMCode() {
        return subjSPFMCode;
    }

    public void setSubjSPFMCode(String subjSPFMCode) {
        this.subjSPFMCode = subjSPFMCode;
    }

    public String getSubjOCode() {
        return subjOCode;
    }

    public void setSubjOCode(String subjOCode) {
        this.subjOCode = subjOCode;
    }

    public String getSubjOUCode() {
        return subjOUCode;
    }

    public void setSubjOUCode(String subjOUCode) {
        this.subjOUCode = subjOUCode;
    }

    public String getSubjUserCode() {
        return subjUserCode;
    }

    public void setSubjUserCode(String subjUserCode) {
        this.subjUserCode = subjUserCode;
    }

    public Date getCertBeginTime() {
        return certBeginTime;
    }

    public void setCertBeginTime(Date certBeginTime) {
        this.certBeginTime = certBeginTime;
    }

    public Date getCertEndTime() {
        return certEndTime;
    }

    public void setCertEndTime(Date certEndTime) {
        this.certEndTime = certEndTime;
    }

    public boolean isPrivKeyTimesAvail() {
        return privKeyTimesAvail;
    }

    public void setPrivKeyTimesAvail(boolean privKeyTimesAvail) {
        this.privKeyTimesAvail = privKeyTimesAvail;
    }

    public Date getPrivKeyBeginTime() {
        return privKeyBeginTime;
    }

    public void setPrivKeyBeginTime(Date privKeyBeginTime) {
        this.privKeyBeginTime = privKeyBeginTime;
    }

    public Date getPrivKeyEndTime() {
        return privKeyEndTime;
    }

    public void setPrivKeyEndTime(Date privKeyEndTime) {
        this.privKeyEndTime = privKeyEndTime;
    }

    public int getPublicKeyBits() {
        return publicKeyBits;
    }

    public void setPublicKeyBits(int publicKeyBits) {
        this.publicKeyBits = publicKeyBits;
    }

    public String getPublicKey() {
        return publicKey;
    }

    public void setPublicKey(String publicKey) {
        this.publicKey = publicKey;
    }

    public String getPublicKeyID() {
        return publicKeyID;
    }

    public void setPublicKeyID(String publicKeyID) {
        this.publicKeyID = publicKeyID;
    }

    public boolean isEcdhPublicKeyAvail() {
        return ecdhPublicKeyAvail;
    }

    public void setEcdhPublicKeyAvail(boolean ecdhPublicKeyAvail) {
        this.ecdhPublicKeyAvail = ecdhPublicKeyAvail;
    }

    public int getEcdhPublicKeyBits() {
        return ecdhPublicKeyBits;
    }

    public void setEcdhPublicKeyBits(int ecdhPublicKeyBits) {
        this.ecdhPublicKeyBits = ecdhPublicKeyBits;
    }

    public String getEcdhPublicKey() {
        return ecdhPublicKey;
    }

    public void setEcdhPublicKey(String ecdhPublicKey) {
        this.ecdhPublicKey = ecdhPublicKey;
    }

    public String getEcdhPublicKeyID() {
        return ecdhPublicKeyID;
    }

    public void setEcdhPublicKeyID(String ecdhPublicKeyID) {
        this.ecdhPublicKeyID = ecdhPublicKeyID;
    }

    public String getIssuerPublicKeyID() {
        return issuerPublicKeyID;
    }

    public void setIssuerPublicKeyID(String issuerPublicKeyID) {
        this.issuerPublicKeyID = issuerPublicKeyID;
    }

    public String getKeyUsage() {
        return keyUsage;
    }

    public void setKeyUsage(String keyUsage) {
        this.keyUsage = keyUsage;
    }

    public String getExtKeyUsages() {
        return extKeyUsages;
    }

    public void setExtKeyUsages(String extKeyUsages) {
        this.extKeyUsages = extKeyUsages;
    }

    public String getPolices() {
        return polices;
    }

    public void setPolices(String polices) {
        this.polices = polices;
    }

    public String getCrlDistribPoint1() {
        return crlDistribPoint1;
    }

    public void setCrlDistribPoint1(String crlDistribPoint1) {
        this.crlDistribPoint1 = crlDistribPoint1;
    }

    public String getCrlDistribPoint2() {
        return crlDistribPoint2;
    }

    public void setCrlDistribPoint2(String crlDistribPoint2) {
        this.crlDistribPoint2 = crlDistribPoint2;
    }

    public boolean isPowerCert() {
        return powerCert;
    }

    public void setPowerCert(boolean powerCert) {
        this.powerCert = powerCert;
    }

    public boolean isSubjTypeAvail() {
        return subjTypeAvail;
    }

    public void setSubjTypeAvail(boolean subjTypeAvail) {
        this.subjTypeAvail = subjTypeAvail;
    }

    public boolean isSubjCA() {
        return subjCA;
    }

    public void setSubjCA(boolean subjCA) {
        this.subjCA = subjCA;
    }
}
