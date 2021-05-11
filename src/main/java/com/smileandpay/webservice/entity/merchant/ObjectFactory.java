//
// Ce fichier a été généré par l'implémentation de référence JavaTM Architecture for XML Binding (JAXB), v2.3.2 
// Voir <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Toute modification apportée à ce fichier sera perdue lors de la recompilation du schéma source. 
// Généré le : 2021.05.11 à 01:58:03 PM CEST 
//


package com.smileandpay.webservice.entity.merchant;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.smileandpay.webservice.entity.merchant package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _Merchant_QNAME = new QName("http://www.smileandpay.com/webservice/entity/merchant", "Merchant");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.smileandpay.webservice.entity.merchant
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link Merchant }
     * 
     */
    public Merchant createMerchant() {
        return new Merchant();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Merchant }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link Merchant }{@code >}
     */
    @XmlElementDecl(namespace = "http://www.smileandpay.com/webservice/entity/merchant", name = "Merchant")
    public JAXBElement<Merchant> createMerchant(Merchant value) {
        return new JAXBElement<Merchant>(_Merchant_QNAME, Merchant.class, null, value);
    }

}
