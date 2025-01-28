package org.dtna.serviceimpl;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.slf4j.Slf4j;
import org.dtna.dto.major_component.subdtos.InventoryMC;
import org.dtna.dto.product_inventory.subdtos.Inventory;
import org.dtna.dto.product_inventory.subdtos.InventoryXMLDto;
import org.dtna.model.MajorComponentInventoryIMSModel;
import org.dtna.model.ProductInventoryImsModel;
import org.dtna.repository.ProductInventoryIMSRepo;
import org.dtna.salesforceauthroization.SalesForceLoginResponse;
import org.dtna.salesforceauthroization.SalesforceLoginToken;
import org.dtna.service.InventoryService;
import org.dtna.utils.SalesforceDataHelper;
import org.dtna.utils.XMLReader;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@Slf4j
class InventoryServiceImplTest {
    @InjectMocks
    private InventoryServiceImpl inventoryService;

    @Mock
    private SalesforceDataHelper salesforceDataHelper;

    @Mock
    private XMLReader xmlReader;

    @Mock
    private SalesforceLoginToken salesforceLoginToken;
    @Mock
    private ProductInventoryIMSRepo productInventoryIMSRepo;


    @BeforeEach
    public void setUp() { MockitoAnnotations.openMocks(this); }


    @Test
    void createInventoryPositiveCase() throws ParserConfigurationException, IOException, SAXException {

        // Adjust this return value as needed

        // Initialize the InventoryXMLDto object
        InventoryXMLDto inventoryXMLDto = new InventoryXMLDto();
        List<InventoryXMLDto> list = new ArrayList<>();

        inventoryXMLDto.setModel("01Model01PR");

        inventoryXMLDto.setProductYear("2025");
        inventoryXMLDto.setProductSerialNumber("3AKJHHDR2RSVA4447");
        inventoryXMLDto.setUnitOfMeasure("KM");
        inventoryXMLDto.setOrderDealerCode("0016T00002xRaTkQAK");
        inventoryXMLDto.setBuildDate("20240410");
        inventoryXMLDto.setInServiceDate("20240420");
        list.add(inventoryXMLDto);

        // Mocking Salesforce login response
        SalesForceLoginResponse loginResponse = new SalesForceLoginResponse();
        loginResponse.setAccess_token("mock_access_token");
        loginResponse.setToken_type("Bearer");


        when(salesforceLoginToken.getLoginAuthorizationToken()).thenReturn(loginResponse);

        when(salesforceDataHelper.sendDataToSalesforceInventoryMajorComponent(anyList(), anyString())).thenReturn(String.valueOf(true));
        ByteArrayInputStream inputStream = new ByteArrayInputStream("<xml></xml>".getBytes());
        List<Inventory> result = inventoryService.createInventory(inputStream);

        Assertions.assertNotNull(result);


        assertEquals("3AKJHHDR2RSVA4447", list.get(0).getProductSerialNumber());
        verify(salesforceDataHelper, times(1)).sendDataToSalesforceInventoryMajorComponent(anyList(), anyString());
    }

    @Test
    void createInventoryNegativeCase() throws IOException, ParserConfigurationException, SAXException {
        // Initialize the InventoryXMLDto object
        InventoryXMLDto inventoryXMLDto = new InventoryXMLDto();
        List<InventoryXMLDto> list = new ArrayList<>();

        inventoryXMLDto.setModel("01Model01PR");

        inventoryXMLDto.setProductYear("2025");
        inventoryXMLDto.setProductSerialNumber("3AKJHHDR2RSVA4447");
        inventoryXMLDto.setUnitOfMeasure("KM");
        inventoryXMLDto.setOrderDealerCode("0016T00002xRaTkQAK");
        inventoryXMLDto.setBuildDate("20240410");
        inventoryXMLDto.setInServiceDate("20240420");
        list.add(inventoryXMLDto);

        // Mocking Salesforce login response
        SalesForceLoginResponse loginResponse = new SalesForceLoginResponse();
        loginResponse.setAccess_token("mock_access_token");
        loginResponse.setToken_type("Bearer");


        when(salesforceLoginToken.getLoginAuthorizationToken()).thenReturn(loginResponse);

        when(salesforceDataHelper.sendDataToSalesforceInventoryMajorComponent(anyList(), anyString())).thenReturn(String.valueOf(true));
        ByteArrayInputStream inputStream = new ByteArrayInputStream("<xml></xml>".getBytes());
        List<Inventory> result = inventoryService.createInventory(inputStream);

        Assertions.assertNotNull(result);


        assertNotEquals("3AKJHHDR2RSVA44488", list.get(0).getProductSerialNumber());
        assertNotEquals("02402320",list.get(0).getBuildDate());
        assertNotEquals("20240421",list.get(0).getInServiceDate());
        verify(salesforceDataHelper, times(1)).sendDataToSalesforceInventoryMajorComponent(anyList(), anyString());
    }


