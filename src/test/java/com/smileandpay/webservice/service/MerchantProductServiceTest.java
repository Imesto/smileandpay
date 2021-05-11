package com.smileandpay.webservice.service;

import com.smileandpay.dao.merchant.MerchantDao;
import com.smileandpay.dao.merchantProduct.MerchantProductDao;
import com.smileandpay.dao.product.ProductDao;
import com.smileandpay.entity.MerchantEntity;
import com.smileandpay.entity.MerchantProductEntity;
import com.smileandpay.entity.ProductEntity;
import com.smileandpay.exception.EntityNotFound;
import com.smileandpay.service.MerchantProductServiceImpl;
import com.smileandpay.webservice.service.linkmerchantproductservices.LinkMerchantProductRequest;
import com.smileandpay.webservice.service.linkmerchantproductservices.LinkMerchantProductResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@ExtendWith(MockitoExtension.class)
@ContextConfiguration(
        { "/spring-config.xml", "/hibernateContext.xml" })
public class MerchantProductServiceTest {


    @InjectMocks
    private MerchantProductServiceImpl merchantProductService;

    @Mock
    private MerchantDao merchantDao;

    @Mock
    private ProductDao productDao;

    @Mock
    private MerchantProductDao merchantProductDao;

    /**
     * Association entre un marchand et un produit
     */
    @Test
    public void test_linkMerchantProduct_whenLinkMerchantAndProduct_thenSucceed() {
        // Given
        LinkMerchantProductRequest linkMerchantProductRequest = new LinkMerchantProductRequest();
        linkMerchantProductRequest.setMerchantId(1);
        linkMerchantProductRequest.setProductId(1);


        // When
        when(merchantDao.findMerchantById(1)).thenReturn(new MerchantEntity());
        when(productDao.findProductById(1)).thenReturn(new ProductEntity());
        // Then
        LinkMerchantProductResponse linkMerchantProductResponse = merchantProductService.linkMerchantProduct(linkMerchantProductRequest);

        assertEquals("OK",linkMerchantProductResponse.getStatus());
        verify(merchantProductDao,times(1)).persistMerchantProduct(any(MerchantProductEntity.class));
    }

    /**
     * Association entre un marchand manquant et un produit
     */
    @Test
    public void test_linkMerchantProduct_whenLinkMerchantNotFoundAndProduct_thenFail() {
        // Given
        LinkMerchantProductRequest linkMerchantProductRequest = new LinkMerchantProductRequest();
        linkMerchantProductRequest.setMerchantId(1);
        linkMerchantProductRequest.setProductId(1);


        // When
        when(merchantDao.findMerchantById(1)).thenReturn(null);
        // Then
        Exception exception = assertThrows(EntityNotFound.class,() -> {
            LinkMerchantProductResponse linkMerchantProductResponse = merchantProductService.linkMerchantProduct(linkMerchantProductRequest);
        });

        String expectedMessage = "Merchant not found";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    /**
     * Association entre un marchand et un produit manquant
     */
    @Test
    public void test_linkMerchantProduct_whenLinkMerchantAndProductNotFound_thenFail() {
        // Given
        LinkMerchantProductRequest linkMerchantProductRequest = new LinkMerchantProductRequest();
        linkMerchantProductRequest.setMerchantId(1);
        linkMerchantProductRequest.setProductId(1);


        // When
        when(merchantDao.findMerchantById(1)).thenReturn(new MerchantEntity());
        when(productDao.findProductById(1)).thenReturn(null);

        // Then
        Exception exception = assertThrows(EntityNotFound.class,() -> {
            LinkMerchantProductResponse linkMerchantProductResponse = merchantProductService.linkMerchantProduct(linkMerchantProductRequest);
        });
        String expectedMessage = "Product not found";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }


}
