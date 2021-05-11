package com.smileandpay.service;

import com.smileandpay.dao.merchant.AddressDao;
import com.smileandpay.dao.merchant.MerchantDao;
import com.smileandpay.dao.merchantProduct.MerchantProductDao;
import com.smileandpay.entity.AddressEntity;
import com.smileandpay.entity.MerchantEntity;
import com.smileandpay.exception.EntityNotFound;
import com.smileandpay.webservice.entity.address.Address;
import com.smileandpay.webservice.entity.merchant.Merchant;
import com.smileandpay.webservice.service.addmerchantservices.AddMerchantRequest;
import com.smileandpay.webservice.service.addmerchantservices.AddMerchantResponse;
import com.smileandpay.webservice.service.deletemerchantservices.DeleteMerchantRequest;
import com.smileandpay.webservice.service.deletemerchantservices.DeleteMerchantResponse;
import com.smileandpay.webservice.service.updatemerchantservices.UpdateMerchantRequest;
import com.smileandpay.webservice.service.updatemerchantservices.UpdateMerchantResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.smileandpay.helper.Constants.*;

@Service
public class MerchantServiceImpl implements MerchantService {

    @Autowired
    private MerchantDao merchantDao;

    @Autowired
    private AddressDao addressDao;

    @Autowired
    private MerchantProductDao merchantProductDao;

    @Override
    @Transactional
    public AddMerchantResponse addMerchant (AddMerchantRequest merchant) throws RuntimeException {

        // Wrapper
        MerchantEntity merchantEntity = new MerchantEntity();
        this.unWrappMerchant(merchantEntity,merchant.getMerchantDetails());

        merchantDao.persistMerchant(merchantEntity);
        this.persistAddress(merchantEntity,merchant.getAddressDetails());

        AddMerchantResponse addMerchantResponse = new AddMerchantResponse();
        addMerchantResponse.setStatus(STATUS_OK);
        addMerchantResponse.setMerchantId(merchantEntity.getId());

        return addMerchantResponse;
    }


    @Override
    @Transactional
    public UpdateMerchantResponse updateMerchant(UpdateMerchantRequest merchant) {

        MerchantEntity merchantEntity = merchantDao.findMerchantById(merchant.getMerchantId());
        if(merchantEntity == null) throw  new EntityNotFound(MERCHANT_NOT_FOUND);

        this.unWrappMerchant(merchantEntity,merchant.getMerchantDetails());

        merchantEntity.getAddressesEntity().forEach(address -> addressDao.deleteAddress(address));
        this.persistAddress(merchantEntity,merchant.getAddressDetails());
        merchantDao.updateMerchant(merchantEntity);

        UpdateMerchantResponse updateMerchantResponse = new UpdateMerchantResponse();
        updateMerchantResponse.setStatus(STATUS_OK);
        updateMerchantResponse.setMerchantId(merchantEntity.getId());
        return updateMerchantResponse;
    }

    @Override
    @Transactional
    public DeleteMerchantResponse deleteMerchant(DeleteMerchantRequest merchant) {

        MerchantEntity merchantEntity = merchantDao.findMerchantById(merchant.getMerchantId());
        if(merchantEntity == null) throw  new EntityNotFound(MERCHANT_NOT_FOUND);

        merchantEntity.getProducts().forEach( merchantProduct -> merchantProductDao.deleteMerchantProduct(merchantProduct));


        merchantEntity.getAddressesEntity().forEach(address -> addressDao.deleteAddress(address));

        merchantDao.deleteMerchant(merchantEntity);

        DeleteMerchantResponse deleteMerchantResponse = new DeleteMerchantResponse();
        deleteMerchantResponse.setStatus(STATUS_OK);
        deleteMerchantResponse.setMerchantId(merchantEntity.getId());

        return deleteMerchantResponse;
    }

    private void persistAddress(MerchantEntity merchantEntity, List<Address> addressDetails) {
        addressDetails.forEach(address -> {
            AddressEntity addressEntity = new AddressEntity();
            this.unWrappAddress(addressEntity,address);
            addressEntity.setMerchantEntity(merchantEntity);

            addressDao.persistAddress(addressEntity);
        });
    }

    private void unWrappMerchant(MerchantEntity merchantEntity, Merchant merchant) throws IllegalArgumentException {
        if(merchant.getName() == null || merchant.getLastname() == null || merchant.getBirthday() == null) {
            throw new IllegalArgumentException(MISSING_ARGUMENT);
        }

        merchantEntity.setName(merchant.getName());
        merchantEntity.setLastname(merchant.getLastname());
        merchantEntity.setBirthday(merchant.getBirthday().toGregorianCalendar().getTime());
    }

    private void unWrappAddress(AddressEntity addressEntity, Address address) throws IllegalArgumentException {
        if(address.getNumber() == 0 || address.getStreet() == null || address.getZipcode() == null) {
            throw new IllegalArgumentException(MISSING_ARGUMENT);
        }

        addressEntity.setNumber(address.getNumber());
        addressEntity.setStreet(address.getStreet());
        addressEntity.setZipCode(address.getZipcode());
    }

}
