package com.smileandpay.dao.merchantProduct;

import com.smileandpay.entity.MerchantProductEntity;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Repository
public class MerchantProductDaoImpl implements MerchantProductDao {

    @Autowired
    SessionFactory sessionFactory;

    @Override
    public void persistMerchantProduct(MerchantProductEntity merchantProductEntity) {
        merchantProductEntity.setCreateDate(new Date());
        sessionFactory.getCurrentSession().persist(merchantProductEntity);
    }

    @Override
    public void deleteMerchantProduct(MerchantProductEntity merchantProductEntity) {
        sessionFactory.getCurrentSession().delete(merchantProductEntity);
    }
}
