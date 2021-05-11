package com.smileandpay.webservice.service;

import com.smileandpay.dao.merchant.AddressDao;
import com.smileandpay.dao.merchant.MerchantDao;
import com.smileandpay.dao.merchantProduct.MerchantProductDao;
import com.smileandpay.entity.AddressEntity;
import com.smileandpay.entity.MerchantEntity;
import com.smileandpay.entity.MerchantProductEntity;
import com.smileandpay.exception.EntityNotFound;
import com.smileandpay.service.MerchantServiceImpl;
import com.smileandpay.webservice.entity.address.Address;
import com.smileandpay.webservice.entity.merchant.Merchant;
import com.smileandpay.webservice.service.addmerchantservices.AddMerchantRequest;
import com.smileandpay.webservice.service.addmerchantservices.AddMerchantResponse;
import com.smileandpay.webservice.service.deletemerchantservices.DeleteMerchantRequest;
import com.smileandpay.webservice.service.deletemerchantservices.DeleteMerchantResponse;
import com.smileandpay.webservice.service.updatemerchantservices.UpdateMerchantRequest;
import com.smileandpay.webservice.service.updatemerchantservices.UpdateMerchantResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@ExtendWith(MockitoExtension.class)
@ContextConfiguration(
        { "/spring-config.xml", "/hibernateContext.xml" })
public class MerchantServiceTest {

    @InjectMocks
    private MerchantServiceImpl merchantService;

    @Mock
    private MerchantDao merchantDao;

    @Mock
    private AddressDao addressDao;

    @Mock
    private MerchantProductDao merchantProductDao;

    private Merchant generateMerchant() throws ParseException, DatatypeConfigurationException {
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date date = format.parse("2014-04-24");
        GregorianCalendar cal = new GregorianCalendar();
        cal.setTime(date);

        XMLGregorianCalendar xmlGregCal =  DatatypeFactory.newInstance().newXMLGregorianCalendar(cal);


        Merchant merchant = new Merchant();
        merchant.setName("Thierry");
        merchant.setLastname("Dupont");
        merchant.setBirthday(xmlGregCal);


        return merchant;
    }

    private void addAddress(List<Address> listAddress,int number,String street,String zipCode) {
        Address address = new Address();
        address.setNumber(number);
        address.setStreet(street);
        address.setZipcode(zipCode);

        listAddress.add(address);
    }

    /**
     * Ajout d'un marchand sans adresse
     */
    @Test
    public void test_addMerchant_whenAddMerchantWithoutAddress_thenSucceed() throws ParseException, DatatypeConfigurationException {
        // Given
        Merchant merchant = this.generateMerchant();
        AddMerchantRequest addMerchantRequest = new AddMerchantRequest();
        addMerchantRequest.setMerchantDetails(merchant);


        // When

        doAnswer(invocationOnMock -> {
            MerchantEntity merchantEntity = invocationOnMock.getArgument(0); // merchantEntity
            merchantEntity.setId(1);
            return null;
        }).when(merchantDao).persistMerchant(any(MerchantEntity.class));


        // Then
        AddMerchantResponse addMerchantResponse = merchantService.addMerchant(addMerchantRequest);
        assertEquals("OK",addMerchantResponse.getStatus());
        assertEquals(1,addMerchantResponse.getMerchantId());
        verify(merchantDao,times(1)).persistMerchant(any(MerchantEntity.class));
    }

