package com.smileandpay.entity;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class MerchantProductId implements Serializable {

	@Column(name = "merchant_id", nullable = false)
	private int merchantId;

	@Column(name = "product_id", nullable = false)
	private int productId;

	public int getMerchantId() {
		return merchantId;
	}

	public void setMerchantId(int merchantId) {
		this.merchantId = merchantId;
	}

	public int getProductId() {
		return productId;
	}

	public void setProductId(int productId) {
		this.productId = productId;
	}
}


