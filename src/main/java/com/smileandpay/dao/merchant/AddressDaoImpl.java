package com.smileandpay.dao.merchant;

import com.smileandpay.entity.AddressEntity;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class AddressDaoImpl implements AddressDao {

    @Autowired
    SessionFactory sessionFactory;

    @Override
    public void persistAddress(AddressEntity addressEntity) {
            sessionFactory.getCurrentSession().persist(addressEntity);
    }

    @Override
    public void deleteAddress(AddressEntity addressEntity) {
        sessionFactory.getCurrentSession().delete(addressEntity);
    }

}
