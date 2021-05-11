package com.smileandpay.entity;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table (name = "address")
public class AddressEntity implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "address_seq_gen")
	@SequenceGenerator(name = "address_seq_gen", sequenceName = "address_id_seq", allocationSize=1)
	@Column(name = "id", nullable = false)
	private int id;

	@Column(name = "number", nullable = false)
	private int number;

	@Column(name = "street", nullable = false)
	private String street;

	@Column(name = "zipcode", nullable = false)
	private String zipCode;

	@ManyToOne
	@JoinColumn (name = "merchant_id")
	private MerchantEntity merchantEntity;


	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	public MerchantEntity getMerchantEntity() {
		return merchantEntity;
	}

	public void setMerchantEntity(MerchantEntity merchantEntity) {
		this.merchantEntity = merchantEntity;
	}
}