    /**
     * Ajout d'un marchand avec deux adresses
     */
    @Test
    public void test_addMerchant_whenAddMerchantWithTwoAddress_thenSucceed() throws ParseException, DatatypeConfigurationException {
        // Given
        Merchant merchant = this.generateMerchant();
        AddMerchantRequest addMerchantRequest = new AddMerchantRequest();
        addMerchantRequest.setMerchantDetails(merchant);
        this.addAddress(addMerchantRequest.getAddressDetails(),12,"Rue de paris","75009");
        this.addAddress(addMerchantRequest.getAddressDetails(),25,"Rue de bordeaux","75010");

        final int[] id = {0};

        // When

        doAnswer(invocationOnMock -> {
            MerchantEntity merchantEntity = invocationOnMock.getArgument(0); // param : merchantEntity
            merchantEntity.setId(1);
            return null;
        }).when(merchantDao).persistMerchant(any(MerchantEntity.class));

        doAnswer(invocationOnMock -> {
            AddressEntity addressEntity = invocationOnMock.getArgument(0); // param : addressEntity
            addressEntity.setId(id[0]++);
            return null;
        }).when(addressDao).persistAddress(any(AddressEntity.class));


        // Then
        AddMerchantResponse addMerchantResponse = merchantService.addMerchant(addMerchantRequest);
        assertEquals("OK",addMerchantResponse.getStatus());
        assertEquals(1,addMerchantResponse.getMerchantId());
        verify(merchantDao,times(1)).persistMerchant(any(MerchantEntity.class));
        verify(addressDao,times(2)).persistAddress(any(AddressEntity.class));
    }

