package org.dtna.serviceimpl;

import org.dtna.dto.major_component.subdtos.InventoryMC;
import org.dtna.dto.product_inventory.subdtos.InventoryXMLDto;
import org.dtna.model.MajorComponentInventoryIMSModel;
import org.dtna.repository.MajorComponentInventoryIMSRepo;
import org.dtna.salesforceauthroization.SalesForceLoginResponse;
import org.dtna.salesforceauthroization.SalesforceLoginToken;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class MajorComponentServiceImplTest {

    @InjectMocks
    private MajorComponentServiceImpl majorComponentService;

    @Mock
    private XMLReader xmlReader;

    @Mock
    private SalesforceLoginToken salesforceLoginToken;

    @Mock
    private SalesforceDataHelper salesforceDataHelper;

    @Mock
    private MajorComponentInventoryIMSRepo majorComponentInventoryIMSRepo;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void processProductInventoryPositiveCase() throws ParserConfigurationException, IOException, SAXException {
        List<InventoryXMLDto> inventoryXMLDtoList = new ArrayList<>();
        InventoryXMLDto inventoryXMLDto = new InventoryXMLDto();
        inventoryXMLDto.setName("Test Component");
        inventoryXMLDto.setProductSerialNumber("SN12345");
        inventoryXMLDto.setProductType("Engine");
        inventoryXMLDto.setAxleRation("AR123");
        inventoryXMLDto.setModel("ModelX");
        inventoryXMLDto.setBusinessUnits("UnitA");
        inventoryXMLDto.setOrderDate("2023-01-01");
        inventoryXMLDto.setOrderingVocation("Voc1");
        inventoryXMLDto.setRegisteredVocation("Voc2");
        inventoryXMLDto.setInServiceDate("2023-02-01");
        inventoryXMLDto.setOrderDealerCode("Dealer123");
        inventoryXMLDto.setBuildDate("2023-03-01");
        inventoryXMLDto.setUnitOfMeasure("UOM");
        inventoryXMLDtoList.add(inventoryXMLDto);

        when(xmlReader.readXML(any(InputStream.class))).thenReturn(inventoryXMLDtoList);
        SalesForceLoginResponse loginResponse = new SalesForceLoginResponse();
        loginResponse.setAccess_token("mock_access_token");
        loginResponse.setToken_type("Bearer");
        when(salesforceLoginToken.getLoginAuthorizationToken()).thenReturn(loginResponse);

        ByteArrayInputStream inputStream = new ByteArrayInputStream("<xml></xml>".getBytes());
        List<InventoryMC> result = majorComponentService.processProductInventory(inputStream);

        Assertions.assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Test Component", result.get(0).getName());
        assertEquals("SN12345", result.get(0).getProductSerialNumber());

        // Verify interactions

        verify(salesforceDataHelper, times(1)).sendDataToSalesforceMajorComponent(anyList(), anyString());
    }


    @Test
    void processProductInventoryNegativeCase() throws ParserConfigurationException, IOException, SAXException {
        when(xmlReader.readXML(any(InputStream.class))).thenThrow(new IOException("Failed to read XML"));

        ByteArrayInputStream inputStream = new ByteArrayInputStream("<xml></xml>".getBytes());

        try {
            majorComponentService.processProductInventory(inputStream);
        } catch (IOException e) {
            assertEquals("Failed to read XML", e.getMessage());
        }

        // Verify no interactions with Salesforce
        verify(salesforceDataHelper, times(0)).sendDataToSalesforceMajorComponent(anyList(), anyString());
    }


    @Test
    void processProductInventoryEmptyCase() throws ParserConfigurationException, IOException, SAXException {
        when(xmlReader.readXML(any(InputStream.class))).thenReturn(new ArrayList<>());

        SalesForceLoginResponse loginResponse = new SalesForceLoginResponse();
        loginResponse.setAccess_token("mock_access_token");
        loginResponse.setToken_type("Bearer");
        when(salesforceLoginToken.getLoginAuthorizationToken()).thenReturn(loginResponse);

        ByteArrayInputStream inputStream = new ByteArrayInputStream("".getBytes());
        List<InventoryMC> result = majorComponentService.processProductInventory(inputStream);

        Assertions.assertNotNull(result);
        assertEquals(0, result.size());

        // Verify no interactions with Salesforce
        verify(salesforceDataHelper, times(0)).sendDataToSalesforceMajorComponent(anyList(), anyString());
    }


    @Test
    void sendMajorComponentInventoryDataToIMSPositiveCase() {
        List<MajorComponentInventoryIMSModel> models = new ArrayList<>();
        MajorComponentInventoryIMSModel model = new MajorComponentInventoryIMSModel();
        // Set the required fields for model
        models.add(model);

        String result = majorComponentService.sendMajorComponentInventoryDataToIMS(models);

        Assertions.assertNotNull(result);
        assertEquals("Data Submitted", result);

        // Verify interaction with the repository
        verify(majorComponentInventoryIMSRepo, times(1)).saveAll(models);
    }

    @Test
    void sendMajorComponentInventoryDataToIMSNegativeCase() {
        List<MajorComponentInventoryIMSModel> models = new ArrayList<>();
        MajorComponentInventoryIMSModel model = new MajorComponentInventoryIMSModel();
        models.add(model);

        // Simulate repository save failure
        doThrow(new RuntimeException("Save failed")).when(majorComponentInventoryIMSRepo).saveAll(models);

        try {
            majorComponentService.sendMajorComponentInventoryDataToIMS(models);
        } catch (RuntimeException e) {
            assertEquals("Save failed", e.getMessage());
        }

        // Verify interaction with the repository
        verify(majorComponentInventoryIMSRepo, times(1)).saveAll(models);
    }

    @Test
    void sendMajorComponentInventoryDataToIMSEmptyCase() {
        List<MajorComponentInventoryIMSModel> models = new ArrayList<>();

        String result = majorComponentService.sendMajorComponentInventoryDataToIMS(models);

        Assertions.assertNotNull(result);
        assertEquals("No Data to Submit", result);

        // Verify interaction with the repository
        verify(majorComponentInventoryIMSRepo, times(0)).saveAll(models);
    }




}
