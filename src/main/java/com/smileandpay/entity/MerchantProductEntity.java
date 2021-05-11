package com.smileandpay.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table (name = "merchant_product")
public class MerchantProductEntity implements Serializable {

	@EmbeddedId
	private MerchantProductId merchantProductId;

	@ManyToOne(fetch = FetchType.LAZY)
	@MapsId("merchantId")
	private MerchantEntity merchant;

	@ManyToOne(fetch = FetchType.LAZY)
	@MapsId("productId")
	private ProductEntity product;

	@Column(name = "create_date", nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date createDate;

	public MerchantProductId getMerchantProductId() {
		return merchantProductId;
	}

	public void setMerchantProductId(MerchantProductId merchantProductId) {
		this.merchantProductId = merchantProductId;
	}

	public MerchantEntity getMerchant() {
		return merchant;
	}

	public void setMerchant(MerchantEntity merchant) {
		this.merchant = merchant;
	}

	public ProductEntity getProduct() {
		return product;
	}

	public void setProduct(ProductEntity product) {
		this.product = product;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
}


