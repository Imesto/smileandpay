package com.smileandpay.webservice.service;

import com.smileandpay.dao.merchantProduct.MerchantProductDao;
import com.smileandpay.dao.product.ProductDao;
import com.smileandpay.entity.MerchantProductEntity;
import com.smileandpay.entity.ProductEntity;
import com.smileandpay.exception.EntityNotFound;
import com.smileandpay.service.ProductServiceImpl;
import com.smileandpay.webservice.entity.product.Product;
import com.smileandpay.webservice.service.addproductservices.AddProductRequest;
import com.smileandpay.webservice.service.addproductservices.AddProductResponse;
import com.smileandpay.webservice.service.deleteproductservices.DeleteProductRequest;
import com.smileandpay.webservice.service.deleteproductservices.DeleteProductResponse;
import com.smileandpay.webservice.service.updatemerchantservices.UpdateMerchantResponse;
import com.smileandpay.webservice.service.updateproductservices.UpdateProductRequest;
import com.smileandpay.webservice.service.updateproductservices.UpdateProductResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@ExtendWith(MockitoExtension.class)
@ContextConfiguration(
        { "/spring-config.xml", "/hibernateContext.xml" })
public class ProductServiceTest {


    @InjectMocks
    private ProductServiceImpl productService;

    @Mock
    private ProductDao productDao;

    @Mock
    private MerchantProductDao merchantProductDao;

    private Product generateProduct() {

        Product product = new Product();
        product.setCurrency("euros");
        product.setHeight(BigDecimal.valueOf(12.0));
        product.setWeight(BigDecimal.valueOf(15.0));
        product.setLabel("Coffre");
        product.setUnitPrice(BigDecimal.valueOf(9.99));


        return product;
    }

    /**
     * Ajout d'un produit
     */
    @Test
    public void test_addProduct_whenAddProduct_thenSucceed() {
        // Given
        Product product = this.generateProduct();
        AddProductRequest addProductRequest = new AddProductRequest();
        addProductRequest.setProductDetails(product);


        // When

        doAnswer(invocationOnMock -> {
            ProductEntity productEntity = invocationOnMock.getArgument(0); // merchantEntity
            productEntity.setId(1);
            return null;
        }).when(productDao).persistProduct(any(ProductEntity.class));


        // Then
        AddProductResponse addProductResponse = productService.addProduct(addProductRequest);
        assertEquals(1,addProductResponse.getProductId());
        verify(productDao,times(1)).persistProduct(any(ProductEntity.class));
    }


    /**
     * Ajout d'un produit avec des params manquants
     */
    @Test
    public void test_addProduct_whenAddProductWithMissingParam_thenFail() {
        // Given
        Product product = this.generateProduct();
        product.setWeight(null);

        AddProductRequest addProductRequest = new AddProductRequest();
        addProductRequest.setProductDetails(product);


        // When

        // Then

        Exception exception = assertThrows(IllegalArgumentException.class,() -> {
            AddProductResponse addProductResponse = productService.addProduct(addProductRequest);
        });

        String expectedMessage = "Missing argument";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    /**
     * Mise à jour d'un produit existant
     */
    @Test
    public void test_updateProduct_whenUpdateProduct_thenSucceed() {
        // Given
        Product product = this.generateProduct();
        UpdateProductRequest updateProductRequest = new UpdateProductRequest();
        updateProductRequest.setProductId(1);
        updateProductRequest.setProductDetails(product);

        ProductEntity productEntity = new ProductEntity();

        // When

        when(productDao.findProductById(1)).thenReturn(productEntity);

        doAnswer(invocationOnMock -> {
            ProductEntity productEntity1 = invocationOnMock.getArgument(0); // param : merchantEntity
            productEntity1.setId(1);
            return null;
        }).when(productDao).updateProduct(any(ProductEntity.class));

        // Then
        UpdateProductResponse updateProductResponse = productService.updateProduct(updateProductRequest);

        assertEquals(1,updateProductResponse.getProductId());
        verify(productDao,times(1)).updateProduct(any(ProductEntity.class));
    }

    /**
     * Mise à jour d'un produit inexistant  (Id inexistant)
     */
    @Test
    public void test_updateProduct_whenUpdateProductWrongId_thenFail() {
        // Given
        Product product = this.generateProduct();
        UpdateProductRequest updateProductRequest = new UpdateProductRequest();
        updateProductRequest.setProductId(1);
        updateProductRequest.setProductDetails(product);

        // When

        when(productDao.findProductById(1)).thenReturn(null);

        // Then

        Exception exception = assertThrows(EntityNotFound.class,() -> {
            UpdateProductResponse updateProductResponse = productService.updateProduct(updateProductRequest);
        });

        String expectedMessage = "Product not found";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    /**
     * Modification d'un produit avec des params manquants
     */
    @Test
    public void test_updateProduct_whenUpdateProductWithMissingParam_thenFail() {
        // Given
        Product product = this.generateProduct();
        product.setWeight(null);

        UpdateProductRequest updateProductRequest = new UpdateProductRequest();
        updateProductRequest.setProductId(1);
        updateProductRequest.setProductDetails(product);

        ProductEntity productEntity = new ProductEntity();
        // When

        when(productDao.findProductById(1)).thenReturn(productEntity);

        // Then

        Exception exception = assertThrows(IllegalArgumentException.class,() -> {
            UpdateProductResponse updateProductResponse = productService.updateProduct(updateProductRequest);
        });

        String expectedMessage = "Missing argument";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    /**
     * Suppression d'un produit existant
     */
    @Test
    public void test_deleteProduct_whenDeleteProductAndLink_thenSucceed()  {
        // Given
        DeleteProductRequest deleteProductRequest = new DeleteProductRequest();
        deleteProductRequest.setProductId(1);

        ProductEntity productEntity = new ProductEntity();

        Set<MerchantProductEntity> setMerchantProductEntity = new HashSet<>();
        MerchantProductEntity merchantProductEntity = new MerchantProductEntity();
        setMerchantProductEntity.add(merchantProductEntity);
        productEntity.setMerchants(setMerchantProductEntity);

        // When

        when(productDao.findProductById(1)).thenReturn(productEntity);


        // Then
        DeleteProductResponse deleteProductResponse = productService.deleteProduct(deleteProductRequest);

        verify(merchantProductDao,times(1)).deleteMerchantProduct(any(MerchantProductEntity.class));
        verify(productDao,times(1)).deleteProduct(any(ProductEntity.class));
    }

    /**
     * Suppression d'un produit existant avec un mauvais Id ou inexistant
     */
    @Test
    public void test_deleteProduct_whenDeleteProductAndLink_thenFail()  {
        // Given
        DeleteProductRequest deleteProductRequest = new DeleteProductRequest();
        deleteProductRequest.setProductId(1);

        ProductEntity productEntity = new ProductEntity();

        Set<MerchantProductEntity> setMerchantProductEntity = new HashSet<>();
        MerchantProductEntity merchantProductEntity = new MerchantProductEntity();
        setMerchantProductEntity.add(merchantProductEntity);
        productEntity.setMerchants(setMerchantProductEntity);

        // When

        when(productDao.findProductById(1)).thenReturn(null);

        // Then

        Exception exception = assertThrows(EntityNotFound.class,() -> {
            DeleteProductResponse deleteProductResponse = productService.deleteProduct(deleteProductRequest);
        });

        String expectedMessage = "Product not found";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

}