    /**
     * Ajout d'un marchand avec un param manquant
     * S'il y a une erreur dans la date de naissance, il sera null et fera parti des arguments manquants
     */
    @Test
    public void test_addMerchant_whenAddMerchantMissingParam_thenFail() throws ParseException, DatatypeConfigurationException {
        // Given
        Merchant merchant = this.generateMerchant();
        merchant.setLastname(null);

        AddMerchantRequest addMerchantRequest = new AddMerchantRequest();
        addMerchantRequest.setMerchantDetails(merchant);

        // When

        // Then

        Exception exception = assertThrows(IllegalArgumentException.class,() -> {
            AddMerchantResponse addMerchantResponse = merchantService.addMerchant(addMerchantRequest);
        });


        String expectedMessage = "Missing argument";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    /**
     * Ajout d'un marchand avec une erreur dans l'adressse
     * Exemple : Si le numéro est un string sa valeur sera 0
     */
    @Test
    public void test_addMerchant_whenAddMerchantWithWrongAddressParam_thenFail() throws ParseException, DatatypeConfigurationException {
        // Given
        Merchant merchant = this.generateMerchant();

        AddMerchantRequest addMerchantRequest = new AddMerchantRequest();
        addMerchantRequest.setMerchantDetails(merchant);
        this.addAddress(addMerchantRequest.getAddressDetails(),0,"Rue de paris","75009");

        // When

        doAnswer(invocationOnMock -> {
            MerchantEntity merchantEntity = invocationOnMock.getArgument(0); // param : merchantEntity
            merchantEntity.setId(1);
            return null;
        }).when(merchantDao).persistMerchant(any(MerchantEntity.class));

        // Then

        Exception exception = assertThrows(IllegalArgumentException.class,() -> {
            AddMerchantResponse addMerchantResponse = merchantService.addMerchant(addMerchantRequest);
        });


        String expectedMessage = "Missing argument";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }


    /**
     * Mise à jour d'un marchand
     */
    @Test
    public void test_updateMerchant_whenUpdateMerchantWithTwoAddress_thenSucceed() throws ParseException, DatatypeConfigurationException {
        // Given
        Merchant merchant = this.generateMerchant();

        UpdateMerchantRequest updateMerchantRequest = new UpdateMerchantRequest();
        updateMerchantRequest.setMerchantId(1);
        updateMerchantRequest.setMerchantDetails(merchant);

        this.addAddress(updateMerchantRequest.getAddressDetails(),12,"Rue de paris","75009");
        this.addAddress(updateMerchantRequest.getAddressDetails(),25,"Rue de bordeaux","75010");

        final int[] id = {0};

        MerchantEntity merchantEntity = new MerchantEntity();
        merchantEntity.setId(1);
        merchantEntity.setBirthday(new Date());
        merchantEntity.setLastname("Thomas");
        merchantEntity.setName("Hon");

        AddressEntity addressEntity = new AddressEntity();
        Set<AddressEntity> setAddressEntity = new HashSet<>();
        setAddressEntity.add(addressEntity);
        merchantEntity.setAddressesEntity(setAddressEntity);

        // When

        when(merchantDao.findMerchantById(1)).thenReturn(merchantEntity);

        doAnswer(invocationOnMock -> {
            MerchantEntity merchantEntity1 = invocationOnMock.getArgument(0); // param : merchantEntity
            merchantEntity1.setId(1);
            return null;
        }).when(merchantDao).updateMerchant(any(MerchantEntity.class));

        doAnswer(invocationOnMock -> {
            AddressEntity addressEntity1 = invocationOnMock.getArgument(0); // param : addressEntity
            addressEntity1.setId(id[0]++);
            return null;
        }).when(addressDao).persistAddress(any(AddressEntity.class));

        // Then
        UpdateMerchantResponse updateMerchantResponse = merchantService.updateMerchant(updateMerchantRequest);

        assertEquals(1,updateMerchantResponse.getMerchantId());
        verify(merchantDao,times(1)).updateMerchant(any(MerchantEntity.class));
        verify(addressDao,times(2)).persistAddress(any(AddressEntity.class));
        verify(addressDao,times(1)).deleteAddress(any(AddressEntity.class));
    }

    /**
     * Mise à jour d'un marchand avec un ID introuvable
     */
    @Test
    public void test_updateMerchant_whenUpdateMerchantWithWrongId_thenFail() throws ParseException, DatatypeConfigurationException {
        // Given
        Merchant merchant = this.generateMerchant();

        UpdateMerchantRequest updateMerchantRequest = new UpdateMerchantRequest();
        updateMerchantRequest.setMerchantId(1);
        updateMerchantRequest.setMerchantDetails(merchant);

        this.addAddress(updateMerchantRequest.getAddressDetails(),12,"Rue de paris","75009");
        this.addAddress(updateMerchantRequest.getAddressDetails(),25,"Rue de bordeaux","75010");

        // When

        when(merchantDao.findMerchantById(1)).thenReturn(null);

        // Then

        Exception exception = assertThrows(EntityNotFound.class,() -> {
            UpdateMerchantResponse updateMerchantResponse = merchantService.updateMerchant(updateMerchantRequest);
        });

        String expectedMessage = "Merchant not found";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    /**
     * Mise à jour d'un marchand avec des params manquants
     */
    @Test
    public void test_updateMerchant_whenUpdateMerchantMissingParam_thenFail() throws ParseException, DatatypeConfigurationException {
        // Given
        Merchant merchant = this.generateMerchant();
        merchant.setLastname(null);

        UpdateMerchantRequest updateMerchantRequest = new UpdateMerchantRequest();
        updateMerchantRequest.setMerchantId(1);
        updateMerchantRequest.setMerchantDetails(merchant);

        this.addAddress(updateMerchantRequest.getAddressDetails(),12,"Rue de paris","75009");
        this.addAddress(updateMerchantRequest.getAddressDetails(),25,"Rue de bordeaux","75010");

        MerchantEntity merchantEntity = new MerchantEntity();
        merchantEntity.setId(1);
        merchantEntity.setBirthday(new Date());
        merchantEntity.setLastname("Thomas");
        merchantEntity.setName("Hon");

        AddressEntity addressEntity = new AddressEntity();
        Set<AddressEntity> setAddressEntity = new HashSet<>();
        setAddressEntity.add(addressEntity);
        merchantEntity.setAddressesEntity(setAddressEntity);

        // When
        when(merchantDao.findMerchantById(1)).thenReturn(merchantEntity);


        // Then

        Exception exception = assertThrows(IllegalArgumentException.class,() -> {
            UpdateMerchantResponse updateMerchantResponse = merchantService.updateMerchant(updateMerchantRequest);
        });

        String expectedMessage = "Missing argument";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }


    /**
     * Mise à jour d'un marchand avec des params d'adresse manquants
     */
    @Test
    public void test_updateMerchant_whenUpdateMerchantMissingAddressParam_thenFail() throws ParseException, DatatypeConfigurationException {
        // Given
        Merchant merchant = this.generateMerchant();

        UpdateMerchantRequest updateMerchantRequest = new UpdateMerchantRequest();
        updateMerchantRequest.setMerchantId(1);
        updateMerchantRequest.setMerchantDetails(merchant);

        this.addAddress(updateMerchantRequest.getAddressDetails(),0,"Rue de paris","75009");
        this.addAddress(updateMerchantRequest.getAddressDetails(),25,"Rue de bordeaux","75010");

        MerchantEntity merchantEntity = new MerchantEntity();
        merchantEntity.setId(1);
        merchantEntity.setBirthday(new Date());
        merchantEntity.setLastname("Thomas");
        merchantEntity.setName("Hon");

        AddressEntity addressEntity = new AddressEntity();
        Set<AddressEntity> setAddressEntity = new HashSet<>();
        setAddressEntity.add(addressEntity);
        merchantEntity.setAddressesEntity(setAddressEntity);

        // When
        when(merchantDao.findMerchantById(1)).thenReturn(merchantEntity);


        // Then

        Exception exception = assertThrows(IllegalArgumentException.class,() -> {
            UpdateMerchantResponse updateMerchantResponse = merchantService.updateMerchant(updateMerchantRequest);
        });

        String expectedMessage = "Missing argument";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));


    }


