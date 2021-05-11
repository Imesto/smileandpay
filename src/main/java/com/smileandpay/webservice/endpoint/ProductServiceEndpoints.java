package com.smileandpay.webservice.endpoint;

import com.smileandpay.service.ProductService;
import com.smileandpay.webservice.service.addproductservices.AddProductRequest;
import com.smileandpay.webservice.service.addproductservices.AddProductResponse;
import com.smileandpay.webservice.service.deleteproductservices.DeleteProductRequest;
import com.smileandpay.webservice.service.deleteproductservices.DeleteProductResponse;
import com.smileandpay.webservice.service.updateproductservices.UpdateProductRequest;
import com.smileandpay.webservice.service.updateproductservices.UpdateProductResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import static com.smileandpay.helper.Constants.STATUS_NOK;

/**
 * Endpoint de Produit (product)
 * Possibilité d'ajouter, modifier ou supprimer un produit
 */
@Endpoint
public class ProductServiceEndpoints {

    private static final String ADD_TARGET_NAMESPACE = "http://www.smileandpay.com/webservice/service/addProductServices";
    private static final String UPDATE_TARGET_NAMESPACE = "http://www.smileandpay.com/webservice/service/updateProductServices";
    private static final String DELETE_TARGET_NAMESPACE = "http://www.smileandpay.com/webservice/service/deleteProductServices";

    @Autowired
    private ProductService productService;

    /**
     * Ajout d'un nouveau produit
     */
    @PayloadRoot(localPart = "AddProductRequest", namespace = ADD_TARGET_NAMESPACE)
    public @ResponsePayload
    AddProductResponse addProduct(@RequestPayload AddProductRequest product) {
        try {
            return productService.addProduct(product);
        } catch(Exception error) {
            AddProductResponse response = new AddProductResponse();
            response.setStatus(STATUS_NOK);
            response.setErrorMessage(error.getMessage());

            return response;
        }
    }

    /**
     * Modifier d'un produit à partir de son ID
     */
    @PayloadRoot(localPart = "UpdateProductRequest", namespace = UPDATE_TARGET_NAMESPACE)
    public @ResponsePayload
    UpdateProductResponse updateProduct(@RequestPayload UpdateProductRequest product) {
        try {
            return productService.updateProduct(product);
        } catch(Exception error) {
            UpdateProductResponse response = new UpdateProductResponse();
            response.setStatus(STATUS_NOK);
            response.setErrorMessage(error.getMessage());

            return response;
        }
    }

    /**
     * Supprimer un produit à partir de son ID
     */
    @PayloadRoot(localPart = "DeleteProductRequest", namespace = DELETE_TARGET_NAMESPACE)
    public @ResponsePayload
    DeleteProductResponse deleteProduct(@RequestPayload DeleteProductRequest product) {
        try {
            return productService.deleteProduct(product);
        } catch(Exception error) {
            DeleteProductResponse response = new DeleteProductResponse();
            response.setStatus(STATUS_NOK);
            response.setErrorMessage(error.getMessage());

            return response;
        }
    }


}
