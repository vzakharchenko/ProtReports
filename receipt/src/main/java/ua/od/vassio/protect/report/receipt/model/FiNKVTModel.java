package ua.od.vassio.protect.report.receipt.model;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import ua.od.vassio.protect.report.receipt.exception.ReceiptReadRuntimeException;
import ua.od.vassio.protect.report.receipt.xsd.DECLAR;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.stream.StreamSource;
import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Created with IntelliJ IDEA.
 * User: vassio
 * Date: 19.10.14
 * Time: 0:07
 */
public class FiNKVTModel extends AbstractReceiptModel<DECLAR> {


    public static String RECEIPT_XSD_MODEL_PATH="ua.od.vassio.protect.report.receipt.xsd";
    private static JAXBContext JAXBCONTEXT;
    private static Unmarshaller UNMARSHALLER;
    private String message;
    static {
        try {
            JAXBCONTEXT = JAXBContext.newInstance(RECEIPT_XSD_MODEL_PATH);
            UNMARSHALLER = JAXBCONTEXT.createUnmarshaller();
        } catch (JAXBException e) {
            throw new RuntimeException(e);
        }
    }


    public boolean isXml() {
        return StringUtils.startsWith(message, "<?xml");
    }


    public DECLAR getResponseXml() {
        try {
            JAXBElement<DECLAR> declarjaxbElement = UNMARSHALLER.unmarshal(new StreamSource(new ByteArrayInputStream(message.getBytes("windows-1251"))), DECLAR.class);
            return declarjaxbElement.getValue();
        } catch (Exception e) {
            throw new ReceiptReadRuntimeException(e);
        }

    }

    @Override
    public String getResponseAsString() {
        if (isXml()) {
            DECLAR declar = getResponseXml();
            return buildString(declar);
        } else {
            return message;
        }

    }

    private String buildString(DECLAR declar) {
        DECLAR.DECLARBODY declarbody = declar.getDECLARBODY();
        String docName = declarbody.getHDOCNAME();
        String docKod = declarbody.getHDOCKOD();
        String fileName = declarbody.getHFILENAME();
        String docType = declarbody.getHDOCSTAN();
        String docPeriod = declarbody.getHPERIOD();
        String companyName = declarbody.getHNAME();
        String resultString = declarbody.getHRESULT();
        String dateString = Objects.toString(declarbody.getHDATE());
        String time = declarbody.getHTIME().toString();
        List<String> resons = new ArrayList<>();
        if (declarbody.getT1RXXXXG1S() != null) {
            List<DECLAR.DECLARBODY.T1RXXXXG1S> t1RXXXXG1Ses = declarbody.getT1RXXXXG1S();
            if (CollectionUtils.isNotEmpty(t1RXXXXG1Ses)) {
                for (DECLAR.DECLARBODY.T1RXXXXG1S t1RXXXXG1S : t1RXXXXG1Ses) {
                    resons.add(t1RXXXXG1S.getValue());
                }
            }
        }
        String message = docType + " : (" + docKod + ") " + docName +
                "\n За период: " + docPeriod +
                "\n Имя Файла: " + fileName +
                "\n " + companyName +
                "\n Статус: " + resultString;
        if (CollectionUtils.isNotEmpty(resons)) {
            message += "\n Причины:";
            for (String reson : resons) {
                message += "\n " + reson;
            }
        }
        return message;
    }

    public void setMessage(String message) {
        this.message = isXml() ? message.substring(0, message.lastIndexOf('>') + 1) : message;
    }
}