    /**
     * Suppression d'un marchand existant
     */
    @Test
    public void test_deleteMerchant_whenDeleteMerchantAndLink_thenSucceed() {
        // Given
        DeleteMerchantRequest deleteMerchantRequest = new DeleteMerchantRequest();
        deleteMerchantRequest.setMerchantId(1);

        MerchantEntity merchantEntity = new MerchantEntity();
        merchantEntity.setId(1);
        merchantEntity.setBirthday(new Date());
        merchantEntity.setLastname("Thomas");
        merchantEntity.setName("Hon");

        AddressEntity addressEntity = new AddressEntity();
        Set<AddressEntity> setAddressEntity = new HashSet<>();
        setAddressEntity.add(addressEntity);
        merchantEntity.setAddressesEntity(setAddressEntity);

        Set<MerchantProductEntity> setMerchantProductEntity = new HashSet<>();
        MerchantProductEntity merchantProductEntity = new MerchantProductEntity();
        setMerchantProductEntity.add(merchantProductEntity);
        merchantEntity.setProducts(setMerchantProductEntity);

        // When

        when(merchantDao.findMerchantById(1)).thenReturn(merchantEntity);


        // Then
        DeleteMerchantResponse deleteMerchantResponse = merchantService.deleteMerchant(deleteMerchantRequest);

        verify(merchantProductDao,times(1)).deleteMerchantProduct(any(MerchantProductEntity.class));
        verify(addressDao,times(1)).deleteAddress(any(AddressEntity.class));
        verify(merchantDao,times(1)).deleteMerchant(any(MerchantEntity.class));
    }


    /**
     * Suppression d'un marchand avec un ID introuvable
     */
    @Test
    public void test_deleteMerchant_whenDeleteMerchantWithWrongId_thenFail() {
        // Given
        DeleteMerchantRequest deleteMerchantRequest = new DeleteMerchantRequest();
        deleteMerchantRequest.setMerchantId(1);

        // When

        when(merchantDao.findMerchantById(1)).thenReturn(null);

        // Then

        Exception exception = assertThrows(EntityNotFound.class,() -> {
            DeleteMerchantResponse deleteMerchantResponse = merchantService.deleteMerchant(deleteMerchantRequest);
        });

        String expectedMessage = "Merchant not found";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

}
