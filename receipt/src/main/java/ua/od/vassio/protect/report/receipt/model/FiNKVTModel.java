package ua.od.vassio.protect.report.receipt.model;

import ua.od.vassio.protect.report.receipt.exception.ReceiptReadRuntimeException;
import ua.od.vassio.protect.report.receipt.xsd.DECLAR;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.stream.StreamSource;
import java.io.ByteArrayInputStream;

/**
 * Created with IntelliJ IDEA.
 * User: vassio
 * Date: 19.10.14
 * Time: 0:07
 */
public class FiNKVTModel extends AbstractReceiptModel<DECLAR> {


    private static JAXBContext JAXBCONTEXT;
    private static Unmarshaller UNMARSHALLER;
    private String message;
    public static String RECEIPT_XSD_MODEL_PATH="ua.od.vassio.protect.report.receipt.xsd";

    static {
        try {
            JAXBCONTEXT = JAXBContext.newInstance(RECEIPT_XSD_MODEL_PATH);
            UNMARSHALLER = JAXBCONTEXT.createUnmarshaller();
        } catch (JAXBException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public boolean isXml() {
        return true;
    }

    @Override
    public DECLAR getResponse() {
        try {
            JAXBElement<DECLAR> declarjaxbElement = UNMARSHALLER.unmarshal(new StreamSource(new ByteArrayInputStream(message.getBytes("windows-1251"))), DECLAR.class);
            return declarjaxbElement.getValue();
        } catch (Exception e) {
            throw new ReceiptReadRuntimeException(e);
        }

    }

    public void setMessage(String message) {
        this.message = message.substring(0, message.lastIndexOf('>')+1);
    }
}