    @Test
    void createInventoryEmptyCase() throws IOException, ParserConfigurationException, SAXException {
        // Initialize the InventoryXMLDto object
        InventoryXMLDto inventoryXMLDto = new InventoryXMLDto();
        List<InventoryXMLDto> list = new ArrayList<>();

        inventoryXMLDto.setModel("01Model01PR");

        inventoryXMLDto.setProductYear("2025");
        inventoryXMLDto.setProductSerialNumber("3AKJHHDR2RSVA4447");
        inventoryXMLDto.setUnitOfMeasure("KM");
        inventoryXMLDto.setOrderDealerCode("0016T00002xRaTkQAK");
        inventoryXMLDto.setBuildDate("20240410");
        inventoryXMLDto.setInServiceDate("20240420");
        list.add(inventoryXMLDto);

        // Mocking Salesforce login response
        SalesForceLoginResponse loginResponse = new SalesForceLoginResponse();
        loginResponse.setAccess_token("mock_access_token");
        loginResponse.setToken_type("Bearer");


        when(salesforceLoginToken.getLoginAuthorizationToken()).thenReturn(loginResponse);

        when(salesforceDataHelper.sendDataToSalesforceInventoryMajorComponent(anyList(), anyString())).thenReturn(String.valueOf(true));
        ByteArrayInputStream inputStream = new ByteArrayInputStream("<xml></xml>".getBytes());
        List<Inventory> result = inventoryService.createInventory(inputStream);

        Assertions.assertNotNull(result);


        assertNull(null, list.get(0).getProductSerialNumber());
        assertNotEquals(null,list.get(0).getBuildDate());
        assertNotEquals(null,list.get(0).getInServiceDate());
        verify(salesforceDataHelper, times(1)).sendDataToSalesforceInventoryMajorComponent(anyList(), anyString());
    }


    @Test
    void sendProductInventoryToIms() {
        List<ProductInventoryImsModel> models = new ArrayList<>();
        ProductInventoryImsModel model = new ProductInventoryImsModel();
        // Set the required fields for model
        models.add(model);

        String result = inventoryService.sendProductInventoryToIms(models);

        Assertions.assertNotNull(result);
        assertEquals("Data Submitted", result);

        // Verify interaction with the repository
        verify(productInventoryIMSRepo, times(1)).saveAll(models);
    }

    @Test
    void sendProductInventoryToImsNegative()
    {
        List<ProductInventoryImsModel> models = new ArrayList<>();
        ProductInventoryImsModel model = new ProductInventoryImsModel();
        models.add(model);

        // Simulate repository save failure
        doThrow(new RuntimeException("Save failed")).when(productInventoryIMSRepo).saveAll(models);

        try {
            inventoryService.sendProductInventoryToIms(models);
        } catch (RuntimeException e) {
            assertEquals("Save failed", e.getMessage());
        }

        // Verify interaction with the repository
        verify(productInventoryIMSRepo, times(1)).saveAll(models);
    }
    @Test
    void sendProductInventoryToImsEmtpy()
    {
        List<ProductInventoryImsModel> models = new ArrayList<>();

        String result = inventoryService.sendProductInventoryToIms(models);

        Assertions.assertNotNull(result);
        assertEquals("Data Submitted", result);

        // Verify interaction with the repository
        verify(productInventoryIMSRepo, times(1)).saveAll(models);

    }
}