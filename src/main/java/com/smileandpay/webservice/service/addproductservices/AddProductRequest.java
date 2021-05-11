//
// Ce fichier a été généré par l'implémentation de référence JavaTM Architecture for XML Binding (JAXB), v2.3.2 
// Voir <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Toute modification apportée à ce fichier sera perdue lors de la recompilation du schéma source. 
// Généré le : 2021.05.11 à 01:58:03 PM CEST 
//


package com.smileandpay.webservice.service.addproductservices;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import com.smileandpay.webservice.entity.product.Product;


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
 *         &lt;element name="productDetails" type="{http://www.smileandpay.com/webservice/entity/product}Product"/&gt;
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
    "productDetails"
})
@XmlRootElement(name = "AddProductRequest")
public class AddProductRequest {

    @XmlElement(required = true)
    protected Product productDetails;

    /**
     * Obtient la valeur de la propriété productDetails.
     * 
     * @return
     *     possible object is
     *     {@link Product }
     *     
     */
    public Product getProductDetails() {
        return productDetails;
    }

    /**
     * Définit la valeur de la propriété productDetails.
     * 
     * @param value
     *     allowed object is
     *     {@link Product }
     *     
     */
    public void setProductDetails(Product value) {
        this.productDetails = value;
    }

}
