package com.smileandpay.service;


import com.smileandpay.webservice.service.linkmerchantproductservices.LinkMerchantProductRequest;
import com.smileandpay.webservice.service.linkmerchantproductservices.LinkMerchantProductResponse;

public interface MerchantProductService {


    /**
     * Association entre un marchand et un produit
     */
    LinkMerchantProductResponse linkMerchantProduct(LinkMerchantProductRequest linkMerchantProduct);

}
