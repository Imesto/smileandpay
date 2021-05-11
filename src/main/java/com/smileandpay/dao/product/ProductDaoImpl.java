package com.smileandpay.dao.product;

import com.smileandpay.entity.ProductEntity;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Repository
public class ProductDaoImpl implements ProductDao {

    @Autowired
    SessionFactory sessionFactory;

    @Override
    public void persistProduct(ProductEntity ProductEntity) {
            ProductEntity.setCreateDate(new Date());
            sessionFactory.getCurrentSession().persist(ProductEntity);
    }

    @Override
    public ProductEntity findProductById(int id) {
        return sessionFactory.getCurrentSession().get(ProductEntity.class, id);
    }

    @Override
    public void updateProduct(ProductEntity ProductEntity) {
        sessionFactory.getCurrentSession().update(ProductEntity);
    }

    @Override
    public void deleteProduct(ProductEntity ProductEntity) {
        sessionFactory.getCurrentSession().delete(ProductEntity);
    }
}
