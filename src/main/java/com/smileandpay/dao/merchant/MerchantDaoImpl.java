package com.smileandpay.dao.merchant;

import com.smileandpay.entity.MerchantEntity;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Repository
public class MerchantDaoImpl implements MerchantDao {

    @Autowired
    SessionFactory sessionFactory;

    @Override
    public void persistMerchant(MerchantEntity merchantEntity) {
            merchantEntity.setCreateDate(new Date());
            sessionFactory.getCurrentSession().persist(merchantEntity);
    }

    @Override
    public MerchantEntity findMerchantById(int id) {
        return sessionFactory.getCurrentSession().get(MerchantEntity.class, id);
    }

    @Override
    public void updateMerchant(MerchantEntity merchantEntity) {
        sessionFactory.getCurrentSession().update(merchantEntity);
    }

    @Override
    public void deleteMerchant(MerchantEntity merchantEntity) {
        sessionFactory.getCurrentSession().delete(merchantEntity);
    }
}
