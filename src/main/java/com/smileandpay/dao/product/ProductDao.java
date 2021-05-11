package com.smileandpay.dao.product;

import com.smileandpay.entity.ProductEntity;

public interface ProductDao {

	void persistProduct(ProductEntity ProductEntity);

	ProductEntity findProductById(int id);

	void updateProduct(ProductEntity ProductEntity);

	void deleteProduct(ProductEntity ProductEntity);

}
