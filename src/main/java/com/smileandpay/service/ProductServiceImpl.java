package com.smileandpay.service;

import com.smileandpay.dao.merchantProduct.MerchantProductDao;
import com.smileandpay.dao.product.ProductDao;
import com.smileandpay.entity.ProductEntity;
import com.smileandpay.exception.EntityNotFound;
import com.smileandpay.webservice.entity.product.Product;
import com.smileandpay.webservice.service.addproductservices.AddProductRequest;
import com.smileandpay.webservice.service.addproductservices.AddProductResponse;
import com.smileandpay.webservice.service.deleteproductservices.DeleteProductRequest;
import com.smileandpay.webservice.service.deleteproductservices.DeleteProductResponse;
import com.smileandpay.webservice.service.updateproductservices.UpdateProductRequest;
import com.smileandpay.webservice.service.updateproductservices.UpdateProductResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.smileandpay.helper.Constants.*;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductDao productDao;

    @Autowired
    private MerchantProductDao merchantProductDao;


    @Override
    @Transactional
    public AddProductResponse addProduct(AddProductRequest product) {
        // Wrapper
        ProductEntity productEntity = new ProductEntity();
        this.unWrappProduct(productEntity,product.getProductDetails());

        productDao.persistProduct(productEntity);

        AddProductResponse addProductResponse = new AddProductResponse();
        addProductResponse.setStatus(STATUS_OK);
        addProductResponse.setProductId(productEntity.getId());

        return addProductResponse;
    }

    @Override
    @Transactional
    public UpdateProductResponse updateProduct(UpdateProductRequest product) {
        ProductEntity productEntity = productDao.findProductById(product.getProductId());
        if(productEntity == null) throw  new EntityNotFound(PRODUCT_NOT_FOUND);

        this.unWrappProduct(productEntity,product.getProductDetails());

        productDao.updateProduct(productEntity);

        UpdateProductResponse updateProductResponse = new UpdateProductResponse();
        updateProductResponse.setStatus(STATUS_OK);
        updateProductResponse.setProductId(productEntity.getId());

        return updateProductResponse;
    }

    @Override
    @Transactional
    public DeleteProductResponse deleteProduct(DeleteProductRequest product) {
        ProductEntity productEntity = productDao.findProductById(product.getProductId());
        if(productEntity == null) throw new EntityNotFound(PRODUCT_NOT_FOUND);

        productEntity.getMerchants().forEach( merchantProduct -> merchantProductDao.deleteMerchantProduct(merchantProduct));

        productDao.deleteProduct(productEntity);

        DeleteProductResponse deleteProductResponse = new DeleteProductResponse();
        deleteProductResponse.setStatus(STATUS_OK);
        deleteProductResponse.setProductId(productEntity.getId());

        return deleteProductResponse;
    }

    private void unWrappProduct(ProductEntity productEntity , Product product) throws RuntimeException {
        if(product.getCurrency() == null || product.getLabel() == null || product.getUnitPrice() == null
        || product.getWeight() == null || product.getHeight() == null) {
            throw new IllegalArgumentException(MISSING_ARGUMENT);
        }

        productEntity.setCurrency(product.getCurrency());
        productEntity.setLabel(product.getLabel());
        productEntity.setUnitPrice(product.getUnitPrice());
        productEntity.setWeight(product.getWeight());
        productEntity.setHeight(product.getHeight());
    }
}
