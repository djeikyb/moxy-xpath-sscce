package com.example;

import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.transform.stream.StreamSource;

import org.eclipse.persistence.jaxb.JAXBContext;
import org.eclipse.persistence.jaxb.JAXBContextFactory;

/**
 * @see http://stackoverflow.com/a/17700827/659715
 */
public final class Another {

    public static void main(String[] args) throws JAXBException {
        JAXBContext jaxbContext = (JAXBContext) JAXBContextFactory.createContext(new Class[] {Transaction.class}, null);
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
        StreamSource streamSource= new StreamSource("src/main/resources/input.xml");
        Transaction transaction = unmarshaller.unmarshal(streamSource, Transaction.class).getValue();

        String displayValue = jaxbContext.getValueByXPath(transaction, "SendingCustomer/@firstName", null, String.class);
        System.out.println(displayValue);

        Customer customer = jaxbContext.getValueByXPath(transaction, "SendingCustomer", null, Customer.class);
        System.out.println(customer.lastNameDecrypted);
    }

    @XmlRootElement(name="Transaction")
    public static class Transaction {

        @XmlElement(name="SendingCustomer")
        private Customer sendingCustomer;

    }

    public static class Customer {

        @XmlAttribute
        private String firstName;

        @XmlAttribute
        private String lastNameDecrypted;

        @XmlAttribute(name="OnWUTrustList")
        private boolean onWUTrustList;

        @XmlAttribute(name="WUTrustListType")
        private String wuTrustListType;

    }
}
