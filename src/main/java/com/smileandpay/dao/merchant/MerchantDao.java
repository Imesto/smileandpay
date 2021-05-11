package com.smileandpay.dao.merchant;

import com.smileandpay.entity.MerchantEntity;

public interface MerchantDao {

	void persistMerchant(MerchantEntity merchantEntity);

	MerchantEntity findMerchantById(int id);

	void updateMerchant(MerchantEntity merchantEntity);

	void deleteMerchant(MerchantEntity merchantEntity);

}
