package com.smileandpay.dao.merchantProduct;

import com.smileandpay.entity.MerchantProductEntity;

public interface MerchantProductDao {

	void persistMerchantProduct(MerchantProductEntity merchantProductEntity);

	void deleteMerchantProduct(MerchantProductEntity merchantProductEntity);

}
