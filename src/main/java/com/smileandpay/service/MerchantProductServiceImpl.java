package com.smileandpay.service;

import com.smileandpay.dao.merchant.MerchantDao;
import com.smileandpay.dao.merchantProduct.MerchantProductDao;
import com.smileandpay.dao.product.ProductDao;
import com.smileandpay.entity.MerchantEntity;
import com.smileandpay.entity.MerchantProductEntity;
import com.smileandpay.entity.MerchantProductId;
import com.smileandpay.entity.ProductEntity;
import com.smileandpay.exception.EntityNotFound;
import com.smileandpay.webservice.service.linkmerchantproductservices.LinkMerchantProductRequest;
import com.smileandpay.webservice.service.linkmerchantproductservices.LinkMerchantProductResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.smileandpay.helper.Constants.*;

@Service
public class MerchantProductServiceImpl implements MerchantProductService {

    @Autowired
    private MerchantDao merchantDao;

    @Autowired
    private ProductDao productDao;

    @Autowired
    private MerchantProductDao merchantProductDao;

    @Override
    @Transactional
    public LinkMerchantProductResponse linkMerchantProduct(LinkMerchantProductRequest linkMerchantProduct) {

        MerchantEntity merchantEntity = merchantDao.findMerchantById(linkMerchantProduct.getMerchantId());
        if(merchantEntity == null)
            throw  new EntityNotFound(MERCHANT_NOT_FOUND);
        ProductEntity productEntity = productDao.findProductById(linkMerchantProduct.getProductId());
        if(productEntity == null)
            throw  new EntityNotFound(PRODUCT_NOT_FOUND);


        MerchantProductEntity merchantProductEntity = new MerchantProductEntity();
        MerchantProductId merchantProductId = new MerchantProductId();
        merchantProductId.setMerchantId(linkMerchantProduct.getMerchantId());
        merchantProductId.setProductId(linkMerchantProduct.getProductId());
        merchantProductEntity.setMerchantProductId(merchantProductId);

        merchantProductEntity.setMerchant(merchantEntity);
        merchantProductEntity.setProduct(productEntity);
        merchantProductDao.persistMerchantProduct(merchantProductEntity);

        LinkMerchantProductResponse linkMerchantProductResponse = new LinkMerchantProductResponse();
        linkMerchantProductResponse.setStatus(STATUS_OK);
        linkMerchantProductResponse.setMerchantProductId(1);
        return linkMerchantProductResponse;
    }
}
