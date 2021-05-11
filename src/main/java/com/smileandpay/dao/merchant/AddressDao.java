package com.smileandpay.dao.merchant;

import com.smileandpay.entity.AddressEntity;

public interface AddressDao {

	void persistAddress(AddressEntity addressEntity);

	void deleteAddress(AddressEntity addressEntity);

}
