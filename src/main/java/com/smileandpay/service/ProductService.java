package com.smileandpay.service;


import com.smileandpay.webservice.service.addproductservices.AddProductRequest;
import com.smileandpay.webservice.service.addproductservices.AddProductResponse;
import com.smileandpay.webservice.service.deleteproductservices.DeleteProductRequest;
import com.smileandpay.webservice.service.deleteproductservices.DeleteProductResponse;
import com.smileandpay.webservice.service.updateproductservices.UpdateProductRequest;
import com.smileandpay.webservice.service.updateproductservices.UpdateProductResponse;

public interface ProductService {

    /**
     * Ajouter un nouveau produit
     */
    AddProductResponse addProduct(AddProductRequest product);

    /**
     * Modifier un produit existant
     */
    UpdateProductResponse updateProduct(UpdateProductRequest product);

    /**
     * Supprimer un produit existant
     */
    DeleteProductResponse deleteProduct(DeleteProductRequest product);
}
