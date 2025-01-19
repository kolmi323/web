package ru.gnezdilov.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class ServiceFactoryTest {
    @Test
    public void getInstance_ReturnNotNull() {
        assertNotNull(ServiceFactory.getInstance());
    }

    @Test
    public void getAuthService_ReturnNotNull() {
        assertNotNull(ServiceFactory.getInstance().getAuthService());
    }

    @Test
    public void getAccountService_ReturnNotNull() {
        assertNotNull(ServiceFactory.getInstance().getAccountService());
    }

    @Test
    public void getTypeTransactionService_ReturnNotNull() {
        assertNotNull(ServiceFactory.getInstance().getTypeTransactionService());
    }
}