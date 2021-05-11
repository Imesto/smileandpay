package com.smileandpay.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table (name = "merchant")
public class MerchantEntity implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "merchant_seq_gen")
	@SequenceGenerator(name = "merchant_seq_gen", sequenceName = "merchant_id_seq", allocationSize=1)
	@Column(name = "id", nullable = false)
	private int id;

	@Column(name = "create_date", nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date createDate;

	@Column(name = "name", nullable = false)
	private String name;

	@Column(name = "lastname", nullable = false)
	private String lastname;

	@Column(name = "birthdate", nullable = false)
	@Temporal(TemporalType.DATE)
	private Date birthday;

	@OneToMany(mappedBy = "merchantEntity")
	private Set<AddressEntity> addressesEntity = new HashSet<>();

	@OneToMany(mappedBy = "merchant")
	private Set<MerchantProductEntity> products = new HashSet<>();

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public Set<AddressEntity> getAddressesEntity() {
		return addressesEntity;
	}

	public void setAddressesEntity(Set<AddressEntity> addressesEntity) {
		this.addressesEntity = addressesEntity;
	}

	public Set<MerchantProductEntity> getProducts() {
		return products;
	}

	public void setProducts(Set<MerchantProductEntity> products) {
		this.products = products;
	}
}


