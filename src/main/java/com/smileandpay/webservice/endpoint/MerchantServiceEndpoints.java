package com.smileandpay.webservice.endpoint;

import com.smileandpay.service.MerchantService;
import com.smileandpay.webservice.service.addmerchantservices.AddMerchantRequest;
import com.smileandpay.webservice.service.addmerchantservices.AddMerchantResponse;
import com.smileandpay.webservice.service.deletemerchantservices.DeleteMerchantRequest;
import com.smileandpay.webservice.service.deletemerchantservices.DeleteMerchantResponse;
import com.smileandpay.webservice.service.updatemerchantservices.UpdateMerchantRequest;
import com.smileandpay.webservice.service.updatemerchantservices.UpdateMerchantResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import static com.smileandpay.helper.Constants.STATUS_NOK;

/**
 * Endpoint de Marchand (merchant)
 * Possibilité d'ajouter, modifier ou supprimer un marchand ainsi que son addresse
 */
@Endpoint
public class MerchantServiceEndpoints {

    private static final String ADD_TARGET_NAMESPACE = "http://www.smileandpay.com/webservice/service/addMerchantServices";
    private static final String UPDATE_TARGET_NAMESPACE = "http://www.smileandpay.com/webservice/service/updateMerchantServices";
    private static final String DELETE_TARGET_NAMESPACE = "http://www.smileandpay.com/webservice/service/deleteMerchantServices";

    @Autowired
    private MerchantService merchantService;

    /**
     * Ajout d'un nouveau marchand
     */
    @PayloadRoot(localPart = "AddMerchantRequest", namespace = ADD_TARGET_NAMESPACE)
    public @ResponsePayload
    AddMerchantResponse addMerchant(@RequestPayload AddMerchantRequest merchant)  {
        try {
            return merchantService.addMerchant(merchant);
        } catch(Exception error) {
            AddMerchantResponse response = new AddMerchantResponse();
            response.setStatus(STATUS_NOK);
            response.setErrorMessage(error.getMessage());

            return response;
        }
    }

    /**
     * Modifier d'un marchand à partir de son ID
     */
    @PayloadRoot(localPart = "UpdateMerchantRequest", namespace = UPDATE_TARGET_NAMESPACE)
    public @ResponsePayload
    UpdateMerchantResponse updateMerchant(@RequestPayload UpdateMerchantRequest merchant) {
        try {
            return merchantService.updateMerchant(merchant);
        } catch(Exception error) {
            UpdateMerchantResponse response = new UpdateMerchantResponse();
            response.setStatus(STATUS_NOK);
            response.setErrorMessage(error.getMessage());

            return response;
        }
    }

    /**
     * Supprimer un marchand à partir de son ID
     */
    @PayloadRoot(localPart = "DeleteMerchantRequest", namespace = DELETE_TARGET_NAMESPACE)
    public @ResponsePayload
    DeleteMerchantResponse deleteMerchant(@RequestPayload DeleteMerchantRequest merchant) {
        try {
            return merchantService.deleteMerchant(merchant);
        } catch(Exception error) {
            DeleteMerchantResponse response = new DeleteMerchantResponse();
            response.setStatus(STATUS_NOK);
            response.setErrorMessage(error.getMessage());

            return response;
        }
    }


}
