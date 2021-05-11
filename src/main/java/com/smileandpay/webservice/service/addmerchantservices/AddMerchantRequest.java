//
// Ce fichier a été généré par l'implémentation de référence JavaTM Architecture for XML Binding (JAXB), v2.3.2 
// Voir <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Toute modification apportée à ce fichier sera perdue lors de la recompilation du schéma source. 
// Généré le : 2021.05.11 à 01:58:03 PM CEST 
//


package com.smileandpay.webservice.service.addmerchantservices;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import com.smileandpay.webservice.entity.address.Address;
import com.smileandpay.webservice.entity.merchant.Merchant;


/**
 * <p>Classe Java pour anonymous complex type.
 * 
 * <p>Le fragment de schéma suivant indique le contenu attendu figurant dans cette classe.
 * 
 * <pre>
 * &lt;complexType&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="merchantDetails" type="{http://www.smileandpay.com/webservice/entity/merchant}Merchant"/&gt;
 *         &lt;element name="addressDetails" type="{http://www.smileandpay.com/webservice/entity/address}Address" maxOccurs="unbounded" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "merchantDetails",
    "addressDetails"
})
@XmlRootElement(name = "AddMerchantRequest")
public class AddMerchantRequest {

    @XmlElement(required = true)
    protected Merchant merchantDetails;
    protected List<Address> addressDetails;

    /**
     * Obtient la valeur de la propriété merchantDetails.
     * 
     * @return
     *     possible object is
     *     {@link Merchant }
     *     
     */
    public Merchant getMerchantDetails() {
        return merchantDetails;
    }

    /**
     * Définit la valeur de la propriété merchantDetails.
     * 
     * @param value
     *     allowed object is
     *     {@link Merchant }
     *     
     */
    public void setMerchantDetails(Merchant value) {
        this.merchantDetails = value;
    }

    /**
     * Gets the value of the addressDetails property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the addressDetails property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getAddressDetails().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Address }
     * 
     * 
     */
    public List<Address> getAddressDetails() {
        if (addressDetails == null) {
            addressDetails = new ArrayList<Address>();
        }
        return this.addressDetails;
    }

}
