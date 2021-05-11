package com.smileandpay.service;

import com.smileandpay.webservice.service.addmerchantservices.AddMerchantRequest;
import com.smileandpay.webservice.service.addmerchantservices.AddMerchantResponse;
import com.smileandpay.webservice.service.deletemerchantservices.DeleteMerchantRequest;
import com.smileandpay.webservice.service.deletemerchantservices.DeleteMerchantResponse;
import com.smileandpay.webservice.service.updatemerchantservices.UpdateMerchantRequest;
import com.smileandpay.webservice.service.updatemerchantservices.UpdateMerchantResponse;

public interface MerchantService {

    /**
     * Ajouter un nouveau marchand
     */
    AddMerchantResponse addMerchant(AddMerchantRequest merchant);

    /**
     * Modifie un marchand existant
     */
    UpdateMerchantResponse updateMerchant(UpdateMerchantRequest merchant);

    /**
     * Supprimer un marchand existant
     */
    DeleteMerchantResponse deleteMerchant(DeleteMerchantRequest merchant);
}
