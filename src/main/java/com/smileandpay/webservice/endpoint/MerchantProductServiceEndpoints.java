package com.smileandpay.webservice.endpoint;

import com.smileandpay.service.MerchantProductService;
import com.smileandpay.webservice.service.linkmerchantproductservices.LinkMerchantProductRequest;
import com.smileandpay.webservice.service.linkmerchantproductservices.LinkMerchantProductResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import static com.smileandpay.helper.Constants.STATUS_NOK;

/**
 * Endpoint de Marchand - produit (product)
 * Possibilité d'associer un marchand à un produit
 */
@Endpoint
public class MerchantProductServiceEndpoints {

    private static final String ADD_TARGET_NAMESPACE = "http://www.smileandpay.com/webservice/service/linkMerchantProductServices";

    @Autowired
    private MerchantProductService merchantProductService;

    /**
     * Associer un marchand à un produit
     */
    @PayloadRoot(localPart = "LinkMerchantProductRequest", namespace = ADD_TARGET_NAMESPACE)
    public @ResponsePayload
    LinkMerchantProductResponse addProduct(@RequestPayload LinkMerchantProductRequest linkMerchantProduct) {
        try {
            return merchantProductService.linkMerchantProduct(linkMerchantProduct);
        } catch(Exception error) {
            LinkMerchantProductResponse response = new LinkMerchantProductResponse();
            response.setStatus(STATUS_NOK);
            response.setErrorMessage(error.getMessage());

            return response;
        }
    }

 }
